<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: rail
  Date: 9/11/23
  Time: 5:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/registration" method="post">
    <label for="name"> Name:
        <input type="text" name="name" id="name">
    </label>

    <label for="password"> Password:
        <input type="password" name="password" id="password">
    </label>

    <label for="email"> Email:
        <input type="text" name="email" id="email">
    </label>
    <select name="role">
        <c:forEach var="role" items="${requestScope.roles}">
            <option value="${role}">${role}</option>
        </c:forEach>
    </select>
    <br/>
    <c:forEach var="gender" items="${requestScope.genders}">
        <input type="radio" name="gender" value="${gender}">${gender}
        <br/>
    </c:forEach>

    <input type="submit" value="Sent">
</form>

</body>
</html>
