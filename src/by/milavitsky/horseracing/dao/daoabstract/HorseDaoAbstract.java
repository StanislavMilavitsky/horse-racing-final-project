package by.milavitsky.horseracing.dao.daoabstract;

import by.milavitsky.horseracing.dao.Dao;
import by.milavitsky.horseracing.entity.Horse;
import by.milavitsky.horseracing.exception.DaoException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class HorseDaoAbstract implements Dao<Horse, Long> {
    /**
     * Find all active horses in database
     * @return list of horses
     * @throws DaoException
     */
    public abstract List<Horse> findAll() throws DaoException;

    /**
     * Find horses by race
     * @param raceId
     * @return set of horses
     * @throws DaoException
     */
    public abstract Set<Horse> findByRace(Long raceId) throws DaoException;

    @Override
    public final Optional<Horse> create(Horse horse) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Horse>  read(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Horse>  update(Horse horse) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
