package com.example.controller;

import com.example.dbc.DatabaseConnection;
import com.example.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

@WebServlet("/uploadImage")
@MultipartConfig(maxFileSize = 16177215) // 16MB
public class UploadImageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Part filePart = request.getPart("profileImage");
        InputStream inputStream = null;

        if (filePart != null) {
            inputStream = filePart.getInputStream();
        }

        try (Connection conn = DatabaseConnection.initializeDatabase()) {
            String sql = "UPDATE users SET profile_pic = ? WHERE student_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            if (inputStream != null) {
                stmt.setBinaryStream(1, inputStream, (int) filePart.getSize());
            } else {
                stmt.setNull(1, Types.BINARY);
            }
            stmt.setString(2, currentUser.getStudent_num());
            stmt.executeUpdate();

            // Re-fetch updated user with profile_pic from DB
            String fetchSql = "SELECT * FROM users WHERE student_number = ?";
            PreparedStatement ps = conn.prepareStatement(fetchSql);
            ps.setString(1, currentUser.getStudent_num());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                byte[] profilePic = rs.getBytes("profile_pic");
                User updatedUser = new User(
                        rs.getString("student_number"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        profilePic
                );
                session.setAttribute("currentUser", updatedUser);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("dashboard.jsp");
    }
}
