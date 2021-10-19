package by.milavitsky.horseracing.dao.daoabstract;

import by.milavitsky.horseracing.dao.pool.ProxyConnection;
import by.milavitsky.horseracing.entity.enums.Ratio;
import by.milavitsky.horseracing.exception.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class RatioDaoAbstract implements Dao<Ratio, Long> {

    public abstract boolean setRatios(Set<Ratio> ratioSet) throws DaoException;

    public abstract List<Ratio> findRatio(Long raceId) throws DaoException;

    public abstract boolean deleteByRace(ProxyConnection connection, long raceId) throws SQLException;

    @Override
    public final Optional<Ratio> create(Ratio ratio) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Ratio> read(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Ratio> update(Ratio ratio) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
