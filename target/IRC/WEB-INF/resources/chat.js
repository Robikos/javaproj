/**
 * 
 */
var BASE_URL = 'ws://localhost:8080//IRC//websocket';
var sock;

$(document).ready(function(){
	sock = new WebSocket(BASE_URL);
	init();
	
	$("#submit").click(function()
	{
		msg = $("#input").val();
		console.log("Wysylanie: "+msg);
		$("#messages").append(username+ " napisal: "+msg+"<br>");
		sock.send("PRIVMSG "+channel+":"+msg);
	});

});

function init()
{
	if (!("WebSocket" in window))
    {
		alert("WebSocket nie jest obsługiwany przez przeglądarkę");
    }
	
	console.log("Łącze...");
	
    
    
    sock.onopen = function()
    {
       sock.send("USER "+username+" trzy cztery piec");
       sock.send("NICK "+username);
       sock.send("JOIN "+channel);
       console.log("Socket connected");
    };
		
    sock.onmessage = function (e) 
    { 
       console.log("Data received: "+e.data);
       $("#messages").append(e.data+"<br>");
    };
		
    sock.onclose = function()
    { 
       console.log("Socket disconnected"); 
    };
}
