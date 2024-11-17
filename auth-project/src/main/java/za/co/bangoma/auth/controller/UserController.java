package za.co.bangoma.auth.controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jetbrains.annotations.NotNull;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import za.co.bangoma.auth.infrastructure.Database;
import za.co.bangoma.auth.model.User;
import za.co.bangoma.auth.service.UserService;

import java.sql.Connection;

public class UserController {

    private static final UserService userService = UserService.getInstance();
    private static final UserValidator validator = UserValidator.getInstance();
    private static final UserResponseHandler responseHandler = UserResponseHandler.getInstance();

    public static void deleteAllUserData() 
    {
        try ( Connection connection = Database.getConnection() ) 
        {
            String deleteSQL = "DELETE FROM user_accounts";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("All data deleted successfully, " + rowsDeleted + " rows deleted.");
                } else {
                    System.out.println("There is no data to delete in the user_accounts table.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new BadRequestResponse("Failed to execute the SQL command.");
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            throw new BadRequestResponse("Could not access the database to delete user data.");
        }
    }

    public void create( @NotNull Context ctx ) 
    {
        UserCredentials credentials = userService.extractCredentials( ctx );
        validator.validateCredentials( credentials );
        userService.createUser( credentials );
        responseHandler.handleSuccessfulCreation( ctx );
    }

    public void authenticate( @NotNull Context ctx ) 
    {
        UserCredentials credentials = userService.extractCredentials( ctx );
        validator.validateCredentials( credentials );
        User user = userService.findUser( credentials );
        responseHandler.handleSuccessfulAuthentication( ctx, user );
    }

}
