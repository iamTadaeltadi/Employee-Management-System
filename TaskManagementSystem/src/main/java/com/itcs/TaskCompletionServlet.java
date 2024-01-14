package com.itcs;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class TaskCompletionServlet
 */
public class TaskCompletionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskCompletionServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    int taskId = Integer.parseInt(request.getParameter("taskId"));

    Connection connection = null;
    PreparedStatement statement = null;
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");
    
    try {
        // Establish a database connection
        connection = DBManager.getConnection();

        // Retrieve the task from the database using taskId
        String selectQuery = "SELECT * FROM tasks WHERE task_id = ?";
        statement = connection.prepareStatement(selectQuery);
        statement.setInt(1, taskId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            // Create a Task object from the retrieved data
            int id = resultSet.getInt("task_id");
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            String dueDate = resultSet.getString("due_date");
            String priority = resultSet.getString("priority");
            boolean completed = resultSet.getBoolean("completed");

            Task task = new Task(id, title, description, dueDate, priority, completed);

            // Mark the task as completed
            task.setCompleted(true);

            // Update the task in the database
            String updateQuery = "UPDATE tasks SET completed = ? WHERE task_id = ?";
            statement = connection.prepareStatement(updateQuery);
            statement.setBoolean(1, task.isCompleted());
            statement.setInt(2, task.getId());
            statement.executeUpdate();
            String name=(String)session.getAttribute("name");
            // Redirect to the dashboard page
            List<Task> tasks = fetchTasksFromDatabase(name);
            session.setAttribute("tasks", tasks);
            response.sendRedirect("dashboard.jsp");
        } else {
            // Handle task not found scenario
            response.getWriter().append("Task not found");
        }
    } catch (SQLException e) {
        throw new RuntimeException("Failed to update task in the database.", e);
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
	public static List<Task> fetchTasksFromDatabase(String username) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = DBManager.getConnection()) {
            String query = "SELECT * FROM tasks WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int taskId = rs.getInt("task_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String dueDate = rs.getString("due_date");
                String priority = rs.getString("priority");
                Boolean completed=rs.getBoolean("completed");
                
                Task task = new Task(taskId, title, description, dueDate, priority,completed);
                tasks.add(task);
            }
        }
        
        return tasks;
    }

}
