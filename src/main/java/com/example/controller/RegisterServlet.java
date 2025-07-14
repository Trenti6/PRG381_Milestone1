package com.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // TODO: Save user to DB (this is just a placeholder for now)
        System.out.println("Registering user: " + name + ", " + email);

        // Simulate success by starting session and redirecting
        HttpSession session = request.getSession();
        session.setAttribute("user", email);

        response.sendRedirect("dashboard.jsp");
    }
}
