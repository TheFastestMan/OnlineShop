<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.railshop.onlineshop.dto.ProductDto" %>
<%@ page import="ru.railshop.onlineshop.service.UserService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>User's Products</title>
</head>
<body>
<h1>User's Products:</h1>

<c:set var="userId" value="${param.userId}" scope="request" />

<c:choose>
  <c:when test="${empty userId}">
    <p>No user ID specified.</p>
  </c:when>
  <c:otherwise>
    <c:set var="products" value='<%= UserService.getInstance().getAllProductsByUserId(Long.parseLong(userId)) %>' scope="request" />
    <ul>
      <c:forEach var="product" items="${products}">
        <li>ID: ${product.id}, Description: ${product.description}</li>
      </c:forEach>
    </ul>
  </c:otherwise>
</c:choose>
</body>
</html>
