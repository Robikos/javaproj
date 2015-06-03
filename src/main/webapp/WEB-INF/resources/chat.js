/**
 * 
 */
var BASE_URL = 'ws://localhost:9998';

$(document).ready(function(){
	init();
	
	$("#submit").click(function()
	{
		console.log("Siema");
		$("#messages").append("<b>Wcisnieto</b><br>");
	});

});

function init()
{
	if (!("WebSocket" in window))
    {
		alert("WebSocket nie jest obsługiwany przez przeglądarkę");
    }
	
	console.log("Łącze...");
	
    var sock = new WebSocket(BASE_URL);
    
    sock.onopen = function()
    {
       sock.send("User "+username+" trzy cztery piec");
       console.log("Socket connected");
    };
		
    sock.onmessage = function (e) 
    { 
       console.log("Data received: "+e.data);
    };
		
    sock.onclose = function()
    { 
       console.log("Socket disconnected"); 
    };
}
