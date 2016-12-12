<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  User: katerina caletkova
--%>
<html>
    <head>
        <title>Users</title>
    </head>
    <h2>Users</h2>
    <table class="table">
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>E-mail</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.mail}"/></td>
                <td>
                    <a href="/pa165/user/detail/${user.id}" class="btn btn-primary">Detail</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</html>

