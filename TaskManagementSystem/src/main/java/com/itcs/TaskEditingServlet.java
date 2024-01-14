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
 * Servlet implementation class TaskEditingServlet
 */
public class TaskEditingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskEditingServlet() {
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

	    // Retrieve the task based on the taskId from the database

	    Task task = retrieveTaskFromDatabase(taskId); // Replace this with your implementation to retrieve the task

	    if (task != null) {
	        request.setAttribute("task", task);
	        request.getRequestDispatcher("edittask.jsp").forward(request, response);
	    } else {
	        // Handle task not found scenario
	        response.getWriter().append("Task not found");
	    }
	}
	public static Task retrieveTaskFromDatabase(int taskId) {
	    // Your implementation to retrieve the task from the database goes here
	    // Replace the code below with your actual database retrieval logic

	    // Assuming you are using a database connection and a PreparedStatement
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    Task task = null;

	    try {
	        // Establish a database connection
	        connection = DBManager.getConnection();

	        // Prepare the SQL query
	        String query = "SELECT * FROM tasks WHERE task_id = ?";
	        statement = connection.prepareStatement(query);

	        // Set the taskId parameter
	        statement.setInt(1, taskId);

	        // Execute the query
	        resultSet = statement.executeQuery();

	        // Check if a task record was found
	        if (resultSet.next()) {
	            // Retrieve the task properties from the result set
	            String title = resultSet.getString("title");
	            String description = resultSet.getString("description");
	            String dueDate = resultSet.getString("due_date");
	            String priority = resultSet.getString("priority");
	            Boolean completed = resultSet.getBoolean("completed");

	            // Create the Task object
	            task = new Task(taskId, title, description, dueDate, priority,completed);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle any database errors
	    } finally {
	        // Close the database resources
	        try {
	            if (resultSet != null) resultSet.close();
	            if (statement != null) statement.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle any database errors while closing resources
	        }
	    }

	    return task;
	}
}
