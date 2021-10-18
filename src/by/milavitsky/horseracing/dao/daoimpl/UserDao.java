package by.milavitsky.horseracing.dao.daoimpl;


import by.milavitsky.horseracing.dao.daoabstract.UserDaoAbstract;
import by.milavitsky.horseracing.dao.pool.ConnectionManager;
import by.milavitsky.horseracing.dao.pool.ProxyConnection;
import by.milavitsky.horseracing.entity.Role;
import by.milavitsky.horseracing.entity.User;
import by.milavitsky.horseracing.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao extends UserDaoAbstract {
    private static final Logger logger = LogManager.getLogger(BetDao.class);

    private static final String BAN_SQL = "UPDATE users SET status=0  WHERE id = ?;";

    private static final String SAVE_SQL = "INSERT INTO users (name, email," +
            " password, date_of_register, surname) VALUES" +
            " (?, ?, ?, ?, ?);";

    public static final String FIND_ALL_SQL = "SELECT id, name, email, bank_account, password, status," +
            " persentage_of_win, avatar, date_of_register, rates, surname, role_id, cash FROM users where status=1 LIMIT ? OFFSET  ?";

    private static final String USER_AUTHORIZED_SQL = "SELECT id, password, name, surname, cash, role_id FROM users WHERE email=? AND status=1";

    public static final String USER_EXIST_SQL = "SELECT 1 FROM users WHERE email = ?;";

    public static final String SHOW_USER_CASH_SQL = "SELECT cash FROM users WHERE id = ?;";

    public static final String UPDATE_CASH_SQL = "UPDATE users SET cash=? WHERE id=?;";

    private static final String COUNT_USERS_SQL = "SELECT count(id) FROM users WHERE status=1 ;";

    private UserDao() {
    }

    @Override
    public User authorization(User user) throws DaoException {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(USER_AUTHORIZED_SQL)) {//todo
            statement.setString(1, user.getEmail());
            var resultSet = statement.executeQuery();
            User userDao = null;
            while (resultSet.next()) {
                userDao = new User();
                userDao.setId(resultSet.getLong("id"));
                userDao.setEmail(user.getEmail());
                userDao.setPassword(resultSet.getString("password"));
                userDao.setSurname(resultSet.getString("surname"));
                userDao.setName(resultSet.getString("name"));
                userDao.setCash(resultSet.getBigDecimal("cash"));
                userDao.setRole(new Role((resultSet.getLong("role_id"))));

            }
            return userDao;
        } catch (SQLException ex) {
            logger.error("User authorization failed!", ex);
            throw new DaoException("User authorization failed!", ex);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(USER_EXIST_SQL)) {
            statement.setString(1, email);
            var set = statement.executeQuery();
            if (set.next()) {
                return Optional.of(new User());
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("User exist failed!", e);
            throw new DaoException("User exist failed!", e);

        }
    }

    @Override
    public Optional<User> registration(User user) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setObject(4, LocalDateTime.now());
            preparedStatement.setString(5, user.getSurname());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            }
            return Optional.of(user);
        } catch (SQLException e) {
            logger.fatal("Cant create user!", e);
            throw new DaoException(e);
        }

    }

    @Override
    public List<User> findAll(int limit, int offset) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            var resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }
            return users;
        } catch (SQLException ex) {
            logger.error("User find all failed!", ex);
            throw new DaoException("User find all failed!", ex);
        }

    }

    @Override
    public BigDecimal findCash(ProxyConnection connection, Long userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SHOW_USER_CASH_SQL);
        statement.setLong(1, userId);
        ResultSet resultSet = statement.executeQuery();
        BigDecimal cash = BigDecimal.ZERO;
        while (resultSet.next()) {
            cash = resultSet.getBigDecimal("cash");
        }
        return cash;
    }

    @Override
    public boolean ban(Long id) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(BAN_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            logger.error("User registration failed!", ex);
            throw new DaoException("User registration failed!", ex);
        }
    }


    @Override
    public boolean updateCash(BigDecimal cash, Long userId) throws DaoException {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_CASH_SQL)) {
            statement.setBigDecimal(1, cash);
            statement.setLong(2, userId);
            int rowEffected = statement.executeUpdate();
            return rowEffected > 0;
        } catch (SQLException e) {
            logger.error("Update cash failed!", e);
            throw new DaoException("Update cash failed!", e);
        }
    }

    @Override
    public boolean updateCash(ProxyConnection connection, BigDecimal cash, Long userId) throws SQLException {
        var statement = connection.prepareStatement(UPDATE_CASH_SQL);
        statement.setBigDecimal(1, cash);
        statement.setLong(2, userId);
        int rowEffected = statement.executeUpdate();
        return rowEffected > 0;

    }

    @Override
    public long count() throws DaoException {

        try (var proxyConnection = ConnectionManager.get();
             var statement = proxyConnection.createStatement()){
            var resultSet = statement.executeQuery(COUNT_USERS_SQL);
            long count = 0L;
            if (resultSet.next()) {
                count = resultSet.getLong("count(id)");
            }
            return count;
        } catch (SQLException e) {
            logger.error("Count races exception!", e);
            throw new DaoException("Count races exception!", e);
        }
    }

    private static class UserDaoHolder {
        private static final UserDao HOLDER_INSTANCE = new UserDao();
    }

    public static UserDao getInstance() {
        return UserDaoHolder.HOLDER_INSTANCE;
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("bank_account"),
                resultSet.getString("password"),
                resultSet.getString("status"),
                resultSet.getBigDecimal("persentage_of_win"),
                resultSet.getString("avatar"),
                resultSet.getObject("date_of_register", Timestamp.class).toLocalDateTime(),
                resultSet.getInt("rates"),
                resultSet.getString("surname"),
                new Role(resultSet.getLong("role_id")),
                resultSet.getBigDecimal("cash")
        );
    }
}
