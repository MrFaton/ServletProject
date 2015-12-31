<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${user.login}</title>
</head>
<body style="text-align: center;">
	<h1>Hello, ${user.login}!</h1>
	<br>
	<p>
		Click <a href="<%=request.getContextPath()%>/logout.do">here</a> to
		logout
	</p>
</body>
</html>