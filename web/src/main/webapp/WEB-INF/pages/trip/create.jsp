<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  User: jakub_kremlacek
--%>
<my:pagetemplate title="Create a trip">
<jsp:attribute name="body">
    <script>
    function checkform() {        
        if(checkDate(document.frmMr.dateFrom, "Date From") === false) {return false;} 
        if(checkDate(document.frmMr.dateTo, "Date To") === false) {return false;} 
            
        document.frmMr.submit();
    }
    </script>
<form:form name="frmMr" method="post" action="${pageContext.request.contextPath}/trip/create" modelAttribute="newTrip">
    <table class="table">
        <tr>
            <td>Name</td>
            <td><form:input path="name" type="text" size="30" required="required" pattern=".*\S+.*"/></td>
        </tr>
        <tr>
            <td>Destination</td>
            <td><form:input path="destination" type="text" size="40" required="required" pattern=".*\S+.*"/></td>
        </tr>
        <tr>
            <td>Description</td>
            <td><form:input path="description" type="text" size="40" /></td>
        </tr>
        <tr>
            <td>Capacity</td>
            <td><form:input path="capacity" type="number" size="40" required="required" min="1"/></td>
        </tr>
        <tr>
            <td>Date From</td>
            <td>
                <form:label path="dateFrom"/>
                <c:set var="now" value="<%=new java.util.Date()%>"/>
                <form:input name="dateFrom" path="dateFrom" class="date" type="text"
                            value="" required="required" pattern="^(?:(?:31(\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})[,] [012]{0,1}[0-9]:[0-5][0-9]$"/>
                <br>
                Format: dd.MM.yyyy, HH:mm
            </td>
        </tr>
        <tr>
            <td>Date To</td>
            <td>
                <form:label path="dateTo"/>
                <c:set var="now" value="<%=new java.util.Date()%>"/>
                <form:input name="dateTo" path="dateTo" class="date" type="text"
                            value="" required="required" pattern="^(?:(?:31(\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})[,] [012]{0,1}[0-9]:[0-5][0-9]$"/>
                <br>
                Format: dd.MM.yyyy, HH:mm
            </td>
        </tr>
        <tr>
            <td>Price</td>
            <td><form:input path="price" type="number" size="10" required="required" min="1"/> CZK</td>
        </tr>
    </table>
    <button class="btn btn-primary" type="submit" onClick="return checkform()">Create Trip</button>
</form:form>
</jsp:attribute>
</my:pagetemplate>