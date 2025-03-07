package za.co.bangoma.auth.model;


public class User {
    
    public String username;
    public String password;
    public String email;

    public User(String username, String password, String email) {
        if (username == null || password == null || email == null) {
            throw new IllegalArgumentException("Missing username, password, or email");
        }

        this.username = username;
        this.password = password;
        this.email = email;
    }

}
