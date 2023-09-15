<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">

    <label for="email"> Email:
        <input type="text" name="email" id="email" value="${param.name}" required>
    </label><br>

    <label for="password"> Password:
        <input type="password" name="password" id="password" required>
    </label><br>

    <button type="submit">Login </button>
    <a href="${pageContext.request.contextPath}/registration">
        <button type="button">Register</button>
    </a>
    <c:if test="${param.error !=null}">
    <div style="color: red">
        <span> Email or password is not correct</span>
    </div>
    </c:if>
</form>
</body>
</html>