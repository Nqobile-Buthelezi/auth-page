package za.co.bangoma.auth.service.command;

import io.javalin.http.Context;
import za.co.bangoma.auth.model.UserCredentials;

public interface CredentialCommand {

    UserCredentials execute( Context ctx );
    String extractUsername( Context ctx );
    String extractPassword( Context ctx );

}
