<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <form action="login" method="post">
        <div style="text-align: center;">
            <img src="https://cdn-icons-png.flaticon.com/512/3177/3177440.png" class="card-icon" alt="User Icon">
        </div>

        <h2>Welcome Back</h2>
        <p class="subtitle">Please login to continue</p>

        <input type="email" name="email" placeholder="Email Address" required>

        <div class="password-container">
                    <input type="password" id="password" name="password" placeholder="Password" required>
                    <span class="toggle-password" onclick="togglePassword()">🙈</span>
                </div>

        <button type="button" class="forgot-btn">Forgot password?</button>

        <input type="submit" value="Login">
    </form>

    <script>
        function togglePassword() {
            const passwordInput = document.getElementById("password");
            const icon = document.querySelector(".toggle-password");
            if (passwordInput.type === "password") {
                passwordInput.type = "text";
                icon.textContent = "👁️";
            } else {
                passwordInput.type = "password";
                icon.textContent = "🙈";
            }
        }
        </script>
</body>
</html>
