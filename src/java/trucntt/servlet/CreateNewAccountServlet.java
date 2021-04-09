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
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import trucntt.tblaccount.TblAccountCreateErrors;
import trucntt.tblaccount.TblAccountDAO;
import trucntt.utilities.SHA256Helper;

/**
 *
 * @author DELL
 */
@WebServlet(name = "CreateNewAccountServlet", urlPatterns = {"/CreateNewAccountServlet"})
public class CreateNewAccountServlet extends HttpServlet {

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
        String name = request.getParameter("txtName").trim();
        String password = request.getParameter("txtPassword").trim();
        String confirm = request.getParameter("txtConfirm");

        ServletContext context = request.getServletContext();
        Map<String, String> indexMap = (Map<String, String>) context.getAttribute("INDEXMAP");
        String url = indexMap.get("createErrPage");

        try {
            boolean foundErr = false;
            TblAccountCreateErrors errs = new TblAccountCreateErrors();
            TblAccountDAO dao = new TblAccountDAO();

            if (!email.matches("\\w+@\\w+[.]\\w+([.]\\w+)?") || email.length() > 50) {
                foundErr = true;
                errs.setEmailFormatErr("Email invalidate");
            }
            if (dao.checkPrimaryKey(email)) {
                foundErr = true;
                errs.setEmailExistedErr("Email is existed");
            }
            if (name.length() < 1 || name.length() > 50) {
                foundErr = true;
                errs.setNameLengthErr("Name requires 1 to 50 chars");
            }
            if (password.length() < 6 || password.length() > 20) {
                foundErr = true;
                errs.setPasswordLengthErr("Password requires 6 to 20 chars");
            } else if (!password.equals(confirm)) {
                foundErr = true;
                errs.setConfirmNotMatched("Confirm must match password");
            }

            if (!foundErr) {
                String encryptedPass = SHA256Helper.encryptPassword(password);

                boolean result = dao.createAccount(email, name, encryptedPass);
                if (result) {
                    url = indexMap.get("");
                }
            } else {
                request.setAttribute("CREATEACCERR", errs);
            }
        } catch (NoSuchAlgorithmException ex) {
            url = indexMap.get("errPage");
            log("CreateNewAccountServlet_NoSuchAlgorithm " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            url = indexMap.get("errPage");
            log("CreateNewAccountServlet_UnsupportedEncoding " + ex.getMessage());
        } catch (NamingException ex) {
            url = indexMap.get("errPage");
            log("CreateNewAccountServlet_Naming " + ex.getMessage());
        } catch (SQLException ex) {
            url = indexMap.get("errPage");
            log("CreateNewAccountServlet_SQL" + ex.getMessage());
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
