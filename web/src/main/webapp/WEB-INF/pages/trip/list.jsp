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
        <c:out value="${trip.date}" /><br>
        <c:out value="${trip.price}" /><br>
        <br><br>
    </c:forEach>
</body>
</html>