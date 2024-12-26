package ru.vsu.cs.nsavchenko.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.vsu.cs.nsavchenko.db.DatabaseConnection;
import ru.vsu.cs.nsavchenko.model.Task;

public class TaskDAO {
    public List<Task> getTasksByStudentId(Long studentId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE student_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Task task = new Task();
                    task.setId(rs.getLong("id"));
                    task.setStudentId(rs.getLong("student_id"));
                    task.setTaskNumber(rs.getInt("task_number"));
                    task.setCompleted(rs.getBoolean("is_completed"));
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    public void updateTaskStatus(Long taskId, boolean completed) throws SQLException {
        String sql = "UPDATE tasks SET is_completed = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBoolean(1, completed);
            stmt.setLong(2, taskId);
            stmt.executeUpdate();
        }
    }
} 