<!-- access-denied.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Access Denied</title>
</head>
<body>
    <h2 style="color: red;">🚫 Access Denied</h2>
    <p>${error}</p>
    <a href="/login">🔐 Go back to Login</a>
</body>
</html>
