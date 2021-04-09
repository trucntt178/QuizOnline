<%-- 
    Document   : login
    Created on : Jan 24, 2021, 3:54:31 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Login Page</h1>
        <form action="login">
            Email: <input type="text" name="txtEmail" value="" /><br><br>
            Password: <input type="password" name="txtPassword" value="" /><br><br>
            <input type="submit" value="Login" name="btnAction"/>
            <input type="reset" value="Reset" /><br>
        </form>
        <c:if test="${not empty param.invalidMsg}">
            <font color="red">
            ${param.invalidMsg}
            </font><br>
        </c:if>
            <br><a href="createNewAccountPage"><button>Create New Account</button></a>
    </body>
</html>
