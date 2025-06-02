package controllers;

import dao.StockDAO;
import dto.Stock;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/UpdateController")
public class UpdateController extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getSession().getAttribute("LOGIN_USER") == null) {
      resp.sendRedirect(req.getContextPath() + "/login.jsp");
      return;
    }
    req.setCharacterEncoding("UTF-8");
    try {
      String ticker = req.getParameter("ticker");
      String name   = req.getParameter("name");
      String sector = req.getParameter("sector");
      float price   = Float.parseFloat(req.getParameter("price"));

      Stock s = new Stock(ticker, name, sector, price, true);
      new StockDAO().update(s);
    } catch (Exception e) {
      log("Update error", e);
    }
    resp.sendRedirect(req.getContextPath() + "/MainController");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.sendRedirect(req.getContextPath() + "/MainController");
  }
}
