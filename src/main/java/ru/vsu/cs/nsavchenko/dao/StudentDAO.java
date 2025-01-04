package ru.vsu.cs.nsavchenko.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.vsu.cs.nsavchenko.db.DatabaseConnection;
import ru.vsu.cs.nsavchenko.model.Student;

public class StudentDAO {

    public List<Student> getAllStudents(Long groupId) throws SQLException, ClassNotFoundException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE group_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement((sql));
            stmt.setLong(1, groupId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getLong("id"));
                    student.setFirstName(rs.getString("first_name"));
                    student.setLastName(rs.getString("last_name"));
                    student.setGroupId(rs.getLong("group_id"));
                    students.add(student);
                }
            } catch (Exception e) {
            }

        }
        return students;
    }

    public Student getStudentById(Long id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getLong("id"));
                    student.setFirstName(rs.getString("first_name"));
                    student.setLastName(rs.getString("last_name"));
                    student.setGroupId(rs.getLong("group_id"));
                    return student;
                }
            }
        }
        return null;
    }

    public void addStudent(Student student) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setLong(3, student.getGroupId());
            stmt.executeUpdate();
        }
    }

}
