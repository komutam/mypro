<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
	.fileDrop {
		width: 100%;
		height: 200px;
		border: 1px dotted blue;
	}
	small {
		margin-left: 3px;
		font-weight: bold;
		color: gray;
	}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>Ajax File upload</h3>
	<div class="fileDrop"></div>
	<div class="uploadedList"></div>
	
	<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
	<script>
		$(".fileDrop").on("dragenter dragover", function(envet){
			event.preventDefault();
		});
		$(".fileDrop").on("drop", function(event){
			event.preventDefault();
			var files = event.originalEvent.dataTransfer.files;
			var file = files[0];
			
			//FormData 객체를 이용하여 <form> 태그로 만든 데이터의 전송방식과 동일하게 파일 데이터를 전송
			var formData = new FormData();
			formData.append("file", file);
			
			$.ajax({
				url: '/mypro01/uploadAjax',
				data: formData,
				dataType: 'text',
				processData: false,
				contentType: false,
				type: 'POST',
				success: function(data){

					var str="";
					
					console.log(data);
					console.log(checkImageType(data));
					
					//문자열이 이미지 파일인지를 확인하는 작업은 checkImageType() 메소드를 이용해 처리
					if(checkImageType(data)){
						  str ="<div><a target='_blank' href='displayFile?fileName="+getImageLink(data)+"'>"
						  +"<img src='displayFile?fileName="+data+"'/>"
						  +"</a><small data-src="+data+">X</small></div>";
					  }else{
						  str = "<div><a href='displayFile?fileName="+data+"'>" 
						  + getOriginalName(data)+"</a>"
						  +"<small data-src="+data+">X</small></div>";
					  }
					$(".uploadedList").append(str);
				}
			});
		});
		
		//썸네일 이미지 클릭 시 원본 이미지 파일 열리게 하는 기능
		function getImageLink(fileName){
			//현재 보여지는 이미지는 썸네일이므로 중간에 's_'경로만 제거하면 원래의 이미지 파일이 된다.
			//따라서 /년/월/일 폴더 뒤에 's_'로 시작되는 부분을 제거하면 원본 파일의 링크가 됨
			if(!checkImageType(fileName)){
				return;
			}
			var front = fileName.substr(0, 12); //fileName.substr(0,12)는 /년/월/일 경로를 추출하는 용도
			var end = fileName.substr(14); //fileName.substr(14)는 파일 이름 앞의 's_'를 제거하는 용도
			
			return front + end;
		}
		
		//일반 파일 긴 이름 줄이는 기능
		function getOriginalName(fileName){
			if(checkImageType(fileName)){
				return;
			}
			var idx=fileName.indexOf("_") +1;
			return fileName.substr(idx);
		}
		function checkImageType(fileName){
			var pattern = /jpg|gif|png|jpeg/i;
			return fileName.match(pattern);
		}
		
		//삭제를 uploadController에 전달하는 Ajax
		//사용자가 <small> 태그로 처리된 x를 클릭하면 data-src 속성 값으로 사용된 파일의 이름을 얻어와서 POST 방식으로 호출하게 된다.
		$(".uploadedList").on("click", "small", function(event){
			var that = $(this);
			
			$.ajax({
				url:"/mypro01/deleteFile",
				type:"post",
				data: {fileName:$(this).attr("data-src")},
				dataType:"text",
				success:function(result){
					if(result == 'deleted'){
						that.parent("div").remove();
					}
				}
			});
		});
	</script>
	<!-- 'dragenter, dragover, drop' 시 기본 동작을 막도록 작성되어 있는데,
		이는 브라우저에서 파일을 끌어다 놓으면 아무런 동작을 하지 않도록 막아주는 역할을 함.
		이벤트에 대한 event.preventDefault() 제거 or 중단 처리를 하지 않으면
		브라우저에서 보여줄 수 있는 파일을 끌어다 놓으면 자동으로 새로운 창이 열리고 파일이 보이게 됨. -->
	<!-- 브라우저가 직접 생성한 이벤트 오브젝트는,
		jQuery 가 알아서 사용하기 편한 method 와 properties 로 감싸버린다.
		jQuery 가 감싸지 않은 기존 이벤트를 접근해야할 필요가 있을텐데
		그때 event.originalEvent 를 사용하면 된다.
		기존의 이벤트 오브젝트에 접근하기 위해선 originalEvent 를 사용 -->
</body>
</html>