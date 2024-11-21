package za.co.bangoma.auth.infrastructure;

import io.javalin.http.BadRequestResponse;
import za.co.bangoma.auth.model.UserCredentials;

public class UserValidator {

    private static final UserValidator INSTANCE = new UserValidator();

    private UserValidator() {}

    public static UserValidator getInstance() {
        return INSTANCE;
    }

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
