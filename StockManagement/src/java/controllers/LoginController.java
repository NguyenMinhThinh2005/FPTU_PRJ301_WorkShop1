package controllers;

import dao.StockDAO;
import dto.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userID = req.getParameter("userID");
        String password = req.getParameter("password");
        try {
            User user = new StockDAO().login(userID, password);
            if (user != null) {
                req.getSession().setAttribute("LOGIN_USER", user);
                resp.sendRedirect(req.getContextPath() + "/MainController");
            } else {
                req.setAttribute("MSG", "Invalid user or password");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect("login.jsp");
    }
}
