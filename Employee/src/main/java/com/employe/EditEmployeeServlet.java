package com.employe;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editEmployee")  // Updated servlet mapping
public class EditEmployeeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String query = "UPDATE employee SET name=?, designation=?, salary=? WHERE id=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");

        String idParam = req.getParameter("id");
        int id = 0;  // Default value or handle it accordingly
        if (idParam != null && !idParam.isEmpty()) {
            try {
                id = Integer.parseInt(idParam);
            } catch (NumberFormatException e) {
                // Handle the exception or provide a default value
            }
        }

        String employeeName = req.getParameter("employeeName");
        employeeName = (employeeName != null) ? employeeName : "";

        String designation = req.getParameter("designation");
        designation = (designation != null) ? designation : "";

        String salaryStr = req.getParameter("salary");
        float salary = 0.0f;  // Default value or handle it accordingly
        if (salaryStr != null && !salaryStr.isEmpty()) {
            try {
                salary = Float.parseFloat(salaryStr);
            } catch (NumberFormatException e) {
                // Handle the exception or provide a default value
            }
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "tada", "tadael")) {

            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, employeeName);
                ps.setString(2, designation);
                ps.setFloat(3, salary);
                ps.setInt(4, id);

                int count = ps.executeUpdate();

                if (count == 1) {
                    pw.println("<h2>Record is edited successfully.</h2>");
                } else {
                    pw.println("<h2>Record not edited.</h2>");
                }

            } catch (SQLException se) {
                se.printStackTrace();
                pw.println("<h1>" + se.getMessage() + "</h1>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        } catch (Exception ex) {
            ex.printStackTrace();
            pw.println("<h1>" + ex.getMessage() + "</h1>");
        }

        pw.println("<a href='Home.html'>Home</a>");
        pw.print("<br>");
        pw.println("<a href='employeelist'>Employee List</a>");  // Updated hyperlink
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
