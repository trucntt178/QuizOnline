/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trucntt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
import trucntt.tblquestion.TblQuestionDTO;
import trucntt.tblsubject.TblSubjectDAO;
import trucntt.tblsubject.TblSubjectDTO;

/**
 *
 * @author DELL
 */
@WebServlet(name = "LoadQuestionServlet", urlPatterns = {"/LoadQuestionServlet"})
public class LoadQuestionServlet extends HttpServlet {

    public static final int MINUTE = 60 * 1000;

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

        String subjectId = request.getParameter("subjectId");
        String url = "takeQuizPage";
        try {
            List<TblQuestionDTO> questionList;
            Date deadline = null;
            String timeStart = null;
            TblSubjectDTO subjectDto;
            HttpSession session = request.getSession();
            if (subjectId == null) {
                subjectDto = (TblSubjectDTO) session.getAttribute("SUBJECT");
                subjectId = subjectDto.getSubjectId();
            } else {
                TblSubjectDAO subjectDao = new TblSubjectDAO();
                subjectDto = subjectDao.getSubject(subjectId);
                session.setAttribute("SUBJECT", subjectDto);
            }

            if (subjectDto != null) {
                TblHistoryDAO historyDao = new TblHistoryDAO();
                TblQuestionDAO questionDao = new TblQuestionDAO();
                String email = (String) session.getAttribute("EMAIL");
                Date takeDate = historyDao.findUnfinishedQuiz(subjectId, email);
                System.out.println(takeDate);
                if (takeDate != null) {
                    timeStart = new SimpleDateFormat("dd-M-yyyy HH:mm:ss").format(takeDate);
                    questionDao.getQuestion(subjectId, subjectDto.getNumOfQuestion(), email, timeStart);
                    questionList = questionDao.getQuestionList();
                    for (TblQuestionDTO dto : questionList) {
                        System.out.println(dto.getId());
                    }
                    if (questionList != null) {
                        deadline = new Date(takeDate.getTime() + TimeUnit.MINUTES.toMillis(subjectDto.getTime()));
                    }
                } else {
                    System.out.println(subjectId);
                    questionDao.getQuestion(subjectId, subjectDto.getNumOfQuestion(), null, null);
                    questionList = questionDao.getQuestionList();
                    if (questionList != null) {

                        Date now = new Date();
                        timeStart = new SimpleDateFormat("dd-M-yyyy HH:mm:ss").format(now);

                        TblHistoryDTO historyDto = new TblHistoryDTO(subjectId, email, timeStart, questionList.size(), 0, 0, false);
                        historyDao.saveHistory(historyDto);
                        for (TblQuestionDTO dto : questionList) {
                            questionDao.saveQuestionInQuiz(subjectId, email, timeStart, dto.getId());
                        }
                        deadline = new Date(now.getTime() + TimeUnit.MINUTES.toMillis(subjectDto.getTime()));
                    }
                }
                if (questionList != null && deadline != null) {
                    session.setAttribute("QUESTIONLISTINQUIZ", questionList);
                    session.setAttribute("DEADLINE", deadline.getTime());
                    session.setAttribute("TIMESTART", timeStart);
                    session.setAttribute("TOTALQUEST", questionList.size());
                }
            }
        } catch (NamingException ex) {
            url = "errPage";
            log("LoadQuestionServlet_Naming " + ex.getMessage());
        } catch (SQLException ex) {
            url = "errPage";
            log("LoadQuestionServlet_SQL " + ex.getMessage());
        } catch (ParseException ex) {
            url = "errPage";
            log("LoadQuestionServlet_Parse " + ex.getMessage());
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
