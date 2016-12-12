<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<my:pagetemplate title="User detail">
<jsp:attribute name="body">

    <div class="row">
        <div class="col-xs-6">
            <table>
                <tr>
                    <td><h3>Name:</h3></td>
                    <td><h3><c:out value="${user.name}"/> (Id: <c:out value="${user.id}"/>)</h3></td>
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
        </div>
    </div>
</jsp:attribute>
</my:pagetemplate>