<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  User: jakub_kremlacek
--%>
<my:pagetemplate title="Edit a trip">
<jsp:attribute name="body">    
    <form:form name="frmMr" method="post" action="${pageContext.request.contextPath}/trip/update/${toUpdate.id}" modelAttribute="toUpdate">
    <table class="table">
        <tr>
            <th>Name</th>
            <td><form:input path="name" type="text" size="30" value="${toUpdate.name}" required="required" pattern=".*\S+.*"/></td>
        </tr>
        <tr>
            <th>Destination</th>
            <td><form:input path="destination" type="text" size="40" value="${toUpdate.destination}" required="required" pattern=".*\S+.*"/></td>
        </tr>
        <tr>
            <th>Description</th>
            <td><form:input path="description" type="text" size="40" value="${toUpdate.description}" required="required" pattern=".*\S+.*"/></td>
        </tr>
        <tr>
            <th>Capacity</td>
            <td><form:input path="capacity" type="number" size="40" value="${toUpdate.capacity}" required="required" min="1"/></td>
        </tr>
        <tr>
            <th>Date From</th>
            <td>
                <form:input name="dateFrom" path="dateFrom" type="datetime" required="required" pattern="^([1-9]|([012][0-9])|(3[01])).([0]{0,1}[1-9]|1[012]).\d\d\d\d, [012]{0,1}[0-9]:[0-5][0-9]$"/>
                <%--value="<fmt:formatDate value="${toUpdate.dateFrom}" pattern="dd.MM.yyyy, HH:mm" />"/>--%>
                <br>
                Format: dd.MM.yyyy, HH:mm
            </td>
        </tr>
        <tr>
            <th>Date To</th>
            <td>
                <form:input name="dateTo" path="dateTo" type="datetime" required="required" pattern="^([1-9]|([012][0-9])|(3[01])).([0]{0,1}[1-9]|1[012]).\d\d\d\d, [012]{0,1}[0-9]:[0-5][0-9]$"/>
                <%--value="<fmt:formatDate value="${toUpdate.dateTo}" pattern="dd.MM.yyyy, HH:mm" />"/>--%>
                <br>
                Format: dd.MM.yyyy, HH:mm
            </td>
        </tr>
        <tr>
            <th>Price</th>
            <td>
                <form:input path="price" type="number" size="10" value="${toUpdate.price}" min="1" required="required"/> CZK
            </td>
        </tr>
    </table>
    <button class="btn btn-primary" type="submit">Update Trip</button>
</form:form>
</jsp:attribute>
</my:pagetemplate>