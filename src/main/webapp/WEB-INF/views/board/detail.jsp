<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- header -->
<%@ include file="../layout/header.jsp"%> 
<!-- /header -->
<div class="container card">
	<div class="card-body">
		<a href="/"><button class="btn btn-info">돌아가기</button></a>
		<c:if test="${board.user.id == principal.user.id}">
			<!-- principal : PrincipalDetail의 user -->
			<a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
			<button id="btn-delete" class="btn btn-danger">삭제</button>
		</c:if>
	</div>
	<div class="card-header" style="margin-bottom: 10px;">
		글 번호 : <span id="id"><i>${board.id}</i></span> 작성자 : <span><i>${board.user.username}
		</i></span> <span style="float: right;"> <span id="blike">추천 :
				${board.blike}</span> &nbsp; <span>조회수 : ${board.count}</span>&nbsp;
		</span>
	</div>
	<div class="form-group card-body" style="height: 20px">
		<h3>${board.title}</h3>
	</div>
	<hr>
	<div class="form-group card-body">
		<div>${board.content}</div>
	</div>
	<br /> <br /> <br />
	<div style="margin: 10px;">  
		<c:choose>
			<c:when test=""></c:when>
		</c:choose> 
		<button type="button"  class="btn btn-Light" onclick="index.like(${likeCount})"
			style="border: 1px solid black;">👍</button>
		<button type="button"  class="btn btn-Light" onclick="index.unlike(${likeCount})"
			style="border: 1px solid black;">👎</button>
		<div class="btn-group dropup" style="float: right;">
			<button type="button" class="btn btn-Light dropdown-toggle"
				data-toggle="dropdown" style="border: 1px solid black;">📤</button>
			<div class="dropdown-menu">
				<a class="dropdown-item" id="btnFacebook"href="javascript:shareFacebook();">페이스북으로 공유</a> 
				<a class="dropdown-item" id="btnKakao" href="javascript:shareKakao(${board.id});">카카오톡으로 공유</a> 
				<a class="dropdown-item" id="btnTwitter" href="javascript:shareTwitter();">트위터로 공유</a> 
				<a class="dropdown-item" id="link-copy" href="javascript:;">URL 복사</a> 
				<input type="hidden" id="link-area" class="link-area" value="http://localhost:8000/board/${board.id}">
			</div>
		</div> 
	</div>
	<div class="card" style="margin-bottom: 20px;">
		<div class="card">
			<form>
				<input type="hidden" id="userId" value="${principal.user.id}">
				<input type="hidden" id="boardId" value="${board.id}"> 
				<div class="card-body">
					<textarea id="reply-content" class="form-control" rows="1"></textarea>
				</div>
				<div class="card-footer">
					<button type="button" id="btn-reply-save" class="btn btn-primary">등록</button>
				</div>
			</form>
		</div>
		<br>
		<div class="card">
			<div class="card-header">댓글 리스트</div>
			<ul id="reply-box" class="list-group">
				<c:forEach var="reply" items="${board.replys}">
					<li id="reply-${reply.id}"
						class="list-group-item d-flex justify-content-between">
						<div>${reply.content}</div>
						<div class="d-flex">
							<div class="font-italic">작성자 : ${reply.user.username}
								&nbsp;</div>
							<c:if test="${principal.user.username == reply.user.username}">
								<button onclick="index.replyDelete(${board.id}, ${reply.id})"
									class="badge">삭제</button>
							</c:if>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>
<script src="/js/share.js"></script>
<script src="/js/board.js"></script>
<!-- footer -->
<%@ include file="../layout/footer.jsp"%>
<!-- /footer -->

