package com.example.controller;

import com.example.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/DisplayImage")
public class DisplayImageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("default-avatar.png");
            return;
        }

        User user = (User) session.getAttribute("currentUser");

        if (user == null || user.getProfilePic() == null) {
            // Redirect to default avatar if not uploaded yet
            response.sendRedirect("default-avatar.png");
            return;
        }

        byte[] imageBytes = user.getProfilePic();
        response.setContentType("image/jpeg"); // or detect actual type
        response.setContentLength(imageBytes.length);

        OutputStream os = response.getOutputStream();
        os.write(imageBytes);
        os.flush();
    }
}

