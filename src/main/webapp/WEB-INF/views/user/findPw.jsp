<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header -->
<%@ include file="../layout/header.jsp"%>
<!-- /header -->

<div class="container">
	<div class="form-group"><h2><label>비밀번호 찾기</label></h2></div>
	
	<div class="form-group">
		<c:choose>
			<c:when test="${findPw != null}">
				<label>이메일로 임시비밀번호를 발송했습니다.</label>
			</c:when>
		</c:choose>
	</div>
	
	<form action="/auth/findPw" method="POST">
		<div class="form-group">
			<label for="username">Username</label> <input type="text" id="username" name="username" class="form-control" placeholder="Enter username" id="username">
		</div>
		<div class="form-group">
			<label for="email">Email</label> <input type="text" id="email"  name="email" class="form-control" placeholder="Enter email" id="email">
		</div>
		<div>
			<button onclick="alert('이메일로 임시비밀번호를 보내드렸습니다.')" class="btn btn-info">Find PASSWORD</button>
		</div>  
	</form>
	

</div>

<script src="/js/user.js"></script>
<script src="/js/loginCheck.js"></script>
<!-- footer -->
<%@ include file="../layout/footer.jsp"%>
<!-- /footer -->

