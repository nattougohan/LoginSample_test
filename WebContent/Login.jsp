<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
</head>
<body>
	<form method="POST" action="LoginServlet">
		ユーザーID：<input type="text" name="user_id" /><br />
		パスワード：<input type="password" name="password" /><br />
		<input type="submit" value="ログイン">
	</form>
</body>
</html>