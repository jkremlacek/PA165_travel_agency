<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  User: jakub_kremlacek
--%>
<my:pagetemplate title="Trips">
    <jsp:attribute name="body">
        <table class="table">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Destination</th>
                    <th>Date from</th>
                    <th>Date to</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${trips}" var="trip">
                <tr>
                    <td><c:out value="${trip.name}"/></td>
                    <td><c:out value="${trip.destination}"/></td>
                    <td><fmt:formatDate value="${trip.dateFrom}" pattern="dd.MM.yyyy, HH:mm"/></td>
                    <td><fmt:formatDate value="${trip.dateTo}" pattern="dd.MM.yyyy, HH:mm"/></td>
                    <td><c:out value="${trip.price}"/> CZK</td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}/trip/detail/${trip.id}">
                            <button type="submit" class="btn btn-primary">Detail</button>
                        </form>
                    </td>
                    <c:if test="${authUser.getIsAdmin()}">
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
                    </c:if>

                </tr>
                </c:forEach>
                <tr>
                    <c:if test="${authUser.getIsAdmin()}">
                        <td>
                            <form method="get" action="${pageContext.request.contextPath}/trip/new">
                                <button type="submit" class="btn btn-primary">New trip</button>
                            </form>
                        </td>
                    </c:if>
                </tr>
            </tbody>
        </table>
</jsp:attribute>
</my:pagetemplate>