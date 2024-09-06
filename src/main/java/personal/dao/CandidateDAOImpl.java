package personal.dao;

import personal.dao.exceptions.CandidateDAOException;
import personal.model.Candidate;
import personal.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michail E. Koutrakis
 */
public class CandidateDAOImpl implements ICandidateDAO {
    @Override
    public Candidate insert(Candidate candidate) throws CandidateDAOException {
        String sql = "INSERT INTO candidates (firstname, lastname) VALUES (?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, candidate.getFirstname());
            ps.setString(2, candidate.getLastname());

            ps.executeUpdate();

            return candidate;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CandidateDAOException("SQL error in insert candidate: " + candidate);
        }
    }

    @Override
    public Candidate update(Candidate candidate) throws CandidateDAOException {
        String sql = "UPDATE candidates SET firstname = ?, lastname = ? WHERE cid = ?;";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, candidate.getFirstname());
            ps.setString(2, candidate.getLastname());
            ps.setInt(3, candidate.getCid());

            ps.executeUpdate();

            return candidate;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CandidateDAOException("SQL error in update candidate: " + candidate);
        }
    }

    @Override
    public void delete(Integer cid) throws CandidateDAOException {
        String sql = "DELETE FROM candidates WHERE cid = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cid);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CandidateDAOException("SQL error in delete candidate with cid: " + cid);
        }
    }

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

    @Override
    public Candidate getCandidateById(Integer cid) throws CandidateDAOException {
        String sql = "SELECT * FROM candidates WHERE cid = ?;";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            Candidate candidate = null;
            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                candidate = new Candidate();
                candidate.setCid(rs.getInt("cid"));
                candidate.setFirstname(rs.getString("firstname"));
                candidate.setLastname(rs.getString("lastname"));
            }

            return candidate;
        } catch (SQLException e) {
            throw new CandidateDAOException("SQL error in get candidate by id: " + cid);
        }
    }

    @Override
    public Map<Candidate, Integer> getAllCandidatesWithVotes() throws CandidateDAOException {
        Map<Candidate, Integer> candidatesWithVotes = new HashMap<>();
        String sql = "SELECT c.cid, c.firstname, c.lastname, COUNT(votedCid) AS 'total_votes'" +
                " FROM candidates c LEFT JOIN users u ON u.votedCid = c.cid" +
                " GROUP BY c.cid, c.firstname, c.lastname";
        try(Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            Candidate candidate = null;
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                candidate = new Candidate();
                candidate.setCid(rs.getInt("cid"));
                candidate.setFirstname(rs.getString("firstname"));
                candidate.setLastname(rs.getString("lastname"));

                candidatesWithVotes.put(candidate, rs.getInt("total_votes"));
            }

            return candidatesWithVotes;
        } catch (SQLException e) {
            throw new CandidateDAOException("SQL error in get all candidates with votes");
        }
    }
}
