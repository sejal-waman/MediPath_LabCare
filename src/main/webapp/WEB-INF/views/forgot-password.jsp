<%@ page contentType="text/html;charset=UTF-8" %>




<html>
<head>
    <title>Forgot Password - MediPath LabCare</title>
	<link rel="stylesheet" href="/css/auth.css">
	

</head>
<body>
    <div class="form-box">
        <h2>Forgot Password</h2>
        <form action="/send-reset-link" method="post">
            <input type="email" name="email" placeholder="Enter your registered email" required />
            <input type="submit" value="Send Reset Link" />
        </form>
        <p><a href="/login">Back to Login</a></p>
    </div>
</body>
</html>
