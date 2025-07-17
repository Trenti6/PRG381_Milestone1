<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reset Password</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="centered-container">
    <form action="ResetPassword" method="post">
        <div class="icon-wrapper">
            <img src="https://cdn-icons-png.flaticon.com/512/2919/2919600.png" class="card-icon" alt="Reset Icon">
        </div>

        <h2>Reset Password</h2>
        <p class="subtitle">Enter your email to reset your password</p>

        <input type="email" name="email" placeholder="Email Address" required>

        <input type="submit" value="Send Reset Link">

        <div class="form-footer">
            <a href="login.jsp" class="forgot-link">Back to Login</a>
        </div>
    </form>
</div>
</body>
</html>
