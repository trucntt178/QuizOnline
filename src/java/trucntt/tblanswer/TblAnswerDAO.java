/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trucntt.tblanswer;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import trucntt.utilities.DBHelper;

/**
 *
 * @author DELL
 */
public class TblAnswerDAO implements Serializable{
    public int counNumOfAnswer() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(answerId) AS numOfAnswer "
                        + "FROM tblAnswer";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int numOfAnswer = rs.getInt("numOfAnswer");
                    return numOfAnswer;
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
        return -1;
    }
    
    public boolean createAnswer(int questionId, String content, boolean isTrue) 
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        int numOfAnswer = counNumOfAnswer();
        if (numOfAnswer > -1) {
            int answerId = numOfAnswer + 1;
            try {
                con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblAnswer(answerId, questionId, answerContent, isTrue) "
                        + "VALUES(?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setInt(1, answerId);
                stm.setInt(2, questionId);
                stm.setString(3, content);
                stm.setBoolean(4, isTrue);
                
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
        }
        return false;
    }
    
    public boolean updateAnswer(int answerId, String content, boolean isTrue) 
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblAnswer "
                        + "SET answerContent = ?, isTrue = ? "
                        + "WHERE answerId = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, content);
                stm.setBoolean(2, isTrue);
                stm.setInt(3, answerId);
                
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
