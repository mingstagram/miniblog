function shareKakao(id) {
 
  // 사용할 앱의 JavaScript 키 설정
  Kakao.init('d1fe7d9c621a7b36cf6a41ba676a04b4');
 
  // 카카오링크 버튼 생성
  Kakao.Link.createDefaultButton({
    container: '#btnKakao', // 카카오공유버튼ID 
    objectType: 'feed',
    content: {
      title: "미니블로그", // 보여질 제목
      description: "미니 블로그입니다", // 보여질 설명
      imageUrl: `/board/${id}`, // 콘텐츠 URL
      link: {
         mobileWebUrl: `http://localhost:8000/board/${id}`,
         webUrl: `http://localhost:8000/board/${id}`
      }
    }
  });
}

function shareTwitter() {
    var sendText = "미니블로그"; // 전달할 텍스트
    var sendUrl = `http://localhost:8000/board/${id}`; // 전달할 URL
    window.open("https://twitter.com/intent/tweet?text=" + sendText + "&url=" + sendUrl);
}


function shareFacebook() {
    var sendUrl = `http://localhost:8000/board/${id}`; // 전달할 URL
    window.open("http://www.facebook.com/sharer/sharer.php?u=" + sendUrl);
}

$("#link-copy").click(function(){
	$("#link-area").attr("type", "text");
	$("#link-area").select();
	var success = document.execCommand("copy");
	$("#link-area").attr("type", "hidden");
	if(success){
		alert("링크가 복사되었습니다.");
	}
});
