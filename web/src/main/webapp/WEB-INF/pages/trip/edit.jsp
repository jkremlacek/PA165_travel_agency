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
<form:form method="post" action="${pageContext.request.contextPath}/trip/update/${toUpdate.id}" modelAttribute="toUpdate">
    <table class="table">
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
            <td>Date From</td>
            <td>
                    <form:input path="dateFrom" type="text"/>
                           <%--value="<fmt:formatDate value="${toUpdate.date}" pattern="dd.MM.yyyy, HH:mm" />"/>--%>
            </td>
        </tr>
        <tr>
            <td>Date To</td>
            <td>
                    <form:input path="dateFrom" type="text"/>
                           <%--value="<fmt:formatDate value="${toUpdate.date}" pattern="dd.MM.yyyy, HH:mm" />"/>--%>
            </td>
        </tr>
        <tr>
            <td>Price</td>
            <td>
                <form:input path="price" type="text" size="10" value="${toUpdate.price}"/> CZK
            </td>
        </tr>
    </table>
    <button class="btn btn-primary" type="submit">Update Trip</button>
</form:form>
</jsp:attribute>
</my:pagetemplate>