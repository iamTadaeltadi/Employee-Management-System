package com.itcs;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class ChangePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Get the user's previous password and new password from the form
        String previousPassword = request.getParameter("previousPassword");
        String newPassword = request.getParameter("newPassword");

        // Check if the user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("name") == null) {
            response.sendRedirect("login.jsp");
        } else {
            String username = (String) session.getAttribute("name");

            // Verify the user's previous password from the database
            if (verifyPreviousPassword(username, previousPassword)) {
                // Update the password in the database
                if (updatePassword(username, newPassword)) {
                    
                    session.setAttribute("passwordChanged", true);
                    response.sendRedirect("changepassword.jsp");
                } else {
                	request.setAttribute("passwordchanged", false);
                	response.sendRedirect("changepassword.jsp");
                }
            } else {
            	request.setAttribute("passwordchanged", false);
            	response.sendRedirect("changepassword.jsp");
            }
        }
    }

    private boolean verifyPreviousPassword(String username, String previousPassword) {
        try {
            // Get database connection
            Connection connection = DBManager.getConnection();

            // Prepare SQL statement
            String query = "SELECT password FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if the query returned any result
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");

                // Compare the stored password with the previous password
                if (storedPassword.equals(previousPassword)) {
                    // Close database connection and resources
                    resultSet.close();
                    statement.close();
                    connection.close();
                    return true;
                }
            }

            // Close database connection and resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean updatePassword(String username, String newPassword) {
        try {
            // Get database connection
            Connection connection = DBManager.getConnection();

            // Prepare SQL statement
            String query = "UPDATE users SET password = ? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setString(2, username);

            // Execute the update
            int rowsAffected = statement.executeUpdate();

            // Close database connection and resources
            statement.close();
            connection.close();

            // Check if the update was successful
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}