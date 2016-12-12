<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  User: katerina caletkova
--%>
<html>
    <head>
        <title>User</title>
    </head>
    <h2>User <c:out value="${user.id}"/></h2>
        <table>
            <tr>
                <td><h4>Name:</h4></td>
                <td><h4><c:out value="${user.name}"/> </h4></td>
            </tr>
            <tr>
                <td><h4>E-mail:</h4></td>
                <td><h4><c:out value="${user.mail}"/></h4></td>
            </tr>
            <tr>
                <td><h4>Birth date:</h4></td>
                <td><h4><c:out value="${user.birthDate}"/></h4></td>
            </tr>
            <tr>
                <td><h4>Personal number:</h4></td>
                <td><h4><c:out value="${user.personalNumber}"/></h4></td>
            </tr>
            <tr>
                <td><h4>Phone number:</h4></td>
                <td><h4><c:out value="${user.phoneNumber}"/></h4></td>
            </tr>        
            <c:if test="${user.getIsAdmin()}">
                <tr>
                    <td><h4>Is user admin:</h4></td>
                    <td><h4>Yes</h4></td>
                </tr>
            </c:if>
            <c:if test="${!user.getIsAdmin()}">
                <tr>
                    <td><h4>Is user admin:</h4></td>
                    <td><h4>No</h4></td>

                </tr>                
            </c:if>
        </table>
        <table>
            <td><h4>Number of reservations:</h4></td>
            <td><h4><c:out value="${fn:length(reservations)}"/></h4></td>
                <tbody>
                <c:forEach items="${reservations}" var="reservation">
                    <tr>
                        <td>
                            <c:out value="${reservation.id}"/>
                        </td>
                        <td>
                            <%--<a href="/pa165/reservation/detail/${reservation.id}"><c:out value="${reservation.trip.name}"/></a>--%>
                        </td>   
                    </tr>
                </c:forEach>
                </tbody>
        </table>
</html>
