<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Martin Salata
  Date: 11.12.2016
  Time: 18:05
--%>
<my:pagetemplate title="Edit an excursion">
<jsp:attribute name="body">
<form:form method="post" action="${pageContext.request.contextPath}/excursion/update/${toUpdate.id}" modelAttribute="toUpdate">
    <table class="table">
        <tr>
            <th>Name</th>
            <td><form:input path="name" type="text" size="30" value="${toUpdate.name}"/></td>
        </tr>
        <tr>
            <th>Destination</th>
            <td><form:input path="destination" type="text" size="40" required="required"  pattern=".*\S+.*" value="${toUpdate.destination}"/></td>
        </tr>
        <tr>
            <th>Description</th>
            <td><form:input path="description" type="text" size="40" value="${toUpdate.description}"/></td>
        </tr>
        <tr>
            <th>Trip</th>
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
            <th>Date</th>
            <td>
                    <form:input path="date" type="text" required="required" pattern="^(?:(?:31(\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})[,] [012]{0,1}[0-9]:[0-5][0-9]$|^(?:29(\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))[,] [012]{0,1}[0-9]:[0-5][0-9]$|^(?:0?[1-9]|1\d|2[0-8])(\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})[,] [012]{0,1}[0-9]:[0-5][0-9]$" />
             <br>
                Format: dd.MM.yyyy, HH:mm
            </td>
        </tr>
       <tr>
            <th>Duration</th>
            <td>

                <form:input path="duration" class="duration" size="10" required="required" min="1" type="number"/> hours

            </td>
        </tr>
         <tr>
            <th>Price</th>
            <td>
                <form:input path="price" type="number" required="required" min="1" size="10" value="${toUpdate.price}"/> CZK
            </td>
        </tr>
    </table>
    <button class="btn btn-primary" type="submit">Update Excursion</button>
</form:form>
</jsp:attribute>
</my:pagetemplate>