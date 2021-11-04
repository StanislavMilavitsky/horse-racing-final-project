package by.milavitsky.horseracing.dao.daoabstract;

import by.milavitsky.horseracing.dao.Dao;
import by.milavitsky.horseracing.dao.pool.ProxyConnection;
import by.milavitsky.horseracing.entity.User;
import by.milavitsky.horseracing.exception.DaoException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public abstract class UserDaoAbstract implements Dao<User, Long> {

    /**
     * Find all information of user in database  by mail and status = 1
     * @param user
     * @return bean of user
     * @throws DaoException
     */
    public abstract User authorization(User user) throws DaoException;

    /**
     * Check exist of user by mail in database
     * @param email
     * @return user
     * @throws DaoException
     */
    public abstract Optional<User> findByEmail(String email) throws DaoException;

    /**
     * Creat user in database if user didnt exist
     * @param user
     * @return created user
     * @throws DaoException
     */
    public abstract Optional<User> registration(User user) throws DaoException;

    /**
     * Find all users in database on limit and offset
     * @param limit
     * @param offset
     * @return list of users
     * @throws DaoException
     */
    public abstract List<User> findAll(int limit, int offset) throws DaoException;

    /**
     * Find cash user by id user
     * @param connection
     * @param userId
     * @return user cash
     * @throws SQLException
     */
    public abstract BigDecimal findCash(ProxyConnection connection, Long userId) throws SQLException;

    /**
     * Change the users status on 0
     * @param id
     * @return true if status changes
     * @throws DaoException
     */
    public abstract boolean ban(Long id) throws DaoException;

    /**
     * Update users cash by id
     * @param cash
     * @param userId
     * @return true if cash changes
     * @throws DaoException
     */
    public abstract boolean updateCash(BigDecimal cash, Long userId) throws DaoException;

    /**
     * Update cash users when bet going to delete
     * @param connection
     * @param cash
     * @param userId
     * @return true if cash was changes
     * @throws SQLException
     */
    public abstract boolean updateCash(ProxyConnection connection, BigDecimal cash, Long userId) throws SQLException;

    /**
     * Calculated users in database
     * @return sum of users
     * @throws DaoException
     */
    public abstract long count() throws DaoException;

    @Override
    public final Optional<User> read(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<User> update(User v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> create(User user) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
