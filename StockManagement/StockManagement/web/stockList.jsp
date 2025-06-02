<%-- 
    Document   : stockList
    Created on : Apr 19, 2025, 8:28:44 AM
    Author     : admin
--%>
<%@page import="dto.Stock"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dto.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stock List Page</title>
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
            <button type="submit" name="action" value="Search">Search</button>
        </form>
        <a href="createStock.jsp">Create New Stock</a><br/>
        <a href="transactionList.jsp">Go to Transaction History</a><br/>
        <a href="alertList.jsp">Go to Alert List</a><br/>
        <%
            String MSG = (String) request.getAttribute("MSG");
            if (MSG != null) {
        %>
        <h3> <%= MSG%> </h3>
        <%
            }
            ArrayList<Stock> list = (ArrayList<Stock>) request.getAttribute("list");
            if (list != null) {
        %>
        <table>
            <tr>
                <th>No</th>
                <th>Ticker</th>
                <th>Name</th>
                <th>Sector</th>
                <th>Price</th>
                <th>Status</th>
                <th>Function</th>
            </tr>
            <%
                int count = 0;
                for (Stock stock : list) {
                    count++;
            %>
            <tr>
            <form action="MainController" method="POST">
                <td><%= count%></td>
                <td><%= stock.getTicker()%></td>
                <td><input type="text" name="name" value="<%= stock.getName()%>"></td>
                <td><input type="text" name="sector" value="<%= stock.getSector()%>"></td>
                <td><input type="text" name="price" value="<%= stock.getPrice()%>"></td>
                <td><%= stock.isStatus()%></td>
                <input type="hidden" name="ticker" value="<%= stock.getTicker()%>">
                <td>
                    <button type="submit" name="action" value="Update">Update</button>
                    <button type="submit" name="action" value="Delete">Delete</button>
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
