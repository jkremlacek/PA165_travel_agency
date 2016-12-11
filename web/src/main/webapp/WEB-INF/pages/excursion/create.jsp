<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: martin
  Date: 08.12.2016
  Time: 22:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Excursion</title>
</head>
<body>
<form:form method="post" action="${pageContext.request.contextPath}/excursion/create" modelAttribute="newExcursion">
    <table>
        <tr>
            <td>Name</td>
            <td><form:input path="name" type="text" size="30"/></td>
        </tr>
        <tr>
            <td>Destination</td>
            <td><form:input path="destination" type="text" size="40"/></td>
        </tr>
        <tr>
            <td>Description</td>
            <td><form:input path="description" type="text" size="40"/></td>
        </tr>
        <tr>
            <td>Trip</td>
            <td>
                <%--TODO: resolve "syntactically not correct" issue--%>
                <form:label path="trip"/>
                <form:select path="trip">
                    <form:options items="${trips}" itemValue="id" itemLabel="name"/>
                    <%--<c:forEach items="${trips}"  var="t">--%>
                        <%--<form:option type="text" value="${t.getId().toString()}">${t.name} ${t.destination}</form:option>--%>
                    <%--</c:forEach>--%>
                </form:select>
            </td>
        </tr>
        <tr>
            <td>Date</td>
            <td>
                <form:label path="date"/>
                <c:set var="now" value="<%=new java.util.Date()%>"/>
                <form:input path="date" class="date" type="text"
                            value=""/>
            </td>
        </tr>
        <tr>
            <td>Duration</td>
            <td>
                <form:label path="duration"/>
                <form:input path="duration" class="duration" type="text"
                            value=""/>
            </td>
        </tr>
        <tr>
            <td>Price</td>
            <td><form:input path="price" type="text" size="10"/> CZK</td>
        </tr>
    </table>
    <button class="btn btn-primary" type="submit">Create Excursion</button>
</form:form>
</body>
</html>
