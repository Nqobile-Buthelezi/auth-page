package za.co.bangoma.auth;

import io.javalin.Javalin;
import org.jetbrains.annotations.NotNull;


public class Routes {
    
    public static void configureRoutes(@NotNull Javalin app) {
        app.get("/", ctx -> ctx.redirect("/index.html"));

        app.get("/login", ctx -> ctx.redirect("/login.html"));

        app.get("/signup", ctx -> ctx.redirect("/signup.html"));

        app.post("/signup", ctx -> UserController.create(ctx));

        app.post("/login", ctx -> UserController.authenticate(ctx));
    }

}
