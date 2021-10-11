package by.milavitsky.horse_racing.dao.dao_entity;


import by.milavitsky.horse_racing.dao.dao_abstract.UserDaoAbstract;
import by.milavitsky.horse_racing.dao.pool.ConnectionManager;
import by.milavitsky.horse_racing.dao.pool.ProxyConnection;
import by.milavitsky.horse_racing.entity.Role;
import by.milavitsky.horse_racing.entity.User;
import by.milavitsky.horse_racing.exception.DaoException;
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

    /**
     * Create simple sql commands in String type
     */
    private static final String DELETE_SQL = "DELETE FROM users WHERE id = ?;";

    private static final String BAN_SQL = "UPDATE users SET status=0  WHERE id = ?;";

    private static final String SAVE_SQL = "INSERT INTO users (name, passport, email, phone, bank_account," +
            " password, status, nickname, persentage_of_win, avatar, date_of_register, rates, surname, role_id, cash) VALUES" +
            " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public static final String FIND_ALL_SQL = "SELECT id, name, passport, email, phone, bank_account, password, status," +
            " nickname, persentage_of_win, avatar, date_of_register, rates, surname, role_id, cash FROM users where status=1 LIMIT ? OFFSET  ?";

    private static final String USER_AUTHORIZED_SQL = "SELECT email, password FROM users WHERE email=?";

    public static final String USER_EXIST_SQL = "SELECT 1 FROM users WHERE email = ?;";

    public static final String SHOW_USER_CASH_SQL = "SELECT cash FROM users WHERE id = ?;";

    public static final String UPDATE_CASH_SQL = "UPDATE users SET cash=? WHERE id=?;";

    private UserDao() {
    }

    @Override
    public Optional<User> authorization(User user) throws DaoException {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(USER_AUTHORIZED_SQL)) {//todo
            statement.setString(1, user.getEmail());
            var resultSet = statement.executeQuery();
            User userDao = null;
            while (resultSet.next()) {
                userDao = new User();
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("passport"));

            }
            return Optional.ofNullable(userDao);
        } catch (SQLException ex) {
            logger.error("User authorization failed!", ex);
            throw new DaoException("User authorization failed!", ex);
        }
    }

    /*public User authorization(User user) throws DaoException {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(USER_AUTHORIZED_SQL)) {//todo
            statement.setString(1, user.getEmail());
            var resultSet = statement.executeQuery();
            User userDao = null;
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String passport = resultSet.getString("passport");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String bankAccount = resultSet.getString("bank_account");
                String password = resultSet.getString("password");
                String status = resultSet.getString("status");
                String nickname = resultSet.getString("nickname");
                BigDecimal persentageOfWin = resultSet.getBigDecimal("persentage_of_win");
                String avatar = resultSet.getString("avatar");
                LocalDateTime dateOfRegister = resultSet.getObject("date_of_register", Timestamp.class).toLocalDateTime();
                Integer rates = resultSet.getInt("rates");
                String surName = resultSet.getString("surname");
                PermissionEnum permissionEnum = PermissionEnum.valueOf(resultSet.getString("access_level"));
                BigDecimal cash = resultSet.getBigDecimal("cash");
                userDao = new User(id, name, passport, email, phone, bankAccount, password, status, nickname, persentageOfWin,
                        avatar, dateOfRegister, rates, surName, permissionEnum, cash);
            }
            return userDao;
        } catch (SQLException e) {
            logger.error("User authorization failed!", e);
            throw new DaoException("User authorization failed!", e);

        }
    }*/

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        User userDao = null;
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
    public Optional<User> create(User user) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassport());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getBankAccount());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setString(7, user.getStatus());
            preparedStatement.setString(8, user.getNickname());
            preparedStatement.setBigDecimal(9, user.getPersentageOfWin());
            preparedStatement.setString(10, user.getAvatar());
            preparedStatement.setObject(11, LocalDateTime.now());
            preparedStatement.setInt(12, user.getRates());
            preparedStatement.setString(13, user.getSurname());
            preparedStatement.setLong(14, user.getRole().getId());
            preparedStatement.setBigDecimal(15, user.getCash());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong("id"));
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            logger.fatal("Cant create user!", e);
            throw new DaoException(e);
        }

    }

    @Override
    public List<User> findAll(int limit, int offset) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
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

    private static class UserDaoHolder {
        private static final UserDao HOLDER_INSTANCE = new UserDao();
    }

    public static UserDao getInstance() {
        return UserDaoHolder.HOLDER_INSTANCE;
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("passport"),
                resultSet.getString("email"),
                resultSet.getString("phone"),
                resultSet.getString("bank_account"),
                resultSet.getString("password"),
                resultSet.getString("status"),
                resultSet.getString("nickname"),
                resultSet.getBigDecimal("persentage_of_win"),
                resultSet.getString("avatar"),
                resultSet.getObject("data_of_register", Timestamp.class).toLocalDateTime(),
                resultSet.getInt("rates"),
                resultSet.getString("surname"),
                new Role(resultSet.getLong("role_id")),
                resultSet.getBigDecimal("cash")
        );
    }
}
