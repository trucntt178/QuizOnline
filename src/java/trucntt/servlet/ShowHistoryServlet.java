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
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import trucntt.tblhistory.TblHistoryDAO;
import trucntt.tblhistory.TblHistoryDTO;
import trucntt.tblsubject.TblSubjectDAO;
import trucntt.tblsubject.TblSubjectDTO;

/**
 *
 * @author DELL
 */
@WebServlet(name = "ShowHistoryServlet", urlPatterns = {"/ShowHistoryServlet"})
public class ShowHistoryServlet extends HttpServlet {

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
        ServletContext context = request.getServletContext();
        Map<String, String> indexMap = (Map<String, String>) context.getAttribute("INDEXMAP");
        String url = indexMap.get("historyPage");
        try {
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
            String email = (String) session.getAttribute("EMAIL");
            TblHistoryDAO historyDao = new TblHistoryDAO();
            Date takeDate = historyDao.findUnfinishedQuiz(subjectId, email);
            if (takeDate != null) {
                request.setAttribute("CONTINUE", "Continue the quiz");
            }       
            historyDao.loadHistory(subjectId, email);
            List<TblHistoryDTO> historyList = historyDao.getHistoryList();
            if (historyList != null) {
                request.setAttribute("HISTORYLIST", historyList);
            }

        } catch (NamingException ex) {
            url = indexMap.get("errPage");
            log("ShowHistoryServlet_Naming " + ex.getMessage());
        } catch (SQLException ex) {
            url = indexMap.get("errPage");
            log("ShowHistoryServlet_SQL " + ex.getMessage());
        } catch (ParseException ex) {
            url = indexMap.get("errPage");
            log("ShowHistoryServlet_Parse " + ex.getMessage());
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
