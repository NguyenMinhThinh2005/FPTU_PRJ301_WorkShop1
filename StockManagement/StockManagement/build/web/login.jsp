<%-- 
    Document   : login
    Created on : Apr 19, 2025, 8:23:15 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Login Page</h1>
        <%
            String alertLogout = (String) request.getAttribute("MSG_LOGOUT");
            if (alertLogout != null) {
        %>
        <h4><%= alertLogout%></h4>
        <% }
        %>
        <form action="MainController" method="POST">
            User ID <input type="text" name="userID"/></br>
            Password<input type="password" name="password"/></br>
            <input class="button" type="submit" name="action" value="Login"/>
            <input class="button" type="reset" value="Reset"/>
                <%
                    String msg = (String) request.getAttribute("MSG");
                    if (msg != null) {
                %>
            <p style="color: red"><%= msg%></p>
            <% }
            %> 
        </form>
    </body>
</html>
