package za.co.bangoma.auth.controller;

import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import za.co.bangoma.auth.infrastructure.UserResponseHandler;
import za.co.bangoma.auth.infrastructure.UserValidator;
import za.co.bangoma.auth.model.CredentialType;
import za.co.bangoma.auth.model.SignUpCredentials;
import za.co.bangoma.auth.model.User;
import za.co.bangoma.auth.model.UserCredentials;
import za.co.bangoma.auth.service.CredentialService;
import za.co.bangoma.auth.service.UserService;

public class UserController {

    private static final UserController INSTANCE = new UserController();

    private static final UserService userService = UserService.getInstance();
    private static final CredentialService credentialService = CredentialService.getInstance();

    private static final UserValidator validator = UserValidator.getInstance();
    private static final UserResponseHandler responseHandler = UserResponseHandler.getInstance();

    private UserController() {}

    public static UserController getInstance() 
    {
        return INSTANCE;
    }

    /**
     * Create a new user
     * @param ctx
     */
    public void create( @NotNull Context ctx ) 
    {
        UserCredentials credentials = credentialService.extractCredentials( ctx, CredentialType.SIGNUP );
        validator.validateCredentials( credentials );
        boolean userHasBeenCreated = userService.createUser( ( SignUpCredentials ) credentials );
        userService.triggerCreationResponse( userHasBeenCreated, ctx );
    }

    /**
     * Authenticate a user
     * @param ctx
     */
    public void authenticate( @NotNull Context ctx ) 
    {
        UserCredentials credentials = credentialService.extractCredentials( ctx, CredentialType.LOGIN );
        validator.validateCredentials( credentials );
        User user = userService.findUser( credentials );
        responseHandler.handleSuccessfulAuthentication( ctx, user );
    }

    /**
     * Delete all users
     * @param ctx
     */
    public void deleteAllUsers( @NotNull Context ctx ) 
    {
        userService.deleteAllUsers();
        responseHandler.handleSuccessfulDeletion( ctx );
    }

}
