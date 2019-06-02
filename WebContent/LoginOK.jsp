<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User, java.util.List, java.util.ArrayList" %>
<%
User loginInformation = (User) session.getAttribute("loginInformation");
List<User> userList = (List<User>) session.getAttribute("userList");
String resultMsg = (String) request.getAttribute("resultMsg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>利用者管理システム</title>
<link rel="stylesheet" href="/LoginSample/CSS/style.css">
</head>
<body>
	<h1>利用者一覧</h1>
	<p><%= loginInformation.getUser_name() %>さん、ようこそ</p>
	<p class="msg">
	<% if(resultMsg != null) { %>
		<%= resultMsg %>
	<% } %>
	</p>
	<table>
		<tr>
			<th>ID</th>
			<th>名前</th>
			<th>パスワード</th>
			<%if("root".equals(loginInformation.getUser_name())) { %>
				<th>処理</th>
			<% } %>
		</tr>
		<tr>
			<form method="POST" action="UserManageServlet">
				<%if("root".equals(loginInformation.getUser_name())) { %>
					<td><input name="user_id" type="text" /></td>
					<td><input name="user_name" type="text" /></td>
					<td><input name="password" type="password" /></td>
					<td><input name="crud" type="submit" value="登録" /></td>
				<% } %>
			</form>
		</tr>
		<% for(User user : userList) {  %>
		<tr>
			<form method="POST" action="UserManageServlet">
				<td><input name="user_id" type="hidden" value="<%= user.getUser_id() %>" /><%= user.getUser_id() %></td>
				<td><input name="user_name" type="text" value="<%= user.getUser_name() %>" /></td>
				<td><input name="password" type="password" value="<%= user.getPassword() %>" /></td>
			<%if("root".equals(loginInformation.getUser_name())) { %>
				<td>
					<input name="crud" type="submit" value="更新" />
					<input name="crud" type="submit" value="削除" />
				</td>
			<% } %>
			</form>
		</tr>
		<% } %>
	</table>
	<form method="GET" action="LoginServlet">
		<input type="submit" value="ログイン画面へ">
	</form>


</body>
</html>