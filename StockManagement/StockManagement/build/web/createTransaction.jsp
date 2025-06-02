<%-- 
    Document   : createTransaction
    Created on : Apr 24, 2025, 7:34:00 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Transaction Page</title>
    </head>
    <body>
        <h1>Create Transaction</h1>
        <form action="MainController" method="POST">
            Ticker: <input type="text" name="ticker" placeholder="Enter ticker" required /><br/>
            Type: 
            <select name="type" required>
                <option value="buy">Buy</option>
                <option value="sell">Sell</option>
            </select><br/>
            Quantity: <input type="number" name="quantity" placeholder="Enter quantity" min="1" required /><br/>
            Price: <input type="number" name="price" placeholder="Enter price" step="0.01" min="0.01" required /><br/>
            <input type="submit" name="action" value="CreateTransaction" /><br/>
            <a href="transactionList.jsp">Back to Transaction List</a>
        </form>
        <% String msg = (String) request.getAttribute("MSG"); %>
        <% if (msg != null) { %>
            <p style="color: red;"><%= msg %></p>
        <% } %>
    </body>
</html>
