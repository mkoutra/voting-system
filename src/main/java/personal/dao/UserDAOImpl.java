package personal.dao;

import personal.dao.exceptions.UserDAOException;
import personal.model.User;
import personal.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
            ps.setInt(7, 0);

            ps.executeUpdate();

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDAOException("SQL error in insert user: " + user);
        }
    }

    @Override
    public User update(User user) throws UserDAOException {
        return null;
    }

    @Override
    public void delete(Integer uid) throws UserDAOException {

    }

    @Override
    public User getById(Integer uid) throws UserDAOException {
        return null;
    }
}
