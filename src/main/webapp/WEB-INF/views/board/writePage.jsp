<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.js"></script>

<style>
	#write-container{
		width:600px;
		margin:0 auto;
		margin-top:50px;
		margin-bottom:70px;
	}
	.fileDrop {
		height: 10%;
		border: 1px dotted gray;
		margin: auto;
	}
</style>

<%@include file="../include/header.jsp"%>

<div id="write-container">
	<form action="writePage" method="post" onSubmit="return checkWrite()" name="writeForm" id="writeForm">
		<div class="writeBox">
			<div class="form-group">
				<label for="userName">User</label>
				<input type="text" class="form-control" name="writer" value="${login.userid}" readonly="readonly">
			</div>
			<div class="form-group">
				<label for="title">Title</label>
				<input type="text" class="form-control" name="title" placeholder="Title Enter.............">
			</div>
			<div class="form-group">
				<label for="content">Content</label>
				<textarea rows="10" style="resize:none;" class="form-control" placeholder="Content Enter............" name="content" id="content"></textarea>
			</div>
			<div class="form-group">
				<label for="inputFile">File Drop</label>
				<div class="col-sm-10">
					<div class="fileDrop"></div>
				</div>
			</div>
		</div>
		<div class=boxFooter>
			<div>
				<hr>
			</div>
			
			<ul class="mailbox-attachments clearfix uploadedList">
			</ul>
			
			<input type="button" class="btn btn-danger" value="Cancel" onClick="location.href='/mypro01/board/listPage'">
			<input type="submit" class="btn btn-primary pull-right" value="Submit">
		</div>
	</form>
</div>

<%@include file="../include/footer.jsp"%>
<script type="text/javascript" src="../resources/js/upload.js"></script>
<script id="template" type="text/x-handlebars-template">
	<li>
		<span class="mailbox-attachment-icon has-img"><img src="{{imgsrc}}" alt="Attachment"></span>
		<div class="mailbox-attachment-info">
			<a href="{{getLink}}" class="mailbox-attachment-name">{{fileName}}</a>
			<a href="{{fullName}}" class="btn btn-default btn-xs pull-right delbtn">
				<i class="glyphicon glyphicon-remove-circle"></i>
			</a>
		</div>
	</li>
</script>

<script>
var template = Handlebars.compile($("#template").html());

$(".fileDrop").on("dragenter dragover", function(event){
	event.preventDefault();
});

$(".fileDrop").on("drop", function(event){
	event.preventDefault();
	var files = event.originalEvent.dataTransfer.files;
	var file = files[0];
	var formData = new FormData();
	
	formData.append("file", file);	
	
	$.ajax({
		  url: '/mypro01/uploadAjax',
		  data: formData,
		  dataType:'text',
		  processData: false,
		  contentType: false,
		  type: 'POST',
		  success: function(data){
			  var fileInfo = getFileInfo(data);
			  var html = template(fileInfo);
			  $(".uploadedList").append(html);
		  }
		});	
});

$("#writeForm").submit(function(event){
	
	event.preventDefault();
	
	var that = $(this);
	
	var str ="";
	$(".uploadedList .delbtn").each(function(index){
		 str += "<input type='hidden' name='files["+index+"]' value='"+$(this).attr("href") +"'> ";
	});
	

		if(document.writeForm.title.value == "" || document.writeForm.content.value == ""){
			return false;
		}
	
	that.append(str);
	that.get(0).submit();
	
});
</script>

<script>
	function checkWrite(){
		if(document.writeForm.title.value == ""){
			alert("제목을 입력해주세요");
			document.writeForm.title.focus();
			return false;
		}
		if(document.writeForm.content.value == ""){
			alert("내용을 입력해주세요");
			document.writeForm.content.focus();
			return false;
		}
	}
</script>
