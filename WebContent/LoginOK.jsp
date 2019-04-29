<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="login.User, java.util.List" %>
<%
List<User> userList = (List<User>) request.getAttribute("userList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン成功</title>
<link rel="stylesheet" href="/LoginSample/CSS/style.css">
</head>
<body>
	ログインに成功しました！<br />
	<table>
		<tr>
			<th>ID</th>
			<th>名前</th>
			<th>パスワード</th>
			<th>処理</th>
		</tr>
		<tr>
			<th><form><input type="text" name="user_id"></form></th>
			<th><form><input type="text" name="user_name"></form></th>
			<th><form><input type="password" name="password"></form></th>
			<th><form><input type="submit" value="登録"></form></th>
		</tr>
		<% for(User user : userList) {  %>
		<tr>
			<td><%= user.getUser_id() %></td>
			<td><%= user.getUser_name() %></td>
			<td><%= user.getPassword() %></td>
			<td><form><input type="submit" value="更新"><input type="submit" value="削除"></form></td>
		</tr>
		<% } %>
	</table>
	<form method="GET" action="LoginServlet">
		<input type="submit" value="ログイン画面へ">
	</form>
</body>
</html>