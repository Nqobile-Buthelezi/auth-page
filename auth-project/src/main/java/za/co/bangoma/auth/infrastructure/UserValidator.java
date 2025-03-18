package za.co.bangoma.auth.infrastructure;

import io.javalin.http.BadRequestResponse;
import za.co.bangoma.auth.model.UserCredentials;

public class UserValidator {

    /**
     * Validates the user credentials to ensure that they are not empty
     * @param credentials
     */
    public void validateCredentials( UserCredentials credentials ) {
        if ( credentials.hasEmptyFields() ) {
            throw new BadRequestResponse("Missing username, password, or email");
        }
    }

}
