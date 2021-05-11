<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header -->
<%@ include file="../layout/header.jsp"%>
<!-- /header -->

<div class="container">
	<form action="/auth/loginProc" method="POST">
		<div class="form-group">
			<label for="username">Username</label> <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
		</div>

		<div class="form-group">
			<label for="password">Password</label> <input type="password" name="password" class="form-control" placeholder="Enter password" id="password" onKeyDown="if(event.keyCode==13)">
		</div>

		<div class="form-group form-check">
			<label class="form-check-label"> 
			<input class="form-check-input" id="save-id" name="save-id" type="checkbox"> Remember me
			</label>
		</div>
		<div>
			<button id="btn-login" class="btn btn-secondary"  onClick="loginProcess()">Login</button>
			<a class="btn btn-warning" href="https://kauth.kakao.com/oauth/authorize?client_id=38a832f0d3de7faf7f5ea88ec14da079&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code">Kakao</a>
			<a class="btn btn-primary" href="https://accounts.google.com/o/oauth2/v2/auth?client_id=753006082056-vrrgdf43s1ve6mmmmc9i6hphfd06itl6.apps.googleusercontent.com&redirect_uri=http://localhost:8000/auth/google/callback&response_type=code&access_type=offline&scope=https://www.googleapis.com/auth/userinfo.email&approval_prompt=force" >Google+</a>
			<a class="btn btn-info" href="https://www.facebook.com/v10.0/dialog/oauth?client_id=284427873395875&redirect_uri=http://localhost:8000/auth/facebook/callback&auth_type=rerequest&scope=email" >Facebook</a>
			<a class="btn btn-success" href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=nmliM_FmEvrfsR5ZPvjC&redirect_uri=http://localhost:8000/auth/naver/callback" >Naver</a>
			<a class="btn btn-danger" style="float:right;" href="/auth/findId">아이디/비밀번호 찾기</a>
		</div>
	</form>

</div>

<script src="/js/user.js"></script>
<script src="/js/loginCheck.js"></script>
<!-- footer -->
<%@ include file="../layout/footer.jsp"%>
<!-- /footer -->

