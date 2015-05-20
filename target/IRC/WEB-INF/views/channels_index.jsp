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
<body>
<h1>
    Add a Channel
</h1>
 
<c:url var="addAction" value="/channels/add" ></c:url>
 
<form:form action="${addAction}" commandName="channel">
<table>
    <c:if test="${!empty channel.name}">
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
            <form:label path="name">
                <spring:message text="Name"/>
            </form:label>
        </td>
        <td>
            <form:input path="name" />
        </td> 
    </tr>
    <tr>
        <td>
            <form:label path="topic">
                <spring:message text="Topic"/>
            </form:label>
        </td>
        <td>
            <form:input path="topic" />
        </td>
    </tr>
    <tr>
        <td>
            <form:label path="description">
                <spring:message text="Description"/>
            </form:label>
        </td>
        <td>
            <form:input path="description" />
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <c:if test="${!empty channel.name}">
                <input type="submit"
                    value="<spring:message text="Edit Channel"/>" />
            </c:if>
            <c:if test="${empty channel.name}">
                <input type="submit"
                    value="<spring:message text="Add Channel"/>" />
            </c:if>
        </td>
    </tr>
</table>  
</form:form>
<br>
<h3>Channels List</h3>
<c:if test="${!empty listChannels}">
    <table class="tg">
    <tr>
        <th width="80">Channel ID</th>
        <th width="120">Channel Name</th>
        <th width="120">Channel Topic</th>
        <th width="120">Channel Description</th>
        <th width="60">Edit</th>
        <th width="60">Delete</th>
    </tr>
    <c:forEach items="${listChannels}" var="channel">
        <tr>
            <td>${channel.id}</td>
            <td>${channel.name}</td>
            <td>${channel.topic}</td>
            <td>${channel.description}</td>
            <td><a href="<c:url value='/channels/edit/${channel.id}' />" >Edit</a></td>
            <td><a href="<c:url value='/channels/remove/${channel.id}' />" >Delete</a></td>
        </tr>
    </c:forEach>
    </table>
</c:if>
</body>
</html>