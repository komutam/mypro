<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<style>
	#read-container{
		width:600px;
		margin:0 auto;
		margin-top:50px;
		margin-bottom:70px;
	}
</style>

<style type="text/css">
.popup {
	position: absolute;
}

.back {
	background-color: gray;
	opacity: 0.5;
	width: 100%;
	height: 300%;
	overflow: hidden;
	z-index: 1101;
}

.front {
	z-index: 1110;
	opacity: 1;
	boarder: 1px;
	margin: auto;
}

.show {
	position: relative;
	max-width: 1200px;
	max-height: 800px;
	overflow: auto;
}
</style>
<div class='popup back' style="display: none;"></div>
<div id="popup_front" class='popup front' style="display: none;">
	<img id="popup_img">
</div>

<%@include file="../include/header.jsp"%>

<div id="read-container">
	<form role="form" method="post" action="modify">
		<input type="hidden" name="bno" value="${dto.bno}">
		<input type="hidden" name="page" value="${cri.page}">
		<input type="hidden" name="perPageNum" value="${cri.perPageNum}">
	</form>
	<div class="readBox">
		<div class="readBox-body">
			<div class="form-group">
				<label for="userName">User</label>
				<input type="text" class="form-control" name="writer" value="${dto.writer}" readonly="readonly">
			</div>
			<div class="form-group">
				<label for="title">Title</label>
				<input type="text" class="form-control" name="title" value="${dto.title}" readonly="readonly">
			</div>
			<div class="form-group">
				<label for="content">Content</label>
				<textarea rows="10" style="resize:none;" class="form-control" readonly="readonly" name="content" id="content">${dto.content}</textarea>
			</div>
		</div>
		<div class=boxFooter>
			<c:if test="${login != null}">
				<input type="button" class="btn btn-warning" value="Modify" id="modifyBtn">
				<input type="button" class="btn btn-danger" value="Delete" id="deleteBtn">
			</c:if>		
			<input type="submit" class="btn btn-primary" value="List" id="listBtn">
		</div>
	</div>
</div>

<%@include file="../include/footer.jsp"%>
<!-- 읽기 페이지 파일첨부 handlebars의 템플릿 -->
<script id="templateAttach" type="text/x-handlebars-template">
<li data-src='{{fullName}}'>
  <span class="mailbox-attachment-icon has-img"><img src="{{imgsrc}}" alt="Attachment"></span>
  <div class="mailbox-attachment-info">
	<a href="{{getLink}}" class="mailbox-attachment-name">{{fileName}}</a>
	</span>
  </div>
</li>                
</script>

<script> //조회화면 파일첨부 처리
var bNo = ${boardVO.bNo};
var template = Handlebars.compile($("#templateAttach").html());

	$.getJSON("/mypro01/board/getAttach/" + bNo, function(list) {
		$(list).each(function() {

			var fileInfo = getFileInfo(this);
	
			var html = template(fileInfo);
	
			$(".uploadedList").append(html);
	
		});
	});
	$(".uploadedList").on("click", ".mailbox-attachment-info a",
		function(event) {

			var fileLink = $(this).attr("href");

			if (checkImageType(fileLink)) {

				event.preventDefault();

				var imgTag = $("#popup_img");
				imgTag.attr("src", fileLink);

				console.log(imgTag.attr("src"));

				$(".popup").show('slow');
				imgTag.addClass("show");
			}
		});

	$("#popup_img").on("click", function() {
		$(".popup").hide('slow');
	
	});
</script>

<script>
	$(function(){
		var formObj = $("form[role='form']");
		console.log(formObj);
		
		$("#modifyBtn").click(function(){
			formObj.attr("action", "/mypro01/board/modifyPage");
			formObj.attr("method", "GET");
			formObj.submit();
		});
		$("#deleteBtn").click(function(){
			formObj.attr("action", "/mypro01/board/deletePage");
			formObj.attr("method", "POST");
			formObj.submit();
		});
		$("#listBtn").click(function(){
			self.location="/mypro01/board/listPage?page=${cri.page}&perPageNum=${cri.perPageNum}";
		});
	});
</script>
