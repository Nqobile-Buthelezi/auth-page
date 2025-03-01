package za.co.bangoma.auth.web;

import io.javalin.Javalin;
import za.co.bangoma.auth.controller.UserController;
import org.jetbrains.annotations.NotNull;


public class Routes {

    /**
     * Uses the Utility class design pattern, 
     * should not be instantiated.
     */
    private Routes() 
    {
        throw new AssertionError( "Utility class - should not be instantiated" );
    }

    private static UserController userController = UserController.getInstance();
    
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
        app.get( RoutePath.indexPath, ctx -> ctx.redirect( RoutePage.indexPage ) );
        app.get( RoutePath.loginPath, ctx -> ctx.redirect( RoutePage.loginPage ) );
        app.get( RoutePath.loginFailedPath, ctx -> ctx.redirect( RoutePage.loginFailedPage ) );
        app.get( RoutePath.signupPath, ctx -> ctx.redirect( RoutePage.signupPage ) );
        app.get( RoutePath.signupFailedPath, ctx -> ctx.redirect( RoutePage.signupFailedPage ) );
    }

    private static void configureAPIRoutes( Javalin app ) 
    {
        app.post( RoutePath.signupPath, userController::create );
        app.post( RoutePath.loginPath, userController::authenticate );
        app.post( RoutePath.deletePath, userController::deleteAllUsers );
    }

}
