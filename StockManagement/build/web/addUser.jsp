<%-- 
    Document   : addUser
    Created on : Jun 1, 2025, 4:34:30 PM
    Author     : datth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dto.User"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Thêm người dùng</title>
</head>
<body>
<%
    User loginUser = (User) session.getAttribute("LOGIN_USER");
    if (loginUser == null || !"AD".equals(loginUser.getRoleID())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<h1>Thêm người dùng mới</h1>

<form action="MainController" method="POST">
    User ID: <input type="text" name="userID" placeholder="Nhập userID" required /><br/>
    Full Name: <input type="text" name="fullName" placeholder="Nhập tên đầy đủ" required /><br/>
    Role ID:
    <select name="roleID" required>
        <option value="AD">AD</option>
        <option value="US">US</option>
    </select><br/>
    Password: <input type="password" name="password" placeholder="Nhập mật khẩu" required /><br/>
    <input type="submit" name="action" value="CreateUser" /><br/>
    <a href="userList.jsp">Về danh sách người dùng</a>
</form>

<%
    String msg = (String) request.getAttribute("MSG");
    if (msg != null) {
%>
    <p style="color: red;"><%= msg %></p>
<%
    }
%>

</body>
</html>
