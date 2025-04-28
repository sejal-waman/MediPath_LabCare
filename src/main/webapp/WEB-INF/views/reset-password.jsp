<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Reset Password</title>
	<link rel="stylesheet" href="/css/auth.css">

</head>
<body>
    <div class="form-box">
        <h2>Reset Your Password</h2>
        <form action="/reset-password" method="post">
            <input type="hidden" name="token" value="${token}" />
            <input type="password" name="newPassword" placeholder="New Password" required />
            <input type="submit" value="Reset Password" />
        </form>
    </div>
</body>
</html>
