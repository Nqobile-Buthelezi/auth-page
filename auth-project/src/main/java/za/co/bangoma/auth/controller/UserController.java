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

    private final UserService userService;
    private final CredentialService credentialService;
    private final UserValidator validator;
    private final UserResponseHandler responseHandler;

    public UserController( UserService userService, 
                    CredentialService credentialService, 
                    UserValidator validator, 
                    UserResponseHandler responseHandler ) 
    {
        this.userService = userService;
        this.credentialService = credentialService;
        this.validator = validator;
        this.responseHandler = responseHandler;
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
        assert user != null;
        responseHandler.handleSuccessfulAuthentication( ctx );
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
