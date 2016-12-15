<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: Martin Salata
  Date: 07.12.2016
  Time: 17:33
--%>
<my:pagetemplate title="Excursions">
    <jsp:attribute name="body">
        <table class="table">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Destination</th>
                    <th>Trip</th>
                    <th>Date</th>
                    <th>Duration</th>
                    <c:if test="${authUser.getIsAdmin()}">
                    <th/>
                    <th/>
                    <th/>
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${excursions}" var="excursion">
                <tr>
                    <td><c:out value="${excursion.name}"/></td>
                    <td><c:out value="${excursion.description}"/></td>
                    <td><c:out value="${excursion.destination}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/trip/detail/${excursion.trip.id}" ><c:out value="${excursion.trip.name}" /></a>
                    </td>
                    <td><fmt:formatDate value="${excursion.date}" pattern="dd.MM.yyyy, HH:mm"/></td>
                    <td><c:out value="${excursion.duration}"/></td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}/excursion/detail/${excursion.id}">
                            <button type="submit" class="btn btn-primary">Detail</button>
                        </form>
                    </td>
                    <c:if test="${authUser.getIsAdmin()}">
                        <td>
                            <form method="get" action="${pageContext.request.contextPath}/excursion/edit/${excursion.id}">
                                <button type="submit" class="btn btn-primary">Edit</button>
                            </form>
                        </td>
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/excursion/delete/${excursion.id}">
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </td>
                    </c:if>

                </tr>
                </c:forEach>
                <tr>
                    <c:if test="${authUser.getIsAdmin()}">
                        <td>
                            <form method="get" action="${pageContext.request.contextPath}/excursion/new">
                                <button type="submit" class="btn btn-success">New excursion</button>
                            </form>
                        </td>
                        <td/>
                        <td/>
                        <td/>
                        <td/>
                        <td/>
                        <td/>
                        <td/>
                        <td/>
                    </c:if>
                </tr>
            </tbody>
        </table>
</jsp:attribute>
</my:pagetemplate>