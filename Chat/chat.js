var socket;
function connectToServer(){
    console.log("INSIDE OF: connectToServer()");
    socket = new WebSocket("ws://localhost:8080/SalBids/ws");
    socket.onopen = function(event){
        document.getElementById("mychat").innerHTML += "Connected" + "<br />";
    }
    socket.onmessage = function(event){
		document.getElementById("mychat").innerHTML += event.data + "<br />";
	}
	socket.onclose = function(event){
		document.getElementById("mychat").innerHTML += "Disconnected";
	}
}

function sendMessage(){
	console.log("INSIDE OF: sendMessage()");
	//DOES THIS EVEN WORK??
	var cookie = getCookie("userName");
	socket.send(cookie + document.chatform.message.value);
	return false;
	
}
