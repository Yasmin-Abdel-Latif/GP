<%@page import="com.fci.itdl.controller.UserController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
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

.roundedtable{
	border-radius: 7px;
}
</style>
<script src="https://code.jquery.com/jquery-1.11.3.js"></script>
<script type="text/javascript" language="javascript">
$(document).ready(function(){
	<%
	String storeEmail = UserController.storeData.getEmail();
	%>
	$(".addoffer").click(function(){
		location.href = "/itdl/addofferview/";
	});
	
	$(".updateaccount").click(function(){
		location.href = "/itdl/updateAccountView/";
	});
	
	$(".deleteoffer").click(function(){
		var $row = $(this).closest("TR");
        var offerDeleteID = $row.find(".updatedelete input[type=hidden]").val();
        document.getElementById("dumyForm").innerHTML = "<form id='formSubmitting' action='/itdl/deleteOffer' method='POST'><input type='hidden' id='OfferID' name='OfferID' value='" + offerDeleteID + "'></form>";
        $("#formSubmitting").submit();
	});
	
	$(".getoffers").click(function(){
		var str = "<%=storeEmail%>";
        document.getElementById("dumyForm").innerHTML = "<form id='formSubmitting' action='/itdl/getOffers' method='POST'><input type='hidden' id='email' name='email' value='" + str + "'></form>";
        $("#formSubmitting").submit();
	});
	
	$(".updateoffer").click(function(){
		var $row = $(this).closest("TR");
        var offerUpdateID = $row.find(".updatedelete input[type=hidden]").val();
        document.getElementById("dumyForm").innerHTML = "<form id='formSubmitting' action='/itdl/updateOfferView/' method='POST'><input type='hidden' id='OfferID' name='OfferID' value='" + offerUpdateID + "'></form>";
        $("#formSubmitting").submit();
	});
});
</script>
</head>
<body>
<br><br>
<br><br>
<div class="logo" align="center">
	<img src="https://lh3.googleusercontent.com/-UaHSTcl8bKE/VsYGc19Ia8I/AAAAAAAAARE/eGR63ESx-zk/w484-h485-no/Logo1.png" alt="My Offers logo" height="110" width="110">
</div>
<br><br>
<div class="home" style="width: 100%; overflow: auto;">
	<h1>My Offers</h1><br>
	<h1>Home Page</h1>
	<br>
	<div style="position: absolute; left: 10%; width: 20%;">
		<div ALIGN="center">
			<input type="submit" class="addoffer" value="Add Offer">
			<input type="submit" class="updateaccount" value="Update Account">
			<input type="submit" class="getoffers" value="Get">
		</div>
	</div>
	<div style="position: absolute; left: 30%; right: 10%;">
		<div ALIGN="center">
			<TABLE class="roundedtable" BORDER="3" BORDERCOLOR="#5252FA" BGCOLOR="#5252FA" style="WIDTH:100%;" CELLSPACING="1" CELLPADDING="3">
				<TR>
					<TH><FONT COLOR="#ffffff" SIZE="4"><B>Offer Category</B></FONT>
					<TH><FONT COLOR="#ffffff" SIZE="4"><B>Offer Content</B></FONT>
					<TH><FONT COLOR="#ffffff" SIZE="4"><B>Start Date</B></FONT>
					<TH><FONT COLOR="#ffffff" SIZE="4"><B>End Date</B></FONT>
					<TH><FONT COLOR="#ffffff" SIZE="4"><B></B></FONT>
				</TR>
				<%
					for(int i = 0 ; i < UserController.offers.size() ; i++)
					{
				%>
				<TR ALIGN="CENTER" BGCOLOR="#ffffff">
					<TD><FONT COLOR="#000000" SIZE="3"><B>
					<%=UserController.offers.get(i).getCategoryID()%>
					</B></FONT></TD>
					<TD><FONT COLOR="#000000" SIZE="3"><B>
					<%=UserController.offers.get(i).getContent()%>
					</B></FONT></TD>
					<TD><FONT COLOR="#000000" SIZE="3"><B>
					<%=UserController.offers.get(i).getStartDate()%>
					</B></FONT></TD>
					<TD><FONT COLOR="#000000" SIZE="3"><B>
					<%=UserController.offers.get(i).getEndDate()%>
					</B></FONT></TD>
					<TD class="updatedelete">
						<input type="image" class="updateoffer" src="https://lh4.googleusercontent.com/-W_7mA1Q06IQ/VtWAlwS7F0I/AAAAAAAAAXc/ybsZlHry1Cw/s256-no/edit.png" id="updateoffer<%=i%>" width="20" height="20">
						<input type="image" class="deleteoffer" src="https://lh4.googleusercontent.com/-fEblv-r9YWI/VtWAmKt3YeI/AAAAAAAAAXc/nwUFuhG3M4Y/s256-no/Delete-icon.png" id="deleteoffer<%=i%>" width="20" height="20">
						<input type="hidden" name="<%=i%>" value="<%=UserController.offers.get(i).getOfferID()%>">
					</TD>
				</TR>
				<%
					}
				%>
			</TABLE>
			<div id="dumyForm"></div>
		</div>
	</div>
</div>
</body>
</html>