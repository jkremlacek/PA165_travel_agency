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
            <td><form:input path="name" type="text" size="30" value="${updatingUser.name}" required="required" pattern=".*\S+.*" /></td>
        </tr>
        <tr>
            <td>Phone number</td>
            <td><form:input path="phoneNumber" type="text" size="30" value="${updatingUser.phoneNumber}" required="required" pattern="^[0-9]{9}$"/>
            
        </tr>
        <tr>
            <td>Birth date</td>
            <td>
                <form:label path="birthDate"/>
                <c:set var="now" value=">%=new java.util.Date()%>"/>
                <form:input path="birthDate" type="text" size="30" required="required" pattern="^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$" />
            
            <br>
                Format: dd.MM.yyyy<td> 
        </tr>
        <tr>
            <td>Personal number</td>

            <td><form:input path="personalNumber" type="text" size="30" value="${updatingUser.personalNumber}" required="required" pattern="^[0-9]{10}$" />
            
        </tr>
    </table>
    <button class="btn btn-primary" type="submit">Update user</button>
</form:form>
</jsp:attribute>
</my:pagetemplate>