package com.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login") // This matches your form's action="login"
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Dummy check - replace with real DB/authentication
        if ("admin@example.com".equals(email) && "password123".equals(password)) {

            // Create session and store user
            HttpSession session = request.getSession();
            session.setAttribute("user", email);

            // Redirect to dashboard
            response.sendRedirect("dashboard.jsp");

        } else {
            // Redirect back to log in with error query param
            response.sendRedirect("login.jsp?error=true");
        }
    }
}
