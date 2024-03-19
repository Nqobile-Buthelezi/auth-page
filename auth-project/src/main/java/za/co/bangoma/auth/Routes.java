package za.co.bangoma.auth;

import io.javalin.Javalin;

public class Routes {
    
    public static void configureRoutes(Javalin app) {
        app.get("/", ctx -> ctx.redirect("/index.html"));

        app.get("/login", ctx -> ctx.redirect("/login.html"));

        app.get("/signup", ctx -> ctx.redirect("/signup.html"));
    }

}
