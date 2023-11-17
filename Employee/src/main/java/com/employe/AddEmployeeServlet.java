package com.employe;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addemployee")
public class AddEmployeeServlet extends HttpServlet {

    private static final String INSERT_QUERY = "INSERT INTO employee (name, designation, salary) VALUES (?, ?, ?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter pw = resp.getWriter();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/employees";
            String username = "tada";
            String password = "tadael";
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            createEmployeeTable(conn);

            resp.setContentType("text/html");
            pw.println("Established Connection ");
            // get the employee info

            String employeeName = req.getParameter("employeeName");
            String designation = req.getParameter("designation");
            int salary = Integer.parseInt(req.getParameter("salary"));

            PreparedStatement ps = conn.prepareStatement(INSERT_QUERY);
            ps.setString(1, employeeName);
            ps.setString(2, designation);
            ps.setInt(3, salary);
            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2> Employee registered successfully.</h2>");
            } else {
                pw.println("<h2> Employee not registered successfully.</h2>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }
        pw.println("<a href='Home.html'>Home</a>");
        pw.print("<br>");
        pw.println("<a href='employeelist'>Employee List</a>");
    }

    private void createEmployeeTable(Connection conn) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS employee (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "designation VARCHAR(255) NOT NULL," +
                "salary INT NOT NULL" +
                ")";
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(createTableQuery);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
