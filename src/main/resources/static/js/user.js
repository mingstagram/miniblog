let index = {
	init:function(){
		// 회원 가입
		$("#btn-save").on("click", ()=>{ // function(){} 대신에 ()=>{} 쓰는 이유는 this를 바인딩하기 위해서!
			this.save();
		}); // btn-save를 찾아서 클릭이 되면 함수 실행
		// 회원 수정
		$("#btn-update").on("click", ()=>{ 
			this.update();
		});
		// 비밀번호 찾기
		$("#btn-findPw").on("click", ()=>{ 
			this.findPw();
		});
		
		
	},
	
	// 회원 가입
	save:function(){
		// alert('user의 save함수 호출됨.')
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		// console.log(data);
		
		// ajax호출시 default가 비동기 호출
		// ajax 통신을 이용해서 3개의 파라미터값을 json으로 변경하여 insert 요청
		// ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해주네요? 
		$.ajax({
			// 회원가입 수행 요청
			type:"POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), // http body 데이터
			contentType: "application/json; charset=utf-8", // body데이터가 어떤 타입인지(MIME)
			dataType: "json" // 생략가능 // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경 
		}).done(function(resp){
			// 성공 
			if(resp.status === 500){
				alert("회원가입이 실패했습니다. 중복아이디")
			} else {
				alert("회원가입이 완료되었습니다.");
				location.href="/";
			}
		}).fail(function(error){
			// 실패
			alert(JSON.stringify(error))
		}); 
	},
	
	// 회원 수정
	update:function(){ 
		let data = { 
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		 if(password.value==""){
        alert("Enter password");
        password.focus();
        return false;
    }else if(email.value==""){
        alert("Enter email");
        email.focus();
        return false;
    }
		$.ajax({ 
			type:"PUT",
			url: "/user",
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(resp){
			// 성공 
			alert("회원수정이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			// 실패
			alert(JSON.stringify(error))
		}); 
	},
	
	// 비밀번호 찾기 
	findPw:function(){
		let data = {
			username: $("#username").val(),
			email: $("#email").val()
		};
		
		$.ajax({ 
			type:"POST",
			url: "/auth/findPw",
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(resp){
			// 성공 
			alert("이메일로 임시비밀번호를 발송했습니다.");
			location.href="/";
		}).fail(function(error){
			// 실패
			alert(JSON.stringify(error))
		}); 
	}
	
	
	
}

index.init();




