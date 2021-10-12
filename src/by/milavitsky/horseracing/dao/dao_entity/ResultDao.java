package by.milavitsky.horseracing.dao.dao_entity;

import by.milavitsky.horseracing.dao.dao_abstract.ResultDaoAbstract;
import by.milavitsky.horseracing.dao.pool.ProxyConnection;
import by.milavitsky.horseracing.entity.Result;

import by.milavitsky.horseracing.entity.enums.TotalResultEnum;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class ResultDao extends ResultDaoAbstract {

    private static final Logger logger = LogManager.getLogger(ResultDao.class);

    private ResultDao() {
    }

    private static final String UPDATE_TOTAL_RESULT_SQL = "UPDATE results SET total_id=? WHERE bets_id=?";

    public static final String FIND_BY_BETS_SQL = "SELECT wining_amount, users_id, total_id FROM results  WHERE bets_id = ?;";

    public static final String CREAT_RESULT_SQL = "INSERT INTO results (users_id, wining_amount, bets_id, total) VALUES (?, ?, ?, ?);";

    public static final String DELETE_RACE_SQL = "DELETE FROM results WHERE bets_id = (SELECT bets_id FROM bets WHERE races_id = ?);";

    @Override
    public boolean deleteRace(ProxyConnection connection, Long id) throws SQLException {
        var statement = connection.prepareStatement(DELETE_RACE_SQL);
        statement.setLong(1, id);

        var rowsEffected = statement.executeUpdate();
        return rowsEffected > 0;
    }

    @Override
    public Optional<Result> findByBets(ProxyConnection connection, Long id) throws SQLException {
        var preparedStatement = connection.prepareStatement(FIND_BY_BETS_SQL, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setLong(1, id);
        var resultSet = preparedStatement.executeQuery();
        var generatedKeys = preparedStatement.getGeneratedKeys();
        Result result = null;
        while (resultSet.next()) {//todo
            result = new Result();
            result.setUserId(resultSet.getLong("users_id"));
            result.setWiningAmount(resultSet.getBigDecimal("wining_amount"));
            result.setBetsId(resultSet.getLong("bets_id"));
            result.setTotalResult(TotalResultEnum.valueOf(resultSet.getString("total").toUpperCase()));

            if (generatedKeys.next()) {
                result.setId(generatedKeys.getLong("id"));
            }
        }

        return Optional.ofNullable(result);//todo
    }

    @Override
    public boolean updateTotalResult(ProxyConnection connection, TotalResultEnum result, Long betId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_TOTAL_RESULT_SQL);
        statement.setInt(1, result.ordinal());
        statement.setLong(2, betId);
        int rowsEffected = statement.executeUpdate();
        return rowsEffected > 0;
    }

    private static class ResultDaoHolder {
        private static final ResultDao HOLDER_INSTANCE = new ResultDao();
    }

    public static ResultDao getInstance() {
        return ResultDaoHolder.HOLDER_INSTANCE;
    }
}
