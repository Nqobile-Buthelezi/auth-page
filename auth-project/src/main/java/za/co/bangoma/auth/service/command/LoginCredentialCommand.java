package za.co.bangoma.auth.service.command;

import io.javalin.http.Context;
import za.co.bangoma.auth.model.LoginCredentials;

public class LoginCredentialCommand implements CredentialCommand {

    private static final LoginCredentialCommand INSTANCE = new LoginCredentialCommand();

    public LoginCredentialCommand() {}
    
    public static LoginCredentialCommand getInstance() {
        return INSTANCE;
    }
    
    @Override
    public LoginCredentials execute(Context ctx) {
        return new LoginCredentials(
            extractUsername( ctx ),
            extractPassword( ctx )
        );
    }

    @Override
    public String extractUsername(Context ctx) 
    {
        return ctx.formParam("username");
    }

    @Override
    public String extractPassword(Context ctx) 
    {
        return ctx.formParam("password");
    }
}