package by.milavitsky.horse_racing.dao.dao_abstract;

import by.milavitsky.horse_racing.dao.pool.ProxyConnection;
import by.milavitsky.horse_racing.entity.Result;
import by.milavitsky.horse_racing.entity.enums.TotalResultEnum;


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
