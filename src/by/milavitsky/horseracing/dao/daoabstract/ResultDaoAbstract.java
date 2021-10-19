package by.milavitsky.horseracing.dao.daoabstract;

import by.milavitsky.horseracing.dao.pool.ProxyConnection;
import by.milavitsky.horseracing.entity.Result;
import by.milavitsky.horseracing.entity.enums.TotalResultEnum;


import java.sql.SQLException;
import java.util.Optional;

public abstract class ResultDaoAbstract implements Dao<Result, Long> {


    public abstract boolean deleteRace(ProxyConnection connection, Long id) throws SQLException;

    public abstract Optional<Result> findByBets(ProxyConnection connection, Long id) throws SQLException;

    public abstract boolean updateTotalResult(ProxyConnection connection, TotalResultEnum result, Long betId) throws SQLException;

    @Override
    public final Optional<Result> read(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Result> update(Result result) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Result> create(Result result) {
        throw new UnsupportedOperationException();
    }
}
