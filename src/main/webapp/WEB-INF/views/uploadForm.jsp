<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style>
iframe {
	width: 0px;
	height: 0px;
	border: 0px
}
</style>

<title>upload Form</title>
</head>
<body>
	<form id="form1" action="uploadForm" method="post" enctype="multipart/form-data" target="zeroFrame">
	  <input name="file" type="file"><input type="submit">
	</form>
	
	<iframe name="zeroFrame"></iframe>
	
	<script type="text/javascript">
		function addFilePath(msg){
			alert(msg);
			document.getElementById("form1").reset();
		}
	</script>
</body>
</html>