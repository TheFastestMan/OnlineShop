<%--
  Created by IntelliJ IDEA.
  User: rail
  Date: 9/4/23
  Time: 2:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<h1> List of products: </h1>
<c:forEach var="product" items="${requestScope.products}">
    <li>${product.description()}</li>
</c:forEach>

</body>
</html>