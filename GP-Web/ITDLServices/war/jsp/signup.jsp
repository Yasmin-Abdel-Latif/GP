<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Sign Up</title>
<style>
@import url(http://fonts.googleapis.com/css?family=Exo:100,200,400);
@import url(http://fonts.googleapis.com/css?family=Source+Sans+Pro:700,400,300);

body {
    background: url('https://lh6.googleusercontent.com/-hRSnnXWUYbA/VsiqWHobC_I/AAAAAAAAAWo/q2AlEfE85-A/w1208-h805-no/web%2B1.jpg') no-repeat fixed center center;
    background-size: cover;
}

.signup {
    width: 400px;
    padding: 20px;
    background: #fff;
    border-radius: 5px;
    border-top: 5px solid #5252FA;
    margin: 0 auto;
}

.signup h1 {
    text-align: center;
    color: #000;
    text-transform: uppercase;
    margin-top: 0;
    margin-bottom: 20px;
    font-family: Monotype Corsiva;
}

.signup input#email {
    background: #fff url('https://lh3.googleusercontent.com/-iGPqITPftTo/Vsc7qaGhnII/AAAAAAAAARo/cfx6YRKP4dY/w41-h161-no/u0XmBmv12.png') 20px top no-repeat;
    background-size: 16px 80px;
}

.signup input#email:focus {
    background: #fff url('https://lh3.googleusercontent.com/-iGPqITPftTo/Vsc7qaGhnII/AAAAAAAAARo/cfx6YRKP4dY/w41-h161-no/u0XmBmv12.png') 20px bottom no-repeat;
    background-size: 16px 80px;
}

.signup input#name {
    background: #fff url('http://i.imgur.com/u0XmBmv.png') 20px top no-repeat;
    background-size: 16px 80px;
}

.signup input#name:focus {
    background: #fff url('http://i.imgur.com/u0XmBmv.png') 20px bottom no-repeat;
    background-size: 16px 80px;
}

.signup input#password {
    background: #fff url('http://i.imgur.com/Qf83FTt.png') 20px top no-repeat;
    background-size: 16px 80px;
}

.signup input#password:focus {
    background: #fff url('http://i.imgur.com/Qf83FTt.png') 20px bottom no-repeat;
    background-size: 16px 80px;
}

.signup input:active, .login-block input:focus {
    border: 1px solid #ff656c;
}


.signup input[type=submit]:hover {
    background: #7A7AF0;
}

.signup input:not([type=submit]){
	width: 100%;
    height: 42px;
    box-sizing: border-box;
    border-radius: 5px;
    border: 1px solid #ccc;
    margin-bottom: 20px;
    font-size: 14px;
    font-family: Montserrat;
    padding: 0 20px 0 50px;
    outline: none;
}

.signup input[type=submit]{
	width: 100%;
    height: 40px;
    background: #5252FA;
    box-sizing: border-box;
    border-radius: 5px;
    border: 1px solid #5252FA;
    color: #fff;
    font-weight: bold;
    text-transform: uppercase;
    font-size: 14px;
    font-family: Montserrat;
    outline: none;
    cursor: pointer;
}

