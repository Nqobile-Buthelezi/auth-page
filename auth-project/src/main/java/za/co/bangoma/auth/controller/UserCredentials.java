package za.co.bangoma.auth.controller;

import io.javalin.http.BadRequestResponse;

public class UserCredentials {

    private String username;
    private String password;
    private String email;

    public UserCredentials(String username, String password, String email) {
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
    public boolean hasEmptyFields() {
        if(username.isEmpty() || password.isEmpty() || email.isEmpty())
        {
            return true;
        } 
        else
        {
            return false;
        }
    }

    // Getter/s
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
