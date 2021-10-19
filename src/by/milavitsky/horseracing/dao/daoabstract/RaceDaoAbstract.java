package by.milavitsky.horseracing.dao.daoabstract;

import by.milavitsky.horseracing.dao.pool.ProxyConnection;
import by.milavitsky.horseracing.entity.Race;
import by.milavitsky.horseracing.exception.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public abstract class RaceDaoAbstract implements Dao<Race, Long> {


    public abstract List<Race> findActive(int limit, int offset) throws DaoException;

    public abstract List<Race> findAll(int limit, int offset) throws DaoException;

    public abstract Optional<Race> addRace(Race race) throws DaoException;

    public abstract boolean deleteRace(ProxyConnection connection, Long id) throws SQLException;

    public abstract long countActual() throws DaoException;

    public abstract  long countAll() throws DaoException;

    public abstract Optional<Race> read(Long id) throws DaoException;

    public abstract boolean addRaceResult(ProxyConnection connection, Map<Integer, Long> resultMap, Long raceId) throws SQLException;

    @Override
    public final Optional<Race> update(Race race) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Race> create(Race race) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(Long id) {
        throw new UnsupportedOperationException();
    }

}
