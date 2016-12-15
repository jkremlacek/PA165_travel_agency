<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pagetemplate title="Edit user">
<jsp:attribute name="body">
<form:form method="post" action="${pageContext.request.contextPath}/user/update/${updatingUser.id}" modelAttribute="updatingUser">
    <table class="table">
        <tr>
            <td>Name</td>
            <td><form:input path="name" type="text" size="30" value="${updatingUser.name}"/></td>
        </tr>
        <tr>
            <td>Phone number</td>
            <td><form:input path="phoneNumber" type="text" size="30" value="${updatingUser.phoneNumber}"/></td>
        </tr>
        <tr>
            <td>Birth date</td>
            <td>
                <form:input path="birthDate" type="text" size="30" />
            <td>    
        </tr>
        <tr>
            <td>Personal number</td>
            <td><form:input path="personalNumber" type="text" size="30" value="${updatingUser.personalNumber}"/></td>
        </tr>
    </table>
    <button class="btn btn-primary" type="submit">Update user</button>
</form:form>
</jsp:attribute>
</my:pagetemplate>