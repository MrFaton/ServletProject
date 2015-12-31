<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="mytag" uri="http://servlet-project/tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
form, form div {
	display: inline;
	padding: 2px;
}

table, th, td {
	border-collapse: collapse;
	border: 2px solid black;
}
</style>
<title>${user.login}</title>
</head>
<body>
	<p align="right">
		Admin ${user.firstName} (<a
			href="<%=request.getContextPath()%>/logout.do">Logout</a>)
	</p>
	<p>
		<a href="<%=request.getContextPath()%>/admin/create_update_user.jsp">Add
			new user</a>
	</p>
	<div align="center">
		<mytag:all_users_table />
	</div>
</body>
</html>