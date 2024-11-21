package za.co.bangoma.auth.model;

import io.javalin.http.BadRequestResponse;

public class LoginCredentials implements UserCredentials {

    private String username;
    private String password;

    public LoginCredentials( String username, String password ) 
    {
        if (username == null || password == null ) 
        {
            throw new BadRequestResponse( "Missing username, password" );
        }
        
        this.username = username;
        this.password = password;
    }

    // Getter/s
    @Override
    public String getUsername() 
    {
        return username;
    }

    @Override
    public String getPassword() 
    {
        return password;
    }

    /**
     * Check if any of the fields are empty
     * @return true if any of the fields are empty, false otherwise.
     */
    @Override
    public boolean hasEmptyFields() 
    {
        return username.isEmpty() || password.isEmpty();
    }

}

