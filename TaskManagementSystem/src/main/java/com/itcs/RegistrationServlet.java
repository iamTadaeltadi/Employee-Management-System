package com.itcs;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class RegistrationServlet
 */
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
	    String username = req.getParameter("username");
	    String password = req.getParameter("password");

	    try (Connection conn = DBManager.getConnection()) {
	        // Check if the username is already taken
	        String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
	        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
	        checkStmt.setString(1, username);
	        ResultSet checkResult = checkStmt.executeQuery();
	        checkResult.next();
	        int count = checkResult.getInt(1);

	        if (count > 0) {
	            req.setAttribute("errorMessage", "Username is already taken");
	            req.getRequestDispatcher("registration.jsp").forward(req, response);
	            return;
	        }

	        // Insert the new user into the database
	        String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
	        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
	        insertStmt.setString(1, username);
	        insertStmt.setString(2, password);
	        insertStmt.executeUpdate();

	        response.sendRedirect("login.jsp");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
