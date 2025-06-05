package controllers;

import dao.TransactionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "UpdateTransactionController", urlPatterns = {"/UpdateTransactionController"})
public class UpdateTransactionController extends HttpServlet {

    private static final String ERROR = "transactionList.jsp";
    private static final String SUCCESS = "transactionList.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("LOGIN_USER") == null) {
                url = "login.jsp";
            } else {
                String transactionId = request.getParameter("transactionId");
                String type = request.getParameter("type");
                String quantity = request.getParameter("quantity");
                String price = request.getParameter("price");
                String status = request.getParameter("status");

                boolean check = true;
                if (quantity == null || quantity.trim().isEmpty()) {
                    request.setAttribute("ERROR_QUANTITY", "Quantity is required!");
                    check = false;
                } else {
                    try {
                        int qty = Integer.parseInt(quantity);
                        if (qty <= 0) {
                            request.setAttribute("ERROR_QUANTITY", "Quantity must be greater than 0!");
                            check = false;
                        }
                    } catch (NumberFormatException e) {
                        request.setAttribute("ERROR_QUANTITY", "Quantity must be a number!");
                        check = false;
                    }
                }

                if (price == null || price.trim().isEmpty()) {
                    request.setAttribute("ERROR_PRICE", "Price is required!");
                    check = false;
                } else {
                    try {
                        double prc = Double.parseDouble(price);
                        if (prc <= 0) {
                            request.setAttribute("ERROR_PRICE", "Price must be greater than 0!");
                            check = false;
                        }
                    } catch (NumberFormatException e) {
                        request.setAttribute("ERROR_PRICE", "Price must be a number!");
                        check = false;
                    }
                }

                if (check) {
                    TransactionDAO dao = new TransactionDAO();
                    boolean update = dao.update(Integer.parseInt(transactionId), Integer.parseInt(quantity), Double.parseDouble(price), type, status);
                    if (update) {
                        request.setAttribute("SUCCESS", "Update transaction successfully!");
                        url = SUCCESS;
                    } else {
                        request.setAttribute("ERROR", "Update transaction failed!");
                    }
                }
            }
        } catch (Exception e) {
            log("Error at UpdateTransactionController: " + e.toString());
            request.setAttribute("ERROR", "An error occurred while updating the transaction!");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
        return "Short description";
    }
} 