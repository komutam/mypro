<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style>
	*{box-sizing:border-box;}
	#listPage-container{
		margin:0 auto;
		width:1000px;
	}
	#listPage-container table{
		margin:0;
		padding:0;
		background:white;
	}
	.listNumber, .listWriter, .listDate, .listViewCount{
		text-align:center;
	}
	.listBox-footer{
		width:1000px;
		margin:0 auto;
		text-align:center;
		height:45px;
	}
	.listPaging ul{
		margin:0;
		padding:0;
		margin-top:5px;
	}
</style>

<%@include file="../include/header.jsp"%>

<script>
	var result = '${msg}';
	if (result == "success")
		alert('처리가 완료되었습니다.');
</script>

<div id="listPage-container">
	<div class="list-header with-border">
		<h3 class="list-title">List All Page</h3>
	</div>
	<div class="listBox table-responsive">
		<table class="table table-bordered table-hover">
			<tr>
				<th class="listNumber" width="70px">글번호</th>
				<th class="listTitle" style="text-align:center;" width="630px">글제목</th>
				<th class="listWriter" width="80px">작성자</th>
				<th class="listDate" width="140px">작성일</th>
				<th class="listViewCount" width="70px">조회수</th>
			</tr>
			<c:forEach var="boardVO" items="${list}">
				<tr>
					<td class="listNumber">${boardVO.bno}</td>
					<td class="listTitle"><a href="<c:url value='/board/readPage${pageMaker.makeQuery(pageMaker.cri.page)}&bno=${boardVO.bno}'/>">${boardVO.title}</a></td>
					<td class="listWriter">${boardVO.writer}</td>
					<td class="listDate">
						<fmt:parseDate var="regdate1" value='${boardVO.regdate }' pattern="yyyy-MM-dd HH:mm"/>
						<fmt:formatDate value="${regdate1 }" pattern="yyyy-MM-dd HH:mm"/>
					</td>
					<td class="badges listViewCount"><span class="badge">${boardVO.viewcnt}</span></td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${pageMaker.totalCount == 0}">
			<div style="text-align:center;">
				<span><strong>글을 입력해주세요</strong></span>
			</div>
		</c:if>
	</div>
		
	<div class="listBox-footer">
		<div class="listPaging">
			<ul class="pagination">
				<c:if test="${pageMaker.prev }">
					<li class="page-item"><a href="${pageMaker.startPage -1 }" tabindex="-1">&laquo;</a></li>
				</c:if>
				<c:if test="${not pageMaker.prev }">
				</c:if>
				
				<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
					<li <c:out value="${pageMaker.cri.page == idx? 'class=active page-item':'class=page-item'}"/>>
						<a href="${idx }" class="page-link">${idx }</a>
					</li>		
				</c:forEach>
				
				<c:if test="${pageMaker.next && pageMaker.endPage > 0 }">
					<li class="page-item"><a href="${pageMaker.endPage +1 }" class="page-link">&raquo;</a></li>
				</c:if>
				<c:if test="${not pageMaker.next || pageMaker.endPage <= 0 }">	
				</c:if>
			</ul>
		</div>
	</div>
	<div class="homeBtn">
		<input type="button" class="btn btn-primary pull-left" value="홈으로" onClick="location.href='/mypro01'">
	</div>
	<%-- <c:if test="${login != null}">
		<div class="writeButton">
			<input type="button" class="btn btn-primary pull-right" value="글쓰기" onClick="location.href='/mypro01/board/writePage'">
		</div>
	</c:if> --%>
	<div class="writeButton">
		<input type="button" class="btn btn-primary pull-right" value="글쓰기" onClick="location.href='/mypro01/board/writePage'">
	</div>
</div>

<%@include file="../include/footer.jsp"%>

 <form id="jobForm">
	<input type="hidden" name="page" value="${pageMaker.cri.page}">
	<input type='hidden' name="perPageNum" value="${pageMaker.cri.perPageNum}">
</form>
<script>
	$(".pagination li a").on("click",function(event){
				event.preventDefault();
				var targetPage = $(this).attr("href");
				var jobForm = $("#jobForm");
				jobForm.find("[name='page']").val(targetPage);
				jobForm.attr("action", "<c:url value='/board/listPage'/>").attr("method", "get");
				jobForm.submit();
			});
</script>