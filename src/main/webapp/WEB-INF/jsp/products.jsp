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


<form action="${pageContext.request.contextPath}/addToCart" method="post">
    <c:forEach items="${pr}" var="product">
        <li>ID: ${product.productId}</li>
        <li>Name: ${product.productName}</li>
        <li>Description: ${product.description}</li>
        <li>Price: ${product.price}</li>
        <input type="hidden" name="productId" value="${product.productId}"/>
        <input type="submit" value="Add to Cart"/>
    </c:forEach>
</form>

</body>
</html>