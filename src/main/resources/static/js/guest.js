let index = {
	init:function(){
		$("#btn-save").on("click", ()=>{ // function(){} 대신에 ()=>{} 쓰는 이유는 this를 바인딩하기 위해서!
			this.save();
		});
	},
	
	save:function(){
		let data = {
			writer: $("#writer").val(),
			password: $("#password").val(),
			content: $("#content").val()
		}; 
		
		if(content.value==""){
	        alert("Enter content");
	        content.focus();
	        return false;
	    } else if(writer.value==""){
	        alert("Enter writer");
	        writer.focus();
	        return false;
	    }
		
		if(data.password.value==""){
			alert("Enter password");
			password.focus();
			return false;
		} else {
			var num_check=/^[0-9]*$/;
			if(!num_check.test(data.password)) { 
				alert("비밀번호는 숫자만 입력해주세요."); 
				return false;
			}	
		}

		
		if(confirm("작성하시겠습니까?")){
		
			$.ajax({ 
				type:"POST",
				url: "/api/guestBook",
				data: JSON.stringify(data), // http body 데이터
				contentType: 'application/json; charset=utf-8', // body데이터가 어떤 타입인지(MIME)
				dataType: "json" // 생략가능 // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경 
			}).done(function(resp){
				// 성공 
				alert("방명록 작성이 완료되었습니다.");
				location.href="/guest/guestBook";
			}).fail(function(error){
				// 실패
				alert(JSON.stringify(error))
			}); 
		}
	},
	
	bookDelete:function(id, password){   
		
		var passwordConfirm = prompt("비밀번호를 입력해주세요.", "");
		
		if(passwordConfirm != password){
			alert("비밀번호가 틀렸습니다.");
			return false;
		}
		
		$.ajax({
			type:"DELETE",
			url: "/api/guestBook/"+id,
			dataType: "json"  
		}).done(function(resp){
			// 성공 
			alert("방명록이 삭제완료되었습니다.");
			location.href="/guest/guestBook";
		}).fail(function(error){
			// 실패
			alert(JSON.stringify(error))
		}); 
	}
}

index.init();