<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add user</title>
<style type="text/css">
	.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
	.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
	.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
	.tg .tg-4eph{background-color:#f9f9f9}
</style>
</head>
<body>
<h1>
    Add a user
</h1>
 
<c:url var="addAction" value="/users/add" ></c:url>
 
<form:form action="${addAction}" commandName="user">
<table>
    <c:if test="${!empty user.login}">
    <tr>
        <td>
            <form:label path="id">
                <spring:message text="ID"/>
            </form:label>
        </td>
        <td>
            <form:input path="id" readonly="true" size="8"  disabled="true" />
            <form:hidden path="id" />
        </td> 
    </tr>
    </c:if>
    <tr>
        <td>
            <form:label path="login">
                <spring:message text="Login"/>
            </form:label>
        </td>
        <td>
            <form:input path="login" />
        </td> 
    </tr>
    <tr>
        <td>
            <form:label path="password">
                <spring:message text="Password"/>
            </form:label>
        </td>
        <td>
            <form:input path="password" />
        </td>
    </tr>
    <tr>
        <td>
            <form:label path="avatar_filename">
                <spring:message text="Avatar Filename"/>
            </form:label>
        </td>
        <td>
            <form:input path="avatar_filename" />
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <c:if test="${!empty user.login}">
                <input type="submit"
                    value="<spring:message text="Edit user"/>" />
            </c:if>
            <c:if test="${empty user.login}">
                <input type="submit"
                    value="<spring:message text="Add user"/>" />
            </c:if>
        </td>
    </tr>
</table>  
</form:form>

</body>
</html>