package by.milavitsky.horse_racing.dao.dao_entity;

import by.milavitsky.horse_racing.dao.dao_abstract.BetDaoAbstract;
import by.milavitsky.horse_racing.dao.pool.ConnectionManager;

import by.milavitsky.horse_racing.dao.pool.ProxyConnection;
import by.milavitsky.horse_racing.entity.Bet;
import by.milavitsky.horse_racing.entity.Horse;
import by.milavitsky.horse_racing.entity.Race;
import by.milavitsky.horse_racing.entity.enums.BetType;
import by.milavitsky.horse_racing.entity.enums.TransferStatusEnum;
import by.milavitsky.horse_racing.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class BetDao extends BetDaoAbstract {
    private static final Logger logger = LogManager.getLogger(BetDao.class);

    private static final String SHOW_BET_BY_USER_SQL =
            "SELECT b.id,b.time,r.hippodrome,b.amount_bet,b.ratio,r.race_type,b.horse_id,h.name, bt.name" +
                    " FROM bets b " +
                    " INNER JOIN races r ON b.races_id = r.id" +
                    " INNER JOIN bets_type bt ON b.bets_type_id = bt.id" +
                    " INNER JOIN horses h ON b.horse_id = h.id" +
                    " WHERE b.user_id = ? ORDER BY r.time";

    private static final String ADD_BET_SQL = "INSERT INTO bets (amount_bet, ratio, races_id, transfer_status, time, user_id, horse_id, bets_type_id) " +
            "VALUES (?,?,?,?,CURRENT_TIMESTAMP,?,?,?)";

    private static final String DELETE_RACE_BET_SQL = "DELETE FROM bets WHERE races_id=?";

    public static final String SELECT_BETS_BY_RACE_SQL = "SELECT b.id, b.amount_bet, b.ratio, ts.name," +
            " b.time, b.user_id, b.horse_id, bt.name FROM bets b" +
            " INNER JOIN transfer_status ts ON b.transfer_status" +
            " INNER JOIN bets_type bt ON b.bets_type_id" +
            " WHERE races_id = ?;";
    private static final String FIND_RATIO_LIST_SQL = "SELECT b.horse_id, b.ratio, (SELECT bt.name WHERE b.bets_type_id = bt.id) FROM bets b " +
            "INNER JOIN bets_type bt WHERE races_id = ?;";

    private static final String ADD_RATIO_SQL = "INSERT INTO bets(races_id, horse_id, bets_type_id, ratio) VALUES (?, ?, ?, ?);";

    private BetDao() {
    }

    @Override
    public List<Bet> findByUser(Long userId) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SHOW_BET_BY_USER_SQL)) {
            preparedStatement.setLong(1, userId);
            var resultSet = preparedStatement.executeQuery();
            Bet bet = null;
            List<Bet> bets = null;
            while (resultSet.next()) {
                Horse horse = new Horse();
                Race race = new Race();
                horse.setName(resultSet.getString("h.name"));
                race.setHippodrome(resultSet.getString("r.hippodrome"));
                race.setRaceType(resultSet.getString("r.race_typ"));
                bet = new Bet(
                        resultSet.getLong("id"),
                        resultSet.getBigDecimal("amount_bet"), //todo
                        resultSet.getBigDecimal("ratio"),
                        resultSet.getLong("races_id"),
                        TransferStatusEnum.valueOf(resultSet.getString("ts.name")),
                        resultSet.getObject("time", Timestamp.class).toLocalDateTime(),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("horse_id"),
                        horse,
                        race,
                        BetType.valueOf(resultSet.getString("bt.name").toUpperCase())
                );
                bets.add(bet);
            }
            return bets;
        } catch (SQLException e) {
            logger.fatal("Show all bets exception", e);
            throw new DaoException("Show all bets exception", e);
        }
    }

    @Override
    public List<Bet> findByRace(ProxyConnection connection, Long raceId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_BETS_BY_RACE_SQL);
        statement.setLong(1, raceId);
        ResultSet resultSet = statement.executeQuery();
        List<Bet> bets = new ArrayList<>();
        while (resultSet.next()) {
            Bet bet = new Bet(
                    resultSet.getLong("id"),
                    resultSet.getBigDecimal("amount_bet"),
                    resultSet.getBigDecimal("ratio"),
                    raceId,
                    TransferStatusEnum.valueOf(resultSet.getString("ts.name")),
                    resultSet.getObject("time", Timestamp.class).toLocalDateTime(),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("horse_id"),
                    BetType.valueOf(resultSet.getString("bt.name").toUpperCase())

            );
            bets.add(bet);
        }
        return bets;
    }

    @Override
    public List<Bet> findByRaceRatio(Long raceId) throws DaoException {
        List<Bet> ratioList = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_RATIO_LIST_SQL)) {
            statement.setLong(1, raceId);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Bet bet = new Bet();
                bet.setHorseId(resultSet.getLong("horse_id"));
                bet.setBetType(BetType.valueOf(resultSet.getString("bets_type_id").toUpperCase()));
                bet.setRatio(resultSet.getBigDecimal("ratio"));
                ratioList.add(bet);
            }
            return ratioList;
        } catch (SQLException e) {
            logger.error("Find ratios error!", e);
            throw new DaoException("Find ratios error!", e);
        }
    }

    @Override
    public boolean deleteByRace(ProxyConnection connection, Long raceId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_RACE_BET_SQL);
        statement.setLong(1, raceId);
        int rowEffected = statement.executeUpdate();
        return rowEffected > 0;
    }

    @Override
    public Bet create(ProxyConnection connection, Bet bet) throws DaoException {
        try (var preparedStatement = connection.prepareStatement(ADD_BET_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setBigDecimal(1, bet.getAmountBet());
            preparedStatement.setBigDecimal(2, bet.getRatio());
            preparedStatement.setLong(3, bet.getRacesId());
            preparedStatement.setLong(4, bet.getTransferStatus().ordinal() + 1L);
            preparedStatement.setLong(5, bet.getUserId());
            preparedStatement.setLong(6, bet.getHorseId());
            preparedStatement.setLong(7, bet.getBetType().ordinal() + 1L);

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                bet.setId(generatedKeys.getLong("id"));
            }
            return bet;//todo
        } catch (SQLException e) {
            logger.fatal("Cant create bet!", e);
            throw new DaoException(e);
        }
    }


    @Override
    public boolean setRatios(Set<Bet> betSet) throws DaoException {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(ADD_RATIO_SQL)) {
            for (Bet bet : betSet) {
                statement.setLong(1, bet.getRacesId());
                statement.setLong(2, bet.getHorseId());
                statement.setLong(3, bet.getBetTypeId());
                statement.setBigDecimal(4, bet.getRatio());
                statement.addBatch();
            }
            int[] executeBatch = statement.executeBatch();
            return executeBatch.length > 0;
        } catch (SQLException e) {
            logger.error("Set ratios error!", e);
            throw new DaoException("Set ratios error!", e);
        }
    }

    private static class BetDaoHolder {
        private static final BetDao HOLDER_INSTANCE = new BetDao();
    }

    public static BetDaoAbstract getInstance() {
        return BetDaoHolder.HOLDER_INSTANCE;
    }
}
