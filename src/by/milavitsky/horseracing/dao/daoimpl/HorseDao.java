package by.milavitsky.horseracing.dao.daoimpl;

import by.milavitsky.horseracing.dao.daoabstract.HorseDaoAbstract;
import by.milavitsky.horseracing.dao.pool.ConnectionManager;
import by.milavitsky.horseracing.dao.pool.ProxyConnection;
import by.milavitsky.horseracing.entity.Horse;
import by.milavitsky.horseracing.entity.enumentity.SexEnum;
import by.milavitsky.horseracing.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HorseDao extends HorseDaoAbstract {
    private static final Logger logger = LogManager.getLogger(BetDao.class);
    private static final String FIND_ALL_HORSES_SQL = "SELECT id, name, sex, weight, breed, age, status, wins," +
            " participation, jockey FROM horses";
    private static final String FIND_HORSE_BY_RACE = "SELECT id, name, sex, weight, breed, age, status, wins, participation," +
            " jockey, place  FROM horses INNER JOIN races_has_horses rhh ON horses.id = rhh.horses_id WHERE rhh.races_id = ?;";
    private static final String ADD_WIN_HORSE = "UPDATE horses SET wins  = wins + 1 WHERE  id = ?;";
    private static final String ADD_PARTICIPATION_HORSE = "UPDATE horses SET participation = participation + 1 WHERE id = ?;" ;

    private HorseDao() {
    }

    @Override
    public List<Horse> findAll() throws DaoException {
        List<Horse> horses = new ArrayList<>();

        try (var connection = ConnectionManager.get();
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(FIND_ALL_HORSES_SQL);
            while (resultSet.next()) {
                horses.add(createHorse(resultSet));
            }
            return horses;
        } catch (SQLException e) {
            logger.error("Find all horses fail!", e);
            throw new DaoException("Find all horses fail!", e);

        }
    }

    @Override
    public Set<Horse> findByRace(Long raceId) throws DaoException {
        Set<Horse> horses = new HashSet<>();
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_HORSE_BY_RACE)) {
            statement.setLong(1, raceId);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                horses.add(createHorse(resultSet));
            }
            return horses;
        } catch (SQLException e) {
            logger.error("Show horses by race fail!", e);
            throw new DaoException("Show horses by race fail!", e);
        }
    }

    @Override
    public boolean updateParticipation(ProxyConnection connection, Map<Integer, Long>  resultMap) throws SQLException {
        var statementWin = connection.prepareStatement(ADD_WIN_HORSE);
        var statementParticipation = connection.prepareStatement(ADD_PARTICIPATION_HORSE);
        statementWin.setLong(1, resultMap.get(1));
        int executeUpdate = 0;
        for (int i = 1; i < resultMap.size() ; i++) {
            statementParticipation.setLong(1, resultMap.get(i));
             executeUpdate = statementParticipation.executeUpdate();
        }
        int rowsEffected = statementParticipation.executeUpdate();
        return rowsEffected + executeUpdate > 0;

    }

    public static HorseDao getInstance() {
        return HorseDaoHolder.HOLDER_INSTANCE;
    }

    private static class HorseDaoHolder {
        private static final HorseDao HOLDER_INSTANCE = new HorseDao();

    }

    private Horse createHorse(ResultSet set) throws SQLException {
        Long id = set.getLong("id");
        String name = set.getString("name");
        SexEnum sex = SexEnum.valueOf(set.getString("sex").toUpperCase());
        Double weight = set.getDouble("weight");
        String breed = set.getString("breed");
        Integer age = set.getInt("age");
        String status = set.getString("status");
        Integer win = set.getInt("wins");
        Integer participation = set.getInt("participation");
        String jockey = set.getString("jockey");
        Integer percentageOfWin = (int) (Double.valueOf(win) / Double.valueOf(participation) * 100);
        return new Horse(id, name, sex, weight, breed, age, status, win, participation, jockey,percentageOfWin);
    }
}
