package za.co.bangoma.auth.web;

import io.javalin.Javalin;
import za.co.bangoma.auth.controller.UserController;
import org.jetbrains.annotations.NotNull;


public class Routes {

    private static UserController userController = UserController.getInstance();
    
    /**
     * Configure the routes for the application
     * @param app
     */
    public static void configureRoutes( @NotNull Javalin app ) 
    {
        app.get( "/", ctx -> ctx.redirect( "/index.html" ) );

        app.get( "/login", ctx -> ctx.redirect( "/login.html" ) );

        app.get( "/signup", ctx -> ctx.redirect( "/signup.html" ) );

        app.get( "/signup-failed", ctx -> ctx.redirect( "/signup-failed.html") );

        app.post( "/signup", userController::create );

        app.post( "/login", userController::authenticate );

        app.post( "/delete", userController::deleteAllUsers );
    }

}
