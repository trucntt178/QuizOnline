/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trucntt.tblquestion;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import trucntt.tblanswer.TblAnswerDTO;
import trucntt.tblsubject.TblSubjectDTO;
import trucntt.utilities.DBHelper;

/**
 *
 * @author DELL
 */
public class TblQuestionDAO implements Serializable {

    private List<TblQuestionDTO> questionList;

    public List<TblQuestionDTO> getQuestionList() {
        return questionList;
    }

    public void searchQuestion(String subjectId, String searchContentValue, String searchStatusValue,
            int firstRecord, int lastRecord)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                if ("All".equals(searchStatusValue)) {
                    String sql = "SELECT questionId, questionContent, name, answerId, "
                            + "answerContent, isTrue, createDate, status "
                            + "FROM "
                            + "(SELECT q.questionId, q.questionContent, s.name, a.answerId, "
                            + "a.answerContent, a.isTrue, q.createDate, q.status, "
                            + "ROW_NUMBER() OVER (ORDER BY q.questionContent) AS RowNum "
                            + "FROM tblQuestion AS q, tblSubject AS s, tblAnswer AS a "
                            + "WHERE q.subjectId = s.subjectId "
                            + "AND q.subjectId = ? "
                            + "AND q.questionId = a.questionId "
                            + "AND q.questionContent LIKE ? ) AS questionTable "
                            + "WHERE questionTable.RowNum BETWEEN ? AND ?";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, subjectId);
                    stm.setString(2, "%" + searchContentValue + "%");
                    stm.setInt(3, firstRecord);
                    stm.setInt(4, lastRecord);
                } else {
                    boolean status = false;
                    if ("Active".equals(searchStatusValue)) {
                        status = true;
                    }
                    String sql = "SELECT questionId, questionContent, name, answerId, "
                            + "answerContent, isTrue, createDate, status "
                            + "FROM "
                            + "(SELECT q.questionId, q.questionContent, s.name, a.answerId, "
                            + "a.answerContent, a.isTrue, q.createDate, q.status, "
                            + "ROW_NUMBER() OVER (ORDER BY q.questionContent) AS RowNum "
                            + "FROM tblQuestion AS q, tblSubject AS s, tblAnswer AS a "
                            + "WHERE q.subjectId = s.subjectId "
                            + "AND q.subjectId = ? "
                            + "AND q.questionId = a.questionId "
                            + "AND q.questionContent LIKE ? "
                            + "AND q.status = ? ) AS questionTable "
                            + "WHERE questionTable.RowNum BETWEEN ? AND ?";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, subjectId);
                    stm.setString(2, "%" + searchContentValue + "%");
                    stm.setBoolean(3, status);
                    stm.setInt(4, firstRecord);
                    stm.setInt(5, lastRecord);
                }
                rs = stm.executeQuery();
                TblAnswerDTO answer;
                List<TblAnswerDTO> answerList = null;
                TblQuestionDTO question;
                int currQuestion = 0;
                while (rs.next()) {
                    int questionId = rs.getInt("questionId");
                    String questionContent = rs.getString("questionContent");
                    String subjectName = rs.getString("name");
                    TblSubjectDTO subject = new TblSubjectDTO(subjectId, subjectName);
                    int answerId = rs.getInt("answerId");
                    String answerContent = rs.getString("answerContent");
                    boolean isTrue = rs.getBoolean("isTrue");
                    Date createDate = rs.getDate("createDate");
                    boolean questionStatus = rs.getBoolean("status");

                    if (questionList == null) {
                        questionList = new ArrayList<>();
                    }
                    if (answerList == null) {
                        answerList = new ArrayList<>();
                    }

                    answer = new TblAnswerDTO(answerId, answerContent, isTrue);
                    if (currQuestion != questionId) {
                        currQuestion = questionId;
                        answerList = new ArrayList<>();
                        answerList.add(answer);
                        question = new TblQuestionDTO(questionId, subject, questionContent, answerList, createDate, questionStatus);
                    } else {
                        answerList.add(answer);
                        questionList.remove(questionList.size() - 1);
                        question = new TblQuestionDTO(questionId, subject, questionContent, answerList, createDate, questionStatus);
                    }
                    questionList.add(question);
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

    public int countNumOfQuestionPage(String subjectId, String searchContentValue,
            String searchStatusValue, int numOfRecords)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                if ("All".equals(searchStatusValue)) {
                    String sql = "SELECT COUNT(questionId) AS numOfQuestion "
                            + "FROM tblQuestion "
                            + "WHERE subjectId = ? "
                            + "AND questionContent LIKE ? ";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, subjectId);
                    stm.setString(2, "%" + searchContentValue + "%");
                } else {
                    boolean status = false;
                    if ("Active".equals(searchStatusValue)) {
                        status = true;
                    }
                    String sql = "SELECT COUNT(questionId) AS numOfQuestion "
                            + "FROM tblQuestion "
                            + "WHERE subjectId = ? "
                            + "AND questionContent LIKE ? "
                            + "AND status = ?";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, subjectId);
                    stm.setString(2, "%" + searchContentValue + "%");
                    stm.setBoolean(3, status);
                }
                rs = stm.executeQuery();
                if (rs.next()) {
                    int numOfQuestion = rs.getInt("numOfQuestion");
                    int numOfPage = (int) Math.ceil((double) numOfQuestion / numOfRecords);
                    return numOfPage;
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

    public int countNumOfQuestion() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(questionId) AS numOfQuestion "
                        + "FROM tblQuestion";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int numOfQuestion = rs.getInt("numOfQuestion");
                    return numOfQuestion;
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

    public int createQuestion(String subjectId, String content, Date createDate, boolean status)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        int numOfQuestion = countNumOfQuestion();
        if (numOfQuestion > -1) {
            int questionId = numOfQuestion + 1;
            try {
                con = DBHelper.makeConnection();
                if (con != null) {
                    String sql = "INSERT INTO tblQuestion(questionId, subjectId, questionContent, createDate, status) "
                            + "VALUES(?, ?, ?, ?, ?)";
                    stm = con.prepareStatement(sql);
                    stm.setInt(1, questionId);
                    stm.setString(2, subjectId);
                    stm.setString(3, content);
                    stm.setDate(4, new java.sql.Date(createDate.getTime()));
                    stm.setBoolean(5, status);

                    int row = stm.executeUpdate();
                    if (row > 0) {
                        return questionId;
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
        return -1;
    }

    public boolean updateQuestion(int questionId, String subjectId, String content, boolean status)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblQuestion "
                        + "SET subjectId = ?, questionContent = ?, status = ? "
                        + "WHERE questionId = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectId);
                stm.setString(2, content);
                stm.setBoolean(3, status);
                stm.setInt(4, questionId);

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

    public boolean updateStatus(int questionId, boolean status)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblQuestion "
                        + "SET status = ? "
                        + "WHERE questionId = ?";
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, status);
                stm.setInt(2, questionId);

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

    public void getQuestion(String subjectId, int numOfQuestion, String email, String takeDate)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                if (takeDate == null) {
                    String sql = "SELECT questions.questionId, questions.questionContent, tblAnswer.answerId, "
                            + "tblAnswer.answerContent, tblAnswer.isTrue "
                            + "FROM "
                            + "(SELECT TOP (?) questionId, questionContent "
                            + "FROM tblQuestion "
                            + "WHERE subjectId = ? "
                            + "AND status = 1 "
                            + "ORDER BY NEWID()) as questions, tblAnswer "
                            + "WHERE questions.questionId = tblAnswer.questionId";
                    stm = con.prepareStatement(sql);
                    stm.setInt(1, numOfQuestion);
                    stm.setString(2, subjectId);
                } else {
                    String sql = "SELECT q.questionId, q.questionContent, a.answerId, a.answerContent, a.isTrue "
                            + "FROM tblQuestInQuiz AS qiq, tblQuestion AS q, tblAnswer AS a "
                            + "WHERE qiq.subjectId = ? "
                            + "AND qiq.email = ? "
                            + "AND qiq.takeDate = ? "
                            + "AND q.questionId = a.questionId "
                            + "AND qiq.questionId = q.questionId";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, subjectId);
                    stm.setString(2, email);
                    stm.setString(3, takeDate);
                }
                rs = stm.executeQuery();

                TblAnswerDTO answer;
                List<TblAnswerDTO> answerList = null;
                TblQuestionDTO question;
                int currQuestion = 0;
                while (rs.next()) {
                    int questionId = rs.getInt("questionId");
                    String questionContent = rs.getString("questionContent");
                    int answerId = rs.getInt("answerId");
                    String answerContent = rs.getString("answerContent");
                    boolean isTrue = rs.getBoolean("isTrue");

                    if (questionList == null) {
                        questionList = new ArrayList<>();
                    }
                    if (answerList == null) {
                        answerList = new ArrayList<>();
                    }

                    answer = new TblAnswerDTO(answerId, answerContent, isTrue);
                    if (currQuestion != questionId) {
                        currQuestion = questionId;
                        answerList = new ArrayList<>();
                        answerList.add(answer);
                        question = new TblQuestionDTO(questionId, questionContent, answerList);
                    } else {
                        answerList.add(answer);
                        questionList.remove(questionList.size() - 1);
                        question = new TblQuestionDTO(questionId, questionContent, answerList);
                    }
                    questionList.add(question);
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

    public boolean saveQuestionInQuiz(String subjectId, String email, String takeDate, int questionId)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblQuestInQuiz(subjectId, email, takeDate, questionId) "
                        + "VALUES(?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectId);
                stm.setString(2, email);
                stm.setString(3, takeDate);
                stm.setInt(4, questionId);
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
    
    public boolean checkValidQuest(int questionId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT status "
                        + "FROM tblQuestion "
                        + "WHERE questionId = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, questionId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    boolean isValid = rs.getBoolean("status");
                    return isValid;
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

    public boolean checkCorrectAns(int questionId, int answerId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT a.isTrue "
                        + "FROM tblQuestion AS q, tblAnswer AS a "
                        + "WHERE q.questionId = ? "
                        + "AND answerId = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, questionId);
                stm.setInt(2, answerId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    boolean correct = rs.getBoolean("isTrue");
                    return correct;
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
}
