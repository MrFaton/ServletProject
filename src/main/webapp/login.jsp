<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
	<div
		style="margin: 10% 0px 0px 35%; border: 2px solid black; width: 400px;">
		<form action="<%=request.getContextPath()%>/login.do" method="post">
			<table align="center" border="0" cellpadding="2" cellspacing="5">
				<tr>
					<td>Login:</td>
					<td><input type="text" name="login" required></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password" required></td>
				</tr>
				<tr>
					<td colspan=2 align="right"><input type="submit"
						value="Sign in"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>