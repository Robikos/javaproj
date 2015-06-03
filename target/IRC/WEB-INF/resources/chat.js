/**
 * 
 */
$(document).ready(function(){
	BASE_URL = 'http://localhost:9999';
	var sock = new SockJS(BASE_URL);
	
	// Open the connection
	sock.onopen = function() {
	  console.log('open');
	  console.log('Username: '+username);
	};

	// On connection close
	sock.onclose = function() {
	  console.log('close');
	};
	
	sock.onmessage = function(e) {
	  console.log('Message: '+e.data);
	}
	
	$("#submit").click(function()
	{
		console.log("Siema");
		$("#messages").append("<b>Wcisnieto</b><br>");
	});

});
