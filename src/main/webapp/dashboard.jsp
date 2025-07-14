<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.User" %>
<%
    User user = (User) session.getAttribute("currentUser");
    if (user == null) {
        response.sendRedirect("login.jsp?msg=login_required");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="dashboard-box">
        <h2>Dashboard</h2>
        <p>Welcome, <strong><%= user.getName() %></strong>!</p>

        <div class="user-info">
            <p><strong>Student Number:</strong> <%= user.getStudent_num() %></p>
            <p><strong>Email:</strong> <%= user.getEmail() %></p>
            <p><strong>Phone:</strong> <%= user.getPhone() %></p>
        </div>

        <!-- Profile Image Upload -->
        <div class="profile-section">
            <img src="default-avatar.png" alt="Profile Picture" class="profile-pic" id="profilePreview">
            <form action="uploadImage" method="post" enctype="multipart/form-data">
                <input type="file" name="profileImage" accept="image/*" onchange="previewImage(event)">
                <input type="submit" value="Upload Image" class="btn">
            </form>
        </div>

        <!-- Navigation Buttons -->
        <div class="button-group" style="margin-top: 2rem;">
            <a href="index.jsp" class="btn" style="color: white;">Home</a>
            <a href="Logout" class="btn" style="color: white;">Logout</a>
        </div>
    </div>

    <script>
    function previewImage(event) {
        const preview = document.getElementById('profilePreview');
        preview.src = URL.createObjectURL(event.target.files[0]);
        preview.onload = () => URL.revokeObjectURL(preview.src);
    }
    </script>
</body>
</html>
