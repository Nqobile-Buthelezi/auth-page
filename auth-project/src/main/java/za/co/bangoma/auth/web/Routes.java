package za.co.bangoma.auth.web;

import io.javalin.Javalin;
import za.co.bangoma.auth.controller.UserController;

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
