package za.co.bangoma.auth.config;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import za.co.bangoma.auth.web.Routes;


public class AppConfig {

    // Creating our Javalin app and configuring the routes
    public static Javalin createApp() {
        Javalin app = Javalin.create( JavalinConfiguration -> 
            JavalinConfiguration.staticFiles.add("/static", Location.CLASSPATH)
        );

        Routes.configureRoutes(app);

        return app;
    }

    // Getting the correct path
    static String dataBaseDirectory = System.getProperty("user.dir") + "/auth-project/src/main/java/za/co/bangoma/auth/db";

    // SQLite database URL
    public static final String DATABASE_URL = "jdbc:sqlite:" + dataBaseDirectory + "/database.db";
}
