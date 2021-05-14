let index = {
	init:function(){
		$("#btn-save").on("click", ()=>{ // function(){} 대신에 ()=>{} 쓰는 이유는 this를 바인딩하기 위해서!
			this.save();
		}); // btn-save를 찾아서 클릭이 되면 함수 실행
		$("#btn-delete").on("click", ()=>{ 
			this.deleteById();
		});
		$("#btn-update").on("click", ()=>{ 
			this.update();
		});
		$("#btn-reply-save").on("click", ()=>{ 
			this.replySave();
		});
		$("#btn-like").on("click", ()=>{ 
			this.like();
		});
		$("#btn-unlike").on("click", ()=>{ 
			this.unlike();
		});
	},
	 
	save:function(){ 
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
		
		// console.log(data);
		
		// ajax호출시 default가 비동기 호출
		// ajax 통신을 이용해서 3개의 파라미터값을 json으로 변경하여 insert 요청
		// ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해주네요? 
		$.ajax({
			// 회원가입 수행 요청
			type:"POST",
			url: "/api/board",
			data: JSON.stringify(data), // http body 데이터
			contentType: "application/json; charset=utf-8", // body데이터가 어떤 타입인지(MIME)
			dataType: "json" // 생략가능 // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경 
		}).done(function(resp){
			// 성공 
			alert("글 작성이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			// 실패
			alert(JSON.stringify(error))
		}); 
	},
	
	deleteById:function(){   
		var id = $("#id").text();
		
		$.ajax({ 
			type:"DELETE",
			url: "/api/board/"+id,
			dataType: "json",
			contentType: 'application/json; charset=utf-8'
		}).done(function(resp){
			// 성공 
			alert("삭제가 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			// 실패
			alert(JSON.stringify(error))
		}); 
	},
	
	update:function(){   
		let id = $("#id").val();
		
		let data = { 
			title: $("#title").val(),
			content: $("#content").val()
		};
		
		$.ajax({ 
			type:"PUT",
			url: "/api/board/"+id,
			data: JSON.stringify(data),
			dataType: "json",
			contentType: 'application/json; charset=utf-8'
		}).done(function(resp){
			// 성공 
			alert("수정이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			// 실패
			alert(JSON.stringify(error))
		}); 
	},
	
	replySave:function(){ 
		let data = { 
			userId: $("#userId").val(),
			boardId: $("#boardId").val(),
			content: $("#reply-content").val()
		}; 
		 
		$.ajax({
			type:"POST",
			url: `/api/board/${data.boardId}/reply`,
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json"  
		}).done(function(resp){
			// 성공 
			alert("댓글 작성이 완료되었습니다.");
			location.href=`/board/${data.boardId}`;
		}).fail(function(error){
			// 실패
			alert(JSON.stringify(error))
		}); 
	},
	
	replyDelete:function(boardId, replyId){  
		$.ajax({
			type:"DELETE",
			url: `/api/board/${boardId}/reply/${replyId}`,  
			dataType: "json"  
		}).done(function(resp){
			// 성공 
			alert("댓글삭제 성공");
			location.href=`/board/${boardId}`;
		}).fail(function(error){
			// 실패
			alert(JSON.stringify(error))
		}); 
	},
	
	like:function(likeCount){  
		var id = $("#id").text();   
		if(likeCount == undefined){
			$.ajax({ 
				method: "POST",
				url: "/api/boardlike/"+id,
				data: JSON.stringify(id),
				contentType: "application/json; charset=utf-8", 
				dataType: "json", 
			}).done(function(resp){
				// 성공 
				alert("추천했습니다.");
				location.href=`/board/${id}`;
			}).fail(function(error){
				// 실패
				alert(JSON.stringify(error))
			}); 
		} else {
			alert("이미 추천했습니다.");
			location.href=`/board/${id}`;
		}
	},
	
	unlike:function(likeCount){  
		var id = $("#id").text(); 
		
		if(likeCount == undefined){
			$.ajax({ 
				type:"POST",
				url: "/api/boardunlike/"+id,
				data: JSON.stringify(id),
				contentType: "application/json; charset=utf-8", 
				dataType: "json", 
			}).done(function(resp){
				// 성공 
				alert("비추천했습니다.");
				location.href=`/board/${id}`;
			}).fail(function(error){
				// 실패
				alert(JSON.stringify(error))
			}); 
		} else {
			alert("이미 추천했습니다.");
			location.href=`/board/${id}`;
		}
	},
	
}

index.init();