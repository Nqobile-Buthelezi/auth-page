package za.co.bangoma.auth.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.javalin.http.Context;
import za.co.bangoma.auth.model.User;

public class UserResponseHandler {

    private static final Logger logger = LogManager.getLogger( UserResponseHandler.class );

    private UserResponseHandler() {}

    public static UserResponseHandler getInstance() {
        return new UserResponseHandler();
    }

    public void handleSuccessfulCreation(Context ctx) {
        logger.info("User created successfully!");
        ctx.status(201);
        ctx.redirect("/login");
    }

    public void handleSuccessfulAuthentication( Context ctx, User user ) {
        logger.info("User authenticated successfully!");
        ctx.status(200);
        ctx.redirect("/");
    }

}
