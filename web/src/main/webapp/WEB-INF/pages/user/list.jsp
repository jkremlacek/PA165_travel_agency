<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Users">
<jsp:attribute name="body">
<table class="table">
        <thead>
        <tr>
            <th>Name</th>
            <th>E-mail</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.mail}"/></td>                
                <td>
                    <a href="/pa165/user/detail/${user.id}" class="btn btn-primary">Detail</a>
                </td>
                
                
                <c:if test="${authUser.id==user.id}">
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}/user/edit/${user.id}">
                                <button type="submit" class="btn btn-primary">Edit</button>
                        </form>

                    </td>
                </c:if>   
                <c:if test="${authUser.getIsAdmin()}">
                    <c:if test="${authUser.id!=user.id}">
                    <td>
                        <c:if test="${user.getIsAdmin()}">
                            <form method="get" action="${pageContext.request.contextPath}/user/changeRole/${user.id}">
                                <button type="submit" class="btn btn-primary">Cancel admin</button>
                            </form>
                        </c:if>
                        <c:if test="${!user.getIsAdmin()}">
                            <form method="get" action="${pageContext.request.contextPath}/user/changeRole/${user.id}">
                                <button type="submit" class="btn btn-primary">Make admin</button>
                            </form>
                        </c:if>
                    </td>
                    </c:if>
                </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</jsp:attribute>
</my:pagetemplate>

