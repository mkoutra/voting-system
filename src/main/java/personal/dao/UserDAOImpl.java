package personal.dao;

import personal.dao.exceptions.UserDAOException;
import personal.model.User;
import personal.service.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michail E. Koutrakis
 */
public class UserDAOImpl implements IUserDAO {
    @Override
    public User insert(User user) throws UserDAOException {
        String sql = "INSERT INTO users (username, password, email, firstname, lastname, dob, hasVoted, votedCid) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFirstname());
            ps.setString(5, user.getLastname());
            ps.setDate(6, new java.sql.Date(user.getDob().getTime()));
            ps.setInt(7, user.getHasVoted());

            if (user.getVotedCid() == null) {
                ps.setNull(8, Types.INTEGER);
            } else {
                ps.setInt(8, user.getVotedCid());
            }

            ps.executeUpdate();

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDAOException("SQL error in insert user: " + user);
        }
    }

    @Override
    public User update(User user) throws UserDAOException {
        String sql = "UPDATE users SET username = ?, password = ?, email = ?, " +
                     "firstname = ?, lastname = ?, dob = ?, hasVoted = ?, votedCid = ? " +
                     "WHERE uid = ?;";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFirstname());
            ps.setString(5, user.getLastname());
            ps.setDate(6, new java.sql.Date(user.getDob().getTime()));
            ps.setInt(7, user.getHasVoted());

            if (user.getVotedCid() == null) {
                ps.setNull(8, Types.INTEGER);
            } else {
                ps.setInt(8, user.getVotedCid());
            }

            ps.setInt(9, user.getUid());

            ps.executeUpdate();

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDAOException("SQL error in update user: " + user);
        }
    }

    @Override
    public void delete(Integer uid) throws UserDAOException {
        String sql = "DELETE FROM users WHERE uid = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, uid);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDAOException("SQL error in delete user with uid: " + uid);
        }
    }

    @Override
    public User getById(Integer uid) throws UserDAOException {
        String sql = "SELECT * FROM users WHERE uid = ?";
        User user = null;

        try(Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUid(rs.getInt("uid"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setDob(rs.getDate("dob"));
                user.setPassword(rs.getString("password"));
                user.setHasVoted(rs.getInt("hasVoted"));
                user.setVotedCid(rs.getInt("votedCid"));
                if (rs.wasNull()) {
                    user.setVotedCid(null);
                }
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDAOException("SQL error in get user by id = " + uid);
        }
    }

    @Override
    public User getByUsername(String username) throws UserDAOException {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;

        try(Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUid(rs.getInt("uid"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setDob(rs.getDate("dob"));
                user.setPassword(rs.getString("password"));
                user.setHasVoted(rs.getInt("hasVoted"));
                user.setVotedCid(rs.getInt("votedCid"));
                if (rs.wasNull()) {
                    user.setVotedCid(null);
                }
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDAOException("SQL error in get user by username = " + username);
        }
    }

    @Override
    public boolean usernameExists(String username) throws UserDAOException {
        String sql = "SELECT 1 WHERE EXISTS(SELECT 1 FROM users WHERE username = ?);";
        int userExists = 0;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userExists = rs.getInt(1);
            }

            return userExists == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDAOException("SQL error in usernameExists for username = " + username);
        }
    }

    @Override
    public boolean emailExists(String email) throws UserDAOException {
//        String sql = "SELECT COUNT(1) FROM users WHERE email = ?";
        String sql = "SELECT 1 WHERE EXISTS(SELECT 1 FROM users WHERE email = ?);";
        int userExists = 0;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userExists = rs.getInt(1);
            }

            return userExists != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDAOException("SQL error in emailExists for email = " + email);
        }
    }

    @Override
    public List<User> getAllUsers() throws UserDAOException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users;";
        try(Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUid(rs.getInt("uid"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setDob(rs.getDate("dob"));
                user.setPassword(rs.getString("password"));
                user.setHasVoted(rs.getInt("hasVoted"));
                user.setVotedCid(rs.getInt("votedCid"));
                if (rs.wasNull()) {
                    user.setVotedCid(null);
                }

                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            throw new UserDAOException("SQL error in get all users");
        }
    }

    @Override
    public List<User> getUsersByVotedCid(Integer votedCid) throws UserDAOException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE votedCid = ?;";

        try(Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, votedCid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUid(rs.getInt("uid"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setDob(rs.getDate("dob"));
                user.setPassword(rs.getString("password"));
                user.setHasVoted(rs.getInt("hasVoted"));
                user.setVotedCid(rs.getInt("votedCid"));
                if (rs.wasNull()) {
                    user.setVotedCid(null);
                }

                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            throw new UserDAOException("SQL error in get users by voted cid");
        }
    }

    @Override
    public void removeAllVotesOfSpecificCid(Integer votedCid) throws UserDAOException {
        String sql = "UPDATE users SET hasVoted = 0, votedCid = NULL WHERE votedCid = ?;";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, votedCid);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new UserDAOException("SQL error in clean users vote specific cid");
        }
    }
}
