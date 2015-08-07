<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
#messages {
	padding: 5px;
	width: 100%;
	height: 400px;
	border: 1px solid black;
	border-radius: 2px;
}
#input { width: 100% }
#left { width: 80%; float: left;}
#right { width: 20%; float: right;}
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.4.2.min.js"></script>
<!-- <script type="text/javascript" src="http://cdn.jsdelivr.net/sockjs/1.0.0/sockjs.min.js"></script>  -->
<script type="text/javascript" src="../res/chat.js"></script>
<script type="text/javascript">
var username = '${user.login}';
var channel = '${channel.name}';
</script>

<title>Channel - ${channel.name}</title>
</head>
<body>
Welcome to ${channel.name} Channel !<br>
<br>
Username: ${user.login}<br>
Password: ${user.encrypted_password}<br>
<div id="left">
<h1>Czat:</h1>
<div id="messages">
	Tekst:
</div><br>
Input:<br>
<input id="input"></input><br>
<button id="submit" type="button">Send!</button>
</div>


<div id="right">
Lista:<br>
<div id="rcontent">

</div>
</div>

</body>
</html>