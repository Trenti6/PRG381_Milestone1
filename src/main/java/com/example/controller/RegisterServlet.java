package com.example.controller;

import java.io.*;
import java.sql.*;
import java.util.regex.*;

import com.example.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;
import com.example.dbc.DatabaseConnection;

@WebServlet(name = "RegisterServlet", urlPatterns = "/Register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String studentNum = req.getParameter("student_number");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        // Basic server-side validation
        if (studentNum == null || name == null || surname == null || email == null || phone == null || password == null ||
                studentNum.isEmpty() || name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            res.sendRedirect("register.jsp?msg=empty_fields");
            return;
        }

        // Validate email format - example format - john.doe@example.com
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        if (!emailPattern.matcher(email).matches()) {
            res.sendRedirect("register.jsp?msg=invalid_email");
            return;
        }

        // Validate phone number is digits only - example format - 0829876543/278212345678
        if (!phone.matches("\\d{10,15}")) {
            res.sendRedirect("register.jsp?msg=invalid_phone");
            return;
        }

        // Validate password length - Trent@2025
        if (password.length() < 6) {
            res.sendRedirect("register.jsp?msg=weak_password");
            return;
        }

        // Hash the password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(studentNum, name, surname, email, phone, hashedPassword);

        try (Connection conn = DatabaseConnection.initializeDatabase()) {

            // Optional: check for existing email or student number before insert
            String checkSql = "SELECT 1 FROM users WHERE email = ? OR student_number = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, email);
            checkStmt.setString(2, studentNum);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                res.sendRedirect("register.jsp?msg=already_exists");
                return;
            }

            String sql = "INSERT INTO users (student_number, name, surname, email, phone, password, profile_pic) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getStudent_num());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getSurname());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getPassword());
            stmt.setNull(7, java.sql.Types.BINARY);  // Insert NULL for profile_pic

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                res.sendRedirect("login.jsp?msg=registered");
            } else {
                res.sendRedirect("register.jsp?msg=error");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            res.sendRedirect("register.jsp?msg=sql_error");
        }
    }
}
