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

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
            // Authenticate user
            User user = authenticateUser(username, password);
            HttpSession session = request.getSession();
            if (user != null) {
                // Authentication successful
                
                session.setAttribute("user", user);
                session.setAttribute("name", user.getName());
                
                // Fetch tasks and set the "tasks" attribute
                List<Task> tasks = fetchTasksFromDatabase(user.getName());
                session.setAttribute("tasks", tasks);
                
                
                response.sendRedirect("dashboard.jsp");
            } else {
                // Authentication failed
            	session.setAttribute("logincheck",true);
                response.sendRedirect("login.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp");
        }
    }
    
    private User authenticateUser(String username, String password) throws SQLException {
        try (Connection conn = DBManager.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // User exists
                int userId = rs.getInt("user_id");
                String name = rs.getString("username");
                User user = new User();
                user.setName(name);
                user.setPassword(password);
                return user;
            }
        }
        
        return null; // User not found
    }
    
    private List<Task> fetchTasksFromDatabase(String username) throws SQLException {
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