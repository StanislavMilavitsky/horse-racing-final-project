package by.milavitsky.horse_racing.dao.pool;

import by.milavitsky.horse_racing.util.PropertiesUtil;
import by.milavitsky.horse_racing.exception.DaoException;
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
     * Create settings by properties in resourses
     */
    private static final String PASSWORD_KEY = "db.password";
    private static final String USERNAME_KEY = "db.userName";
    private static final String URL_KEY = "db.url";
    private static final String POOL_SIZE_KEY = "db.pool.size";
    /**
     * Create blocking deque for pool connection
     */
    private static BlockingQueue<ProxyConnection> pool;
    public static final Integer DEFAULT_POOL_SIZE = 10;
    private static List<Connection> sourceConnections;
    //private static final int FETCH_SIZE = 10;


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
     * Initialization connection pool throw proxy object from reflection
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
            /*var proxyConnection = (Connection)
                    Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
                            new Class[]{Connection.class},
                            (proxy, method, args) -> method.getName().equals("close")
                                    ? pool.add((Connection) proxy)
                                    : method.invoke(connection, args));*/
        }
    }

    /**
     * get connection from ConnectionManager
     */
    public static ProxyConnection get() throws DaoException {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Join in databes with password,login and url
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
