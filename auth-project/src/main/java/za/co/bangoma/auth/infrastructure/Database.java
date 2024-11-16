package za.co.bangoma.auth.infrastructure;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import za.co.bangoma.auth.config.AppConfig;

public class Database {

    private static final String URL = AppConfig.DATABASE_URL;


    public static Connection getConnection() throws SQLException {
        try {
            // Check if the database file exists, create it if it doesn't
            File databaseFile = new File(URL.replace("jdbc:sqlite:", ""));

            if (!databaseFile.exists()) {
                createDatabase(databaseFile);
            }

            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    private static void createDatabase(File databaseFile) {
        try {
            File parentDir = databaseFile.getParentFile();
            if (!parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    throw new IOException("Failed to create parent directory: " + parentDir.getAbsolutePath());
                }
            }
    
            if (databaseFile.createNewFile()) {
                System.out.println("Database file created: " + databaseFile.getAbsolutePath());
            } else {
                throw new RuntimeException("Failed to create the database file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create the database file.");
        }
    }

    public static void createUserTable() {
        try (Connection connection = Database.getConnection()) {
            // Create a table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS user_accounts (\n" +
                    "    id INTEGER PRIMARY KEY,\n" +
                    "    username TEXT UNIQUE,\n" +
                    "    email TEXT UNIQUE,\n" +
                    "    password TEXT\n" +
                    ");\n";
            try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create the table.");
        }
    }
    
}
