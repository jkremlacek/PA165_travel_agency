<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  User: jakub_kremlacek
--%>

<my:pagetemplate title="Trip detail">
<jsp:attribute name="body">
    <table class="table">
    <tr>
        <td>Name</td>
        <td><c:out value="${trip.name}"/></td>
    </tr>
    <tr>
        <td>Destination</td>
        <td><c:out value="${trip.destination}"/></td>
    </tr>
    <tr>
        <td>Description</td>
        <td><c:out value="${trip.description}"/></td>
    </tr>
    <tr>
        <td>Date From</td>
        <td><fmt:formatDate value="${trip.dateFrom}" pattern="dd.MM.yyyy HH:mm" /></td>
    </tr>
    <tr>
        <td>Date To</td>
        <td><fmt:formatDate value="${trip.dateTo}" pattern="dd.MM.yyyy HH:mm" /></td>
    </tr>
    <tr>
        <td>Price</td>
        <td><c:out value="${trip.price}"/> CZK</td>
    </tr>
    <c:if test="${authUser.getIsAdmin()}">
        <tr>
        <td>
            <form method="get" action="${pageContext.request.contextPath}/trip/edit/${trip.id}">
                <button type="submit" class="btn btn-primary">Edit</button>
            </form>
        </td>
        <td>
            <form method="post" action="${pageContext.request.contextPath}/trip/delete/${trip.id}">
                <button type="submit" class="btn btn-primary">Delete</button>
            </form>
        </td>
        </tr>
    </c:if>
</table>
</jsp:attribute>
</my:pagetemplate>