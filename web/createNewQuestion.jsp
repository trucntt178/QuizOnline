<%-- 
    Document   : createNewQuestion
    Created on : Jan 27, 2021, 2:57:41 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Question</title>
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
            <h1>Create New Question</h1>
            <form action="createQuestion" method="POST">
                <c:set var="errs" value="${requestScope.CREATEQUESERR}"/>
                <c:set var="currSub" value="${sessionScope.SUBJECT}"/>
                Subject: <select name="cboChooseSubject">
                    <c:forEach var="subject" items="${sessionScope.SUBJECTLIST}">
                        <option value="${subject.subjectId}"                           
                                ${subject.subjectId == currSub.subjectId && errs == null ? 'selected="selected"' : ''}
                                ${subject.subjectId == param.cboChooseSubject && errs != null ? 'selected="selected"' : ''}>${subject.subjectId} - ${subject.name}</option>
                    </c:forEach>
                </select><br><br>
                Question Content: <textarea name="txtQuestionContent" rows="10" cols="30" 
                                            value="${param.txtQuestionContent}">${param.txtQuestionContent}</textarea><br>
                <c:if test="${not empty errs.contentLengthErr}">
                    <font color="red">
                    ${errs.contentLengthErr}
                    </font><br>
                </c:if><br>
                Answer:<br>
                <input type="radio" name="answer" value="1" ${param.answer == '1' ? 'checked="checked"' : ''}/>
                <input type="text" name="txtAnswer1" value="${param.txtAnswer1}" /><br>
                <c:if test="${not empty errs.answer1LengthErr}">
                    <font color="red">
                    ${errs.answer1LengthErr}
                    </font><br>
                </c:if>
                <input type="radio" name="answer" value="2" ${param.answer == '2' ? 'checked="checked"' : ''}/>
                <input type="text" name="txtAnswer2" value="${param.txtAnswer2}" /><br>
                <c:if test="${not empty errs.answer2LengthErr}">
                    <font color="red">
                    ${errs.answer2LengthErr}
                    </font><br>
                </c:if>
                <input type="radio" name="answer" value="3" ${param.answer == '3' ? 'checked="checked"' : ''}/>
                <input type="text" name="txtAnswer3" value="${param.txtAnswer3}" /><br>
                <c:if test="${not empty errs.answer3LengthErr}">
                    <font color="red">
                    ${errs.answer3LengthErr}
                    </font><br>
                </c:if>
                <input type="radio" name="answer" value="4" ${param.answer == '4' ? 'checked="checked"' : ''}/>
                <input type="text" name="txtAnswer4" value="${param.txtAnswer4}" /><br>
                <c:if test="${not empty errs.answer4LengthErr}">
                    <font color="red">
                    ${errs.answer4LengthErr}
                    </font><br>
                </c:if>
                <c:if test="${not empty errs.correctAnsErr}">
                    <font color="red">
                    ${errs.correctAnsErr}
                    </font><br>
                </c:if><br>
                <c:if test="${not empty errs.duplicatedAns}">
                    <font color="red">
                    ${errs.duplicatedAns}
                    </font><br>
                </c:if><br>
                <input type="submit" value="Create" name="btnAction" /><br>           
            </form>
            <br><a href="searchQuestion"><button>Return To Search Page</button></a>
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
