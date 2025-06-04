<%-- 
    Document   : createStock
    Created on : Apr 23, 2025, 7:42:06 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Stock Page</title>
    </head>
    <body>
        <h1>Create Stock</h1>
        <form action="${pageContext.request.contextPath}/CreateController" method="POST">
            Ticker <input type="text" placeholder="Enter ticker" name="ticker" required/><br/>
            Name <input type="text" placeholder="Enter name" name="name" required/><br/>
            Sector <input type="text" placeholder="Enter sector" name="sector" required/><br/>
            Price <input type="number" step="0.01" placeholder="Enter price" name="price" required/><br/>
            <input type="submit" name="action" value="Create" /><br/>
            <a href="MainController">Back</a></br>
        </form>
        <% String msg = (String) request.getAttribute("MSG"); %>
        <% if (msg != null) {%>
        <p style="color: red;"><%= msg%></p>
        <% }%>
    </body>
</html>
