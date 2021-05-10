<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header -->
<%@ include file="../layout/header.jsp"%>
<!-- /header -->

<div class="container">
	<div class="form-group"><h2><label>아이디 찾기</label></h2></div>
	<div class="form-group">
		<c:choose>
			<c:when test="${findUsername != null}">
				<label>검색 된 아이디는 <b>${findUsername}</b> 입니다</label>
			</c:when>
		</c:choose>
	</div>
	
	<form action="/auth/findId" method="POST">
	
		<div class="form-group">
			<label for="email">Email</label> <input type="text" name="email" class="form-control" placeholder="Enter Email" id="email">
		</div>
		<div>
			<button class="btn btn-info" >Find ID</button>
			<a class="btn btn-warning"  href="/auth/findPw">Find PASSWORD</a>  
		</div>
	</form>
	

</div>

<script src="/js/user.js"></script>
<script src="/js/loginCheck.js"></script>
<!-- footer -->
<%@ include file="../layout/footer.jsp"%>
<!-- /footer -->

