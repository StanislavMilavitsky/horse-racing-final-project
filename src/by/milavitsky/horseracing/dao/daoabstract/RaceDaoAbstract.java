package by.milavitsky.horseracing.dao.daoabstract;

import by.milavitsky.horseracing.dao.Dao;
import by.milavitsky.horseracing.dao.pool.ProxyConnection;
import by.milavitsky.horseracing.entity.Race;
import by.milavitsky.horseracing.exception.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public abstract class RaceDaoAbstract implements Dao<Race, Long> {

    /**
     * Find all active races that date > current date
     * @param limit
     * @param offset
     * @return list of races
     * @throws DaoException
     */
    public abstract List<Race> findActive(int limit, int offset) throws DaoException;

    /**
     * Find all races in database
     * @param limit
     * @param offset
     * @return list of races
     * @throws DaoException
     */
    public abstract List<Race> findAll(int limit, int offset) throws DaoException;

    /**
     * If race dont exist in database creat race
     * @param race
     * @return Created race
     * @throws DaoException
     */
    public abstract Optional<Race> addRace(Race race) throws DaoException;

    /**
     * Delete race from database
     * @param connection from Proxy
     * @param id
     * @return true if race exist in database
     * @throws SQLException
     */
    public abstract boolean deleteRace(ProxyConnection connection, Long id) throws SQLException;

    /**
     * Find all active races and sum them
     * @return sum of actual races
     * @throws DaoException
     */
    public abstract long countActual() throws DaoException;

    /**
     * Find all races and sum them
     * @returnsum of races
     * @throws DaoException
     */
    public abstract  long countAll() throws DaoException;

    /**
     * Find all information on race by race id
     * @param id the id
     * @return race
     * @throws DaoException
     */
    public abstract Optional<Race> read(Long id) throws DaoException;

    /**
     * Add race result in database
     * @param connection from Proxy
     * @param resultMap
     * @param raceId
     * @return true if result added
     * @throws SQLException
     */
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
