package by.milavitsky.horseracing.dao.daoabstract;

import by.milavitsky.horseracing.dao.Dao;
import by.milavitsky.horseracing.entity.Role;
import by.milavitsky.horseracing.exception.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class
RolePermissionsDaoAbstract implements Dao<Role, Long> {
    /**
     * Find all roles
     * @return list of roles
     * @throws DaoException
     */
    public abstract List<Role> findAll() throws DaoException;

    @Override
    public Optional<Role> create(Role a) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Role> read(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Role> update(Role a) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
