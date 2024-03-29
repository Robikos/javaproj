<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Channels</title>
    <style type="text/css">
        .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
        .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
        .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
        .tg .tg-4eph{background-color:#f9f9f9}
    </style>
</head>

<br>
<h3>Channels List</h3>
<c:if test="${!empty listChannels}">
    <table class="tg">
    <tr>
        <th width="80">Channel ID</th>
        <th width="120">Channel Name</th>
        <th width="120">Channel Topic</th>
        <th width="120">Channel Description</th>
        <th width="60">Connect</th>
        <th width="60">Edit</th>
        <th width="60">Delete</th>
    </tr>
    <c:forEach items="${listChannels}" var="channel">
        <tr>
            <td>${channel.id}</td>
            <td>${channel.name}</td>
            <td>${channel.topic}</td>
            <td>${channel.description}</td>
            <td><a href="<c:url value='/channels/${channel.id}' />" >Connect!</a></td>
            <td><a href="<c:url value='/channels/edit/${channel.id}' />" >Edit</a></td>
            <td><a href="<c:url value='/channels/remove/${channel.id}' />" >Delete</a></td>
        </tr>
    </c:forEach>
    </table>
</c:if>
<br>
<a href="<c:url value='/channels/add/' />" >Add a channel</a>, 
<a href="<c:url value='/users/login/' />" >Login</a>, 
<a href="<c:url value='/users/me' />" >Me</a>
</body>
</html>