package by.milavitsky.horseracing.dao.daoabstract;

import by.milavitsky.horseracing.dao.Dao;
import by.milavitsky.horseracing.dao.pool.ProxyConnection;
import by.milavitsky.horseracing.entity.Bet;
import by.milavitsky.horseracing.entity.enumentity.TotalResultEnum;
import by.milavitsky.horseracing.exception.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class BetDaoAbstract implements Dao<Bet, Long> {
    /**
     *
     * @param connection from Proxy
     * @param raceId
     * @return List of bets from databases
     * @throws SQLException
     */
    public abstract List<Bet> findByRace(ProxyConnection connection, Long raceId) throws SQLException;

    /**
     * Delete race from database
     * @param connection from Proxy
     * @param raceId
     * @return boolean result of deleted
     * @throws SQLException
     */
    public abstract boolean deleteByRace(ProxyConnection connection, Long raceId) throws SQLException;

    /**
     * Creat bet in database
     * @param connection from Proxy
     * @param bet
     * @return if bet doesnt exist return bet
     * @throws DaoException
     */
    public abstract Optional<Bet> create(ProxyConnection connection, Bet bet) throws DaoException;

    /**
     * Find bets by user
     * @param userId
     * @return list of bets
     * @throws DaoException
     */
    public abstract List<Bet> findByUser(Long userId) throws DaoException;

    /**
     * Update total result of bet
     * @param connection from Proxy
     * @param result
     * @param betId
     * @return boolean result of update
     * @throws SQLException
     */
    public abstract boolean updateTotalResult(ProxyConnection connection, TotalResultEnum result, Long betId) throws SQLException;

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
    public final Optional<Bet> create(Bet bet) {
        throw new UnsupportedOperationException();
    }
}
