package za.co.bangoma.auth.model;

import io.javalin.http.BadRequestResponse;

public class SignUpCredentials implements UserCredentials {

    private String username;
    private String password;
    private String email;

    public SignUpCredentials(String username, String password, String email) {
        if (username == null || password == null || email == null) {
            throw new BadRequestResponse("Missing username, password, or email");
        }
        
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Check if any of the fields are empty
     * @return true if any of the fields are empty, false otherwise.
     */
    @Override
    public boolean hasEmptyFields() {
        return username.isEmpty() || password.isEmpty() || email.isEmpty();
    }

    // Getter/s
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    
}

