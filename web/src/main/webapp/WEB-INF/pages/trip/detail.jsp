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
    <h4>Details of trip with id <c:out value="${trip.id}"/></h4>
    <table class="table">
    <tr>
        <th>Name</th>
        <td><c:out value="${trip.name}"/></td>
    </tr>
    <tr>
        <th>Destination</th>
        <td><c:out value="${trip.destination}"/></td>
    </tr>
    <tr>
        <th>Description</th>
        <td><c:out value="${trip.description}"/></td>
    </tr>
    <tr>
        <th>Total Capacity</th>
        <td><c:out value="${trip.capacity}"/></td>
    </tr>
    <tr>
        <th>Available Capacity</th>
        <td><c:out value="${availableCapacity}"/></td>
    </tr>
    <tr>
        <th>Date From</th>
        <td><fmt:formatDate value="${trip.dateFrom}" pattern="dd.MM.yyyy" /></td>
    </tr>
    <tr>
        <th>Date To</th>
        <td><fmt:formatDate value="${trip.dateTo}" pattern="dd.MM.yyyy" /></td>
    </tr>
    <tr>
        <th>Price</th>
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
                <button type="submit" class="btn btn-primary btn-danger">Delete</button>
            </form>
        </td>
        </tr>
    </c:if>
</table>
<h4>Available Excursions</h4>
<table class="table">
    <tr>
        <th>
            Name
        </th>
        <th>
            Price
        </th>
        <th/>
    </tr>
    <c:forEach items="${excursions}" var="excursion">
        <tr>
            <td>
                <c:out value="${excursion.name}"/>
            </td>
            <td>
                <c:out value="${excursion.price} CZK"/>
            </td>
            <td>
                <form method="get" action="${pageContext.request.contextPath}/excursion/detail/${excursion.id}">
                    <button type="submit" class="btn btn-primary">Detail</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</jsp:attribute>
</my:pagetemplate>