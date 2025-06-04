<%@page import="dto.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<%
    User user = (User) request.getAttribute("USER");
    if (user == null) {
        response.sendRedirect("userList.jsp"); // Nếu không có user, quay lại danh sách
        return;
    }
%>

<h2>Edit User</h2>

<form action="UpdateUserController" method="post">
    <table>
        <tr>
            <td>User ID:</td>
            <td><input type="text" name="userID" value="<%= user.getUserID() %>" readonly /></td>
        </tr>
        <tr>
            <td>Full Name:</td>
            <td><input type="text" name="fullName" value="<%= user.getFullName() %>" required /></td>
        </tr>
        <tr>
            <td>Role ID:</td>
            <td>
                <select name="roleID" required>
                    <option value="AD" <%= "AD".equals(user.getRoleID()) ? "selected" : "" %>>Admin</option>
                    <option value="US" <%= "US".equals(user.getRoleID()) ? "selected" : "" %>>User</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" value="<%= user.getPassword() %>" required /></td>
        </tr>
        <tr>
            <td colspan="2">
                <button type="submit">Update</button>
                <a href="MainController?action=SearchUser">Cancel</a>
            </td>
        </tr>
    </table>
</form>

</body>
</html>
