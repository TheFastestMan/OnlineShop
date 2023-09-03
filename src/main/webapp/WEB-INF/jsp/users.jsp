<%--
  Created by IntelliJ IDEA.
  User: rail
  Date: 9/3/23
  Time: 11:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h1>List of users:</h1>
<ul>
    <c:if test="${not empty requestScope.users}">
        <c:forEach var="user" items="${requestScope.users}">
            <li><a href="${pageContext.request.contextPath}/user?userId=${user.id()}">${user.description()}</a></li>
        </c:forEach>
    </c:if>
</ul>
</body>
</html>
