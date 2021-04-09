/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trucntt.tblhistory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.naming.NamingException;
import trucntt.utilities.DBHelper;

/**
 *
 * @author DELL
 */
public class TblHistoryDAO implements Serializable{

    private List<TblHistoryDTO> historyList;

    public List<TblHistoryDTO> getHistoryList() {
        return historyList;
    }

    public boolean saveHistory(TblHistoryDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblHistory(subjectId, email, takeDate, totalQuestion, numOfCorrect, score, status) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getSubjectId());
                stm.setString(2, dto.getEmail());
                stm.setString(3, dto.getTakeDate());
                stm.setInt(4, dto.getTotalQuestion());
                stm.setInt(5, dto.getNumOfCorrect());
                stm.setFloat(6, dto.getScore());
                stm.setBoolean(7, dto.isIsDone());

                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public Date findUnfinishedQuiz(String subjectId, String email) throws NamingException, SQLException, ParseException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT h.takeDate, h.totalQuestion, s.timeInMinute "
                        + "FROM tblHistory AS h, tblSubject AS s "
                        + "WHERE h.subjectId = ? "
                        + "AND h.email = ? "
                        + "AND h.status = 0 "
                        + "AND h.subjectId = s.subjectId";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectId);
                stm.setString(2, email);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String takeDate = rs.getString("takeDate");
                    int minute = rs.getInt("timeInMinute");
                    int totalQuestion = rs.getInt("totalQuestion");
                    Date takeTime = new SimpleDateFormat("dd-M-yyyy HH:mm:ss").parse(takeDate);
                    Date deadline = new Date(takeTime.getTime() + TimeUnit.MINUTES.toMillis(minute));
                    Date now = new Date();
                    if (now.getTime() >= deadline.getTime()) {
                        TblHistoryDTO dto = new TblHistoryDTO(subjectId, email, takeDate, totalQuestion, 0, 0, true);
                        updateHistory(dto);                        
                    } else {
                        return takeTime;
                    }
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }
    
    public boolean checkFinishedQuiz(String subjectId, String email, String takeDate) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT status "
                        + "FROM tblHistory "
                        + "WHERE subjectId = ? "
                        + "AND email = ? "
                        + "AND takeDate = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectId);
                stm.setString(2, email);
                stm.setString(3, takeDate);
                rs = stm.executeQuery();
                if (rs.next()) {
                    boolean isDone = rs.getBoolean("status");
                    return isDone;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public void loadHistory(String subjectId, String email) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT takeDate, totalQuestion, numOfCorrect, score "
                        + "FROM tblHistory "
                        + "WHERE subjectId = ? "
                        + "AND email = ? "
                        + "AND status = 1";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectId);
                stm.setString(2, email);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String takeDate = rs.getString("takeDate");
                    int totalQuestion = rs.getInt("totalQuestion");
                    int numOfCorrect = rs.getInt("numOfCorrect");
                    float score = rs.getFloat("score");

                    TblHistoryDTO dto = new TblHistoryDTO(subjectId, email, takeDate, totalQuestion, numOfCorrect, score, true);

                    if (historyList == null) {
                        historyList = new ArrayList<>();
                    }
                    historyList.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public boolean updateHistory(TblHistoryDTO dto)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblHistory "
                        + "SET numOfCorrect = ?, score = ?, totalQuestion = ?, status = ? "
                        + "WHERE subjectId = ? "
                        + "AND email = ? "
                        + "AND takeDate = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, dto.getNumOfCorrect());
                stm.setFloat(2, dto.getScore());
                stm.setInt(3, dto.getTotalQuestion());
                stm.setBoolean(4, dto.isIsDone());
                stm.setString(5, dto.getSubjectId());
                stm.setString(6, dto.getEmail());
                stm.setString(7, dto.getTakeDate());

                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
}
