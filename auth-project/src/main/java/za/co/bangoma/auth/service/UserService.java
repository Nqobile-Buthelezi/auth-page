package za.co.bangoma.auth.service;

import io.javalin.http.Context;
import za.co.bangoma.auth.controller.UserCredentials;
import za.co.bangoma.auth.infrastructure.repository.UserRepository;
import za.co.bangoma.auth.model.User;

public class UserService {

    private final UserRepository userRepository;

    private UserService() {
        this.userRepository = new UserRepository();
    }

    public static UserService getInstance() {
        return new UserService();
    }

    public UserCredentials extractCredentials( Context ctx ) 
    {
        return new UserCredentials(
            ctx.formParam( "username" ),
            ctx.formParam( "password" ),
            ctx.formParam( "email" )
        );
    }

    public void createUser( UserCredentials credentials ) 
    {
        userRepository.createUserTable();
        userRepository.persistUser( credentials );
    }
    
    public User findUser( UserCredentials credentials )
    {
        return userRepository.findUser( credentials );
    }

}