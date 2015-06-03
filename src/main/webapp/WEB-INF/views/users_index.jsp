<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Users</title>
    <style type="text/css">
        .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
        .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
        .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
        .tg .tg-4eph{background-color:#f9f9f9}
    </style>
</head>

<br>
<h3>Users List</h3>
<c:if test="${!empty listUsers}">
    <table class="tg">
    <tr>
        <th width="80">User ID</th>
        <th width="120">User Name</th>
        <th width="120">User Encrypted Password</th>
        <th width="120">User registration date</th>
        <th width="120">User avatar filename</th>
        <th width="60">Edit</th>
        <th width="60">Delete</th>
    </tr>
    <c:forEach items="${listUsers}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.login}</td>
            <td>${user.encrypted_password}</td>
            <td>${user.registration_date}</td>
            <td>${user.avatar_filename}</td>
            <td><a href="<c:url value='/users/edit/${user.id}' />" >Edit</a></td>
            <td><a href="<c:url value='/users/remove/${user.id}' />" >Delete</a></td>
        </tr>
    </c:forEach>
    </table>
</c:if>
<a href="<c:url value='/users/add/' />" >Add a user</a>
<a href="<c:url value='/users/login/' />" >Login!</a>
</body>
</html>