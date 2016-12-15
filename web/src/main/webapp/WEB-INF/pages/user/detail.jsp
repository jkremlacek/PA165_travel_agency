<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<my:pagetemplate title="User">
    <jsp:attribute name="body"> 
      <tr><h3>Detail: </h3></tr>
        <table class="table">
            <thead>
               <tr>
                    <td>Name: </td>
                    <td><c:out value="${user.name}"/></td>
                </tr>
                <tr>
                    <td>E-mail: </td>
                    <td><c:out value="${user.mail}"/></td>
                </tr>
                <tr>
                    <td>Birth date:</td>                    
                    <td><fmt:formatDate value="${user.birthDate}" pattern="dd.MM.yyyy" /></td>
                </tr>
                <tr>
                    <td>Personal number:</td>
                    <td><c:out value="${user.personalNumber}"/></td>
                </tr>
                <tr>
                    <td>Phone number:</td>
                    <td><c:out value="${user.phoneNumber}"/></td>
                </tr>        
                <c:if test="${user.getIsAdmin()}">
                    <tr>
                        <td>Is user admin:</td>
                        <td>Yes</td>
                    </tr>
                </c:if>
                <c:if test="${!user.getIsAdmin()}">
                    <tr>
                        <td>Is user admin:</td>
                        <td>No</td>

                    </tr>   

                </c:if>
                <tr>
                    <td>Number of reservations:</td>
                    <td><c:out value="${fn:length(reservations)}"/></td>
                </tr>  
                <c:if test="${authUser.id==user.id}">
                    <tr>
                        <td>                  
                        <form method="get" action="${pageContext.request.contextPath}/user/edit/${user.id}">
                            <button type="submit" class="btn btn-primary">Edit</button>
                        </form>                    
                        </td>
                        <td></td> 
                    </tr>
                </c:if>                  
            </thead>             
        
            
        </table>
        <c:if test="${fn:length(reservations) > 0}">
            <td><h4>Reservations:</h4></td>
            <table class="table">
                <thead>
                    <tr>
                        <th>Trip</th>
                    </tr>
                </thead>
                 <tbody>
                    <c:forEach items="${reservations}" var="reservation">
                        <tr>
                           <td><c:out value="${reservation.trip.name}"/></td>
                            <td>
                                <a href="/pa165/reservation/detail/${reservation.id}" class="btn btn-primary">Detail</a>
                            </td>   
                        </tr>
                    </c:forEach>
                    </tbody>
            </table>
        </c:if>
           
    </jsp:attribute>
</my:pagetemplate>
