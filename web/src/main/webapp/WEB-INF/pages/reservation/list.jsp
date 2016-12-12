<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<my:pagetemplate title="Reservation">
<jsp:attribute name="body">
<table class="table">
        <thead>
        <tr>
            <th>id</th>
            <th>trip</th>
            <th>excursions number</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reservations}" var="reservation">
            <tr>
                <td>${reservation.id}</td>
                <td><c:out value="${reservation.trip.name}"/></td>
                <td><c:out value="${fn:length(reservation.excursionSet)}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/reservation/detail/${reservation.id}" class="btn btn-primary">Detail</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</jsp:attribute>
</my:pagetemplate>