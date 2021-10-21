package by.milavitsky.horseracing.dao.daoabstract;

import by.milavitsky.horseracing.dao.pool.ProxyConnection;
import by.milavitsky.horseracing.entity.Bet;
import by.milavitsky.horseracing.entity.enums.TotalResultEnum;
import by.milavitsky.horseracing.exception.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class BetDaoAbstract implements Dao<Bet, Long> {

    public abstract List<Bet> findByRace(ProxyConnection connection, Long raceId) throws SQLException;

    public abstract boolean deleteByRace(ProxyConnection connection, Long raceId) throws SQLException;

    public abstract Optional<Bet> create(ProxyConnection connection, Bet bet) throws DaoException;

    public abstract List<Bet> findByUser(Long userId) throws DaoException;

    @Override
    public final Optional<Bet> read(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Bet> update(Bet bet) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Bet> create(Bet bet) throws DaoException {
        throw new UnsupportedOperationException();
    }

    public abstract boolean updateTotalResult(ProxyConnection connection, TotalResultEnum result, Long betId) throws SQLException;
}
