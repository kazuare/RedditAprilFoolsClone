<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MLDeer</title>
<style>
	html, body, div, canvas {
    margin: 0;
    padding: 0;
}
</style>
</head>
<body>
<canvas id="myCanvas" width="1000" height="600" style="border:1px solid #000000;"></canvas>

<script src='http://code.jquery.com/jquery-latest.min.js' type='text/javascript'></script>
<script  src="http://code.jquery.com/ui/1.12.1/jquery-ui.min.js"  integrity="sha256-VazP97ZCwtekAsvgPBSUwPFKdrwD3unUfSGVYrahUqU="  crossorigin="anonymous"></script>

<script>
$(document).ready(function(){
	color = "red";
	var canvas = document.getElementById('myCanvas');  //store canvas outside event loop
	ctx = canvas.getContext("2d");
	var isDown = false;     //flag we use to keep track
	var x1, y1, x2, y2;     //to store the coords

	$( "#red" ).click(function() {
		color = "red";
	});
	$( "#green" ).click(function() {
		color = "green";
	});
	$( "#blue" ).click(function() {
		color = "blue";
	});

	$( "#white" ).click(function() {
		color = "white";
	});

	$( "#black" ).click(function() {
		color = "black";
	});
	
	// when mouse button is clicked and held    
	$('#myCanvas').on('mousedown', function(e){
	    if (isDown === false) {

	        isDown = true;

	        var pos = getMousePos(canvas, e);
	        x1 = pos.x;
	        y1 = pos.y;
	    }
	});

	// when mouse button is released (note: window, not canvas here)
	$(window).on('mouseup', function(e){

	    if (isDown === true) {

	        var pos = getMousePos(canvas, e);
	        x2 = pos.x;
	        y2 = pos.y;

	        isDown = false;

	        $.ajax({
	  		  type: "GET",
	  		  //we got message_id from the previous refresh call
	  		  url: 'http://80.87.202.12:8080/epam-app/login',
	  		  data:{
	  		  	x:Math.floor(x1/50),
	  		  	y:Math.floor(y1/50),
	  		  	color:color
	  		  },
			  success: function( data ) {
				  if(data!="OK")
				  	alert(data);  
			  }
	        });
	    }
	});

	// get mouse pos relative to canvas (yours is fine, this is just different)
	function getMousePos(canvas, evt) {
	    var rect = canvas.getBoundingClientRect();
	    return {
	        x: evt.clientX - rect.left,
	        y: evt.clientY - rect.top
	    };
	}
	
	var timer = setInterval(function() {
		$.ajax({
	  		type: "GET",
	  		//we got message_id from the previous refresh call
	  		url: 'http://80.87.202.12:8080/epam-app/world',
			  success: function( data ) {
				  var d = data.split(" ");
				  var it = 0;
				  for(var i = 0; i < 1000/50; i++)
					  for(var j = 0; j < 600/50; j++){
						  var color = d[it];
						  it++;
						  ctx.beginPath();
						  ctx.rect(i*50, j*50, i*50+50, j*50+50);
						  ctx.fillStyle = color;
						  ctx.fill();
					  }
						  
			  }
	  		
	        });
	}, 500);
});



</script>
<button id='red'>red</button>
<button id='green'>green</button>
<button id='blue'>blue</button>
<button id='black'>black</button>
<button id='white'>white</button>
</body>
</html>