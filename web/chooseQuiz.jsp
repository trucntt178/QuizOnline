<%-- 
    Document   : chooseQuiz
    Created on : Jan 30, 2021, 9:50:43 AM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Choose Quiz</title>
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
        <c:if test="${not empty name && !isAdmin}">
            <h1>Choose subject to take quiz</h1>
            <c:set var="subjectList" value="${sessionScope.SUBJECTLIST}"/>
            <c:if test="${not empty subjectList}">
                <c:forEach var="subject" items="${subjectList}" varStatus="counter">
                    <form action="showHistory">
                        <input type="submit" value="${subject.subjectId} - ${subject.name}" name="btnAction" />
                        <input type="hidden" name="subjectId" value="${subject.subjectId}" />
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
        <c:if test="${not empty name && isAdmin}">
            <br>Only student can access this page.
        </c:if>
    </body>
</html>
