<%-- 
    Document   : searchQuestion
    Created on : Jan 26, 2021, 12:54:40 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Question</title>
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
            <h1>Search Question Page</h1>
            <form action="searchQuestion">
                Search By Content <input type="text" name="txtSearchContentValue" 
                                         value="${param.txtSearchContentValue}" />
                By Status <select name="cboSearchStatus">
                    <option value="All">All</option>
                    <option value="Active" 
                            ${'Active' == param.cboSearchStatus ? 'selected="selected"' : ''}>Active</option>
                    <option value="Inactive" 
                            ${'Inactive' == param.cboSearchStatus ? 'selected="selected"' : ''}>Inactive</option>
                </select>
                <input type="submit" value="Search" name="btnAction" /><br><br>
                <c:if test="${not empty requestScope.SEARCHERR}">
                    <font color="red">
                    ${requestScope.SEARCHERR}
                    </font><br>
                </c:if>
            </form>
            <a href="loadSubject"><button>Choose Another Subject To Search</button></a>
            <a href="createQuestionPage"><button>Create New Question</button></a><br><br>
            <c:set var="result" value="${requestScope.QUESTIONLIST}"/>
            <c:if test="${not empty result}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Id</th>
                            <th>Subject</th>
                            <th>Content</th>
                            <th>Answer</th>
                            <th>Create Date</th>
                            <th>Status</th>
                            <th>Update</th>
                            <th>Remove</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="errs" value="${requestScope.UPDATEERRS}"/>
                        <c:forEach var="dto" items="${result}" varStatus="counter1">
                        <form action="updateQuestion">
                            <tr>
                                <td>
                                    ${counter1.count}
                                    .</td>
                                <td>
                                    ${dto.id}
                                    <input type="hidden" name="questionId" value="${dto.id}" />
                                </td>
                                <td>
                                    <c:if test="${dto.status}">
                                        <select name="cboUpdateSubject">
                                            <c:forEach var="subject" items="${sessionScope.SUBJECTLIST}">
                                                <option value="${subject.subjectId}"
                                                        ${subject.subjectId == dto.subject.subjectId ? 'selected="selected"' : ''}>
                                                    ${subject.subjectId} - ${subject.name}</option>
                                                </c:forEach>
                                        </select>
                                    </c:if>
                                    <c:if test="${!dto.status}">
                                        ${dto.subject.subjectId} - ${dto.subject.name}
                                    </c:if>                                  
                                </td>
                                <td style="width: 350px">
                                    <c:if test="${dto.status}">
                                        <textarea name="txtUpdateContent" rows="5" cols="40" 
                                                  value="${dto.content}">${dto.content}</textarea>
                                        <c:if test="${not empty errs.contentLengthErr && dto.id == param.questionId}">
                                            <font color="red">
                                            <br>${errs.contentLengthErr}
                                            </font>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${!dto.status}">
                                        ${dto.content}
                                    </c:if>
                                </td>
                                <td>
                                    <c:forEach var="answer" items="${dto.answerList}" varStatus="counter2">
                                        <input type="radio" name="rdoAnswer${dto.id}" value="${counter2.count}"
                                               ${answer.isTrue ? 'checked="checked"' : ''} ${dto.status ? '' : 'disabled="true"'}/>
                                        <c:if test="${dto.status}">
                                            <input type="text" name="txtUpdateAnswer${counter2.count}" value="${answer.content}" /><br>
                                            <input type="hidden" name="answerId${counter2.count}" value="${answer.id}" />
                                            <c:if test="${not empty errs.answer1LengthErr && dto.id == param.questionId && counter2.count == '1'}">
                                                <font color="red">
                                                ${errs.answer1LengthErr}<br>
                                                </font>
                                            </c:if>
                                            <c:if test="${not empty errs.answer2LengthErr && dto.id == param.questionId && counter2.count == '2'}">
                                                <font color="red">
                                                ${errs.answer2LengthErr}<br>
                                                </font>
                                            </c:if>
                                            <c:if test="${not empty errs.answer3LengthErr && dto.id == param.questionId && counter2.count == '3'}">
                                                <font color="red">
                                                ${errs.answer3LengthErr}<br>
                                                </font>
                                            </c:if>
                                            <c:if test="${not empty errs.answer4LengthErr && dto.id == param.questionId && counter2.count == '4'}">
                                                <font color="red">
                                                ${errs.answer4LengthErr}<br>
                                                </font>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${!dto.status}">
                                            ${answer.content}<br>
                                        </c:if>
                                    </c:forEach>

                                </td>
                                <td>
                                    ${dto.createDate}
                                </td>
                                <td>
                                    <select name="cboUpdateStatus">
                                        <option value="Active" ${dto.status ? 'selected="selected"' : ''}>Active
                                        </option>
                                        <option value="Inactive" ${dto.status ? '' : 'selected="selected"'}>Inactive
                                        </option>
                                    </select>
                                </td>
                                <td>
                                    <input type="submit" value="Update" name="btnAction" />
                                    <input type="hidden" name="txtSearchContentValue" value="${param.txtSearchContentValue}" />
                                    <input type="hidden" name="cboSearchStatus" value="${param.cboSearchStatus}" />
                                    <input type="hidden" name="currPage" value="${param.currPage}" />
                                </td>
                        </form>
                        <td>
                            <c:if test="${dto.status}">
                                <c:url var="urlRewriting" value="removeQuestion">
                                    <c:param name="pk" value="${dto.id}"/>
                                    <c:param name="lastSearchContentValue" value="${param.txtSearchContentValue}"/>
                                    <c:param name="lastSearchStatus" value="${param.cboSearchStatus}"/>
                                    <c:param name="currPage" value="${param.currPage}"/>
                                </c:url>
                                <a href="${urlRewriting}" onclick="return confirm('Do you want to remove?')"><button>Remove</button></a>
                            </c:if>
                            <c:if test="${!dto.status}">
                                <button disabled="true">Remove</button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <form action="searchQuestion">
            <input type="submit" value="<" name="btnAction" />
            ${param.currPage} / ${param.numOfPage}
            <input type="hidden" name="txtSearchContentValue" value="${param.txtSearchContentValue}" />
            <input type="hidden" name="cboSearchStatus" value="${param.cboSearchStatus}" />
            <input type="hidden" name="currPage" value="${param.currPage}" />
            <input type="submit" value=">" name="btnAction" />
        </form>
    </c:if>
    <c:if test="${empty result}">
        No records!!
    </c:if>
</form>
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
