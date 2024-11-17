package za.co.bangoma.auth.infrastructure.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import io.javalin.http.BadRequestResponse;
import za.co.bangoma.auth.controller.UserCredentials;
import za.co.bangoma.auth.infrastructure.Database;
import za.co.bangoma.auth.model.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserRepository {

    private static final Logger logger = LogManager.getLogger( UserRepository.class );

    /**
     * A method to create the user table in the database
     */
    public void createUserTable() 
    {
        Database.createUserTable();
    }

    /**
     * A method to persist a user to the database
     * @param credentials
     */
    public void persistUser( UserCredentials credentials ) 
    {
        try ( Connection conn = Database.getConnection() ) 
        {
            executeInsert( conn, credentials );
        } 
        catch ( SQLException e ) 
        {
            handleDatabaseError( e );
        }
    }

    public User findUser( UserCredentials credentials )
    {
        try ( Connection conn = Database.getConnection() )
        {
            return executeSelect(conn, credentials);
        }
        catch ( SQLException e )
        {
            handleDatabaseError( e );
        }
        return null;
    }

    private User executeSelect(Connection conn, UserCredentials credentials) throws SQLException
    {
        String selectSQL = "SELECT * FROM user_accounts WHERE username = ? AND password = ?";
        try ( PreparedStatement preparedStatement = conn.prepareStatement( selectSQL ) )
        {
            preparedStatement.setString(1, credentials.getUsername());
            preparedStatement.setString(2, credentials.getPassword());

            return Database.extractUser( preparedStatement.executeQuery() );
        }
    }

    /**
     * A method to execute the insert statement for a user
     * @param conn
     * @param credentials
     * @throws SQLException
     */
    private void executeInsert( Connection conn, UserCredentials credentials ) throws SQLException 
    {
        String insertSQL = "INSERT INTO user_accounts (username, email, password) VALUES (?, ?, ?)";
        try ( PreparedStatement preparedStatement = conn.prepareStatement( insertSQL ) ) 
        {
            preparedStatement.setString( 1, credentials.getUsername() );
            preparedStatement.setString( 2, credentials.getEmail() );
            preparedStatement.setString( 3, credentials.getPassword() );
            preparedStatement.executeUpdate();
        }
    }

    /**
     * A method to handle database errors
     * @param e
     */
    private void handleDatabaseError( SQLException e ) 
    {
        
        String errorMessage;

        switch ( e.getSQLState() )
        {
            case "23505": // This is a unique violation, as the user already exists
                errorMessage = "Username or email already exists";
                break;
            case "23502":
                errorMessage = "Missing username, password, or email";
                break;
            case "08006":
                errorMessage = "Failed to connect to database, unexpected error please try again later.";
                break;
            case "42P01":
                errorMessage = "Failed to create user, table does not exist, unexpected schema error.";
                break;
            default:
                errorMessage = "Failed to create user, an unexpected error occurred during user creation";
                break;
        }

        logger.error(
            "Database error during user creation: {} - {}",
            errorMessage,
            e.getMessage(),
            e
        );

        throw new BadRequestResponse( errorMessage );
    }
    
}