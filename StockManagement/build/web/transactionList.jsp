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
        %>
        <h1>Welcome: <%= loginUser.getFullName()%></h1>
        <form action="MainController" method="POST">
            <button type="submit" name="action" value="Logout">Logout</button> </br>
            <input type="text" name="search" placeholder="Search">
            <button type="submit" name="action" value="SearchTransaction">Search</button>
        </form>
        <a href="createTransaction.jsp">Create New Transaction</a><br/>
        <a href="stockList.jsp">Go to Stock List</a><br/>
        <a href="alertList.jsp">Go to Alert List</a><br/>
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
                    <% if (loginUser != null && "AD".equals(loginUser.getRoleID())) { %>
                <th>Function</th>
                    <% } %>
            </tr>
            <%
                int count = 0;
                for (Transaction transaction : list) {
                    count++;
            %>
            <tr>
            <form action="MainController" method="POST">
                <td><%= count%></td>
                <td><%= transaction.getUserID()%></td>
                <td><%= transaction.getTicker()%></td>
                <td><%= transaction.getType()%></td>
                <td><%= transaction.getQuantity()%></td>
                <td><%= transaction.getPrice()%></td>
                <td><%= transaction.getStatus()%></td>
                <input type="hidden" name="id" value="<%= transaction.getId()%>">
                <td>
                    <% if ("pending".equals(transaction.getStatus()) && loginUser != null && "AD".equals(loginUser.getRoleID())) {%>
                    <form action="MainController" method="POST">
                        <input type="hidden" name="transactionId" value="<%= transaction.getId()%>" />
                        <input type="hidden" name="status" value="executed" />
                        <button type="submit" name="action" value="UpdateTransaction">Update</button>
                    </form>
                    <% } %>
                </td>
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
