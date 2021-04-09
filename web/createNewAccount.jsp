<%-- 
    Document   : createNewAccount
    Created on : Jan 24, 2021, 3:15:54 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Account</title>
    </head>
    <body>
        <h1>Create New Account</h1>
        <form action="createAccountAction" method="POST">
            <c:set var="errs" value="${requestScope.CREATEACCERR}"/>
            Email <input type="text" name="txtEmail" value="${param.txtEmail}" /> (Format: name@domain. Max: 50 chars)<br>
            <c:if test="${not empty errs.emailFormatErr}">
                <font color="red">
                ${errs.emailFormatErr}
                </font><br>
            </c:if>
            <c:if test="${not empty errs.emailExistedErr}">
                <font color="red">
                ${errs.emailExistedErr}
                </font><br>
            </c:if><br>
                Name <input type="text" name="txtName" value="${param.txtName}" /> (1 - 50 chars)<br>
            <c:if test="${not empty errs.nameLengthErr}">
                <font color="red">
                ${errs.nameLengthErr}
                </font><br>
            </c:if><br>
                Password <input type="password" name="txtPassword" value="" /> (6 - 20 chars)<br>
            <c:if test="${not empty errs.passwordLengthErr}">
                <font color="red">
                ${errs.passwordLengthErr}
                </font><br>
            </c:if><br>
            Confirm Password <input type="password" name="txtConfirm" value="" /><br>
            <c:if test="${not empty errs.confirmNotMatched}">
                <font color="red">
                ${errs.confirmNotMatched}
                </font><br>
            </c:if><br>
                <input type="submit" value="Create" name="btnAction" />
            <input type="reset" value="Reset" />
        </form>
            <br><a href="loginErr"><button>Return To Login Page</button></a>
    </body>
</html>
