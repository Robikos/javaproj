<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>This is me</title>
</head>
<body>
	<table class="tg">
    <tr>
        <th width="80">User ID</th>
        <th width="120">User Name</th>
        <th width="120">User Encrypted Password</th>
        <th width="120">User registration date</th>
        <th width="120">User avatar filename</th>
    </tr>
    <tr>
        <td>${currentUser.id}</td>
        <td>${currentUser.login}</td>
        <td>${currentUser.encrypted_password}</td>
        <td>${currentUser.registration_date}</td>
        <td>${currentUser.avatar_filename}</td>
    </tr>
	</table>
</body>
</html>