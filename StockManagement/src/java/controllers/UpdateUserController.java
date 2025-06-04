/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dao.UserDAO;
import dto.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/UpdateUserController")
public class UpdateUserController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        User loginUser = (User) request.getSession().getAttribute("LOGIN_USER");
        UserDAO dao = new UserDAO();

        try {
            // Block access if not logged in
            if (loginUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // Get data from form
            String userID = request.getParameter("userID");
            String fullName = request.getParameter("fullName");
            String roleID = request.getParameter("roleID");
            String password = request.getParameter("password");

            // Basic validation
            if (fullName == null || fullName.trim().isEmpty()
                    || roleID == null || roleID.trim().isEmpty()
                    || password == null || password.trim().isEmpty()) {
                request.setAttribute("MSG", "All fields are required.");
                request.setAttribute("USER_LIST", dao.getAll(""));
                request.getRequestDispatcher("userList.jsp").forward(request, response);
                return;
            }

            // Create user object and update
            User user = new User(userID, fullName, roleID, password);
            boolean updated = dao.update(user);

            if (updated) {
                request.setAttribute("MSG", "User updated successfully.");
            } else {
                request.setAttribute("MSG", "Update failed. Please try again.");
            }

            // Reload user list
            List<User> list = dao.getAll("");
            request.setAttribute("USER_LIST", list);
            request.getRequestDispatcher("userList.jsp").forward(request, response);

        } catch (Exception e) {
            log("Error at UpdateUserController: " + e.toString());
            request.setAttribute("MSG", "An error occurred while updating the user.");
            try {
                request.setAttribute("USER_LIST", dao.getAll(""));
            } catch (Exception ex) {
                log("Error loading user list: " + ex.toString());
            }
            request.getRequestDispatcher("userList.jsp").forward(request, response);
        }
    }

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
        return "UpdateUserController handles user update logic";
    }
}
