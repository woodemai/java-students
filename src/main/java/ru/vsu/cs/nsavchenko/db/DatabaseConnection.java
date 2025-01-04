package ru.vsu.cs.nsavchenko.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres?user=postgres.pcxtjqpkmnzxcifxtifk&password=wooDe.73java-students";
    private static final String USER = "postgres.pcxtjqpkmnzxcifxtifk";
    private static final String PASSWORD = "wooDe.73java-students";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initializeDatabase() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

            // Выполнение SQL-скрипта
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    DatabaseConnection.class.getResourceAsStream("./init.sql")));
            String line;
            StringBuilder sql = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }
            stmt.execute(sql.toString());
        } catch (Exception e) {
        }
    }
}
