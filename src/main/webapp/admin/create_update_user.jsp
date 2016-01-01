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
	<p>edit = ${edit}</p>
	<p align="right">
		Admin ${user.firstName} (<a
			href="<%=request.getContextPath()%>/logout.do">Logout</a>)
	</p>
	<h1>
		<c:if test="${edit==null}">Add </c:if>
		<c:if test="${edit!=null}">Edit </c:if>
		user
	</h1>

	<c:if test="${error_message!=null}">
		<p style="color: red;">Error: ${error_message}</p>
	</c:if>

	<form action="<%=request.getContextPath()%>/admin/user_operations.do"
		method="post">

		<c:if test="${edit==null}">
			<input type="hidden" name="action" value="create">
		</c:if>

		<c:if test="${edit!=null}">
			<input type="hidden" name="action" value="update">
		</c:if>

		<table align="left" border="0" cellpadding="2" cellspacing="5">

			<tr>
				<td>Login</td>
				<c:if test="${edit==null}">
					<td><input type="text" name="login"
						value="${user_form['login']}" required></td>
				</c:if>
				<c:if test="${edit!=null}">
					<td><input type="text" name="login"
						value="${user_form['login']}" readonly></td>
				</c:if>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password"
					value="${user_form['password']}" required></td>
			</tr>
			<tr>
				<td>Password again</td>
				<td><input type="password" name="confirm_password"
					value="${user_form['confirm_password']}" required></td>
			</tr>
			<tr>
				<td>Email</td>
				<td><input type="text" name="email"
					value="${user_form['email']}" required></td>
			</tr>
			<tr>
				<td>First name</td>
				<td><input type="text" name="first_name"
					value="${user_form['first_name']}" required></td>
			</tr>
			<tr>
				<td>Last name</td>
				<td><input type="text" name="last_name"
					value="${user_form['last_name']}" required></td>
			</tr>
			<tr>
				<td>Birthday</td>
				<td><input type="text" name="birth_day"
					value="${user_form['birth_day']}" placeholder="25-12-1990" required></td>
			</tr>
			<tr>
				<td>Role</td>
				<td><select size="1" name="role" required>
						<option selected="selected">${user_form['role']}</option>
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