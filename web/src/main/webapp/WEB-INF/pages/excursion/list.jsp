<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: martin
  Date: 07.12.2016
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Excursions</title>
</head>
<body>
<h1>Excursion super nice page</h1>
    <c:forEach items="${excursions}" var="excursion">
        <c:out value="${excursion.name}" /><br>
        <c:out value="${excursion.description}" /><br>
        <c:out value="${excursion.destination}" /><br>
        <fmt:formatDate value="${excursion.date}" pattern="dd.MM.yyyy, HH:mm" /><br>
        <c:out value="${excursion.duration}" /><br>
        <br><br>
    </c:forEach>
</body>
</html>
