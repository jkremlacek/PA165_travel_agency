<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: martin
  Date: 11.12.2016
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit an Excursion</title>
</head>
<body>
<form:form method="post" action="${pageContext.request.contextPath}/excursion/update/${toUpdate.id}" modelAttribute="toUpdate">
    <table>
        <tr>
            <td>Name</td>
            <td><form:input path="name" type="text" size="30" value="${toUpdate.name}"/></td>
        </tr>
        <tr>
            <td>Destination</td>
            <td><form:input path="destination" type="text" size="40" value="${toUpdate.destination}"/></td>
        </tr>
        <tr>
            <td>Description</td>
            <td><form:input path="description" type="text" size="40" value="${toUpdate.description}"/></td>
        </tr>
        <tr>
            <td>Trip</td>
            <td>
                <form:label path="trip"/>
                <form:select path="trip" class="selectpicker" data-live-search="true" data-container="body">
                    <%--<form:options items="${trips}" itemValue="id" itemLabel="name" />--%>
                    <c:forEach items="${trips}"  var="t">
                        <c:choose>
                            <c:when test="${toUpdate.trip.id==t.id}">
                                <form:option type="text" value="${t.getId().toString()}" selected="selected">${t.name} ${t.destination}</form:option>
                            </c:when>
                            <c:otherwise>
                                <form:option type="text" value="${t.getId().toString()}">${t.name} ${t.destination}</form:option>
                            </c:otherwise>
                        </c:choose>

                    </c:forEach>

                </form:select>
            </td>
        </tr>
        <tr>
            <td>Date</td>
            <td>
                    <form:input path="date" type="text"/>
                           <%--value="<fmt:formatDate value="${toUpdate.date}" pattern="dd.MM.yyyy, HH:mm" />"/>--%>

            </td>
        </tr>
        <tr>
            <td>Duration</td>
            <td>

                <form:input path="duration" class="duration" type="text"/>

            </td>
        </tr>
        <tr>
            <td>Price</td>
            <td>
                <form:input path="price" type="text" size="10" value="${toUpdate.price}"/> CZK
            </td>
        </tr>
    </table>
    <button class="btn btn-primary" type="submit">Update Excursion</button>
</form:form>
</body>
</html>
