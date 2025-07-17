<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>

<!-- Custom Alert -->
<div class="custom-alert" id="customAlert">
    <p id="alertMessage">Invalid email or password. Please try again.</p>
    <button onclick="closeAlert()">OK</button>
</div>

<!-- Login Form -->
<div class="centered-container">
    <form action="Login" method="post">
        <div class="icon-wrapper">
            <img src="https://cdn-icons-png.flaticon.com/512/3177/3177440.png" class="card-icon" alt="User Icon">
        </div>

        <h2>Welcome Back</h2>
        <p class="subtitle">Please login to continue</p>

        <input type="email" name="email" placeholder="Email Address" required>

        <div class="password-container">
            <input type="password" id="password" name="password" placeholder="Password" required>
            <span class="toggle-password" onclick="togglePassword()">🙈</span>
        </div>

        <input type="submit" value="Login">
        <br>
        <div class="form-footer">
            <a href="forgot_password.jsp" class="forgot-link">Forgot your password?</a>
            <br>
            <br>
            <a href="index.jsp" class="home">Home</a>
        </div>
    </form>
</div>

<!-- JavaScript -->
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

    function showAlert(message) {
        const alertBox = document.getElementById("customAlert");
        const alertMsg = document.getElementById("alertMessage");
        alertMsg.textContent = message;
        alertBox.style.display = "block";
    }

    function closeAlert() {
        document.getElementById("customAlert").style.display = "none";
    }
</script>

<!-- JSP conditional logic to trigger alert -->
<% if (request.getAttribute("error") != null) { %>
<script>
    showAlert("<%= request.getAttribute("error") %>");
</script>
<% } %>

</body>
</html>
