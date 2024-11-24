package za.co.bangoma.auth.web;

import io.javalin.Javalin;
import za.co.bangoma.auth.config.ConfigurationManager;
import za.co.bangoma.auth.controller.UserController;
import org.jetbrains.annotations.NotNull;


public class Routes {

    private static UserController userController = UserController.getInstance();
    private static ConfigurationManager config = ConfigurationManager.getInstance();

    // Paths
    private static String INDEX_PATH = config.getProperty( "index.path" );
    private static String LOGIN_PATH = config.getProperty( "login.path" );
    
    /**
     * Configure the routes for the application
     * @param app
     */
    public static void configureRoutes( @NotNull Javalin app ) 
    {
        configurePages( app );
        configureAPIRoutes( app );
    }

    private static void configurePages( Javalin app ) 
    {
        app.get( INDEX_PATH, ctx -> ctx.redirect( "/index.html" ) );
        app.get( "/login", ctx -> ctx.redirect( "/login.html" ) );
        app.get( "/signup", ctx -> ctx.redirect( "/signup.html" ) );
        app.get( "/signup-failed", ctx -> ctx.redirect( "/signup-failed.html") );
    }

    private static void configureAPIRoutes( Javalin app ) 
    {
        app.post( "/signup", userController::create );
        app.post( "/login", userController::authenticate );
        app.post( "/delete", userController::deleteAllUsers );
    }

}
