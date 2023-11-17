package com.employe;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/employeelist")
public class ViewEmployeesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String query = "SELECT id, name, designation, salary FROM employee";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/employees", "tada", "tadael");
                 PreparedStatement ps = conn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                pw.println("<html><head><title>Employee List</title></head><body>");
                pw.println("<h2>Employee List</h2>");

                pw.println("<table border='1'>");
                pw.println("<tr>");
                pw.println("<th>Employee Id</th>");
                pw.println("<th>Name</th>");
                pw.println("<th>Designation</th>");
                pw.println("<th>Salary</th>");
                pw.println("<th>Edit</th>");
                pw.println("<th>Delete</th>");
                pw.println("</tr>");

                while (rs.next()) {
                    pw.println("<tr>");
                    pw.println("<td>" + rs.getInt("id") + "</td>");
                    pw.println("<td>" + rs.getString("name") + "</td>");
                    pw.println("<td>" + rs.getString("designation") + "</td>");
                    pw.println("<td>" + rs.getFloat("salary") + "</td>");
                    pw.println("<td><a href='editScreen?id=" + rs.getInt("id") + "'>edit</a></td>");
                    pw.println("<td><a href='deleteEmployee?id=" + rs.getInt("id") + "'>delete</a></td>");
                    pw.println("</tr>");
                }

                pw.println("</table>");
                pw.println("<a href='Home.html'>Home</a>");
                pw.println("</body></html>");

            } catch (SQLException se) {
                se.printStackTrace();
                pw.println("<h1>Error: " + se.getMessage() + "</h1>");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            pw.println("<h1>Error: " + e.getMessage() + "</h1>");
        }
    }
}
