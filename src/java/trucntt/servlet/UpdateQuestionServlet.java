/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trucntt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import trucntt.tblanswer.TblAnswerDAO;
import trucntt.tblquestion.TblQuestionCreateErrs;
import trucntt.tblquestion.TblQuestionDAO;

/**
 *
 * @author DELL
 */
@WebServlet(name = "UpdateQuestionServlet", urlPatterns = {"/UpdateQuestionServlet"})
public class UpdateQuestionServlet extends HttpServlet {

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

        String questionId = request.getParameter("questionId");
        String subjectId = request.getParameter("cboUpdateSubject");
        String content = request.getParameter("txtUpdateContent");
        String updateStatus = request.getParameter("cboUpdateStatus");

        ServletContext context = request.getServletContext();
        Map<String, String> indexMap = (Map<String, String>) context.getAttribute("INDEXMAP");
        String url = indexMap.get("searchQuestion");
        try {
            int quesId = Integer.parseInt(questionId);
            boolean status = false;
            if ("Active".equals(updateStatus)) {
                status = true;
            }
            if (content != null) {
                content = content.trim();
                int answer1Id = Integer.parseInt(request.getParameter("answerId1"));
                String answer1 = request.getParameter("txtUpdateAnswer1").trim();
                int answer2Id = Integer.parseInt(request.getParameter("answerId2"));
                String answer2 = request.getParameter("txtUpdateAnswer2").trim();
                int answer3Id = Integer.parseInt(request.getParameter("answerId3"));
                String answer3 = request.getParameter("txtUpdateAnswer3").trim();
                int answer4Id = Integer.parseInt(request.getParameter("answerId4"));
                String answer4 = request.getParameter("txtUpdateAnswer4").trim();
                String correctAns = request.getParameter("rdoAnswer" + questionId);

                boolean foundErr = false;
                TblQuestionCreateErrs errs = new TblQuestionCreateErrs();
                if (content.length() < 1 || content.length() > 250) {
                    foundErr = true;
                    errs.setContentLengthErr("Question requires 1 - 250 chars");
                }
                if (answer1.length() < 1 || answer1.length() > 100) {
                    foundErr = true;
                    errs.setAnswer1LengthErr("Answer requires 1 - 100 chars");
                }
                if (answer2.length() < 1 || answer2.length() > 100) {
                    foundErr = true;
                    errs.setAnswer2LengthErr("Answer requires 1 - 100 chars");
                }
                if (answer3.length() < 1 || answer3.length() > 100) {
                    foundErr = true;
                    errs.setAnswer3LengthErr("Answer requires 1 - 100 chars");
                }
                if (answer4.length() < 1 || answer4.length() > 100) {
                    foundErr = true;
                    errs.setAnswer4LengthErr("Answer requires 1 - 100 chars");
                }

                if (!foundErr) {
                    TblQuestionDAO questionDao = new TblQuestionDAO();
                    questionDao.updateQuestion(quesId, subjectId, content, status);
                    TblAnswerDAO answerDao = new TblAnswerDAO();
                    answerDao.updateAnswer(answer1Id, answer1, correctAns.equals("1"));
                    answerDao.updateAnswer(answer2Id, answer2, correctAns.equals("2"));
                    answerDao.updateAnswer(answer3Id, answer3, correctAns.equals("3"));
                    answerDao.updateAnswer(answer4Id, answer4, correctAns.equals("4"));
                } else {
                    request.setAttribute("UPDATEERRS", errs);
                }
            } else {
                TblQuestionDAO questionDao = new TblQuestionDAO();
                questionDao.updateStatus(quesId, status);
            }

        } catch (NamingException ex) {
            url = indexMap.get("errPage");
            log("UpdateQuestionServlet_Naming " + ex.getMessage());
        } catch (SQLException ex) {
            url = indexMap.get("errPage");
            log("UpdateQuestionServlet_SQL " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
