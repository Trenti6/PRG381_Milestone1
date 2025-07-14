<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <form action="register" method="post">
        <div class="icon-wrapper">
            <img src="https://cdn-icons-png.flaticon.com/512/3177/3177440.png" class="card-icon" alt="User Icon">
        </div>

        <h2>Create Account</h2>
        <p class="subtitle">Fill in your details below</p>

        <input type="text" name="student_number" placeholder="Student Number" required>
        <input type="text" name="name" placeholder="First Name" required>
        <input type="text" name="surname" placeholder="Surname" required>
        <input type="email" name="email" placeholder="Email Address" required>
        <input type="tel" name="phone" placeholder="Phone Number" required>

        <div class="password-container">
            <input type="password" id="password" name="password" placeholder="Password" required>
            <span class="toggle-password" onclick="togglePassword()">🙈</span>
        </div>

        <input type="submit" value="Register">

        <div class="form-footer">
            <a href="login.jsp" class="forgot-link">Already have an account? Login</a>
        </div>
    </form>

    <script>
        function toggleRegisterPassword() {
            const pwd = document.getElementById("registerPassword");
            pwd.type = pwd.type === "password" ? "text" : "password";
        }
    </script>
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
