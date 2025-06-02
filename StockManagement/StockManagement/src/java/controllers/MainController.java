/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

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
public class MainController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String LOGIN = "Login";
    private static final String LOGIN_CONTROLLER = "LoginController";
    private static final String LOGOUT="Logout";
    private static final String LOGOUT_CONTROLLER="LogoutController";
    private static final String SEARCH = "Search";
    private static final String SEARCH_CONTROLLER = "SearchController";
    private static final String CREATE = "Create";
    private static final String CREATE_CONTROLLER = "CreateController";
    private static final String UPDATE = "Update";
    private static final String UPDATE_CONTROLLER = "UpdateController";
    private static final String DELETE = "Delete";
    private static final String DELETE_CONTROLLER = "DeleteController";
    private static final String SEARCH_TRANSACTION = "SearchTransaction";
    private static final String SEARCH_TRANSACTION_CONTROLLER = "SearchTransactionController";
    private static final String CREATE_TRANSACTION = "CreateTransaction";
    private static final String CREATE_TRANSACTION_CONTROLLER = "CreateTransactionController";
    private static final String UPDATE_TRANSACTION = "UpdateTransaction";
    private static final String UPDATE_TRANSACTION_CONTROLLER = "UpdateTransactionController";
    private static final String SEARCH_ALERT = "SearchAlert";
    private static final String SEARCH_ALERT_CONTROLLER = "SearchAlertController";
    private static final String CREATE_ALERT = "CreateAlert";
    private static final String CREATE_ALERT_CONTROLLER = "CreateAlertController";
    private static final String UPDATE_ALERT = "UpdateAlert";
    private static final String UPDATE_ALERT_CONTROLLER = "UpdateAlertController";
    private static final String DELETE_ALERT = "DeleteAlert";
    private static final String DELETE_ALERT_CONTROLLER = "DeleteAlertController";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url= "login.jsp";
        try {
            String action= request.getParameter("action");
            if(LOGIN.equals(action)){
                url= LOGIN_CONTROLLER;
            }else if(LOGOUT.equals(action)){
                url= LOGOUT_CONTROLLER;
            }else if(SEARCH.equals(action)) {
                url = SEARCH_CONTROLLER;
            }else if(CREATE.equals(action)) {
                url = CREATE_CONTROLLER;
            }else if(UPDATE.equals(action)) {
                url = UPDATE_CONTROLLER;
            }else if(DELETE.equals(action)) {
                url = DELETE_CONTROLLER;
            }else if(SEARCH_TRANSACTION.equals(action)) {
                url = SEARCH_TRANSACTION_CONTROLLER;
            }else if(CREATE_TRANSACTION.equals(action)) {
                url = CREATE_TRANSACTION_CONTROLLER;
            }else if(UPDATE_TRANSACTION.equals(action)) {
                url = UPDATE_TRANSACTION_CONTROLLER;
            }else if(SEARCH_ALERT.equals(action)) {
                url = SEARCH_ALERT_CONTROLLER;
            }else if(CREATE_ALERT.equals(action)) {
                url = CREATE_ALERT_CONTROLLER;
            }else if(UPDATE_ALERT.equals(action)) {
                url = UPDATE_ALERT_CONTROLLER;
            }else if(DELETE_ALERT.equals(action)) {
                url = DELETE_ALERT_CONTROLLER;
            }
        } catch (Exception e) {
            log("Error at MainController: "+ e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
