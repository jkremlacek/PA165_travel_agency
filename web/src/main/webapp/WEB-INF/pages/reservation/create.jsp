<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<my:pagetemplate title="Book a trip and excursions">
<jsp:attribute name="body">
<h4>Creating reservation to trip ${trip.name}</h4>
<th>Choose excursions to this trip: </th>
<form:form method="post" action="${pageContext.request.contextPath}/reservation/create/${trip.id}" modelAttribute="checkedExcursions">
    <%--<form:hidden path="trip"/>--%>
    <%--<form:hidden path="user"/>--%>
    <c:forEach items="${tripExcursions}" var="excursion">
        <form:checkbox path="functionList" value="${excursion.id.toString()}" />
        <a href="${pageContext.request.contextPath}/excursion/detail/${excursion.id}"><c:out value="${excursion.name}"/></a>
    <br>
    </c:forEach>
    <br>
    <button class="btn btn-success" type="submit">Make reservation</button>
</form:form>
</jsp:attribute>
</my:pagetemplate>