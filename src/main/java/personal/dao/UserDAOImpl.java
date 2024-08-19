package personal.dao;

import personal.dao.exceptions.UserDAOException;
import personal.model.User;
import personal.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements IUserDAO {
    @Override
    public User insert(User user) throws UserDAOException {
        String sql = "INSERT INTO users (username, password, email, firstname, lastname, dob, hasVoted) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFirstname());
            ps.setString(5, user.getLastname());
            ps.setDate(6, new java.sql.Date(user.getDob().getTime()));
            ps.setInt(7, user.getHasVoted());

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
                     "firstname = ?, lastname = ?, dob = ?, hasVoted = ?, voted_cid = ? " +
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
            ps.setInt(8, user.getVotedCid());
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
                user.setVotedCid(rs.getInt("voted_cid"));
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDAOException("SQL error in get user by id = " + uid);
        }
    }
}
