<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:pagetemplate title="Book a trip and excursions">
<jsp:attribute name="body">
<form:form method="post" action="${pageContext.request.contextPath}/reservation/create/${trip.id}" modelAttribute="createReservation">
    <form:hidden path="trip"/>
    <form:hidden path="user"/>
    <c:forEach items="${tripExcursions}" var="excursion">
                            <form:checkbox path="excursionSet" value="${excursion}" />
                            <a href="${pageContext.request.contextPath}/excursion/detail${excursion.id}"><c:out value="${excursion.name}"/></a>
                </c:forEach>
                            <button class="btn btn-success" type="submit">Make reservation</button>
</form:form>
</jsp:attribute>
</my:pagetemplate>