<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><c:if test="${edit==null}">Add </c:if> <c:if
		test="${edit!=null}">Edit </c:if> User</title>
</head>
<body>
	<p align="right">
		Admin ${user.firstName} (<a href="./logout.do">Logout</a>)
	</p>
	<h1>
		<c:if test="${edit==null}">Add </c:if>
		<c:if test="${edit!=null}">Edit </c:if>
		user
	</h1>

	<form action="./admin/user_operation.do" method="post">
		<input type="hidden" name="action" value="create">
		<table align="left" border="0" cellpadding="2" cellspacing="5">

			<tr>
				<td>Login</td>
				<c:if test="${edit==null}">
					<td><input type="text" name="login" required></td>
				</c:if>
				<c:if test="${edit!=null}">
					<td><input type="text" name="login" value="${user.login}"></td>
				</c:if>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password" required></td>
			</tr>
			<tr>
				<td>Password again</td>
				<td><input type="password" name="password_again" required></td>
			</tr>
			<tr>
				<td>Email</td>
				<td><input type="text" name="email"required"></td>
			</tr>
			<tr>
				<td>First name</td>
				<td><input type="text" name="first_name" required></td>
			</tr>
			<tr>
				<td>Last name</td>
				<td><input type="text" name="last_name" required></td>
			</tr>
			<tr>
				<td>Birthday</td>
				<td><input type="text" name="birthday" required></td>
			</tr>
			<tr>
				<td>Role</td>
				<td><select size="1" name="role" required>
						<option selected disabled>Choose role</option>
						<option value="Admin">Admin</option>
						<option value="User">User</option>
				</select></td>
			</tr>
			<tr>
				<td><input type="submit" value="Ok"> <input
					type="reset" value="Cansel"></td>
			</tr>
		</table>
	</form>
</body>
</html>