/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.StockDAO;
import dao.TransactionDAO;
import dto.Transaction;
import dto.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet("/CreateTransactionController")
public class CreateTransactionController extends HttpServlet {

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
            String ticker = request.getParameter("ticker");
            String type = request.getParameter("type");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            float price = Float.parseFloat(request.getParameter("price"));

            if (quantity <= 0 || price <= 0) {
                request.setAttribute("MSG", "Quantity and price must be greater than 0.");
                request.getRequestDispatcher("createTransaction.jsp").forward(request, response);
                return;
            }

            StockDAO stockDAO = new StockDAO();
            if (!stockDAO.isTickerExist(ticker)) {
                request.setAttribute("MSG", "Ticker does not exist.");
                request.getRequestDispatcher("createTransaction.jsp").forward(request, response);
                return;
            }

            User loginUser = (User) request.getSession().getAttribute("LOGIN_USER");
            if (loginUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            Transaction transaction = new Transaction(0, loginUser.getUserID(), ticker, type, quantity, price, "pending");
            TransactionDAO transactionDAO = new TransactionDAO();
            if (transactionDAO.create(transaction)) {
                request.setAttribute("MSG", "Transaction created successfully.");
            } else {
                request.setAttribute("MSG", "Failed to create transaction.");
            }
            request.getRequestDispatcher("transactionList.jsp").forward(request, response);
        } catch (Exception e) {
            log(e.getMessage());
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
