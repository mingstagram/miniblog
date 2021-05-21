<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header -->
<%@ include file="../layout/header.jsp"%>
<!-- /header -->

<div class="container">
	<form>
		<input type="hidden" id="id" value="${principal.user.id}" />
		<div class="form-group">
			<label for="username">Username</label> 
			<input type="text" class="form-control"  value="${principal.user.username}" 
			name="username" id="username" placeholder="username은 변경할 수 없습니다." readonly>
		</div>
		
		<!-- sns로그인 이라면 개인정보를 수정할 수 없다. -->
		<c:choose>
			<c:when test="${empty principal.user.oauth}">
				<div class="form-group">
					<label for="password">Password</label> 
					<input type="password" class="form-control"  placeholder="Enter password"
					 name="password" id="password">
				</div>
				<div class="form-group">
					<label for="email">Email</label> 
					<input value="${principal.user.email}" type="email" class="form-control" 
					placeholder="Enter email"  name="email" id="email">
				</div>
			</c:when>
			<c:otherwise>
				<div class="form-group">
					<label for="password">Password</label> 
					<input type="password" class="form-control" 
					placeholder="sns로그인은 변경할 수 없습니다." id="password" readonly>
				</div>
				<div class="form-group">
					<label for="email">Email</label> <input value="${principal.user.email}" type="email" class="form-control" placeholder="sns로그인은 변경할 수 없습니다." id="email" readonly>
				</div>
			</c:otherwise>
		</c:choose> 

	</form>
	<c:if test="${empty principal.user.oauth}">
		<button id="btn-update" class="btn btn-primary" >회원수정</button>
	</c:if>
</div>

<script src="/js/user.js"></script> 
<!-- footer -->
<%@ include file="../layout/footer.jsp"%>
<!-- /footer -->

