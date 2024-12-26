package ru.vsu.cs.nsavchenko.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "postgresql://postgres:wooDe.73java-students@db.pcxtjqpkmnzxcifxtifk.supabase.co:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "wooDe.73java-students";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
