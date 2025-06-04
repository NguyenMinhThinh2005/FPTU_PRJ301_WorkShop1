package controllers;

import dao.UserDAO;
import dto.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/CreateUserController")
public class CreateUserController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            // Kiểm tra người dùng đã đăng nhập
            User loginUser = (User) request.getSession().getAttribute("LOGIN_USER");
            if (loginUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // Lấy dữ liệu từ form
            String userID = request.getParameter("userID");
            String fullName = request.getParameter("fullName");
            String roleID = request.getParameter("roleID");
            String password = request.getParameter("password");

            UserDAO dao = new UserDAO();

            // Kiểm tra userID trùng
            if (dao.checkDuplicate(userID)) {
                request.setAttribute("MSG", "UserID đã tồn tại!");
                request.getRequestDispatcher("createUser.jsp").forward(request, response);
                return;
            }

            // Thêm người dùng mới
            User newUser = new User(userID, fullName, roleID, password);
            boolean result = dao.insert(newUser);

            if (result) {
                request.setAttribute("MSG", "Thêm người dùng thành công!");
            } else {
                request.setAttribute("MSG", "Không thể thêm người dùng.");
            }

            // Lấy danh sách user và forward sang userList.jsp
            request.setAttribute("USER_LIST", dao.getAllUsers());
            request.getRequestDispatcher("userList.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
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
        return "Create new user";
    }
}
