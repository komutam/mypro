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

<style type="text/css">
li{
	list-style:none;
}

	#main-header{
		width:100%;
		height:50px;
		background:#333;
		margin:0 auto;
		padding:0;
	}
	.main-container{
		width:1000px;
		height:100%;
		background:yellow;
		margin:0 auto;
		text-align:center;
	}
	
	body{
		box-sizing:border-box;
		background:#f2f2f2;
	}
	#main-footer{
		position:relative;
		background:green;
		width:100%;
		line-height:50px;
		text-align:center;
		height:50px;
		margin:0;
		padding:0;
		margin-top:50px;
	}
	
	.headerBar{
		margin-right:30px;
		padding-top:7px;
	}
</style>

<title>mypro01</title>
</head>
<body>
 <div id="wrap">

	<header id="main-header">
		<c:if test="${login != null}">
			<div class="headerBar">
				<input type="button" class="btn btn-primary pull-right" value="로그아웃" style="margin-left:5px;" onClick="location.href='/mypro01/member/logout'">
				<input type="button" class="btn btn-primary pull-right" value="MyPage">
			</div>
		</c:if>
		<c:if test="${empty login}">
			<div class="headerBar">
				<input type="button" value="회원가입" class="btn btn-default pull-right" style="margin-left:5px;" onClick="location.href='/mypro01/member/signup'">
				<input type="button" value="로그인" class="btn btn-primary pull-right" onClick="location.href='/mypro01/member/login'">
			</div>
		</c:if>
	</header>
