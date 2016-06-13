<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Login</title>
<style>
@import url(http://fonts.googleapis.com/css?family=Exo:100,200,400);
@import url(http://fonts.googleapis.com/css?family=Source+Sans+Pro:700,400,300);

body {
    background: url('https://lh6.googleusercontent.com/-hRSnnXWUYbA/VsiqWHobC_I/AAAAAAAAAWo/q2AlEfE85-A/w1208-h805-no/web%2B1.jpg') no-repeat fixed center center;
    background-size: cover;
}

.login-block {
    width: 320px;
    padding: 20px;
    background: #fff;
    border-radius: 5px;
    border-top: 5px solid #5252FA;
    margin: 0 auto;
}

.login-block h1 {
    text-align: center;
    color: #000;
    text-transform: uppercase;
    margin-top: 0;
    margin-bottom: 20px;
    font-family: Monotype Corsiva;
}

.login-block input#email {
    background: #fff url('https://lh3.googleusercontent.com/-iGPqITPftTo/Vsc7qaGhnII/AAAAAAAAARo/cfx6YRKP4dY/w41-h161-no/u0XmBmv12.png') 20px top no-repeat;
    background-size: 16px 80px;
}

.login-block input#email:focus {
    background: #fff url('https://lh3.googleusercontent.com/-iGPqITPftTo/Vsc7qaGhnII/AAAAAAAAARo/cfx6YRKP4dY/w41-h161-no/u0XmBmv12.png') 20px bottom no-repeat;
    background-size: 16px 80px;
}

.login-block input#password {
    background: #fff url('http://i.imgur.com/Qf83FTt.png') 20px top no-repeat;
    background-size: 16px 80px;
}

.login-block input#password:focus {
    background: #fff url('http://i.imgur.com/Qf83FTt.png') 20px bottom no-repeat;
    background-size: 16px 80px;
}

.login-block input:active, .login-block input:focus {
    border: 1px solid #ff656c;
}


.login-block input[type=submit]:hover {
    background: #7A7AF0;
}

.login-block input:not([type=submit]){
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

.login-block input[type=submit]{
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

</style>
</head>
<body>
<br><br>
<br><br>
<div class="logo" align="center">
	<img src="https://lh3.googleusercontent.com/-UaHSTcl8bKE/VsYGc19Ia8I/AAAAAAAAARE/eGR63ESx-zk/w484-h485-no/Logo1.png" alt="My Offers logo" height="220" width="220">
</div>
<br><br>
<div class="login-block">
    <h1>Login</h1>
	<form action="/itdl/home" method="POST">
		<input type="email" id="email" name="email" placeholder="Email..."> <br>
		<input type="password" id="password" name="password" placeholder="Password..." style="margin-top: 10px;"> <br>
		<input type="submit" class="loginbtn" value="Login">
	</form>
</div>
</body>
</html>