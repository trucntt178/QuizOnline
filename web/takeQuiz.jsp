<%-- 
    Document   : takeQuiz
    Created on : Jan 30, 2021, 4:43:31 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">       
        <title>Take quiz</title>
        <script>
            var deadline = ${sessionScope.DEADLINE};
            var x = setInterval(quizTimer, 1000);
            var pageSize = 10;
            var totalQuest = ${sessionScope.TOTALQUEST};
            var numOfPage = Math.ceil(totalQuest / pageSize);
            var currentPage = 1;
            function quizTimer() {
                var now = new Date();
                var t = deadline - now;
                var min = Math.floor((t % (1000 * 60 * 60)) / (1000 * 60));
                var sec = Math.floor((t % (1000 * 60)) / 1000);
                if (parseInt(sec) > 0) {
                    document.getElementById("showTime").innerHTML = "Time Remaining: " + pad(min, 2)
                            + ":" + pad(sec, 2);
                    if (parseInt(min) === 0) {
                        document.getElementById("showTime").style.color = "red";
                    }
                    sec = parseInt(sec) - 1;
                } else if (parseInt(min) === 0 && parseInt(sec) === 0) {
                    document.getElementById("showTime").innerHTML = "Time Remaining: " + pad(min, 2)
                            + ":" + pad(sec, 2);
                    document.getElementById("showTime").style.color = "red";
                    clearInterval(x);
                    document.submitForm.submit();
                } else if (parseInt(sec) === 0) {
                    document.getElementById("showTime").innerHTML = "Time Remaining: " + pad(min, 2)
                            + ":" + pad(sec, 2);
                    min = parseInt(min) - 1;
                    sec = 59;
                }
            }

            function  pad(number, length) {
                var str = '' + number;
                if (str.length < length) {
                    str = '0' + str;
                }
                return str;
            }

            function next() {
                if (currentPage < numOfPage) {
                    currentPage++;
                    setVisible();
                }
            }

            function previous() {
                if (currentPage > 1) {
                    currentPage--;
                    setVisible();
                }
            }

            function setVisible() {
                var firstRecord = pageSize * (currentPage - 1) + 1;
                var lastRecord = pageSize * currentPage;
                if (lastRecord > totalQuest) {
                    lastRecord = totalQuest;
                }
                for (var i = firstRecord; i <= lastRecord; i++) {
                    document.getElementById(i).hidden = false;
                }
                for (var i = 1; i < firstRecord; i++) {
                    document.getElementById(i).hidden = true;
                }
                for (var i = lastRecord + 1; i <= totalQuest; i++) {
                    document.getElementById(i).hidden = true;
                }
            }

            function submit() {
                if (confirm("Do you want to submit?")) {
                    document.submitForm.submit();
                }
            }
        </script>   
    </head>
    <body onload="quizTimer()">
        <c:set var="name" value="${sessionScope.NAME}"/>
        <c:set var="isAdmin" value="${sessionScope.ISADMIN}"/>
        <c:if test="${not empty name}">
            <font color="red">
            Welcome, ${sessionScope.NAME}
            </font>
            <a href="logOut"><button>Log Out</button></a>
        </c:if>
        <c:if test="${not empty name && !isAdmin}">
            <h1>Take Quiz</h1>
            <h2 id="showTime"></h2>           
            <c:set var="questionList" value="${sessionScope.QUESTIONLISTINQUIZ}"/>
            <form action="submitQuiz" name="submitForm">
                <c:forEach var="question" items="${questionList}" varStatus="counter">
                    <p id="${counter.count}" hidden>
                        <b>Question ${counter.count}:</b><br>
                        ${question.content}<br>
                        <c:forEach var="answer" items="${question.answerList}">
                            <input type="radio" name="answer${counter.count}" value="${question.id}-${answer.id}"> ${answer.content}<br>
                        </c:forEach><br>
                    </p>
                </c:forEach>                
            </form>
            <script>
                setVisible(1);
            </script>
            <input type="submit" value="Previous" onclick="previous()"/>
            <input type="submit" value="Next" onclick="next()"/><br><br>
            <input type="submit" value="Finish And Submit" onclick="submit()"/>
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
