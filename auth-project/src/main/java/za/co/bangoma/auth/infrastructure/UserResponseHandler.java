package za.co.bangoma.auth.infrastructure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.javalin.http.Context;
import za.co.bangoma.auth.model.User;

public class UserResponseHandler {

    private static final Logger logger = LogManager.getLogger( UserResponseHandler.class );
    private static final UserResponseHandler INSTANCE = new UserResponseHandler();

    private UserResponseHandler() {}

    public static UserResponseHandler getInstance() {
        return INSTANCE;
    }

    public void handleSuccessfulCreation( Context ctx ) {
        logger.info( "User created successfully!" );
        ctx.status( 201 );
        ctx.redirect( "/login" );
    }

    public void handleUnsuccessfulCreation( Context ctx ) {
        logger.info( "User creation failed!" );
        ctx.status( 400 );
        ctx.redirect( "/signup-failed" );
    }

    public void handleSuccessfulAuthentication( Context ctx, User user ) {
        logger.info( "User authenticated successfully!" );
        ctx.status( 200 );
        ctx.redirect( "/" );
    }

    public void handleSuccessfulDeletion( Context ctx ) {
        logger.info( "User data deleted successfully!" );
        ctx.status( 200 );
        ctx.redirect( "/" );
    }

}
