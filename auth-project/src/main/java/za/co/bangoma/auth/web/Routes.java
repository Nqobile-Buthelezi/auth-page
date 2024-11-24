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
        configurePages( app );
        configureAPIRoutes( app );
    }

    private static void configurePages( Javalin app ) 
    {
        app.get( RoutePath.INDEX_PATH, ctx -> ctx.redirect( RoutePage.INDEX_PAGE ) );
        app.get( RoutePath.LOGIN_PATH, ctx -> ctx.redirect( RoutePage.LOGIN_PAGE ) );
        app.get( RoutePath.LOGIN_FAILED_PATH, ctx -> ctx.redirect( RoutePage.LOGIN_FAILED_PAGE ) );
        app.get( RoutePath.SIGNUP_PATH, ctx -> ctx.redirect( RoutePage.SIGNUP_PAGE ) );
        app.get( RoutePath.SIGNUP_FAILED_PATH, ctx -> ctx.redirect( RoutePage.SIGNUP_FAILED_PAGE ) );
    }

    private static void configureAPIRoutes( Javalin app ) 
    {
        app.post( RoutePath.SIGNUP_PATH, userController::create );
        app.post( RoutePath.LOGIN_PATH, userController::authenticate );
        app.post( RoutePath.DELETE_PATH, userController::deleteAllUsers );
    }

}
