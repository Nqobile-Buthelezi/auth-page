package za.co.bangoma.auth.infrastructure;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import za.co.bangoma.auth.config.ConfigurationManager;
import za.co.bangoma.auth.exceptions.DatabaseException;
import za.co.bangoma.auth.model.SignUpCredentials;
import za.co.bangoma.auth.model.User;
import za.co.bangoma.auth.model.UserCredentials;

public class Database {
    
    private static Database instance;
    private static Connection connection;
    private static ConfigurationManager configurationManager = ConfigurationManager.getInstance();
    private static final String DATABASE_URL = configurationManager.getDatabaseUrl();

    // Private constructor to prevent direct instantiation
    private Database() {
        initialiseConnection();
    }

    // Singleton getInstance method
    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Initialises the connection to the database
     */
    private void initialiseConnection() {
        try 
        {
            // !Check!
            File databaseFile = new File( DATABASE_URL.replace("jdbc:sqlite:", ""));

            if ( !databaseFile.exists() ) 
            {
                createDatabase( databaseFile );
            }

            if ( connection == null || connection.isClosed() ) 
            {
                connection = DriverManager.getConnection( DATABASE_URL );
            }
        }
        catch ( SQLException e ) 
        {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    /**
     * Gets the databases sole connection.
     * @return 
     */
    public Connection getConnection() {
        try {
            // For the purposes of this project this...
            // database class will have only one connections available
            if (connection == null || connection.isClosed()) {
                initialiseConnection();
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get database connection.");
        }
    }

    /**
     * Creates the database file
     * @param databaseFile
     */
    private static void createDatabase( File databaseFile ) {
        try {
            File parentDir = databaseFile.getParentFile();

            if ( !parentDir.exists() ) {
                if ( !parentDir.mkdirs() ) {
                    throw new IOException( "Failed to create parent directory: " + parentDir.getAbsolutePath() );
                }
            }
    
            if ( databaseFile.createNewFile() ) {
                System.out.println( "Database file created: " + databaseFile.getAbsolutePath() );
            } else {
                throw new RuntimeException( "Failed to create the database file." );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create the database file.");
        }
    }

    /**
     * Creates the user table in the database
     */
    public void createUserTable() {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "CREATE TABLE IF NOT EXISTS user_accounts (\n" +
                "    id INTEGER PRIMARY KEY,\n" +
                "    username TEXT UNIQUE,\n" +
                "    email TEXT UNIQUE,\n" +
                "    password TEXT\n" +
                ");\n")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create the table.");
        }
    }

    /**
     * Extracts a user from the result set
     * @param resultSet
     * @return
     * @throws SQLException
     */
    public static User extractUser( ResultSet resultSet ) throws SQLException {
        if (resultSet.next()) {
            return new User(
                resultSet.getString( "username"),
                resultSet.getString( "email"),
                resultSet.getString( "password")
            );
        }
        return null;
    }

    /**
     * Finds a user in the database
     * @param credentials
     * @return
     */
    public User findUser( UserCredentials credentials ) {
        String query = "SELECT * FROM user_accounts WHERE username = ? AND password = ?";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement( query )) {
            preparedStatement.setString(1, credentials.getUsername());
            preparedStatement.setString(2, credentials.getPassword());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return extractUser( resultSet );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to find user: " + e.getMessage());
        }
    }

    /**
     * Deletes all user records from the database
     * @return
     * @throws DatabaseException
     */
    public int deleteAllUserRecords() throws DatabaseException {
        String query = "DELETE FROM user_accounts";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement( query )) {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Failed to delete user data: " + e.getMessage());
        }
    }

    /**
     * Persists a user to the database
     * @param credentials
     * @return true if the user was successfully persisted, false otherwise
     */
    public boolean persistUser( SignUpCredentials credentials ) 
    {
        String checkUserQuery = "SELECT COUNT(*) FROM user_accounts WHERE username = ? OR email = ?";
        String insertUserQuery = "INSERT INTO user_accounts (username, email, password) VALUES (?, ?, ?)";

        try (PreparedStatement checkStatement = getConnection().prepareStatement( checkUserQuery )) 
        {
            // First check if user already exists
            checkStatement.setString( 1, credentials.getUsername() );
            checkStatement.setString( 2, credentials.getEmail() );

            try ( ResultSet resultSet = checkStatement.executeQuery() ) 
            {
                if ( resultSet.next() && resultSet.getInt(1) > 0 ) 
                {
                    // User already exists
                    return false;
                }
            }

            // If we get here, user doesn't exist, so this inserts them into the database
            try ( PreparedStatement insertStatement = getConnection().prepareStatement( insertUserQuery ) ) 
            {
                insertStatement.setString( 1, credentials.getUsername() );
                insertStatement.setString( 2, credentials.getEmail() );
                insertStatement.setString( 3, credentials.getPassword() );

                int rowsAffected = insertStatement.executeUpdate();
                return rowsAffected > 0; // Because one is greater than zero
            }
        } 
        catch ( SQLException e ) 
        {
            e.printStackTrace();
            throw new RuntimeException("Failed to persist user: " + e.getMessage());
        }
    }

    // Prevent cloning of the instance
    @Override
    protected Object clone() throws CloneNotSupportedException 
    {
        throw new CloneNotSupportedException( "Cloning of this class is not allowed" );
    }
}
