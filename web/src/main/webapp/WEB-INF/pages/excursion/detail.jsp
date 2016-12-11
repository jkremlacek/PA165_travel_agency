<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: martin
  Date: 08.12.2016
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Excursion</title>
</head>
<body>
<table>
    <tr>
        <td>Name</td>
        <td><c:out value="${excursion.name}"/></td>
    </tr>
    <tr>
        <td>Destination</td>
        <td><c:out value="${excursion.destination}"/></td>
    </tr>
    <tr>
        <td>Description</td>
        <td><c:out value="${excursion.description}"/></td>
    </tr>
    <tr>
        <td>Date</td>
        <td><c:out value="${excursion.date}"/></td>
    </tr>
    <tr>
        <td>Duration</td>
        <td><c:out value="${excursion.duration}"/></td>
    </tr>
    <tr>
        <td>Price</td>
        <td><c:out value="${excursion.price}"/> CZK</td>
    </tr>
</table>
</body>
</html>
