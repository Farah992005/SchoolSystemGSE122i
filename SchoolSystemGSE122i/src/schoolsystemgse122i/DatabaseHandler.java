package schoolsystemgse122i;

import java.sql.*;

public class DatabaseHandler {
    private static final String URL = "jdbc:mysql://localhost:3306/type school_dp";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection conn;

    public DatabaseHandler() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
            throw new RuntimeException("Driver initialization failed.");
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            throw new RuntimeException("Database initialization failed.");
        }
    }

    public void saveStudent(String name, String studentClass, int rollNo, double feePaid, String date) throws SQLException {
        String query = "INSERT INTO student (name, studentClass, rollNo, feePaid, date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, studentClass);
            stmt.setInt(3, rollNo);
            stmt.setDouble(4, feePaid);
            stmt.setString(5, date);
            stmt.executeUpdate();
        }
    }

    public void saveTeacher(String name, String position, double salary, String date) throws SQLException {
        String query = "INSERT INTO teacher (name, position, salary, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, position);
            stmt.setDouble(3, salary);
            stmt.setString(4, date);
            stmt.executeUpdate();
        }
    }

    public ResultSet getStudents() throws SQLException {
        String query = "SELECT * FROM student";
        return conn.createStatement().executeQuery(query);
    }

    public ResultSet getTeachers() throws SQLException {
        String query = "SELECT * FROM teacher";
        return conn.createStatement().executeQuery(query);
    }

    public void updateStudent(int rollNo, String name, String studentClass, double feePaid, String date) throws SQLException {
        String query = "UPDATE student SET name = ?, studentClass = ?, feePaid = ?, date = ? WHERE rollNo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, studentClass);
            stmt.setDouble(3, feePaid);
            stmt.setString(4, date);
            stmt.setInt(5, rollNo);
            stmt.executeUpdate();
        }
    }

    public void updateTeacher(String name, String position, double salary, String date) throws SQLException {
        String query = "UPDATE teacher SET position = ?, salary = ?, date = ? WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, position);
            stmt.setDouble(2, salary);
            stmt.setString(3, date);
            stmt.setString(4, name);
            stmt.executeUpdate();
        }
    }

    public void deleteStudent(int rollNo) throws SQLException {
        String query = "DELETE FROM student WHERE rollNo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rollNo);
            stmt.executeUpdate();
        }
    }

    public void deleteTeacher(String name) throws SQLException {
        String query = "DELETE FROM teacher WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }

    public ResultSet searchStudent(int rollNo) throws SQLException {
        String query = "SELECT * FROM student WHERE rollNo = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, rollNo);
        return stmt.executeQuery();
    }

    public ResultSet searchTeacher(String name) throws SQLException {
        String query = "SELECT * FROM teacher WHERE name = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, name);
        return stmt.executeQuery();
    }
}