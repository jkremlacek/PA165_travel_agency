<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<my:pagetemplate title="Log in">
<jsp:attribute name="body">
<h4>Log in</h4>
<form:form method="post" action="${pageContext.request.contextPath}/auth/login/">
    <div class="jumbotron">
            E-mail: <input type="email" name="mail"/><br/>
            Password: <input type="password" name="password"/><br/>
            <input type="submit"/>
    </div>
</form:form>
<h4>Registered users</h4>

<table class="table">
        <thead>
        <tr>
            <th>E-mail</th>
            <th>Password</th>
            <th>Role</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>admin@pa165.com</td>
                <td>password</td>
                <td>admin</td>
            </tr>
            <tr>
                <td>sheep@pa165.com</td>
                <td>password</td>
                <td>customer</td>
            </tr>
            <tr>
                <td>jane@pa165.com</td>
                <td>password</td>
                <td>customer</td>
            </tr>
            <tr>
                <td>pablo@pa165.com</td>
                <td>password</td>
                <td>customer</td>
            </tr>
        </tbody>
</table>
    </jsp:attribute>
</my:pagetemplate>