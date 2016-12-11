<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  User: jakub_kremlacek
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Trips</title>
</head>
<body>
<h1>Available trips</h1>
    <c:forEach items="${trips}" var="trip">
        <c:out value="${trip.name}" /><br>
        <c:out value="${trip.description}" /><br>
        <c:out value="${trip.destination}" /><br>
        <c:out value="${trip.dateFrom}" /><br>
        <c:out value="${trip.dateTo}" /><br>
        <c:out value="${trip.capacity}" /><br>
        <c:out value="${trip.price}" /><br>
        <a href="/trip/detail/${trip.id}">detail</a>
        <br><br>
    </c:forEach>
</body>
</html>
