
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>MyOffers</title>
<style>
@import url(http://fonts.googleapis.com/css?family=Exo:100,200,400);

@import
	url(http://fonts.googleapis.com/css?family=Source+Sans+Pro:700,400,300);

body {
    background: url('https://lh6.googleusercontent.com/-hRSnnXWUYbA/VsiqWHobC_I/AAAAAAAAAWo/q2AlEfE85-A/w1208-h805-no/web%2B1.jpg') no-repeat fixed center center;
    background-size: cover;
}

.entryPoint {
    width: 320px;
    padding: 20px;
    background: #fff;
    border-radius: 5px;
    border-top: 5px solid #5252FA;
    margin: 0 auto;
}

.entryPoint h1 {
    text-align: center;
    color: #000;
    text-transform: uppercase;
    margin-top: 0;
    margin-bottom: 20px;
    font-family: Monotype Corsiva;
}

.entryPoint input:hover {
    background: #7A7AF0;
}

.entryPoint input{
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
    font-family: Monotype Corsiva;
    outline: none;
    cursor: pointer;
}

</style>
<script src="jquery-1.11.3.min.js"></script>
</head>
<body>
	<br><br>
	<br><br>
	<div class="logo" align="center">
		<img src="https://lh3.googleusercontent.com/-UaHSTcl8bKE/VsYGc19Ia8I/AAAAAAAAARE/eGR63ESx-zk/w484-h485-no/Logo1.png" alt="My Offers logo" height="220" width="220">
	</div>
	<br><br>
	<div class="entryPoint">
		<h1>My Offers</h1>
		<input type="button" value="Sign up" onclick="SignupButton()"><br>
		<input type="button" value="Login" onclick="LoginButton()" style="margin-top: 10px;"><br>
		<script type="text/javascript">
			function SignupButton() {
				location.href = "/itdl/signupview/";
			}
			function LoginButton() {
				location.href = "/itdl/loginview/";
			}
		</script>
	</div>
</body>
</html>