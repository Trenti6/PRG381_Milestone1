package com.example.controller;

import com.example.dbc.DatabaseConnection;
import com.example.model.User;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.regex.Pattern;

@WebServlet(name = "LoginServlet", urlPatterns = "/Login")
public class LoginServlet extends HttpServlet {

    // Email regex (simple version)
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Sanitize input
        if (email != null) email = email.trim();

        // Server-side input validation
        if (email == null || email.isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) {
            request.setAttribute("error", "Please enter a valid email address.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (password == null || password.length() < 6) {
            request.setAttribute("error", "Password must be at least 6 characters long.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DatabaseConnection.initializeDatabase()) {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                if (BCrypt.checkpw(password, hashedPassword)) {
                    byte[] profilePic = rs.getBytes("profile_pic");

                    User user = new User(
                            rs.getString("student_number"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            null, // Don't store hashed password in session
                            profilePic
                    );

                    HttpSession session = request.getSession();
                    session.setAttribute("currentUser", user);
                    session.setMaxInactiveInterval(30 * 60); // Optional: 30 min timeout

                    response.sendRedirect("dashboard.jsp");
                    return;
                }
            }

            // Forward with custom alert message
            request.setAttribute("error", "Invalid email or password. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "An internal error occurred. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
