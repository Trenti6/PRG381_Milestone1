package com.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false); // avoid creating a new session
        if (session != null) {
            session.invalidate(); // destroys session
        }

        res.sendRedirect("login.jsp?msg=logout"); // redirect after logout
    }
}
