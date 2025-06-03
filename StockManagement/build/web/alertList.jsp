  <%-- 
    Document   : alertList
    Created on : Apr 24, 2025, 8:44:01 AM
    Author     : admin
--%>

<%@page import="dto.Alert"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dto.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alert List Page</title>
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
            <button type="submit" name="action" value="SearchAlert">Search</button>
        </form>
        <a href="createAlert.jsp">Create New Alert</a><br/>
        <a href="transactionList.jsp">Go to Transaction History</a><br/>
        <a href="stockList.jsp">Go to Stock List</a><br/>
        <%
            String MSG = (String) request.getAttribute("MSG");
            if (MSG != null) {
        %>
        <h3> <%= MSG%> </h3>
        <%
            }
            ArrayList<Alert> list = (ArrayList<Alert>) request.getAttribute("list");
            if (list != null) {
        %>
        <table>
            <tr>
                <th>No</th>
                <th>User ID</th>
                <th>Ticker</th>
                <th>Threshold</th>
                <th>Direction</th>
                <th>Status</th>
                <th>Function</th>
            </tr>
            <%
                int count = 0;
                for (Alert alert : list) {
                    count++;
            %>
            <tr>
            <form action="MainController" method="POST">
                <td><%= count%></td>
                <td><%= alert.getUserID()%></td>
                <td><%= alert.getTicker()%></td>
                <td><input type="text" name="threshold" value="<%= alert.getThreshold()%>"></td>
                <td><%= alert.getDirection()%></td>
                <td><select name="status" required>
                        <option value="active" <%= "active".equals(alert.getStatus()) ? "selected" : ""%>>active</option>
                        <option value="inactive" <%= "inactive".equals(alert.getStatus()) ? "selected" : ""%>>inactive</option>
                    </select></td>
                <input type="hidden" name="alertID" value="<%= alert.getAlertID()%>">
                <td>
                    <button type="submit" name="action" value="UpdateAlert">Update</button>
                    <% if ("inactive".equals(alert.getStatus())) { %>
                    <button type="submit" name="action" value="DeleteAlert">Delete</button>
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
