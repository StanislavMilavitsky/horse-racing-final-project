package by.milavitsky.horse_racing.dao.dao_entity;

import by.milavitsky.horse_racing.dao.dao_abstract.HorseDaoAbstract;
import by.milavitsky.horse_racing.dao.pool.ConnectionManager;
import by.milavitsky.horse_racing.entity.Horse;
import by.milavitsky.horse_racing.entity.enums.SexEnum;
import by.milavitsky.horse_racing.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HorseDao extends HorseDaoAbstract {
    private static final Logger logger = LogManager.getLogger(BetDao.class);
    private static final String FIND_ALL_HORSES_SQL = "SELECT id, name, sex, weight, breed, age, status, perсentage_of_wins," +
            " participation, jockey FROM horses";
    private static final String FIND_HORSE_BY_RACE = "SELECT name, sex, weight, breed, age, status, perсentage_of_wins, participation," +
            " jockey, horses_id, rases_id, place  FROM horses INNER JOIN races_has_horses rhh ON horses.id = rhh.horses_id WHERE rhh.rases_id = ?;";

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


    private static class HorseDaoHolder {
        private static final HorseDao HOLDER_INSTANCE = new HorseDao();
    }

    public static HorseDao getInstance() {
        return HorseDaoHolder.HOLDER_INSTANCE;
    }

    private Horse createHorse(ResultSet set) throws SQLException {
        Long id = set.getLong("id");
        String name = set.getString("name");
        SexEnum sex = SexEnum.valueOf(set.getString("sex"));
        Double weight = set.getDouble("weight");
        String breed = set.getString("breed");
        Integer age = set.getInt("age");
        String status = set.getString("status");
        Double perсentageOfWins = set.getDouble("perсentage_of_wins");
        Integer participation = set.getInt("participation");
        String jockey = set.getString("jockey");
        return new Horse(id, name, sex, weight, breed, age, status, perсentageOfWins, participation, jockey);
    }
}