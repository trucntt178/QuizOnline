/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trucntt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import trucntt.tblaccount.TblAccountDAO;
import trucntt.tblaccount.TblAccountDTO;
import trucntt.utilities.SHA256Helper;

/**
 *
 * @author DELL
 */
public class LoginServlet extends HttpServlet {

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

        String email = request.getParameter("txtEmail").trim();
        String password = request.getParameter("txtPassword").trim();

        String invalidMsg = "User not found";
        String urlRewriting = "loginErr?"
                + "&invalidMsg=" + invalidMsg;
        try {
            TblAccountDAO dao = new TblAccountDAO();
            String encryptedPass = SHA256Helper.encryptPassword(password);
            TblAccountDTO dto = dao.checkLogin(email, encryptedPass);
            if (dto != null) {
                urlRewriting = "loadSubject";
                HttpSession session = request.getSession();
                session.setAttribute("EMAIL", dto.getEmail());
                session.setAttribute("NAME", dto.getName());
                session.setAttribute("ISADMIN", dto.isRole());
            }
        } catch (NoSuchAlgorithmException ex) {
            urlRewriting = "errPage";
            log("LoginServlet_NoSuchAlgorithm " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            urlRewriting = "errPage";
            log("LoginServlet_UnsupportedEncoding " + ex.getMessage());
        } catch (NamingException ex) {
            urlRewriting = "errPage";
            log("LoginServlet_Naming " + ex.getMessage());
        } catch (SQLException ex) {
            urlRewriting = "errPage";
            log("LoginServlet_SQL " + ex.getMessage());
        } finally {
            response.sendRedirect(urlRewriting);
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
