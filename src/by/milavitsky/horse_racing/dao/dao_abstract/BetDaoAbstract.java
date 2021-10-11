package by.milavitsky.horse_racing.dao.dao_abstract;

import by.milavitsky.horse_racing.dao.pool.ProxyConnection;
import by.milavitsky.horse_racing.entity.Bet;
import by.milavitsky.horse_racing.exception.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class BetDaoAbstract implements Dao<Bet, Long> {

    public abstract List<Bet> findByRace(ProxyConnection connection, Long raceId) throws SQLException;

    public abstract List<Bet> findByRaceRatio(Long raceId) throws DaoException;

    public abstract boolean deleteByRace(ProxyConnection connection, Long raceId) throws SQLException;

    public abstract  Bet create(ProxyConnection connection, Bet bet) throws DaoException;

    public abstract List<Bet> findByUser(Long userId) throws DaoException;

    public abstract boolean setRatios(Set<Bet> ratioSet) throws DaoException;

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
    public final Optional<Bet> create(Bet a) {
        throw new UnsupportedOperationException();
    }
}
