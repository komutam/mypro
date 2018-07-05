<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<style>
	.signupBox{
		width:600px;
		margin:0 auto;
		margin-top:50px;
	}
	.signup-footer{
		margin-top:0;
		padding:0;
		width:600px;
		height:50px;
	}
	.signupBox tbody{
		background:white;
	}
</style>

<script type="text/javascript">
function openDaumPostcode(){
	new daum.Postcode({
		oncomplete:function(data){
			//document.getElementById('post1').value=data.postcode1;
			//document.getElementById('post2').value=data.postcode2;
			document.getElementById('addr1').value=data.postcode1+"-"+data.postcode2;
			document.getElementById('addr2').value=data.address;
 		}
		
	}).open();
}//openDaumPostcode()---
</script>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

<%@include file="../include/header.jsp"%>


<div class="signup-container">

	<div class="signupBox" class="table-responsive">
		<form action="signup" method="post" onsubmit="return checkMember()" name="memberForm">
			<table class="table table-bordered table-hover" style="text-align:center; border:1px solid #dddddd;">
				<thead>
					<tr class="signupTitle">
						<th colspan="3" style="text-align:center; background:#2289f2; color:white;">
							<h4>회원가입</h4>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td width="110px"><h5>아이디</h5></td>
						<td>
							<input type="text" name="userid" id="userid" class="form-control" maxlength="20" placeholder="아이디를 입력해주세요">
						</td>
						<td width="110px">
							<button class="btn btn-primary" type="button" onClick="insertFunction();">
								중복확인							
							</button>
						</td>
					</tr>
					<tr>
						<td width="110px"><h5>비밀번호</h5></td>
						<td colspan="2">
							<input type="password" class="form-control" name="userpwd" id="userpwd" placeholder="비밀번호를 입력해주세요" onkeyup="passwordCheck();">
						</td>
					</tr>
					<tr>
						<td width="110px"><h5>비밀번호 확인</h5></td>
						<td colspan="2">
							<input type="password" class="form-control" name="userpwd2" id="userpwd2" placeholder="비밀번호를 한번 더 입력해주세요" onkeyup="passwordCheck();">
							<h5 style="color:red;" id="passwordCheckMessage" class="chekmss"></h5>
						</td>
					</tr>
					<tr>
						<td width="110px"><h5>이 름</h5></td>
						<td colspan="2">
							<input type="text" class="form-control" name="username" id="username" maxlength="20" placeholder="본인의 이름을 정확히 입력">
						</td>
					</tr>
					<tr>
						<td width="110px" rowspan="3"><h5>주소</h5></td>			
						<td>
							<input type="text" class="form-control" name="addr1" id="addr1" placeholder="우편번호" readonly="readonly">
						</td>
						<td>
							<input type="button" value="주소찾기" onclick="openDaumPostcode();" class="btn btn-primary">
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="text" class="form-control" name="addr2" id="addr2" placeholder="주소" readonly="readonly">
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="text" class="form-control" name="addr3" id="addr3" placeholder="상세주소" >
						</td>
					</tr>
                    <tr>
						<td width="110px"><h5>이메일</h5></td>
						<td colspan="2">
							<input type="email" class="form-control" name="email" id="email">
						</td>
					</tr>
				</tbody>
			</table>
			<div class="signup-footer">
			  	<input type="button" class="btn btn-primary pull-left" value="취소" onClick="location.href='/mypro01'">
			  	<input type="submit" class="btn btn-primary pull-right" value="회원가입">
			</div>
		</form>
	</div>

</div>


<%@include file="../include/footer.jsp"%>

<script type="text/javascript">
function insertFunction(){
	if($('#userid').val() == ''){
		alert("ID를 입력해주세요");
		$('#userid').focus();
	}else{
		$.ajax({
			type : 'POST',
			url : '/mypro01/member/checkId',
			data : "userid="+$('#userid').val(),
			dataType : 'JSON',
			success:function(data){
				if(data.check > 0){
					alert("사용중인 ID");
					$("#userid").css("background-color", "#FFCECE");
					$('#userid').val("").focus();
				}else{
					alert("사용가능한 ID");
					$('#userid').css("background-color", "#B0F6AC");
					$('#userpwd').focus();
				}
			}
		});
	}//else end
}

