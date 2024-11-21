package za.co.bangoma.auth.service.command;

import io.javalin.http.Context;
import za.co.bangoma.auth.model.SignUpCredentials;

public class SignupCredentialCommand implements CredentialCommand {

    private static final SignupCredentialCommand INSTANCE = new SignupCredentialCommand();

    private SignupCredentialCommand() {}

    public static SignupCredentialCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public SignUpCredentials execute(Context ctx) {
        return new SignUpCredentials(
                    extractUsername(ctx),
                    extractPassword(ctx),
                    extractEmail(ctx)
                 );
    }
    
    /**
     * Extracts username from context
     * @param ctx The context containing form parameters
     * @return username string
     */
    @Override
    public String extractUsername(Context ctx) {
        return ctx.formParam("username");
    }

    /**
     * Extracts password from context
     * @param ctx The context containing form parameters
     * @return password string
     */
    @Override
    public String extractPassword(Context ctx) {
        return ctx.formParam("password");
    }

    /**
     * Extracts email from context
     * @param ctx The context containing form parameters
     * @return email string
     */
    public String extractEmail(Context ctx) {
        return ctx.formParam("email");
    }

}
