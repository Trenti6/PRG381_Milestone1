<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <style>
        .strength-meter {
            height: 8px;
            width: 100%;
            background-color: #334155;
            border-radius: 4px;
            overflow: hidden;
            margin-top: -10px;
            margin-bottom: 8px;
        }

        .strength-bar {
            height: 100%;
            width: 0%;
            background-color: red;
            transition: width 0.3s ease, background-color 0.3s ease;
        }

        .strength-label {
            font-size: 0.9em;
            color: #f1f5f9;
            text-align: right;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="custom-alert" id="customAlert">
    <p id="alertMessage">Something went wrong.</p>
    <button onclick="closeAlert()">OK</button>
</div>

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

    <div class="strength-meter">
        <div id="strengthBar" class="strength-bar"></div>
    </div>
    <div id="strengthLabel" class="strength-label">Strength:</div>

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

    function showAlert(message) {
        document.getElementById("alertMessage").textContent = message;
        document.getElementById("customAlert").style.display = "block";
    }

    function closeAlert() {
        document.getElementById("customAlert").style.display = "none";
    }

    // Show alert based on msg param from server
    (function() {
        const urlParams = new URLSearchParams(window.location.search);
        const msg = urlParams.get('msg');

        const messages = {
            'empty_fields': 'Please fill in all required fields.',
            'invalid_email': 'Invalid email address.',
            'invalid_phone': 'Phone number must be digits only (10–15 digits).',
            'weak_password': 'Password must be at least 6 characters.',
            'already_exists': 'An account with this email or student number already exists.',
            'sql_error': 'Database error. Please try again later.'
        };

        if (messages[msg]) {
            showAlert(messages[msg]);
        }
    })();

    // Password strength meter logic
    document.getElementById("password").addEventListener("input", function () {
        const bar = document.getElementById("strengthBar");
        const label = document.getElementById("strengthLabel");
        const pwd = this.value;
        let strength = 0;

        if (pwd.length >= 6) strength += 1;
        if (/[A-Z]/.test(pwd)) strength += 1;
        if (/[0-9]/.test(pwd)) strength += 1;
        if (/[^A-Za-z0-9]/.test(pwd)) strength += 1;

        let width = "0%";
        let color = "red";
        let text = "Too Weak";

        switch (strength) {
            case 1:
                width = "25%"; color = "red"; text = "Weak"; break;
            case 2:
                width = "50%"; color = "orange"; text = "Medium"; break;
            case 3:
                width = "75%"; color = "#facc15"; text = "Strong"; break;
            case 4:
                width = "100%"; color = "limegreen"; text = "Very Strong"; break;
        }

        bar.style.width = width;
        bar.style.backgroundColor = color;
        label.textContent = "Strength: " + text;
    });
</script>

</body>
</html>
