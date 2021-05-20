<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header -->
<%@ include file="layout/header.jsp"%>
<!-- /header -->
<div class="container">
 
	<c:forEach var="board" items="${boards}">
		<div class="card m-1">
			<div class="card-body">
				<h4 class="card-title">${board.title}</h4>
				<a href="/board/${board.id}" class="btn btn-primary">상세보기 </a>
				<h6 style="float:right">조회수 : ${board.count}</h6>
			</div>
		</div>
	</c:forEach>
	
<ul class="pagination justify-content-center" style="margin-top:20px;">
	<c:choose>
		<c:when test="${pageMaker.prev}">
			<li class="page-item"><a class="page-link" href="?search=${search}&page=${pageMaker.startPage-1}">Previous</a></li>
		</c:when>
		<c:otherwise>
			<li class="page-item disabled"><a class="page-link" href="?search=${search}&page=${pageMaker.startPage-1}" >Previous</a></li>
		</c:otherwise>
	</c:choose> 
	
	<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="pageNum">
		<c:choose>
			<c:when test="${pageNum == curPageNum}">
				<li class="page-item active">
					<a class="page-link" href="?search=${search}&page=${pageNum}">${pageNum}</a>
				</li>
			</c:when>
			<c:otherwise>
				<li class="page-item">
					<a class="page-link" href="?search=${search}&page=${pageNum}">${pageNum}</a>
				</li>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	
	<c:choose>
		<c:when test="${pageMaker.next && pageMaker.endPage > 0}">
			<li class="page-item"><a class="page-link" href="?search=${search}&page=${pageMaker.endPage + 1}">Next</a></li>
		</c:when>
		<c:otherwise>
			<li class="page-item disabled"><a class="page-link" href="?search=${search}&page=${pageMaker.endPage + 1}">Next</a></li>
		</c:otherwise>
	</c:choose>   
</ul>
  
</div>
<!-- footer -->
<%@ include file="layout/footer.jsp"%>
<!-- /footer -->

