<%@page import="com.fci.itdl.controller.UserController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Account</title>
<style>
@import url(http://fonts.googleapis.com/css?family=Exo:100,200,400);
@import url(http://fonts.googleapis.com/css?family=Source+Sans+Pro:700,400,300);

body {
    background: url('https://lh6.googleusercontent.com/-hRSnnXWUYbA/VsiqWHobC_I/AAAAAAAAAWo/q2AlEfE85-A/w1208-h805-no/web%2B1.jpg') no-repeat fixed center center;
    background-size: cover;
}

.home h1 {
    text-align: center;
    color: #000;
    text-transform: uppercase;
    margin-top: 0;
    margin-bottom: 20px;
    font-family: Monotype Corsiva;
}

.home input[type=submit]{
	width: 260px;
	height: 35px;
	background: #fff;
	border: 1px solid #fff;
	cursor: pointer;
	border-radius: 2px;
	color: #a18d6c;
	font-family: 'Exo', sans-serif;
	font-size: 16px;
	font-weight: 400;
	padding: 6px;
	margin-top: 10px;
}

.home input:not([type=submit]){
	width: 250px;
	height: 30px;
	background: white;
	border: 1px solid rgba(255,255,255,0.6);
	border-radius: 2px;
	color: blue;
	font-family: 'Exo', sans-serif;
	font-size: 16px;
	font-weight: 400;
	padding: 4px;
}

.home select{
	width: 300px;
    height: 35px;
    background: #fff;
    border: 1px solid #fff;
    cursor: pointer;
    border-radius: 2px;
    color: #a18d6c;
    font-family: 'Exo', sans-serif;
    font-size: 16px;
    font-weight: 400;
    padding: 6px;
    margin-top: 20px;
}
</style>
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script src="jquery-1.11.3.min.js"></script>
<script src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript">
$(document).ready(function() {
	<%
	session=request.getSession(false);
	String storeEmail=(String)session.getAttribute("storeEmail");
	double lat = UserController.storeData.getLat();
	double lon = UserController.storeData.getLon();
	%>
	$(".gohome").click(function(){
		var str="<%=storeEmail%>";
		location.href = "/jsp/goHome.jsp";
	});
	
	$(".addoffer").click(function(){
		var str="<%=storeEmail%>";
		location.href = "/itdl/addofferview/";
	});
	
	lat = "<%=lat%>";
	lon = "<%=lon%>";
	latlon = new google.maps.LatLng(lat, lon)
	mapholder = document.getElementById('mapholder')
	mapholder.style.height = '350px';
	mapholder.style.width = '700px';

	var myOptions = {
		center : latlon,
		zoom : 14,
		mapTypeId : google.maps.MapTypeId.ROADMAP,
		mapTypeControl : false,
		navigationControlOptions : {
			style : google.maps.NavigationControlStyle.SMALL
		}
	}

	var map = new google.maps.Map(document
			.getElementById("mapholder"), myOptions);
	
	var infowindow = new google.maps.InfoWindow({
	    content: '<b>Store Location</b>'
	});
	
	var marker = new google.maps.Marker({
		position : latlon,
		map : map,
		title : "Store Location"
	});
	
	infowindow.open(map, marker);
});
</script>
</head>
<body>
<br><br>
<div class="logo" align="center">
	<img src="https://lh3.googleusercontent.com/-UaHSTcl8bKE/VsYGc19Ia8I/AAAAAAAAARE/eGR63ESx-zk/w484-h485-no/Logo1.png" alt="My Offers logo" height="220" width="220">
</div>
<br><br>
<div class="home" style="width: 100%; overflow: auto;">
	<h1>My Offers</h1>
	<h1>Update Account</h1>
	<br>
	<div style="position: absolute; left: 10%; width: 20%;">
		<div ALIGN="center">
			<input type="submit" class="addoffer" value="Add Offer">
			<input type="submit" class="gohome" value="Home">
		</div>
	</div>
	<div style="position: absolute; left: 30%; right: 10%;">
		<div ALIGN="center">
			<form action="/itdl/updateAccount" id="storeform" method="post">
				<input type="text" id="name" name="name" value="<%=UserController.storeData.getName()%>"> <br>
				<input type="email" id="email" name="email" value="<%=UserController.storeData.getEmail()%>" style="margin-top: 10px;"> <br>
				<input type="password" id="password" name="password" value="<%=UserController.storeData.getPassword()%>" style="margin-top: 10px;"> <br>
				<p id="demo"></p>
				<div id="mapholder"></div>
				<input type="submit" class="doneEditting" value="Done" style="background: #5252FA;
																	    	  box-sizing: border-box;
																		      border-radius: 5px;
																		      border: 1px solid #5252FA;
																		      color: #fff;">
				<input type="hidden" name="StoreID" value="<%=storeEmail%>">
			</form>
			
		</div>
	</div>
</div>
</body>
</html>