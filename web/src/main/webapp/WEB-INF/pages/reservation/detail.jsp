<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Reservation">
    <jsp:attribute name="body">
        <h4>Details of reservation no. <c:out value="${reservation.id}"/></h4>
        <table class="table">
            <tr>
                <td><h4>Reserved by </h4></td>
                <td><h4><a href="${pageContext.request.contextPath}/user/detail/${reservation.user.id}">
                            <c:out value="${reservation.user.name}"/>
                </a></h4></td>
            </tr>
            <tr>
                <td><h4>For trip </h4></td>
                <td><h4><a href="${pageContext.request.contextPath}/trip/detail/${reservation.trip.id}">
                            <c:out value="${reservation.trip.name}"/>
                </a></h4></td>
            </tr>
            <tr>
                    <td><h4>For price </h4></td>
                    <td><h4><c:out value="${reservationPrice} CZK"/></h4></td>
            </tr>
            <c:if test="${empty reservation.excursionSet}">
                <tr>
                    <td><h4>Excursions</h4></td>
                    <td><h4>No excursion was reserved</h4></td>
                    </tr>
                </c:if>
                <c:if test="${not empty reservation.excursionSet}">
                <tr>
                    <td><h4>Excursions</h4></td>
                    <td><h4>
                        <c:forEach items="${reservation.excursionSet}" var="excursion">
                            <a href="${pageContext.request.contextPath}/excursion/detail/${excursion.id}">
                                <c:out value="${excursion.name}"/></a><br/>
                        </c:forEach>
                    </h4></td>
                </tr>
                </c:if>    
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/reservation/edit/${reservation.id}" 
                           class="btn btn-primary">Edit</a>
                    
                        <a href="${pageContext.request.contextPath}/reservation/delete/${reservation.id}" 
                           class="btn btn-danger">Delete</a>
                    <td></td> 
                </tr>
        </table>
    </jsp:attribute>
</my:pagetemplate>
