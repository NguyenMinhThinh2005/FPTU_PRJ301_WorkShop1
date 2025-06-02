/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.AlertDAO;
import dto.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
public class UpdateAlertController extends HttpServlet {

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
        try {
            User loginUser = (User) request.getSession().getAttribute("LOGIN_USER");
            if (loginUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            String userID = loginUser.getUserID();
            int alertID = Integer.parseInt(request.getParameter("alertID"));
            float threshold = Float.parseFloat(request.getParameter("threshold"));
            String status = request.getParameter("status");

            if (threshold <= 0) {
                request.setAttribute("MSG", "Threshold must be greater than 0.");
                request.getRequestDispatcher("alertList.jsp").forward(request, response);
                return;
            }

            if (!"active".equals(status) && !"inactive".equals(status)) {
                request.setAttribute("MSG", "Invalid status value.");
                request.getRequestDispatcher("alertList.jsp").forward(request, response);
                return;
            }

            AlertDAO dao = new AlertDAO();
            if (dao.update(alertID, userID, threshold, status)) {
                request.setAttribute("MSG", "Alert updated successfully.");
            } else {
                request.setAttribute("MSG", "Failed to update alert. You can only update your own alerts.");
            }
            request.getRequestDispatcher("alertList.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("MSG", "Invalid input format.");
            request.getRequestDispatcher("alertList.jsp").forward(request, response);
        } catch (Exception e) {
            log("Error at UpdateAlertController: " + e.toString());
            request.setAttribute("MSG", "An error occurred while updating the alert.");
            request.getRequestDispatcher("alertList.jsp").forward(request, response);
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
