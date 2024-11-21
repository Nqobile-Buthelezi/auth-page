package za.co.bangoma.auth.service;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import za.co.bangoma.auth.exceptions.DatabaseException;
import za.co.bangoma.auth.infrastructure.Database;
import za.co.bangoma.auth.infrastructure.UserResponseHandler;
import za.co.bangoma.auth.model.SignUpCredentials;
import za.co.bangoma.auth.model.User;
import za.co.bangoma.auth.model.UserCredentials;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {

    private final Database database;
    private static final Logger logger = LogManager.getLogger( UserService.class );
    private static final UserService INSTANCE = new UserService();
    private static final UserResponseHandler responseHandler = UserResponseHandler.getInstance();

    private UserService() 
    {
        this.database = initialiseDatabase();
    }

    public static UserService getInstance() 
    {
        return INSTANCE;
    }

    /**
     * Initialises the database connection
     * @return Database instance
     */
    private Database initialiseDatabase() 
    {
        logger.info( "Initialising database connection" );
        Database db = Database.getInstance();
        logger.info( "Database connection initialised successfully" );
        return db;
    }

    /**
     * Triggers the authentication response based off of wether or
     * not the user had been created or not
     * @param userHasBeenCreated boolean indicating if user was created or not
     * @param ctx The context containing form parameters
     */
    public void triggerCreationResponse( boolean userHasBeenCreated, Context ctx ) 
    {
        if ( userHasBeenCreated ) 
        {
            responseHandler.handleSuccessfulCreation( ctx );
        } 
        else
        {
            responseHandler.handleUnsuccessfulCreation( ctx );
        } 
    }

    /**
     * Creates a new user in the database
     * @param credentials User signup credentials
     * @return boolean indicating if user creation was successful
     */
    public boolean createUser(SignUpCredentials credentials) {
        ensureTableExists();
        return addNewUser( credentials );
    }

    /**
     * Ensures the user table exists in the database
     */
    private void ensureTableExists() {
        database.createUserTable();
    }

    /**
     * Attempts to add a new user to the database
     * @param credentials User signup credentials
     * @return boolean indicating if user was successfully added
     */
    private boolean addNewUser( SignUpCredentials credentials ) {
        String username = credentials.getUsername();
        logAttemptingUserCreation( username );
        
        boolean isUserAdded = database.persistUser( credentials );
        logUserCreationResult( isUserAdded, username );
        
        return isUserAdded;
    }

    /**
     * Logs user creation attempt
     * @param username The username being created
     */
    private void logAttemptingUserCreation(String username) {
        logger.info("Attempting to create new user: {}", username);
    }

    /**
     * Logs the result of user creation
     * @param success Whether the creation was successful
     * @param username The username that was attempted
     */
    private void logUserCreationResult( boolean success, String username ) {
        if ( success ) 
        {
            logger.info( "User created successfully: {}", username );
        } 
        else 
        {
            logger.warn( "Failed to create user - username already exists: {}", username );
        }
    }

    /**
     * Finds a user in the database
     * @param credentials User credentials to search for
     * @return User object if found, null otherwise
     */
    public User findUser( UserCredentials credentials ) {
        String username = credentials.getUsername();
        logSearchAttempt( username );
        
        User user = searchDatabase( credentials );
        logSearchResult( user, username );
        
        return user;
    }

    /**
     * Searches the database for a user
     * @param credentials User credentials to search for
     * @return User object if found, null otherwise
     */
    private User searchDatabase( UserCredentials credentials ) {
        return database.findUser(credentials);
    }

    /**
     * Logs the initial search attempt
     * @param username Username being searched
     */
    private void logSearchAttempt(String username) {
        logger.info("Searching for user: {}", username);
    }

    /**
     * Logs the result of the user search
     * @param user Found user object or null
     * @param username Username that was searched
     */
    private void logSearchResult(User user, String username) {
        Level logLevel = determineLogLevel( user );
        String foundStatus = determineFoundStatus(user);
        
        logger.log(logLevel, "User {} found: {}", foundStatus, username);
    }

    /**
     * Determines the appropriate log level based on search result
     * @param user Found user object or null
     * @return Appropriate logging level
     */
    private Level determineLogLevel( User user ) {
        return user != null ? Level.INFO : Level.WARN;
    }

    /**
     * Determines the found status message
     * @param user Found user object or null
     * @return Status message
     */
    private String determineFoundStatus( User user ) {
        return user == null ? "not" : "";
    }

    /**
     * Logs the result of the deletion of all users
     * @param rowsDeleted
     */
    private void logDeletionResult( int rowsDeleted ) 
    {
        if ( rowsDeleted > 0 ) 
        {
            logger.info( "All data deleted successfully, {} rows deleted", rowsDeleted );
        } 
        else 
        {
            logger.warn( "No data to delete in the user_accounts table" );
        }
    }

    /**
     * Handles the error that occurs when deleting all users
     * @param e
     */
    private void handleDeletionError( DatabaseException e ) 
    {
        logger.error( "Failed to delete user data: {}", e.getMessage(), e );
        throw new BadRequestResponse( "Failed to delete user data: " + e.getMessage() );
    }

    /**
     * Deletes all users from the database
     */
    public void deleteAllUsers() 
    {
        logger.info( "Attempting to delete all users" );
        try {
            int rowsDeleted = database.deleteAllUserRecords();
            logDeletionResult( rowsDeleted );
        } catch ( DatabaseException e ) {
            handleDeletionError( e );
        }
    }

}
