<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="include/header.jsp"%>

<script>
	var result = '${msg}';
	if (result == "success")
		alert('가입이 완료되었습니다.');
</script>

<div class="main-container">

	<h1>
		Main Page!
	</h1>
	<div class="main-img"><img src="${ctxpath}/resources/img/20170526012450a0b.jpg"></div>
	<P>  The time on the server is ${serverTime}. </P>
	
	<input type="button" class="btn btn-primary" value="글목록" onClick="location.href='/mypro01/board/listPage'">

	

</div>



<%@include file="include/footer.jsp"%>