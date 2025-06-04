<%@page import="dto.User"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User List Page</title>
</head>
<body>
<%
    User loginUser = (User) session.getAttribute("LOGIN_USER");
    if (loginUser == null || !"AD".equals(loginUser.getRoleID())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<h1>Welcome: <%= loginUser.getFullName() %>!</h1>

<form action="MainController" method="POST"> 
    <button type="submit" name="action" value="Logout">Logout</button><br/>
    <input type="text" name="search" placeholder="Search by UserID or Full Name">
    <button type="submit" name="action" value="SearchUser">Search</button>
</form>


<a href="createUser.jsp">Add New User</a><br/>
<a href="MainController">Back to Stock List</a><br/>

<%
    String MSG = (String) request.getAttribute("MSG");
    if (MSG != null) {
%>
<h3><%= MSG %></h3>
<%
    }
    ArrayList<User> list = (ArrayList<User>) request.getAttribute("USER_LIST"); 
    if (list != null && !list.isEmpty()) {
%>
<table border="1">
    <tr>
        <th>No</th>
        <th>UserID</th>
        <th>Full Name</th>
        <th>Role ID</th>
        <th>Actions</th>
    </tr>
    <%
        int count = 0;
        for (User u : list) {
            count++;
    %>
    <tr>
    <form action="MainController" method="POST">
        <td><%= count %></td>
        <td><%= u.getUserID() %></td>
        <td><%= u.getFullName() %></td>
        <td><%= u.getRoleID() %></td>
        <td>
            <input type="hidden" name="userID" value="<%= u.getUserID() %>">
            <button type="submit" name="action" value="EditUser">Edit</button>
            <button type="submit" name="action" value="DeleteUser">Delete</button>
        </td>
    </form>
    </tr>
    <% }
    %>
</table>
<% } else { %>
<h3>No users found</h3>
<% } %>
</body>
</html>