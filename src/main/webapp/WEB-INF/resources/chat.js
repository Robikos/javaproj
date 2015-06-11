/**
 * 
 */
var BASE_URL = 'ws://localhost:8080//IRC//websocket';
var sock;

$(document).ready(function(){
	sock = new WebSocket(BASE_URL);
	init();
	
	$(document).keydown(function(e) {
		if ( e.which == 13 ) {
			e.preventDefault();
			msg = $("#input").val();
			if (msg != "")
			{
				console.log("Wysylanie: "+msg);
				$("#messages").append(username+ " napisal: "+msg+"<br>");
				sock.send("PRIVMSG "+channel+":"+msg);
			}
		}
    }); 
	
	$("#submit").click(function()
	{
		msg = $("#input").val();
		console.log("Wysylanie: "+msg);
		$("#messages").append(username+ " napisal: <b>"+msg+"</b><br>");
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
       $("#input").focus();
    };
		
    sock.onmessage = function (e) 
    { 
       console.log("Data received: "+e.data);
       recv = e.data;
       response = parseRec(recv);
       $("#messages").append(response+"<br>");
    };
		
    sock.onclose = function()
    {
       sock.send("QUIT "+username+" "+channel+" message");
       console.log("Socket disconnected"); 
    };
}

function parseRec(recv)
{
	if (recv == "") return "";
	
	var json = JSON.parse(recv);
	response = "";
	switch(json.command)
	{
		case "USER":
			target = json.target;
			payload = json.payload;
			response = "Połączenie: target->"+target+", payload->"+payload+"<br>";
			break;
		case "NICK":
			response = "Dołączył nick: <b>"+json.payload+"</b><br>";
			break;
		case "JOIN":
			who = json.target;
			channelname = json.payload;
			response = who + " dołączył do "+channelname+"<br>";
			break;
		case "PRIVMSG":
			who = json.target;
			from = json.source;
			msg = json.payload;
			if (who == "all")
			{
				response = from + " napisał: <b>"+msg+"</b><br>";
			}
			else
			{
				response = from + " napisał do "+who+": <b>"+msg+"</b><br>";
			}
			break;
		case "NAMES":
			break;
		case "QUIT":
			who = json.target;
			channelname = json.source;
			message = json.payload;
			response = who+" odchodzi z wiadomością: <b>"+message+"</b> z kanału: "+channelname+" <br>";
			break;
	}
	
	return response;
}
