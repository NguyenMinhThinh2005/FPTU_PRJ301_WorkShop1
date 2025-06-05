<%-- 
    Document   : transactionList
    Created on : Apr 23, 2025, 9:35:16 AM
    Author     : admin
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="dto.Transaction"%>
<%@page import="dto.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaction List Page</title>
    </head>
    <body>
        <%
            User loginUser = (User) session.getAttribute("LOGIN_USER");
            if (loginUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>
        <h1>Welcome: <%= loginUser.getFullName()%></h1>
        <form action="MainController" method="POST">
            <button type="submit" name="action" value="Logout">Logout</button> </br>
            <input type="text" name="search" placeholder="Search">
            <button type="submit" name="action" value="SearchTransaction">Search</button>
        </form>
        <a href="createTransaction.jsp">Create New Transaction</a><br/>
        <a href="MainController">Go to Stock List</a><br/>
        <a href="SearchAlertController">Go to Alert List</a><br/>
        <%
            String MSG = (String) request.getAttribute("MSG");
            if (MSG != null) {
        %>
        <h3> <%= MSG%> </h3>
        <%
            }
            ArrayList<Transaction> list = (ArrayList<Transaction>) request.getAttribute("list");
            if (list != null) {
        %>
        <table>
            <tr>
                <th>No</th>
                <th>User ID</th>
                <th>Ticker</th>
                <th>Type</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <%
                int count = 0;
                for (Transaction transaction : list) {
                    count++;
            %>
            <tr>
                <form action="UpdateTransactionController" method="post">
                    <td><%= count %></td>
                    <td><%= transaction.getUserID() %></td>
                    <td><%= transaction.getTicker() %></td>
                    <td>
                        <select name="type">
                            <option value="buy" <%= "buy".equalsIgnoreCase(transaction.getType()) ? "selected" : "" %>>Buy</option>
                            <option value="sell" <%= "sell".equalsIgnoreCase(transaction.getType()) ? "selected" : "" %>>Sell</option>
                        </select>
                    </td>
                    <td>
                        <input type="number" name="quantity" value="<%= transaction.getQuantity() %>" min="1" required>
                    </td>
                    <td>
                        <input type="number" name="price" value="<%= transaction.getPrice() %>" min="0.01" step="0.01" required>
                    </td>
                    <td>
                        <select name="status">
                            <option value="pending" <%= "pending".equalsIgnoreCase(transaction.getStatus()) ? "selected" : "" %>>Pending</option>
                            <option value="executed" <%= "executed".equalsIgnoreCase(transaction.getStatus()) ? "selected" : "" %>>Executed</option>
                            <option value="cancelled" <%= "cancelled".equalsIgnoreCase(transaction.getStatus()) ? "selected" : "" %>>Cancelled</option>
                        </select>
                    </td>
                    <td>
                        <input type="hidden" name="transactionId" value="<%= transaction.getId() %>">
                        <input type="submit" value="Update">
                </form>
                <form action="DeleteTransactionController" method="post" style="display:inline;">
                    <input type="hidden" name="transactionId" value="<%= transaction.getId() %>">
                    <input type="submit" value="Delete" onclick="return confirm('Are you sure?');">
                </form>
            </tr>
            <% }
            %>
        </table>
        <%
            }
        %>
    </body>
</html>