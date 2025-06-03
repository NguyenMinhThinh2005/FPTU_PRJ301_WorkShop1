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
<h1>Chào mừng: <%= loginUser.getFullName() %>!</h1>

<form action="MainController" method="POST">
    <button type="submit" name="action" value="Logout">Đăng xuất</button><br/>
    <input type="text" name="searchID" placeholder="Tìm UserID">
    <input type="text" name="searchName" placeholder="Tìm FullName">
    <button type="submit" name="action" value="SearchUser">Tìm kiếm</button>
</form>

<a href="addUser.jsp">Thêm người dùng mới</a><br/>
<a href="stockList.jsp">Về danh sách cổ phiếu</a><br/>

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
        <th>FullName</th>
        <th>RoleID</th>
        <th>Function</th>
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
            <button type="submit" name="action" value="EditUser">Sửa</button>
            <button type="submit" name="action" value="DeleteUser">Xóa</button>
        </td>
    </form>
    </tr>
    <% }
    %>
</table>
<% } else { %>
<h3>Không tìm thấy người dùng nào</h3>
<% } %>
</body>
</html>
