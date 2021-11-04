package by.milavitsky.horseracing.dao.daoabstract;

import by.milavitsky.horseracing.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Optional;

/**
 * The interface Dao.
 *
 * @param <T> the type parameter
 * @param <K> the type parameter
 */
public interface Dao <T, K extends Serializable> {

    /**
     * The constant logger.
     */
    Logger logger = LogManager.getLogger(Dao.class);

    /**
     * Create optional.
     *
     * @param a the a
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> create(T a) throws DaoException;

    /**
     * Read optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> read(K id) throws DaoException;

    /**
     * Update optional.
     *
     * @param a the a
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> update(T a) throws DaoException;

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean delete(K id) throws DaoException;


}
