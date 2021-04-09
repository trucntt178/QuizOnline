<%-- 
    Document   : chooseSubject
    Created on : Jan 25, 2021, 2:45:35 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Choose Subject</title>
    </head>
    <body>
        <c:set var="name" value="${sessionScope.NAME}"/>
        <c:set var="isAdmin" value="${sessionScope.ISADMIN}"/>
        <c:if test="${not empty name}">
            <font color="red">
            Welcome, ${sessionScope.NAME}
            </font>
            <a href="logOut"><button>Log Out</button></a>
        </c:if>
        <c:if test="${not empty name && isAdmin}">
            <h1>Choose subject to show question</h1>
            <c:set var="subjectList" value="${sessionScope.SUBJECTLIST}"/>
            <c:if test="${not empty subjectList}">
                <c:forEach var="subject" items="${subjectList}" varStatus="counter">
                    <form action="searchQuestion">
                        <input type="submit" value="${subject.subjectId} - ${subject.name}" name="btnAction" />
                        <input type="hidden" name="subjectId" value="${subject.subjectId}" />
                        <input type="hidden" name="subjectName" value="${subject.name}" />
                    </form><br>
                </c:forEach> 
            </c:if>
            <c:if test="${empty subjectList}">
                No subject to choose!!!
            </c:if>
        </c:if>
        <c:if test="${empty name}">
            You are not authorized to access this site<br>
            <a href="">Redirect to the login page</a>
        </c:if>
        <c:if test="${not empty name && !isAdmin}">
            <br>Only admin can access this page.
        </c:if>
    </body>
</html>
