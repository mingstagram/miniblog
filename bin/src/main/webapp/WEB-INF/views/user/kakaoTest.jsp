<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form enctype="application/x-www-form-urlencoded" action="https://kauth.kakao.com/oauth/token" method="POST">
	grant_type : <input type="text" name="grant_type"><br>
	client_id : <input type="text" name="client_id"><br>
	redirect_uri : <input type="text" name="redirect_uri"><br>
	code : <input type="text" name="code" value="${code}"><br>
	<input type="submit" value="전송">
</form>
</body>
</html>