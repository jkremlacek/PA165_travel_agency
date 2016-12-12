<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<my:pagetemplate title="User">
    <jsp:attribute name="body">       
        <table class="table">
            <thead>
                <tr>
                    <td><h4>Id: </h4></td>
                    <td><h4><c:out value="${user.id}"/></h4></td>
                </tr>
                <tr>
                    <td><h4>E-mail: </h4></td>
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
                <tr>
                    <td><h4>Number of reservations:</h4></td>
                    <td><h4><c:out value="${fn:length(reservations)}"/></h4></td>
                </tr> 
            </thead> 
        <c:if test="${fn:length(reservations) > 0}">
            </table>
            <td><h3>Reservations:</h3></td>
            <table class="table">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Trip</th>
                    </tr>
                </thead>
                 <tbody>
                    <c:forEach items="${reservations}" var="reservation">
                        <tr>
                            <td><h4>Reservation to trip:</h4></td>
                            <td><h4><c:out value="${reservation.trip.name}"/></h4></td>
                            <td>
                                <a href="/pa165/reservation/detail/${user.id}" class="btn btn-primary">Detail</a>
                            </td>   
                        </tr>
                    </c:forEach>
                    </tbody>
            </table>
        </c:if>
           
       
    </jsp:attribute>
</my:pagetemplate>
