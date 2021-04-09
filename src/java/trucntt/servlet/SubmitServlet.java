/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trucntt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import trucntt.tblhistory.TblHistoryDAO;
import trucntt.tblhistory.TblHistoryDTO;
import trucntt.tblquestion.TblQuestionDAO;
import trucntt.tblsubject.TblSubjectDTO;

/**
 *
 * @author DELL
 */
@WebServlet(name = "SubmitServlet", urlPatterns = {"/SubmitServlet"})
public class SubmitServlet extends HttpServlet {

    private final float MAX_SCORE = 10;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = "showHistory";
        try {
            HttpSession session = request.getSession();
            TblSubjectDTO subjectDto = (TblSubjectDTO) session.getAttribute("SUBJECT");
            String subjectId = subjectDto.getSubjectId();
            String email = (String) session.getAttribute("EMAIL");
            String timeStart = (String) session.getAttribute("TIMESTART");
            TblHistoryDAO historyDao = new TblHistoryDAO();
            boolean isDone = historyDao.checkFinishedQuiz(subjectId, email, timeStart);
            if (!isDone) {
                int totalQuest = (int) session.getAttribute("TOTALQUEST");
                int numOfCorrectAns = 0;
                TblQuestionDAO dao = new TblQuestionDAO();
                int removedQuest = 0;
                for (int i = 1; i <= totalQuest; i++) {
                    String result = request.getParameter("answer" + i);
                    if (result != null) {
                        String[] tokens = result.split("-");
                        int questionId = Integer.parseInt(tokens[0]);
                        boolean isValid = dao.checkValidQuest(questionId);
                        if (isValid) {
                            int answerId = Integer.parseInt(tokens[1]);
                            boolean correct = dao.checkCorrectAns(questionId, answerId);
                            if (correct) {
                                numOfCorrectAns++;
                            }
                        } else {
                            removedQuest++;
                        }
                    }
                }
                totalQuest = totalQuest - removedQuest;
                float score = ((float) numOfCorrectAns / totalQuest) * MAX_SCORE;
                score = Math.round(score * 100.00) / (float)100.00;
                isDone = true;
                TblHistoryDTO historyDto = new TblHistoryDTO(subjectId, email, timeStart, totalQuest, numOfCorrectAns, score, isDone);
                boolean result = historyDao.updateHistory(historyDto);
                if (result) {
                    session.removeAttribute("QUESTIONLISTINQUIZ");
                    session.removeAttribute("DEADLINE");
                    session.removeAttribute("TIMESTART");
                    session.removeAttribute("TOTALQUEST");
                }
            }
        } catch (NamingException ex) {
            url = "errPage";
            log("SubmitServlet_Naming " + ex.getMessage());
        } catch (SQLException ex) {
            url = "errPage";
            log("SubmitServlet_SQL " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
