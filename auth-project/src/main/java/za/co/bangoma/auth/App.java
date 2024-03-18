package za.co.bangoma.auth;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class App {

    public static void main( String[] args ) {
        Javalin app = Javalin.create( javalinConfig -> {
            javalinConfig.staticFiles.add("/static", Location.CLASSPATH);
        })
                .get("/", ctx -> ctx.redirect("/hello.html"))
                .start(7070);
    }

}
