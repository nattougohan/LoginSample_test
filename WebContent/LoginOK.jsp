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
</head>
<body>
	ログインに成功しました！<br />
	<table>
		<tr>
			<th>UserID</th>
			<th>UserName</th>
			<th>PassWord</th>
		</tr>
		<% for(User user : userList) { // リクエストスコープを受け取らないとuserListは使えない %>
		<tr>
			<td><%= user.getUser_id() %></td>
			<td><%= user.getUser_name() %></td>
			<td><%= user.getPassword() %></td>
		</tr>
		<% } %>
	</table>
	<form method="GET" action="LoginServlet">
		<input type="submit" value="ログイン画面へ">
	</form>
</body>
</html>