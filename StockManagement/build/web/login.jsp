<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Login</title>
</head>
<body>
  <h2>Login</h2>
  <c:if test="${not empty MSG}">
    <p style="color:red">${MSG}</p>
  </c:if>
  <form action="${pageContext.request.contextPath}/LoginController" method="post">
    <label>UserID:
      <input type="text" name="userID" required />
    </label>
    <br/><br/>
    <label>Password:
      <input type="password" name="password" required />
    </label>
    <br/><br/>
    <button type="submit">Login</button>
  </form>
</body>
</html>
