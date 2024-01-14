package com.itcs;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TaskDeletionServlet
 */
public class TaskDeletionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskDeletionServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int taskId = Integer.parseInt(request.getParameter("taskId"));

	    Connection connection = null;
	    PreparedStatement statement = null;
	    jakarta.servlet.http.HttpSession session = request.getSession();

	    try {
	        // Establish a database connection
	        connection = DBManager.getConnection();

	        // Delete the task from the database using taskId
	        String deleteQuery = "DELETE FROM tasks WHERE task_id = ?";
	        statement = connection.prepareStatement(deleteQuery);
	        statement.setInt(1, taskId);
	        int rowsDeleted = statement.executeUpdate();

	        if (rowsDeleted > 0) {
	            // Task deleted successfully
	        	String name=(String)session.getAttribute("name");
	            // Redirect to the dashboard page
	            List<Task> tasks = TaskCompletionServlet.fetchTasksFromDatabase(name);
	            session.setAttribute("tasks", tasks);
	            response.sendRedirect("dashboard.jsp");
	        } else {
	            // Handle task not found scenario
	            response.getWriter().append("Task not found");
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException("Failed to delete task from the database.", e);
	    } finally {
	        // Close the statement and connection
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

}
