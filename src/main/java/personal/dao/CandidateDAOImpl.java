package personal.dao;

import personal.dao.exceptions.CandidateDAOException;
import personal.model.Candidate;
import personal.service.util.DBUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CandidateDAOImpl implements ICandidateDAO {
    @Override
    public boolean cidExists(Integer cid) throws CandidateDAOException {
        String sql = "SELECT COUNT(1) FROM candidates WHERE cid = ?;";
        try(Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            int candidateExists = 0;
            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                candidateExists = rs.getInt(1);
            }

            return candidateExists == 1;
        } catch (SQLException e) {
            throw new CandidateDAOException("SQL error in checking if candidate ID exists");
        }
    }

    @Override
    public List<Candidate> getAllCandidates() throws CandidateDAOException {
        final List<Candidate> candidates = new ArrayList<>();
        Candidate candidate = null;
        String sql = "SELECT * FROM candidates ORDER BY lastname ASC;";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                candidate = new Candidate();
                candidate.setCid(rs.getInt("cid"));
                candidate.setFirstname(rs.getString("firstname"));
                candidate.setLastname(rs.getString("lastname"));

                candidates.add(candidate);
            }

            return candidates;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CandidateDAOException("SQL error in getAllCandidates");
        }
    }
}
