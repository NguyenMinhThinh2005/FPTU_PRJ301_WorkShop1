package controllers;

import dao.StockDAO;
import dto.Stock;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/SearchController")
public class SearchController extends HttpServlet {
    private StockDAO dao = new StockDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<Stock> list;
        try {
            // Bắt cả SQLException và ClassNotFoundException
            list = dao.search(keyword != null ? keyword.trim() : "");
        } catch (Exception e) {
            // Gói mọi ngoại lệ thành ServletException để deploy được
            throw new ServletException(e);
        }
        req.setAttribute("listStock", list);
        req.getRequestDispatcher("/stockList.jsp").forward(req, resp);
    }
}
