package by.milavitsky.horseracing.dao.dao_abstract;

import by.milavitsky.horseracing.dao.pool.ProxyConnection;
import by.milavitsky.horseracing.entity.User;
import by.milavitsky.horseracing.exception.DaoException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public abstract class UserDaoAbstract implements Dao<User, Long> {


    public abstract Optional<User> authorization(User user) throws DaoException;

    public abstract Optional<User> findByEmail(String email) throws DaoException;

    public abstract List<User> findAll(int limit, int offset) throws DaoException;

    public abstract BigDecimal findCash(ProxyConnection connection, Long userId) throws SQLException;

    public abstract boolean ban(Long id) throws DaoException;

    public abstract boolean updateCash(BigDecimal cash, Long userId) throws DaoException;

    public abstract boolean updateCash(ProxyConnection connection, BigDecimal cash, Long userId) throws SQLException;

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

}
