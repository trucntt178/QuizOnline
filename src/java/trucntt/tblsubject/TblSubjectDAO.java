/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trucntt.tblsubject;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import trucntt.utilities.DBHelper;

/**
 *
 * @author DELL
 */
public class TblSubjectDAO implements Serializable {

    private List<TblSubjectDTO> subjectList;

    public List<TblSubjectDTO> getSubjectList() {
        return subjectList;
    }

    public void loadSubject() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT subjectId, name "
                        + "FROM tblSubject";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("subjectId");
                    String name = rs.getString("name");

                    TblSubjectDTO dto = new TblSubjectDTO(id, name);

                    if (subjectList == null) {
                        subjectList = new ArrayList<>();
                    }
                    subjectList.add(dto);
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

    public TblSubjectDTO getSubject(String subjectId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT name, timeInMinute, numOfQuestion "
                        + "FROM tblSubject "
                        + "WHERE subjectId = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    int time = rs.getInt("timeInMinute");
                    int numOfQuestion = rs.getInt("numOfQuestion");

                    TblSubjectDTO dto = new TblSubjectDTO(subjectId, name, time, numOfQuestion);
                    return dto;
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
}
