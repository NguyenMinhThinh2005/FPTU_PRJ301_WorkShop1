/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dao.UserDAO;
import dto.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteUserController")
public class DeleteUserController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            // Check if the user is logged in
            User loginUser = (User) request.getSession().getAttribute("LOGIN_USER");
            if (loginUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            String loginUserID = loginUser.getUserID();
            String userID = request.getParameter("userID");

            UserDAO dao = new UserDAO();

            // Prevent self-deletion
            if (loginUserID.equals(userID)) {
                request.setAttribute("MSG", "You cannot delete your own account.");
            } else {
                boolean deleted = dao.delete(userID);
                if (deleted) {
                    request.setAttribute("MSG", "User deleted successfully.");
                } else {
                    request.setAttribute("MSG", "Failed to delete user.");
                }
            }

            request.setAttribute("list", dao.getAllUsers());
            request.getRequestDispatcher("userList.jsp").forward(request, response);

        } catch (Exception e) {
            log("Error at DeleteUserController: " + e.toString());
            request.setAttribute("MSG", "An error occurred while deleting the user.");
            try {
                UserDAO dao = new UserDAO();
                request.setAttribute("list", dao.getAllUsers());
            } catch (Exception ex) {
                log("Error loading user list: " + ex.toString());
            }
            request.getRequestDispatcher("userList.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "DeleteUserController handles deleting a user, not allowing self-deletion.";
    }
    // </editor-fold>
}
