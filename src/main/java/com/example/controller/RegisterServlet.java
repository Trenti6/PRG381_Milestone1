package com.example.controller;

import java.io.*;
import java.sql.*;

import com.example.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;
import com.example.dbc.DatabaseConnection;

@WebServlet(name = "RegisterServlet", urlPatterns = "/Register")
public class RegisterServlet extends HttpServlet{

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String studentNum = req.getParameter("student_number");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        // Hash the password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(studentNum, name, surname, email, phone, hashedPassword);

        try (Connection conn = DatabaseConnection.initializeDatabase()) {
            String sql = "INSERT INTO users (student_number, name, surname, email, phone, password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getStudent_num());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getSurname());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getPassword());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                res.sendRedirect("hello.jsp?msg=registered");
            } else {
                res.sendRedirect("index.jsp?msg=error");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            res.sendRedirect("index.jsp?msg=exists");
        }
    }

}
