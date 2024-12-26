package ru.vsu.cs.nsavchenko.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.vsu.cs.nsavchenko.db.DatabaseConnection;
import ru.vsu.cs.nsavchenko.model.Group;

public class GroupDAO {
    public List<Group> getAllGroups() throws SQLException {
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT * FROM groups";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getLong("id"));
                group.setGroupName(rs.getString("group_name"));
                groups.add(group);
            }
        }
        return groups;
    }
    
    // Добавьте методы для создания, обновления и удаления групп
} 