package ru.vsu.cs.nsavchenko;

import ru.vsu.cs.nsavchenko.db.DatabaseConnection;

public class App {

    public static void main(String[] args) throws ClassNotFoundException {
        DatabaseConnection.initializeDatabase();
    }
}
