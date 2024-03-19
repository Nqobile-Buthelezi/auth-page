package za.co.bangoma.auth;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class App {

    public static void main( String[] args ) {
        Javalin app = Javalin.create( javalinConfig -> {
            javalinConfig.staticFiles.add("/static", Location.CLASSPATH);
        });

        Routes.configureRoutes(app);
        
        app.start(7070);
    }

}