.mapholder {
    margin: 0 auto;
}
</style>
<script src="https://code.jquery.com/jquery-1.11.3.js"></script>
<script src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript">
$(document).ready(function(){
	var x = document.getElementById("demo");
	var SignedUP = false;
	var latitudeCurrent = null;
	var longitudeCurrent = null;
	var geocoder = new google.maps.Geocoder();
	function geocodePosition(pos) {
	    geocoder.geocode({
	        latLng: pos
	    }, function(responses) {
	        if (responses && responses.length > 0) {
	            updateMarkerAddress(responses[0].formatted_address);
	        } else {
	            updateMarkerAddress('Cannot determine address at this location.');
	        }
	    });
	}
	
	function updateMarkerStatus(str) {
	    document.getElementById('markerStatus').innerHTML = str;
	}
	function updateMarkerPosition(latLng) {
	    document.getElementById('info').innerHTML = [latLng.lat(), latLng.lng()].join(', ');
	    latitudeCurrent = latLng.lat();
		longitudeCurrent = latLng.lng();
	}
	function updateMarkerAddress(str) {
	    document.getElementById('address').innerHTML = str;
	}
	
	function getLocation() {
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(showPosition,
					showError);
		} else {
			x.innerHTML = "Geolocation is not supported by this browser.";
		}
	}

	function showPosition(position) {
		lat = position.coords.latitude;
		latitudeCurrent = lat;
		lon = position.coords.longitude;
		longitudeCurrent = lon;
		latlon = new google.maps.LatLng(lat, lon)
		mapholder = document.getElementById('mapholder')
		mapholder.style.height = '200px';
		mapholder.style.width = '400px';

		var myOptions = {
			center : latlon,
			zoom : 14,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		}

		var map = new google.maps.Map(document
				.getElementById("mapholder"), myOptions);
		
		var infowindow = new google.maps.InfoWindow({
		    content: '<b>Your Current Location</b>'
		});
		
		var marker = new google.maps.Marker({
			position : latlon,
			map : map,
			draggable: true,
			title : "Store Location"
		});
		
		updateMarkerPosition(latlon);
	    geocodePosition(latlon);
	    google.maps.event.addListener(marker, 'dragstart', function() {
	        updateMarkerAddress('Dragging...');
	    });
	    google.maps.event.addListener(marker, 'drag', function() {
	        updateMarkerStatus('Dragging...');
	        updateMarkerPosition(marker.getPosition());
	    });
	    google.maps.event.addListener(marker, 'dragend', function() {
	        updateMarkerStatus('Drag ended');
	        geocodePosition(marker.getPosition());
	    });
	    
		infowindow.open(map, marker);
	}
	
	function showError(error) {
		switch (error.code) {
		case error.PERMISSION_DENIED:
			x.innerHTML = "User denied the request for Geolocation."
			break;
		case error.POSITION_UNAVAILABLE:
			x.innerHTML = "Location information is unavailable."
			break;
		case error.TIMEOUT:
			x.innerHTML = "The request to get user location timed out."
			break;
		case error.UNKNOWN_ERROR:
			x.innerHTML = "An unknown error occurred."
			break;
		}
	}
	
	
	
	$(".signupbtn").click(function(){
		if(SignedUP === false)
		{
			var textEmail = document.getElementById("email").value;
		    var xmlhttp = new XMLHttpRequest();
	        xmlhttp.open("POST","${pageContext.request.contextPath}/ITDLServletSignup?email=" + textEmail,true);
	        xmlhttp.send();
	        xmlhttp.onreadystatechange=function(){
	            if(xmlhttp.readyState === 4 && xmlhttp.status === 200){
	            	if(xmlhttp.responseText)
	           		{
	            		SignedUP = true;
	            		document.getElementById("hiddenUpdate").innerHTML = xmlhttp.responseText;
	                	document.getElementById("email").style.display = 'none';
	            	    document.getElementById("password").style.display = 'none';
	            	    document.getElementById("name").style.display = 'none';
	            		getLocation();
	            		document.getElementById("registerbtn").value = "Complete Regiteration";
	           		}
	            	else
	           		{
	            		document.getElementById("hiddenUpdate").innerHTML = "<font size=\"4\" color=\"#ff3333\" face=\"Times New Roman\"><b>This Email Address is Already Registered</b></font><br>";
	           		}
	            }
	        };
		}
		else
		{
			var textEmail = document.getElementById("email").value;
			var textPassword = document.getElementById("password").value;
			var textName = document.getElementById("name").value;
		    var textAddress = document.getElementById("address").innerHTML;
		    alert("Registration Completed");
		    document.getElementById("dumyForm").innerHTML = "<form id='formSubmitting' action='/itdl/signup' method='POST'><input type='hidden' id='latID' name='lat' value='" + latitudeCurrent + "'><input type='hidden' id='lonID' name='lon' value='" + longitudeCurrent + "'><input type='hidden' id='addressID' name='address' value='" + textAddress + "'><input type='hidden' id='passwordID' name='password' value='" + textPassword + "'><input type='hidden' id='nameID' name='name' value='" + textName + "'><input type='hidden' id='emailID' name='email' value='" + textEmail + "'></form>";
		    $("#formSubmitting").submit();
		}
	});
});
	
</script>
</head>
<body>
<br><br>
<br><br>
<div class="logo" align="center">
	<img src="https://lh3.googleusercontent.com/-UaHSTcl8bKE/VsYGc19Ia8I/AAAAAAAAARE/eGR63ESx-zk/w484-h485-no/Logo1.png" alt="My Offers logo" height="220" width="220">
</div>
<br><br>
<div class="signup">
    <h1>Sign Up</h1>
	<input type="text" id="name" name="name" placeholder="Store Name...">
	<input type="email" id="email" name="email" placeholder="Email..." style="margin-top: 10px;">
	<input type="password" id="password" name="password" placeholder="Password..." style="margin-top: 10px;">
	<p id="demo"></p>
	<div id="mapholder" class="mapholder"></div>
	<div id="hiddenUpdate"></div>
	<div id="dumyForm"></div>
	<div id="markerStatus"></div>
	<b>Current position:</b><div id="info"></div>
	<b>Closest matching address:</b><div id="address"></div>
	<input type="submit" class="signupbtn" id="registerbtn" value="Signup">
	
</div>
</body>
</html>