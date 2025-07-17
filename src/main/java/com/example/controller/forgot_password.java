package com.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;
import java.util.Random;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import org.mindrot.jbcrypt.BCrypt;
import com.example.dbc.DatabaseConnection;

@WebServlet(name = "forgot_password", urlPatterns = "/ResetPassword")
public class forgot_password extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        try (Connection conn = DatabaseConnection.initializeDatabase()) {
            // Check if email exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Generate random password
                String newPassword = generateRandomPassword(6);
                String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

                // Update password in DB
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
                updateStmt.setString(1, hashedPassword);
                updateStmt.setString(2, email);
                updateStmt.executeUpdate();

                // Send email
                sendEmail(email, newPassword);

                response.sendRedirect("forgot_password.jsp?msg=reset_sent");
            } else {
                response.sendRedirect("forgot_password.jsp?msg=email_not_found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("forgot_password.jsp?msg=error");
        }
    }

    private Properties loadEmailConfig() throws IOException {
        Properties props = new Properties();
        try (InputStream input = getServletContext().getResourceAsStream("/WEB-INF/config.properties")) {
            if (input == null) {
                throw new FileNotFoundException("config.properties not found in WEB-INF");
            }
            props.load(input);
        }
        return props;
    }


    // Generate random alphanumeric password
    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }

    // Send email with new password
    private void sendEmail(String toEmail, String newPassword) throws MessagingException, IOException {
        Properties config = loadEmailConfig();
        final String fromEmail = config.getProperty("EMAIL_USER");
        final String password = config.getProperty("EMAIL_PASS");

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Password Reset");
        message.setText("Your new password is: " + newPassword + "\nPlease log in and change it immediately.");

        Transport.send(message);
    }

}
