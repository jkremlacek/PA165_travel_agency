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
            <td><form:input path="name" type="text" size="30"/></td>
        </tr>
        <tr>
            <th>Destination</th>
            <td><form:input path="destination" type="text" size="40"/></td>
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
                                     value="${t.getId().toString()}">${t.name} ${t.destination}</form:option>
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
                            value=""/>
                <br>
                Format: dd.MM.yyyy, HH:mm
            </td>
        </tr>
        <tr>
            <th>Duration</th>
            <td>
                <form:input path="duration" type="text" size="10" value=""/> hours
            </td>
        </tr>
        <tr>
            <th>Price</th>
            <td><form:input path="price" type="text" size="10"/> CZK</td>
        </tr>
    </table>
    <button class="btn btn-success" type="submit">Create Excursion</button>
</form:form>
</jsp:attribute>
</my:pagetemplate>