function passwordCheck(){
	var userpwd = $('#userpwd').val();
	var userpwd2 = $('#userpwd2').val();
	
	if(userpwd != userpwd2){
		$('#passwordCheckMessage').html("비밀번호가 일치하지 않습니다");
	}else{
		$('#passwordCheckMessage').html("");
	}
}
</script>

<script type="text/javascript">
	function checkMember(){
		if(document.memberForm.userid.value == ""){
			alert("아이디를 입력해주세요");
			document.memberForm.userid.focus();
			return false;
		}
		
		for(i=0; i<document.memberForm.userid.value.length; i++){
			ch=document.memberForm.userid.value.charAt(i);
			if(!(ch>="0" && ch<="9") && !(ch>="a" && ch<="z") && !(ch>="A" && ch<="Z")){
				alert("아이디는 대소문자와 숫자만 입력가능합니다");
				document.memberForm.userid.focus();
				return false;
			}
		}
		if(document.memberForm.userid.value.length<4 || document.memberForm.userid.value.length>12){
			alert("아이디를 4~12까지 입력해주세요");
			document.memberForm.userid.focus();
			return false;
		}
		
		if(document.memberForm.userpwd.value == ""){
			alert("비밀번호를 입력해주세요");
			document.memberForm.userpwd.focus();
			return false;
		}
		if(document.memberForm.userpwd.value.length < 6){
			alert("비밀번호는 6자 이상 입력해야 합니다.");
			document.memberForm.userpwd.focus();
			return false;
		}
		if(document.memberForm.userpwd.value == document.memberForm.userid.value){
			alert("아이디와 비밀번호가 같습니다.");
			document.memberForm.userpwd.focus();
			return false;
		}
		if(document.memberForm.userpwd2.value == ""){
			alert("비밀번호를 한번더 입력해주세요");
			document.memberForm.userpwd2.focus();
			return false;
		}
		if(document.memberForm.userpwd.value != document.memberForm.userpwd2.value){
			alert("비밀번호가 일치하지 않습니다");
			document.memberForm.userpwd2.focus();
			return false;
		}
		if(document.memberForm.username.value == ""){
			alert("이름을 입력해주세요");
			document.memberForm.username.focus();
			return false;
		}
		if(document.memberForm.username.value.length < 2){
			alert("이름을 2자 이상 입력해주세요");
			document.memberForm.username.focus();
			return false;
		}
/* 		if(document.memberForm.phone.value == ""){
			alert("전화번호를 입력해주세요");
			document.memberForm.phone.focus();
			return false;
		}
		for(i=0; i<document.memberForm.phone.value.length; i++){
			ch=document.memberForm.phone.value.charAt(i);
			if(!(ch>="0" && ch<="9")){
				alert("숫자만 입력할 수 있습니다");
				document.memberForm.phone.focus();
				return false;
			}
		} */
		if(document.memberForm.addr1.value == ""){
			alert("우편번호를 입력해주세요");
			document.memberForm.zipcode.focus();
			return false;
		}
		if(document.memberForm.addr2.value == ""){
			alert("주소를 입력해주세요");
			document.memberForm.addr.focus();
			return false;
		}
/* 		if(document.memberForm.birth.value == ""){
			alert("생년월일을 입력해주세요");
			document.memberForm.birth.focus();
			return false;
		}
		for(i=0; i<document.memberForm.birth.value.length; i++){
			ch=document.memberForm.birth.value.charAt(i);
			if(!(ch>="0" && ch<="9")){
				alert("생년월일은 숫자만 입력해주세요");
				document.memberForm.birth.focus();
				return false;
			}
		} */
		if(document.memberForm.email.value == ""){
			alert("이메일을 입력해주세요");
			document.memberForm.email.focus();
			return false;
		}
		return true;
	}//check end
</script>