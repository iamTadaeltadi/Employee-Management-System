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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;


public class TaskUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDate = request.getParameter("due_date");
        String priority = request.getParameter("priority");
        jakarta.servlet.http.HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // Retrieve the task from the database
        Task task = TaskEditingServlet.retrieveTaskFromDatabase(taskId); // Replace this with your implementation to retrieve the task

        if (task != null) {
            // Update the task properties
            task.setTitle(title);
            task.setDescription(description);
            task.setDueDate(dueDate);
            task.setPriority(priority);

            // Perform the database update
            boolean success = updateTaskInDatabase(task); // Replace this with your implementation to update the task

            if (success) {
            	List<Task> tasks = null;
				try {
					tasks = fetchTasksFromDatabase(user.getName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                session.setAttribute("tasks", tasks);
                response.sendRedirect("dashboard.jsp");
            } else {
                response.getWriter().append("Failed to update the task");
            }
        } else {
            // Handle task not found scenario
            response.getWriter().append("Task not found");
        }
    }
    
    public List<Task> fetchTasksFromDatabase(String username) throws SQLException {
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
    

    private boolean updateTaskInDatabase(Task task) {
        // Assuming you are using a database connection and a PreparedStatement
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;

        try {
            // Establish a database connection
            connection = DBManager.getConnection();

            // Prepare the SQL query
            String query = "UPDATE tasks SET title = ?, description = ?, due_date = ?, priority = ?, completed = ? WHERE task_id = ?";
            statement = connection.prepareStatement(query);

            // Set the query parameters
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getDueDate());
            statement.setString(4, task.getPriority());
            statement.setBoolean(5, task.isCompleted());
            statement.setInt(6, task.getId());

            // Execute the update query
            int rowsAffected = statement.executeUpdate();

            // Check if the update was successful
            if (rowsAffected > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database errors
        } finally {
            // Close the database resources
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle any database errors while closing resources
            }
        }

   
		return success;
		
    }
}