package controllers;

import dao.StockDAO;
import dao.UserDAO;
import dto.Stock;
import dto.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * MainController.java Xử lý hiển thị danh sách, tìm kiếm và sắp xếp stock, đồng
 * thời làm entry point cho ứng dụng.
 */
public class MainController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private StockDAO dao = new StockDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số tìm kiếm và sắp xếp
        String search = request.getParameter("search");
        String sort = request.getParameter("sort");
        List<Stock> list;

        try {
            if (search != null && !search.trim().isEmpty()) {
                // Nếu có param search → tìm kiếm
                list = dao.search(search.trim());
            } else if ("asc".equalsIgnoreCase(sort)) {
                // Nếu sort=asc → xếp giá tăng dần
                list = dao.findAllOrderByPriceAsc();
            } else if ("desc".equalsIgnoreCase(sort)) {
                // Nếu sort=desc → xếp giá giảm dần
                list = dao.findAllOrderByPriceDesc();
            } else {
                // Mặc định: lấy tất cả không sắp xếp
                list = dao.findAll();
            }
        } catch (Exception e) {
            throw new ServletException("Error loading stock list", e);
        }

        // Đẩy dữ liệu xuống JSP
        request.setAttribute("listStock", list);
        request.getRequestDispatcher("/stockList.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "login.jsp";
        try {
            String action = request.getParameter("action");
            if ("Logout".equals(action)) {
                response.sendRedirect("LogoutController");
            } else if ("CreateAlert".equals(action)) {
                request.getRequestDispatcher("CreateAlertController").forward(request, response);
            } else if ("SearchAlert".equals(action)) {
                request.getRequestDispatcher("SearchAlertController").forward(request, response);
            } else if ("DeleteAlert".equals(action)) {
                request.getRequestDispatcher("DeleteAlertController").forward(request, response);
            } else if ("UpdateAlert".equals(action)) {
                request.getRequestDispatcher("UpdateAlertController").forward(request, response);
            } else if ("CreateUser".equals(action)) {
                request.getRequestDispatcher("CreateUserController").forward(request, response);
            } else if ("SearchUser".equals(action)) {
                request.getRequestDispatcher("SearchUserController").forward(request, response);
            } else if ("DeleteUser".equals(action)) {
                request.getRequestDispatcher("DeleteUserController").forward(request, response);
            } else if ("UpdateUser".equals(action)) {
                request.getRequestDispatcher("UpdateUserController").forward(request, response);
            } else if ("EditUser".equals(action)) {
                String userID = request.getParameter("userID");
                User user = new UserDAO().getUserByID(userID); // Hàm này bạn cần có trong UserDAO
                request.setAttribute("USER", user);
                request.getRequestDispatcher("editUser.jsp").forward(request, response);
            } else {
                response.sendRedirect(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log("Error in MainControllers", e);
        }
    }

    @Override
    public String getServletInfo() {
        return "MainController: handles listing, searching, and sorting stocks";
    }
}
