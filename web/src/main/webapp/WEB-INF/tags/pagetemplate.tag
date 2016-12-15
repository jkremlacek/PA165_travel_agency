<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${title}"/></title>
    <!-- bootstrap loaded from content delivery network -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css"  crossorigin="anonymous">
    <jsp:invoke fragment="head"/>
</head>
<body>
    <script>
        function checkDate(date, name) {
            var regEx = /^\d\d[.]\d\d[.]\d\d\d\d[,]\s\d\d[:]\d\d$/;

            if(regEx.exec(date.value) === null) {
                alert("Please enter a valid " + name + ".");

                date.classList.add("control-group");
                date.classList.add("error");
                return false;
            } else {
                return true;
            }
        }
    </script>
    
<!-- navigation bar -->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Travel Agency</a>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/trip/list">Trips</a>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/excursion/list">Excursions</a>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/user/list">Users</a>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/reservation/list">Reservations</a>
        </div>
        <div>
            <c:if test="${empty authUser}">
                <ul class="nav navbar-nav navbar-right">
                <li><a href="${pageContext.request.contextPath}/auth/login">
                    <span class="glyphicon glyphicon-log-in"></span> Login</a>
                </li>
                </ul>
            </c:if>
            <c:if test="${not empty authUser}">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="${pageContext.request.contextPath}/user/detail/${authUser.id}">
                        <span class="glyphicon glyphicon-user"></span> ${authUser.name}</a>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/auth/logout">
                        <span class="glyphicon glyphicon-log-out"></span> Logout</a>
                    </li>
                </ul>
            </c:if>
        </div>
    </div>
</nav>   

<div class="container">

    <!-- alerts -->
    <c:if test="${not empty alert_danger}">
        <div class="alert alert-danger fade in" role="alert">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            <c:out value="${alert_danger}"/></div>
    </c:if>
    <c:if test="${not empty alert_info}">
        <div class="alert alert-info fade in" role="alert">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <c:out value="${alert_info}"/>
        </div>
    </c:if>
    <c:if test="${not empty alert_success}">
        <div class="alert alert-success fade in" role="alert">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <c:out value="${alert_success}"/>
        </div>
    </c:if>
    <c:if test="${not empty alert_warning}">
        <div class="alert alert-warning fade in" role="alert">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <c:out value="${alert_warning}"/>
        </div>
    </c:if>

    <!-- page body -->
    <jsp:invoke fragment="body"/>

    <!-- footer -->
    <footer class="footer">

    </footer>
</div>
<!-- javascripts placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
