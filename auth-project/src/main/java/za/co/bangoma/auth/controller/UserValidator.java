package za.co.bangoma.auth.controller;

import io.javalin.http.BadRequestResponse;

public class UserValidator {

    private UserValidator() {}

    public static UserValidator getInstance() {
        return new UserValidator();
    }

    public void validateCredentials(UserCredentials credentials) {
        if (credentials.hasEmptyFields()) {
            throw new BadRequestResponse("Missing username, password, or email");
        }
    }

}
