<%--
  Created by IntelliJ IDEA.
  User: rail
  Date: 9/3/23
  Time: 11:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>User</title>
</head>
<body>
<h1>User:</h1>

<ul>
    <c:forEach var="user" items="${requestScope.users}">
        <li>${user.username()}</li>
    </c:forEach>
</ul>

</body>
</html>
