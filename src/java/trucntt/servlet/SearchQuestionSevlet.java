/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trucntt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import trucntt.tblquestion.TblQuestionDAO;
import trucntt.tblquestion.TblQuestionDTO;
import trucntt.tblsubject.TblSubjectDTO;

/**
 *
 * @author DELL
 */
@WebServlet(name = "SearchQuestionSevlet", urlPatterns = {"/SearchQuestionSevlet"})
public class SearchQuestionSevlet extends HttpServlet {

    private final int NUM_OF_RECORDS = 20;
    private final int NUM_OF_ANSWERS_IN_QUESTION = 4;

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

        int currPage = 1, firstRecord, lastRecord;
        String button = request.getParameter("btnAction");
        String subjectId = request.getParameter("subjectId");
        String subjectName = request.getParameter("subjectName");
        String searchContentValue = request.getParameter("txtSearchContentValue");
        String searchStatusValue = request.getParameter("cboSearchStatus");
        String currentPage = request.getParameter("currPage");

        ServletContext context = request.getServletContext();
        Map<String, String> indexMap = (Map<String, String>) context.getAttribute("INDEXMAP");
        String urlRewriting = indexMap.get("searchQuestionPage");
        try {
            HttpSession session = request.getSession();
            if (subjectId == null) {               
                TblSubjectDTO subject = (TblSubjectDTO) session.getAttribute("SUBJECT");
                subjectId = subject.getSubjectId();
            } else {
                TblSubjectDTO subjectDto = new TblSubjectDTO(subjectId, subjectName);
                session.setAttribute("SUBJECT", subjectDto);
            }
            
            if (searchContentValue == null) {
                searchContentValue = "";
            } else if (searchContentValue.length() > (2 ^ 31 - 1)) {
                searchContentValue = "";
                request.setAttribute("SEARCHERR", "Search value too long");
            } else {
                searchContentValue = searchContentValue.trim();
            }
            if (searchStatusValue == null) {
                searchStatusValue = "All";
            }
            TblQuestionDAO dao = new TblQuestionDAO();
            int numOfPage = dao.countNumOfQuestionPage(subjectId, searchContentValue, searchStatusValue, NUM_OF_RECORDS);
            if (currentPage != null) {
                currPage = Integer.parseInt(currentPage);
            }
            if ("<".equals(button) && currPage > 1) {
                currPage--;
            } else if (">".equals(button) && currPage < numOfPage) {
                currPage++;
            } else if (currPage > numOfPage) {
                currPage = 1;
            }

            firstRecord = NUM_OF_RECORDS * NUM_OF_ANSWERS_IN_QUESTION * (currPage - 1) + 1;
            lastRecord = NUM_OF_RECORDS * NUM_OF_ANSWERS_IN_QUESTION * currPage;

            dao.searchQuestion(subjectId, searchContentValue, searchStatusValue, firstRecord, lastRecord);
            List<TblQuestionDTO> questionList = dao.getQuestionList();
            request.setAttribute("QUESTIONLIST", questionList);

            urlRewriting += "?"
                    + "&txtSearchContentValue=" + searchContentValue
                    + "&cboSearchStatus=" + searchStatusValue
                    + "&currPage=" + currPage
                    + "&numOfPage=" + numOfPage;
        } catch (NamingException ex) {
            urlRewriting = indexMap.get("errPage");
            log("SearchQuestionSevlet_Naming " + ex.getMessage());
        } catch (SQLException ex) {
            urlRewriting = indexMap.get("errPage");
            log("SearchQuestionSevlet_SQL " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(urlRewriting);
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
