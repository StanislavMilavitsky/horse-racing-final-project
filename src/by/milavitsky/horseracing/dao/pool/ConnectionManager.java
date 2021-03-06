package by.milavitsky.horseracing.dao.pool;

import by.milavitsky.horseracing.util.PropertiesUtil;
import by.milavitsky.horseracing.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {
    public static final Logger logger = LogManager.getLogger(ConnectionManager.class);
    /**
     * Static fields by properties in resources
     */
    private static final String PASSWORD_KEY = "db.password";
    private static final String USERNAME_KEY = "db.userName";
    private static final String URL_KEY = "db.url";
    private static final String POOL_SIZE_KEY = "db.pool.size";

    private static BlockingQueue<ProxyConnection> pool;
    public static final Integer DEFAULT_POOL_SIZE = 10;
    private static List<Connection> sourceConnections;


    static {
        loadDriver();
        initConnectionPool();
    }

    private ConnectionManager() {
    }

    /**
     * If java until 8 download driver mysql
     */
    private static void loadDriver() {
        try {
            Class<?> aClass = Class.forName(PropertiesUtil.get("db.driver"));
        } catch (ClassNotFoundException e) {
           logger.fatal("Driver not found", e);
        }
    }

    /**
     * Initialization connection pool, make blocking queue and add proxy connection in pool
     */

    private static void initConnectionPool() {
        var poolSize = PropertiesUtil.get(POOL_SIZE_KEY);
        var size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue<>(size);
        sourceConnections = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            var connection = open();
            ProxyConnection proxyConnection = new ProxyConnection(connection, pool);
            pool.add(proxyConnection);
            sourceConnections.add(connection);
        }
    }

    /**
     * @return proxy thread
     * @throws DaoException
     */
    public static ProxyConnection get() throws DaoException {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @return connection with mysql by properties
     */
    private static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            logger.fatal("Cant open connection to data base", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Close our connection because close method from  sourseConnection redefined and dont close our connection
     */
    public static void closePool() {
        try {
            for (Connection sourceConnection : sourceConnections) {
                sourceConnection.close();
            }
        } catch (SQLException e) {
            logger.fatal("Cant close connection to data base");
        }
    }
}
