<%-- 
    Document   : quizResult
    Created on : Feb 1, 2021, 12:04:20 AM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Page</title>
        <c:set var="historyList" value="${requestScope.HISTORYLIST}"/>
        <script>
            var totalHistory = ${fn:length(historyList)};
            var pageSize = 10;
            var numOfPage = Math.ceil(totalHistory / pageSize);
            var currPage = numOfPage;

            function next() {
                if (currPage < numOfPage) {
                    currPage++;
                    setVisible();
                }
            }

            function previous() {
                if (currPage > 1) {
                    currPage--;
                    setVisible();
                }
            }

            function setVisible() {
                var firstRecord = pageSize * (currPage - 1) + 1;
                var lastRecord = pageSize * currPage;
                if (lastRecord > totalHistory) {
                    lastRecord = totalHistory;
                }
                for (var i = firstRecord; i <= lastRecord; i++) {
                    document.getElementById(i).hidden = false;
                }
                for (var i = 1; i < firstRecord; i++) {
                    document.getElementById(i).hidden = true;
                }
                for (var i = lastRecord + 1; i <= totalHistory; i++) {
                    document.getElementById(i).hidden = true;
                }
            }
        </script>
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
            <c:set var="subject" value="${sessionScope.SUBJECT}"/>       
            <h1>${subject.subjectId} - ${subject.name}</h1>
            <c:set var="continueQuiz" value="${requestScope.CONTINUE}"/>

            <c:if test="${not empty historyList}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Take Date</th>
                            <th>Number Of Correct Answers</th>
                            <th>Score</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${historyList}" varStatus="counter">
                            <tr id="${counter.count}">
                                <td>
                                    ${counter.count}
                                    .</td>
                                <td>${dto.takeDate}</td>
                                <td>${dto.numOfCorrect} / ${dto.totalQuestion}</td>
                                <td>${dto.score}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table><br>
                <input type="submit" value="<" onclick="previous()"/>
                <script>document.write(currPage + '/' + numOfPage);</script>
                <input type="submit" value=">" onclick="next()"/><br>
                <script>setVisible();</script>
                <h2>Your last score is ${historyList[fn:length(historyList) - 1].score}/10.0</h2><br>
                <a href="loadQuestion">
                    <c:if test="${not empty continueQuiz}"><button>${continueQuiz}</button></c:if>
                    <c:if test="${empty continueQuiz}"><button>Re-attemp Quiz</button></c:if>
                    </a>
            </c:if>
            <c:if test="${empty historyList}">
                No history<br>            
                <a href="loadQuestion">
                    <c:if test="${not empty continueQuiz}"><button>${continueQuiz}</button></c:if>
                    <c:if test="${empty continueQuiz}"><button>Start Quiz</button></c:if>
                    </a>
            </c:if>
            <br><br><a href="loadSubject"><button>Choose Another Subject To Take Quiz</button></a>
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
