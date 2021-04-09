/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trucntt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
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
@WebServlet(name = "createNewQuestionServlet", urlPatterns = {"/createNewQuestionServlet"})
public class CreateNewQuestionServlet extends HttpServlet {

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
        
        String subjectId = request.getParameter("cboChooseSubject");
        String questionContent = request.getParameter("txtQuestionContent").trim();
        String answer1 = request.getParameter("txtAnswer1").trim();
        String answer2 = request.getParameter("txtAnswer2").trim();
        String answer3 = request.getParameter("txtAnswer3").trim();
        String answer4 = request.getParameter("txtAnswer4").trim();
        String correctAns = request.getParameter("answer");
        
        ServletContext context = request.getServletContext();
        Map<String, String> indexMap = (Map<String, String>)context.getAttribute("INDEXMAP");
        String url = indexMap.get("createQuestionPage");
        
        try {
            boolean foundErr = false;
            TblQuestionCreateErrs errs = new TblQuestionCreateErrs();
            if (questionContent.length() < 1 || questionContent.length() > 250) {
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
            if (correctAns == null) {
                foundErr = true;
                errs.setCorrectAnsErr("Question must has true answer");
            }
            if (answer1.equals(answer2) || answer1.equals(answer3) || answer1.equals(answer4)
                    || answer2.equals(answer3) || answer2.equals(answer4) || answer3.equals(answer4)) {
                foundErr = true;
                errs.setDuplicatedAns("The answers must not be the same.");
            }
            
            if (!foundErr) {
                Date createDate = new Date();
                TblQuestionDAO dao = new TblQuestionDAO();
                int questionId = dao.createQuestion(subjectId, questionContent, createDate, true);
                if (questionId > -1) {
                    TblAnswerDAO ansDao = new TblAnswerDAO();
                    if (ansDao.createAnswer(questionId, answer1, "1".equals(correctAns)) 
                    && ansDao.createAnswer(questionId, answer2, "2".equals(correctAns))
                    && ansDao.createAnswer(questionId, answer3, "3".equals(correctAns))
                    && ansDao.createAnswer(questionId, answer4, "4".equals(correctAns))){
                        url = indexMap.get("searchQuestion");
                    }
                }
            } else {
                request.setAttribute("CREATEQUESERR", errs);
            }
        } catch (NamingException ex) {
            url = indexMap.get("errPage");
            log("createNewQuestionServlet_Naming " + ex.getMessage());
        } catch (SQLException ex) {
            url = indexMap.get("errPage");
            log("createNewQuestionServlet_SQL " + ex.getMessage());
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
