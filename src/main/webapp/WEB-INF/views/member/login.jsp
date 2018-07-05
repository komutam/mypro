<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<c:set var="ctxpath" value="<%=request.getContextPath() %>"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width-device-width, initial-scale=1">
<link rel="stylesheet" href="${ctxpath}/resources/css/bootstrap.css">
<link rel="stylesheet" href="${ctxpath}/resources/css/css.css">
<!-- Bootstrap 3.3.7 -->
<link rel="stylesheet" href="${ctxpath}/resources/bootstrap/css/bootstrap.min.css">

<style>
body{
	background:#d3d7df;
}
*{box-sizing:border-box;}
.loginBox{
	width:400px;
	margin:0 auto;
	background:white;
	padding:10px 25px 25px 25px;
	margin-top:60px;
}
.login-head, .bbb{
	text-align:center;
}

</style>

<title>login page</title>
</head>

<body>
 <div id="login-container">
	<div class="loginBox">
		<div class="login-head">
			<h3>LOGIN</h3>
		</div>
		<form action="<c:url value='/member/loginPost'/>" method="post">
			<div class="form-group">
				<label>ID</label>
				<input type="text" class="form-control" name="userid" id="userid" placeholder="Please enter a user ID">
			</div>
			<div class="form-group">
				<label>PASSWORD</label>
				<input type="password" class="form-control" name="userpwd" id="userpwd" placeholder="Please enter your Password">
			</div>
			<div class="form-group">
				<label>
					<input type="checkbox" name="useCookie">remember me
				</label>
			</div>
			<div class="bbb">
				<input type="submit" value="Login" class="btn btn-primary">
				<input type="button" value="Cancel" class="btn btn-primary" onClick="location.href='/mypro01/'">
			</div>
		</form>
	</div>
 </div>

<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="${ctxpath}/resources/js/bootstrap.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.js"></script>
</body>
</html>