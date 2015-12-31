<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${error_message_title}</title>
</head>
<body style = "color: red; text-align: center;">
    <h2>${error_message}</h2>
    <h4>Go to <a href="<%=request.getContextPath()%>">main</a> page</h4>
</body>
</html>