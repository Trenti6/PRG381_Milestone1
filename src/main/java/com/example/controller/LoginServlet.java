package com.example.controller;

import com.example.dbc.DatabaseConnection;
import com.example.model.User;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "LoginServlet", urlPatterns = "/Login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try (Connection conn = DatabaseConnection.initializeDatabase()) {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                if (BCrypt.checkpw(password, hashedPassword)) {
                    // Create a User object from the DB result
                    byte[] profilePic = rs.getBytes("profile_pic");

                    User user = new User(
                            rs.getString("student_number"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            hashedPassword,
                            profilePic
                    );

                    // Store User object in session
                    HttpSession session = req.getSession();
                    session.setAttribute("currentUser", user);

                    res.sendRedirect("dashboard.jsp");
                    return;
                }
            }

            res.sendRedirect("login.jsp?msg=invalid");

        } catch (SQLException e) {
            e.printStackTrace();
            res.sendRedirect("login.jsp?msg=error");
        }
    }
}
