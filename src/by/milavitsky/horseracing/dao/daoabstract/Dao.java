package by.milavitsky.horseracing.dao.daoabstract;

import by.milavitsky.horseracing.entity.enums.Ratio;
import by.milavitsky.horseracing.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Optional;

public interface Dao <T, K extends Serializable> {

    Logger logger = LogManager.getLogger(Dao.class);

    Optional<T> create(T a) throws DaoException;

    Optional<T> read(K id) throws DaoException;

    Optional<T> update(T a) throws DaoException;

    boolean delete(K id) throws DaoException;

}
