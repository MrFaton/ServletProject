<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="mytag" uri="http://servlet-project/tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
table, th, td {
border-collapse: collapse;
border: 2px solid black;
}
</style>
<title>${user.login}</title>
</head>
<body>
<p align="right">Admin ${user.firstName} (<a href="./logout.do">Logout</a>)</p>
<p><a href="./create_update_user.jsp">Add new user</a></p>
    
    
</body>
</html>