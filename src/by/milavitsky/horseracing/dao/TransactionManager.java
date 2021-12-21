package by.milavitsky.horseracing.dao;

import by.milavitsky.horseracing.dao.daoabstract.*;

import by.milavitsky.horseracing.dao.pool.ConnectionManager;
import by.milavitsky.horseracing.dao.pool.ProxyConnection;
import by.milavitsky.horseracing.entity.Bet;
import by.milavitsky.horseracing.entity.enumentity.TotalResultEnum;
import by.milavitsky.horseracing.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TransactionManager {
    private static final Logger logger = LogManager.getLogger(TransactionManager.class);

    private final RaceDaoAbstract raceDao = (RaceDaoAbstract) DaoFactory.getInstance().getClass(RaceDaoAbstract.class);
    private final BetDaoAbstract betDao = (BetDaoAbstract) DaoFactory.getInstance().getClass(BetDaoAbstract.class);
    private final UserDaoAbstract userDao = (UserDaoAbstract) DaoFactory.getInstance().getClass(UserDaoAbstract.class);
    private final RatioDaoAbstract ratioDao = (RatioDaoAbstract) DaoFactory.getInstance().getClass(RatioDaoAbstract.class);
    private final HorseDaoAbstract horseDao = (HorseDaoAbstract) DaoFactory.getInstance().getClass(HorseDaoAbstract.class);

    private TransactionManager() {
    }

    /**
     * Enter result in database insert in other tables
     *
     * @param resultMap
     * @param raceId
     * @throws DaoException
     */
    public void enterResult(Map<Integer, Long> resultMap, Long raceId) throws DaoException {
        ProxyConnection connection = null;

        try {
            connection = ConnectionManager.get();
            connection.setAutoCommit(false);

            boolean isAddRaceResult = raceDao.addRaceResult(connection, resultMap, raceId);//todo пробелы, private методы
            if (!isAddRaceResult) {
                throw new SQLException("Race result not added!");
            }


            List<Bet> betByRace = betDao.findByRace(connection, raceId);
            for (Bet bet : betByRace) {
                BigDecimal cash = userDao.findCash(connection, bet.getUserId());
                boolean isWin = false;


                switch (bet.getBetType()) {
                    case WIN: {
                        if (bet.getHorseId().equals(resultMap.get(1))) {
                            isWin = true;
                        }
                    }
                    break;
                    case SHOW: {
                        if (bet.getHorseId().equals(resultMap.get(1)) ||
                                bet.getHorseId().equals(resultMap.get(2)) ||
                                bet.getHorseId().equals(resultMap.get(3))) {
                            isWin = true;
                        }
                    }
                    break;
                }

                BigDecimal newCash = cash;
                if (isWin) {

                    BigDecimal betWin = bet.getAmountBet().multiply(bet.getRatio());
                    newCash = cash.add(betWin);
                }
                boolean isUpdateCash = userDao.updateCash(connection, newCash, bet.getUserId());
                if (!isUpdateCash) {
                    throw new SQLException("User cash not update!");
                }
                TotalResultEnum result = isWin ? TotalResultEnum.WIN : TotalResultEnum.LOSE;
                boolean isUpdateResult = betDao.updateTotalResult(connection, result, bet.getId());
                if (!isUpdateResult) {
                    throw new SQLException("Total result not update!");
                }

            }

            boolean isUpdateHorseParticipation = horseDao.updateParticipation(connection, resultMap);
            if (!isUpdateHorseParticipation) {
                throw new SQLException("Win horse increment not update!");
            }
            connection.commit();

        } catch (SQLException e) {
            rollback(connection);
            logger.error("Total result not update!", e);
            throw new DaoException("Total result not update!", e);
        } finally {
            close(connection);
        }
    }

    /**
     * Add bet in database insert in other tables
     *
     * @param bet
     * @return bet if it is insert
     * @throws DaoException
     */
    public Optional<Bet> addBet(Bet bet) throws DaoException {
        ProxyConnection connection = null;

        try {
            connection = ConnectionManager.get();
            connection.setAutoCommit(false);
            BigDecimal cash = userDao.findCash(connection, bet.getUserId());
            if (bet.getAmountBet().compareTo(cash) > 0) {
                return Optional.empty();
            }
            Optional<Bet> betDB = betDao.create(connection, bet);
            if (betDB.isEmpty()) {
                return Optional.empty();
            }

            BigDecimal newCash = cash.subtract(bet.getAmountBet());
            boolean result = userDao.updateCash(connection, newCash, bet.getUserId());
            connection.commit();
            return result ? Optional.of(bet) : Optional.empty();

        } catch (SQLException e) {
            rollback(connection);
            logger.error("Add bet exception!", e);
            throw new DaoException("Add bet exception!", e);
        } finally {
            close(connection);
        }
    }

    /**
     * Delete race from databases affecting different tables
     *
     * @param id
     * @return boolean of result
     * @throws DaoException
     */
    public boolean deleteRace(Long id) throws DaoException {
        ProxyConnection connection = null;
        try {
            connection = ConnectionManager.get();
            connection.setAutoCommit(false);
            List<Bet> bets = betDao.findByRace(connection, id);
            for (Bet bet : bets) {
                if (bet.getResultStatus() == TotalResultEnum.LOSE || bet.getResultStatus() == TotalResultEnum.WIN) {
                    return false;
                }
            }
            for (Bet bet : bets) {
                BigDecimal userCash = userDao.findCash(connection, bet.getUserId());//
                BigDecimal newCash = userCash.add(bet.getAmountBet());
                userDao.updateCash(connection, newCash, bet.getUserId());
            }
            boolean ratioBoolean = ratioDao.deleteByRace(connection, id);
            boolean betBoolean = betDao.deleteByRace(connection, id);
            boolean raceBoolean = raceDao.deleteRace(connection, id);
            if (!betBoolean || !raceBoolean || !ratioBoolean) {
                return false;
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            rollback(connection);
            logger.error("Delete race exception!", e);
            throw new DaoException("Delete race exception!", e);
        } finally {
            close(connection);
        }
    }

    private void close(Connection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                logger.error("Connection close fail!");
            }
        }
    }

    /**
     * Rollback if commit failed
     *
     * @param connection from Proxy
     */
    private void rollback(ProxyConnection connection) {
        if (connection != null) {
            try {
                connection.rollback();
                logger.info("Connection rollback!");
            } catch (SQLException ex) {
                logger.error("Error while rollback!");
            }
            try {
                connection.setAutoCommit(true);
                logger.info("Set auto commit true!");
            } catch (SQLException ex) {
                logger.error("Error while set auto commit!");
            }
        }
    }

    private static class TransactionManagerHolder {
        private static final TransactionManager HOLDER_INSTANCE = new TransactionManager();
    }

    public static TransactionManager getInstance() {
        return TransactionManagerHolder.HOLDER_INSTANCE;
    }
}

