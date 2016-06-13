<%@page import="com.fci.itdl.controller.UserController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Offer</title>
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

.home textarea{
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
<script>
$(document).ready(function() {
	<%
	session=request.getSession(false);
	String storeEmail=(String)session.getAttribute("storeEmail");
	String startDateGiven = (String) UserController.offerChosen.getStartDate();
	%>
	
	$(".addoffer").click(function(){
		location.href = "/itdl/addofferview/";
	});
	
	$(".updateaccount").click(function(){
		location.href = "/itdl/updateAccountView/";
	});
	
	$(".gohome").click(function(){
		location.href = "/jsp/goHome.jsp";
	});
	
	function parseEvaluateDate(str) {
	    var mdy = str.split('/');
	    return new Date(mdy[2], mdy[1]-1, mdy[0]);
	}

	function daydiff(first, second) {
	    return Math.round((second-first)/(1000*60*60*24));
	}
	
	$("#datepickerStart").datepicker({ dateFormat: 'dd/mm/yy' });
	$("#datepickerEnd").datepicker({ dateFormat: 'dd/mm/yy' });
	$("#offerform").submit(function(e){
		var startDate = $("#datepickerStart").val();
		var endDate = $("#datepickerEnd").val();
		var existStartDate ="<%=startDateGiven%>";
		var currentTime = new Date();
	    var month = currentTime.getMonth() + 1;
	    var day = currentTime.getDate();
	    var year = currentTime.getFullYear();
	    var currentDate = day + "/" + month + "/" + year;
	    if((daydiff(parseEvaluateDate(currentDate), parseEvaluateDate(startDate)) < 0) && (daydiff(parseEvaluateDate(existStartDate), parseEvaluateDate(startDate)) !== 0))
		{
			alert("The Offer Start Date MUST Be The Current Date Or Later But Not Earlier!");
			e.preventDefault();
		}
	    else
    	{
	    	if(daydiff(parseEvaluateDate(startDate), parseEvaluateDate(endDate)) < 0)
			{
				alert("The Offer Start Date MUST Be Before The End Date Of The Offer!");
				e.preventDefault();
			}
	    	else
    		{
	    		alert("Your Offer Was Updated");
    		}
    	}
	});
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
	<h1>Update Offer</h1>
	<br>
	<div style="position: absolute; left: 10%; width: 20%;">
		<div ALIGN="center">
			<input type="submit" class="addoffer" value="Add Offer">
			<input type="submit" class="updateaccount" value="Update Account">
			<input type="submit" class="gohome" value="Home">
		</div>
	</div>
	<div style="position: absolute; left: 30%; right: 10%;">
		<div ALIGN="center">
			<form action="/itdl/updateOffer" id="offerform" method="post">
				<font size="4" color="#ffffff" face="Lucida Calligraphy">
					<b>
						Start Date : 
					</b>
				</font>
				<input name="datepickerStart" id="datepickerStart" value="<%=UserController.offerChosen.getStartDate()%>" style="margin-right: 20px;"/>
				<font size="4" color="#ffffff" face="Lucida Calligraphy">
					<b>
						End Date   : 
					</b>
				</font>
				<input name="datepickerEnd" id="datepickerEnd" value="<%=UserController.offerChosen.getEndDate()%>" style="margin-right: 20px;"/>
				<br>
				<select name='category' required>
	                 <option selected="selected" value="<%=UserController.offerChosen.getCategoryID()%>"><%=UserController.offerChosen.getCategoryID()%></option>
	                 <option value="Arts and Entertainments">Arts and Entertainments</option>
	                 <option value="Movies">Movies</option>
	                 <option value="Music">Music</option>
	                 <option value="Food and drinks">Food and drinks</option>
	                 <option value="Technology">Technology</option>
	                 <option value="Sports">Sports</option>
	                 <option value="Health">Health</option>
	                 <option value="Religion">Religion</option>
	                 <option value="Education">Education</option>
	                 <option value="Pets and animals">Pets and animals</option>
	                 <option value="Fashion">Fashion</option>
	                 <option value="Reading">Reading</option>
	             </select>
	             <br>
	             <textarea name="offerContent" rows="7" cols="50" id="offerUpdatedContent" class="offerContent"><%=UserController.offerChosen.getContent()%></textarea>
	             <br>
	             <input type="submit" class="doneAdding" value="Done" style="background: #5252FA;
																		     box-sizing: border-box;
																		     border-radius: 5px;
																		     border: 1px solid #5252FA;
																		     color: #fff;">
	             <input type="hidden" name="StoreID" value="<%=storeEmail%>">
	             <input type="hidden" name="OfferID" value="<%=UserController.offerChosen.getOfferID()%>">
			</form>
			
		</div>
	</div>
</div>
</body>
</html>