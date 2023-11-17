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

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String query = "SELECT name, designation, salary FROM employee WHERE id = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");

        int id = Integer.parseInt(req.getParameter("id"));

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/employees", "tada", "tadael")) {

                // Prepare and execute the SQL query
                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();

                    // Display the form for editing
                    if (rs.next()) {
                        pw.println("<form action='editEmployee?id=" + id + "' method='post'>");
                        pw.println("<table>");
                        pw.println("<tr>");
                        pw.println("<td>Employee Name</td>");
                        pw.println("<td><input type='text' name='employeeName' value='" + rs.getString(1) + "'></td>");
                        pw.println("</tr>");
                        pw.println("<tr>");
                        pw.println("<td>Designation</td>");
                        pw.println("<td><input type='text' name='designation' value='" + rs.getString(2) + "'></td>");
                        pw.println("</tr>");
                        pw.println("<tr>");
                        pw.println("<td>Salary</td>");
                        pw.println("<td><input type='text' name='salary' value='" + rs.getFloat(3) + "'></td>");
                        pw.println("</tr>");
                        pw.println("<tr>");
                        pw.println("<td><input type='submit' value='Edit'></td>");
                        pw.println("<td><input type='reset' value='Cancel'></td>");
                        pw.println("</tr>");
                        pw.println("</table>");
                        pw.println("</form>");
                    } else {
                        pw.println("<h2>Employee not found for ID: " + id + "</h2>");
                    }
                }
            } catch (SQLException se) {
                se.printStackTrace();
                pw.println("<h1>" + se.getMessage() + "</h1>");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }

        pw.println("<a href='Home.html'>Home</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Handle form submission if needed
        // For example, update the employee details in the database
        // and then redirect to the employee list page
        resp.sendRedirect("employeeList");
    }
}
