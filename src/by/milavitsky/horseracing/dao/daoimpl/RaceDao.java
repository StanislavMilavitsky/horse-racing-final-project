package by.milavitsky.horseracing.dao.daoimpl;


import by.milavitsky.horseracing.dao.daoabstract.RaceDaoAbstract;
import by.milavitsky.horseracing.dao.pool.ConnectionManager;
import by.milavitsky.horseracing.dao.pool.ProxyConnection;
import by.milavitsky.horseracing.entity.Race;
import by.milavitsky.horseracing.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class RaceDao extends RaceDaoAbstract {

    private static final Logger logger = LogManager.getLogger(BetDao.class);
    private RaceDao(){
    }

    private static final String SHOW_ALL_RACES_ACTIVE_SQL = "SELECT id, hippodrome, time FROM races " +
            "WHERE time > CURRENT_TIMESTAMP ORDER BY time limit ? offset ?;";;


    private static final String SELECT_RACE_SQL = "SELECT r.hippodrome,r.time,COUNT(b.id),SUM(b.amount_bet) FROM races r" +
            " LEFT JOIN bets b ON r.id = b.races_id WHERE r.id=?;";

    public static final String SHOW_ALL_RACES_SQL = "SELECT id, hippodrome, time FROM races " +
            "ORDER BY time limit ? offset ?;";

    private static final String ADD_RACE_RESULT_SQL = "UPDATE races_has_horses SET place = ? WHERE races_id = ? AND horses_id = ?";

    public static final String ADD_RACE_SQL = "INSERT INTO races (time, hippodrome) VALUES (?, ?);";

    public static final String ADD_HORSE_RACE_SQL = "INSERT INTO races_has_horses (races_id, horses_id) VALUES (?, ?);";

    public static final String RACE_HORSES_BY_ID_SQL = "SELECT r.hippodrome, rh.horses_id FROM races r INNER JOIN races_has_horses rh WHERE r.id = ?";

    public static final String COUNT_ACTUAL_RACES_SQL = "SELECT count(id) FROM races WHERE time > CURRENT_TIMESTAMP;";

    private static final String COUNT_ALL_RACES_SQL = "SELECT count(id) FROM races;";

    private static final String DELETE_RACE_SQL = "DELETE FROM races WHERE id = ?;";

    private static final String DELETE_RACE_HORSES_SQL = "DELETE FROM races_has_horses WHERE races_id = ?;";

    @Override
    public List<Race> findActive(int limit, int offset) throws DaoException {
        return findAll(SHOW_ALL_RACES_ACTIVE_SQL, limit, offset);
    }

    @Override
    public List<Race> findAll(int limit, int offset) throws DaoException {
        return findAll(SHOW_ALL_RACES_SQL, limit, offset);
    }

    @Override
    public Optional<Race> addRace(Race race) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(ADD_RACE_SQL, Statement.RETURN_GENERATED_KEYS);
             var statement = connection.prepareStatement(ADD_HORSE_RACE_SQL)){
            preparedStatement.setString(1, race.getDate().toString());
            preparedStatement.setString(2, race.getHippodrome());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                race.setId(generatedKeys.getLong(1));
            }
                for (long horse : race.getHorse()) {//todo
                    statement.setLong(1, race.getId());
                    statement.setLong(2, horse);
                    statement.executeUpdate();
                }


            return Optional.of(race);
        } catch (SQLException e) {
            logger.error("Create race exception!", e);
            throw new DaoException("Create race exception!", e);
        }
    }

    @Override
    public boolean deleteRace(ProxyConnection connection, Long id) throws SQLException {
            var preparedStatement = connection.prepareStatement(DELETE_RACE_SQL);
            var preparedStatementHorse = connection.prepareStatement(DELETE_RACE_HORSES_SQL);
            preparedStatementHorse.setLong(1, id);
            preparedStatement.setLong(1, id);
            int rowsEffected = preparedStatementHorse.executeUpdate();
            rowsEffected = rowsEffected + preparedStatement.executeUpdate();
            return rowsEffected > 0;

    }

    @Override
    public long countActual() throws DaoException {
        return count(COUNT_ACTUAL_RACES_SQL);
    }

    @Override
    public long countAll() throws DaoException {
        return count(COUNT_ALL_RACES_SQL);
    }

    @Override
    public Optional<Race> read(Long id) throws DaoException {
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(SELECT_RACE_SQL);
            var statementHorse = connection.prepareStatement(RACE_HORSES_BY_ID_SQL)){
            statement.setLong(1, id);
            statementHorse.setLong(1, id);
            Set<Long> horses = new HashSet<>();
            var resultSet = statement.executeQuery();
            var resultSetHorse = statementHorse.executeQuery();
            while (resultSetHorse.next()) {
                horses.add(resultSetHorse.getLong("horses_id"));
            }
            Race race = new Race();
            while (resultSet.next()) {
                race.setId(id);
                race.setHippodrome(resultSet.getString("hippodrome"));
                race.setDate(resultSet.getTimestamp("time").toLocalDateTime());
                race.setBetCount(resultSet.getLong("COUNT(b.id)"));
                race.setBetSum(resultSet.getBigDecimal("SUM(b.amount_bet)"));
                race.setHorse(horses);
            }
            return Optional.of(race);
        } catch (SQLException e) {
            logger.error("Read race exception!", e);
            throw new DaoException("Read race exception!", e);
        }
    }

    @Override
    public boolean addRaceResult(ProxyConnection connection, Map<Integer, Long> resultMap, Long raceId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(ADD_RACE_RESULT_SQL);
        statement.setLong(2, raceId);
        for (int i = 1; i <= resultMap.size(); i++) {
            if (resultMap.get(i) == 0L) {
                statement.setNull(i + 1, Types.NULL);
            }
            statement.setLong(1, resultMap.get(i));
            statement.setLong(3 , i);
        }
        int rowsEffected = statement.executeUpdate();
        return rowsEffected > 0;
    }

    private List<Race> findAll(String sql, int limit, int offset) throws DaoException {
        List<Race> races = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql)){
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
               /*
               var statementHorse = connection.prepareStatement(SELECT_HORSE))
               statementHorse.setLong(1, resultSet.getLong("id"));
                var resultSetHorse = statementHorse.executeQuery();
                Set<Long> horse = new HashSet<>();
                while (resultSetHorse.next()) {
                    horse.add(resultSetHorse.getLong("horses_id"));
                }*/
                Long id = resultSet.getLong("id");
                String hippodrome = resultSet.getString("hippodrome");
                LocalDateTime date = resultSet.getTimestamp("time").toLocalDateTime();

                races.add(new Race(id, hippodrome, date));
            }
            return races;
        } catch (SQLException e) {
            logger.error("Show all races exception!", e);
            throw new DaoException("Show all races exception!", e);

        }
    }

    private long count(String sql) throws DaoException {
        long count = -1;
        try (var connection = ConnectionManager.get();
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getLong("count(id)");
            }
            return count;
        } catch (SQLException e) {
            logger.error("Count races exception!", e);
            throw new DaoException("Count races exception!", e);
        }
    }

    private static class RaceDaoHolder{
        private static final RaceDao HOLDER_INSTANCE = new RaceDao();
    }

    public static RaceDao getInstance() {
        return RaceDaoHolder.HOLDER_INSTANCE;
    }
}