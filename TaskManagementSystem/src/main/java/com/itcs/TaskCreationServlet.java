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


public class TaskCreationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDate = request.getParameter("due_date");
        String priority = request.getParameter("priority");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            try {
                boolean success = createTaskInDatabase(user.getName(), title, description, dueDate, priority);
                if (success) {
                	List<Task> tasks = fetchTasksFromDatabase(user.getName());
                    session.setAttribute("tasks", tasks);
                    response.sendRedirect("dashboard.jsp");
                } else {
                    response.sendRedirect("error.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        } else {
        	
            response.sendRedirect("login.jsp");
        }
    }

    private boolean createTaskInDatabase(String username, String title, String description, String dueDate, String priority) throws SQLException {
        try (Connection conn = DBManager.getConnection()) {
            String query = "INSERT INTO tasks (username, title, description, due_date, priority) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setString(4, dueDate);
            pstmt.setString(5, priority);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
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
}