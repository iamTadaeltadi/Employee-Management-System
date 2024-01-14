package com.itcs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class SearchTaskServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String searchTerm = request.getParameter("search");
        String userName = (String) session.getAttribute("name");
        List<Task> tasks = searchTasksByTitleAndUser(searchTerm, userName);
        session.setAttribute("tasks", tasks);

        response.sendRedirect("dashboard.jsp");
    }

    private List<Task> searchTasksByTitleAndUser(String searchTerm, String userName) {
        List<Task> tasks = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getConnection();
            String query = "SELECT * FROM tasks WHERE title LIKE ? AND username = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + searchTerm + "%");
            statement.setString(2, userName);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int taskId = resultSet.getInt("task_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String dueDate = resultSet.getString("due_date");
                String priority = resultSet.getString("priority");
                boolean completed = resultSet.getBoolean("completed");

                Task task = new Task(taskId, title, description, dueDate, priority, completed);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return tasks;
    }
}