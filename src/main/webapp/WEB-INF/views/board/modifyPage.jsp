<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<style>
	#modify-container{
		width:600px;
		margin:0 auto;
		margin-top:50px;
		margin-bottom:70px;
	}
</style>

<%@include file="../include/header.jsp"%>

<div id="modify-container" class="table-responsive">
 <form action="modifyPage" method="POST" onsubmit="return checkBoard()" name="modifyForm">
	<input type="hidden" name='page' value='${cri.page }'>
	<input type="hidden" name='perPageNum' value='${cri.perPageNum }'>
	<div class="boxBody-modify">
		<div class="form-group">
			<input type="text" class="form-control" name="bno" value="${boardVO.bno }" readonly="readonly">
		</div>
		<div class="form-group">
			<label for="userName">User</label>
			<input type="text" class="form-control" name="writer" value="${boardVO.writer }" readonly="readonly">
		</div>
		<div class="form-group">
			<label for="title">Title</label>
			<input type="text" class="form-control" name="title" value="${boardVO.title }">
		</div>
		<div class="form-group">
			<label for="title">Content</label>
			<textarea class="form-control" name="content"  rows="10" style="resize:none;">${boardVO.content }</textarea>
		</div>
	</div>
	<div class="boxFooter">
		<input type="button" class="btn btn-danger" value="Cancel" onClick="location.href='/mypro01/board/readPage?page=${cri.page}&perPageNum=${cri.perPageNum}&bno=${boardVO.bno}'">
		<input type="submit" class="btn btn-primary pull-right" value="Save">
	</div>
 </form>
	
</div>

<%@include file="../include/footer.jsp"%>
<script type="text/javascript">
	function checkBoard(){
		if(document.modifyForm.title.value == ""){
			alert("제목을 입력해주세요");
			document.writeForm.title.focus();
			return false;
		}
		if(document.modifyForm.content.value == ""){
			alert("내용을 입력해주세요");
			document.writeForm.content.focus();
			return false;
		}
	}
</script>