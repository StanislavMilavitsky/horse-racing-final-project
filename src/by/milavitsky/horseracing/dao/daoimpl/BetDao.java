package by.milavitsky.horseracing.dao.daoimpl;

import by.milavitsky.horseracing.dao.daoabstract.BetDaoAbstract;
import by.milavitsky.horseracing.dao.pool.ConnectionManager;

import by.milavitsky.horseracing.dao.pool.ProxyConnection;
import by.milavitsky.horseracing.entity.Bet;
import by.milavitsky.horseracing.entity.Horse;
import by.milavitsky.horseracing.entity.Race;
import by.milavitsky.horseracing.entity.enums.BetType;
import by.milavitsky.horseracing.entity.enums.TotalResultEnum;
import by.milavitsky.horseracing.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BetDao extends BetDaoAbstract {
    private static final Logger logger = LogManager.getLogger(BetDao.class);

    private static final String SHOW_BET_BY_USER_SQL =
            "SELECT b.id,b.time,r.hippodrome,b.amount_bet,b.ratio,b.horse_id,h.name, bt.name, b.races_id, b.transfer_status, b.total_result" +
                    " FROM bets b " +
                    " INNER JOIN races r ON b.races_id = r.id" +
                    " INNER JOIN bets_type bt ON b.bets_type_id = bt.id" +
                    " INNER JOIN horses h ON b.horse_id = h.id" +
                    " WHERE b.user_id = ? ORDER BY r.time";

    private static final String ADD_BET_SQL = "INSERT INTO bets (amount_bet, ratio, races_id, transfer_status, time, user_id, horse_id, bets_type_id) " +
            "VALUES (?,?,?,?,CURRENT_TIMESTAMP,?,?,?)";

    private static final String DELETE_RACE_BET_SQL = "DELETE FROM bets WHERE races_id=?";

    public static final String SELECT_BETS_BY_RACE_SQL = "SELECT b.id, b.amount_bet, b.ratio," +
            " b.time, b.user_id, b.horse_id, bt.name FROM bets b" +
            " INNER JOIN bets_type bt ON b.bets_type_id" +
            " WHERE races_id = ?;";

    private static final String UPDATE_TOTAL_RESULT_SQL = "UPDATE bets SET total_result=? WHERE id=?";

    private BetDao() {
    }

    @Override
    public List<Bet> findByUser(Long userId) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SHOW_BET_BY_USER_SQL)) {
            preparedStatement.setLong(1, userId);
            var resultSet = preparedStatement.executeQuery();
            Bet bet;
            List<Bet> bets = new ArrayList<>();
            while (resultSet.next()) {
                Horse horse = new Horse();
                Race race = new Race();
                horse.setName(resultSet.getString("h.name"));
                race.setHippodrome(resultSet.getString("r.hippodrome"));
                bet = new Bet();
                        bet.setId(resultSet.getLong("b.id"));
                        bet.setAmountBet(resultSet.getBigDecimal("b.amount_bet")); //todo
                        bet.setRatio(resultSet.getBigDecimal("b.ratio"));
                        bet.setRacesId(resultSet.getLong("b.races_id"));
                        bet.setBetType(BetType.valueOf(resultSet.getString("bt.name").toUpperCase()));
                        bet.setDate(resultSet.getObject("b.time", Timestamp.class).toLocalDateTime());
                        bet.setUserId(userId);
                        bet.setHorseId(resultSet.getLong("b.horse_id"));
                        bet.setHorse(horse);
                        bet.setRace(race);
                        bet.setTransferStatus(resultSet.getString("b.transfer_status"));
                        bet.setResultStatus(TotalResultEnum.valueOf(resultSet.getString("b.total_result").toUpperCase()));
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
            Bet bet = new Bet();
                    bet.setId(resultSet.getLong("id"));
                    bet.setAmountBet(resultSet.getBigDecimal("amount_bet"));
                    bet.setRatio(resultSet.getBigDecimal("ratio"));
                    bet.setRacesId(raceId);
                    bet.setDate(resultSet.getObject("time", Timestamp.class).toLocalDateTime());
                    bet.setUserId(resultSet.getLong("user_id"));
                    bet.setHorseId(resultSet.getLong("horse_id"));
                    bet.setBetType(BetType.valueOf(resultSet.getString("bt.name").toUpperCase()));


            bets.add(bet);
        }
        return bets;
    }

    @Override
    public boolean deleteByRace(ProxyConnection connection, Long raceId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_RACE_BET_SQL);
        statement.setLong(1, raceId);
        int rowEffected = statement.executeUpdate();
        return rowEffected > 0;
    }

    @Override
    public Optional<Bet> create(ProxyConnection connection, Bet bet) throws DaoException {
        try (var preparedStatement = connection.prepareStatement(ADD_BET_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setBigDecimal(1, bet.getAmountBet());
            preparedStatement.setBigDecimal(2, bet.getRatio());
            preparedStatement.setLong(3, bet.getRacesId());
            preparedStatement.setString(4, bet.getTransferStatus());
            preparedStatement.setLong(5, bet.getUserId());
            preparedStatement.setLong(6, bet.getHorseId());
            preparedStatement.setLong(7, bet.getBetType().ordinal() + 1L);

            int rowsEffected  = preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                bet.setId(generatedKeys.getLong(1));
            }

            return rowsEffected  > 0 ? Optional.of(bet) : Optional.empty();
        } catch (SQLException e) {
            logger.fatal("Cant create bet!", e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean updateTotalResult(ProxyConnection connection, TotalResultEnum result, Long betId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_TOTAL_RESULT_SQL);
        statement.setString(1, result.name().toLowerCase());
        statement.setLong(2, betId);
        int rowsEffected = statement.executeUpdate();
        return rowsEffected > 0;
    }


    private static class BetDaoHolder {
        private static final BetDao HOLDER_INSTANCE = new BetDao();
    }

    public static BetDaoAbstract getInstance() {
        return BetDaoHolder.HOLDER_INSTANCE;
    }
}
