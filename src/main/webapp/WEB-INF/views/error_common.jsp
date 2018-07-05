<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- Main content -->
	<section class="content">
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<h2 class="headline text-red">
					<i class="fa fa-warning text-red"></i> ${exception.getMessage() }
				</h2>
				<p>
					<c:forEach items="${exception.getStackTrace() }" var="stack">
						<li>${stack.toString() }</li>
					</c:forEach>
				</p>

			</div>
		</div>
		<!-- /.error-page -->
	</section>
<!-- /.content -->