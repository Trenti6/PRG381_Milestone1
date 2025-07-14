<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>

<form action="Register" method="post">
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
    // Toggle password visibility
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

    // Show alert based on msg param from server
    (function() {
        const urlParams = new URLSearchParams(window.location.search);
        const msg = urlParams.get('msg');

        if (msg === 'empty_fields') {
            alert('Please fill in all required fields.');
        } else if (msg === 'invalid_email') {
            alert('Invalid email address.');
        } else if (msg === 'invalid_phone') {
            alert('Phone number must be digits only (10–15 digits).');
        } else if (msg === 'weak_password') {
            alert('Password must be at least 6 characters.');
        } else if (msg === 'already_exists') {
            alert('An account with this email or student number already exists.');
        } else if (msg === 'sql_error') {
            alert('Database error. Please try again later.');
        }
    })();
</script>

</body>
</html>
