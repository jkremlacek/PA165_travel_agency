<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Martin Salata
  Date: 08.12.2016
  Time: 22:06
  To change this template use File | Settings | File Templates.
--%>
<my:pagetemplate title="Create an excursion">
<jsp:attribute name="body">
<form:form method="post" action="${pageContext.request.contextPath}/excursion/create" modelAttribute="newExcursion">
    <table class="table">
        <tr>
            <th>Name</th>
            <td><form:input path="name" type="text" size="30" required="required"  pattern=".*\S+.*"/></td>
        </tr>
        <tr>
            <th>Destination</th>
            <td><form:input path="destination" type="text" size="40" required="required"  pattern=".*\S+.*"/></td>
        </tr>
       <tr>
            <th>Description</th>
            <td><form:input path="description" type="text" size="40"/></td>
        </tr>
        <tr>
            <th>Trip</th>
            <td>
                <form:select path="trip">
                    <c:forEach items="${trips}" var="t">
                        <form:option type="text" selected="selected"
                             value="${t.getId().toString()}">${t.name}; <fmt:formatDate value="${t.dateFrom}" pattern="dd.MM.yyyy, HH:mm"/> - <fmt:formatDate value="${t.dateTo}" pattern="dd.MM.yyyy, HH:mm"/></form:option>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
        <tr>
            <th>Date</th>
            <td>
                <form:label path="date"/>
                <c:set var="now" value="<%=new java.util.Date()%>"/>
                <form:input path="date" class="date" type="text"
                            value="" required="required" pattern="^(?:(?:31(\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})[,] [012]{0,1}[0-9]:[0-5][0-9]$|^(?:29(\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))[,] [012]{0,1}[0-9]:[0-5][0-9]$|^(?:0?[1-9]|1\d|2[0-8])(\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})[,] [012]{0,1}[0-9]:[0-5][0-9]$" />
            <br>
                Format: dd.MM.yyyy, HH:mm
            </td>
        </tr>
        <tr>
            <th>Duration</th>
            <td>
                <form:input path="duration" type="number" size="10" required="required" min="1" value=""/> hours
            </td>
        </tr>
        <tr>
            <th>Price</th>
            <td><form:input path="price" type="number" min="1" size="10"/> CZK</td>
        </tr>
    </table>
    <button class="btn btn-success" type="submit">Create Excursion</button>
</form:form>
</jsp:attribute>
</my:pagetemplate>