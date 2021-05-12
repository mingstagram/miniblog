<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- header -->
<%@ include file="../layout/header.jsp"%>
<!-- /header -->
<div class="container card">
	<br />
	<div class="card" style="margin-bottom: 20px;">
		<div class="card">
			<form action="/auth/guestBook" method="POST">
				<div class="card-body">
					<div class="form-row" style="margin-bottom:10px;">
						<div class="col">
							<input type="text" class="form-control" id="writer"
								placeholder="이름" name="writer">
						</div>
						<div class="col">
							<input type="password" class="form-control"
								placeholder="비밀번호" id="password" name="password">
						</div>
					</div>

					<div>
						<textarea id="content" name="content" class="form-control" rows="3"
							placeholder="방명록을 입력해주세요."></textarea>
					</div>
				</div>
				<div class="card-footer text-right">
					<input type="submit"  class="btn btn-primary" value="등록" />
				</div>
			</form>

		</div>
		<br>
		<div class="card">
			<ul id="guest-box" class="list-group">
				<c:forEach var="guest" items="${guest}">
					<li id="guest-${guest.id}"
						class="list-group-item d-flex justify-content-between">
						<div>${guest.content}</div>
						<div class="d-flex">
							<div class="font-italic">작성자 : ${guest.writer}&nbsp;</div>
							<%-- <c:if test="${principal.user.username == reply.user.username}">
								<button onclick="index.replyDelete(${board.id}, ${reply.id})" class="badge">삭제</button>
							</c:if> --%>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<%-- <ul class="pagination justify-content-center">

		<c:choose>
			<c:when test="${boards.first}">
				<li class="page-item disabled"><a class="page-link"
					href="?page=${boards.number-1}">Previous</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link"
					href="?page=${boards.number-1}">Previous</a></li>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${boards.last}">
				<li class="page-item disabled"><a class="page-link"
					href="?page=${boards.number+1}">Next</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link"
					href="?page=${boards.number+1}">Next</a></li>
			</c:otherwise>
		</c:choose>

	</ul> --%>
</div>
<!-- footer -->
<%@ include file="../layout/footer.jsp"%>
<!-- /footer -->

