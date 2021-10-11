package by.milavitsky.horse_racing.dao.dao_abstract;

import by.milavitsky.horse_racing.entity.Horse;
import by.milavitsky.horse_racing.exception.DaoException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class HorseDaoAbstract implements Dao<Horse, Long> {

    public abstract List<Horse> findAll() throws DaoException;

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
