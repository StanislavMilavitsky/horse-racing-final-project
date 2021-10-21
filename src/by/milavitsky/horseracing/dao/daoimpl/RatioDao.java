package by.milavitsky.horseracing.dao.daoimpl;

import by.milavitsky.horseracing.dao.daoabstract.RatioDaoAbstract;
import by.milavitsky.horseracing.dao.pool.ConnectionManager;
import by.milavitsky.horseracing.dao.pool.ProxyConnection;
import by.milavitsky.horseracing.entity.enums.Ratio;

import by.milavitsky.horseracing.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RatioDao extends RatioDaoAbstract {
    private static final Logger logger = LogManager.getLogger(RatioDao.class);

    private static final String ADD_RATIO_SQL =
            "INSERT INTO ratio(race_id, horse_id, type_id, ratio) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE ratio = ? ";
    private static final String FIND_RATIO_LIST_SQL =
            "SELECT r.horse_id,r.type_id,r.ratio FROM ratio r WHERE r.race_id=?";
    private static final String DELETE_RACE_RATIO_SQL = "DELETE FROM ratio WHERE race_id=?";

    private RatioDao() {
    }

    @Override
    public boolean setRatios(Set<Ratio> ratioSet) throws DaoException {
        try(var proxyConnection = ConnectionManager.get();
            var statement = proxyConnection.prepareStatement(ADD_RATIO_SQL)) {
            for (Ratio ratio : ratioSet) {
                statement.setLong(1, ratio.getRaceId());
                statement.setLong(2, ratio.getHorseId());
                statement.setLong(3, ratio.getTypeId());
                statement.setBigDecimal(4, ratio.getRatio());
                statement.setBigDecimal(5, ratio.getRatio());
                statement.addBatch();
            }
            int[] executeBatch = statement.executeBatch();
            return executeBatch.length > 0;
        } catch (SQLException e) {
            logger.error("Set ratios error!", e);
            throw new DaoException("Set ratios error!", e);
        }
    }

    @Override
    public List<Ratio> findRatio(Long raceId) throws DaoException {
        try(var proxyConnection = ConnectionManager.get();
            var statement = proxyConnection.prepareStatement(FIND_RATIO_LIST_SQL)) {
            List<Ratio> ratioList = new ArrayList<>();
            statement.setLong(1, raceId);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long horseId = resultSet.getLong("r.horse_id");
                Long typeId = resultSet.getLong("r.type_id");
                BigDecimal ratio = resultSet.getBigDecimal("r.ratio");
                ratioList.add(new Ratio(raceId, horseId, typeId, ratio));
            }
            return ratioList;
        } catch (SQLException e) {
            logger.error("Find ratios error!", e);
            throw new DaoException("Find ratios error!", e);
        }
    }

    @Override
    public boolean deleteByRace(ProxyConnection connection, long raceId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_RACE_RATIO_SQL);
        statement.setLong(1, raceId);
        var rowsEffected = statement.executeUpdate();
        return rowsEffected > 0;
    }

    private static class RatioDaoHolder {
        private static final RatioDao HOLDER_INSTANCE = new RatioDao();
    }

    public static RatioDao getInstance() {
         return RatioDaoHolder.HOLDER_INSTANCE;
    }
}
