package za.co.bangoma.auth.config;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.http.staticfiles.StaticFileConfig;
import za.co.bangoma.auth.web.Routes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AppConfig {

    private static final Logger logger = LogManager.getLogger( AppConfig.class );
    private static final ConfigurationManager config = ConfigurationManager.getInstance();
    
    private static final AppConfig INSTANCE = new AppConfig();
    
    private final String staticFilesDirectory = config.getStaticFilesDirectory();
    private static final Javalin APP = INSTANCE.createApp();
    private final int PORT = config.getPort();

    private AppConfig() {}

    public static AppConfig getInstance()
    {
        return INSTANCE;
    }
    
    /**
     * Creating the app and configuring the routes
     * @return Javalin app
     */
    public Javalin createApp() 
    {
        Javalin app = createJavalinInstance();
        configureRoutes( app );
        logConfigurationComplete();
        return app;
    }

    public void start()
    {
        APP.start( PORT );
    }

    private Javalin createJavalinInstance()
    {
        return Javalin.create( javalinConfig -> {
            javalinConfig.staticFiles.add( staticFiles -> {
                setStaticFilesDirectoryLocation( staticFiles );
                logStaticFilesDirectory();
            });
        });
    }

    private void configureRoutes( Javalin app )
    {
        Routes.configureRoutes( app );
    }

    private void setStaticFilesDirectoryLocation( StaticFileConfig staticFiles ) 
    {
        staticFiles.directory = staticFilesDirectory;
        staticFiles.location = Location.EXTERNAL;
    }

    private void logStaticFilesDirectory()
    {
        logger.info( "Static files directory configured: {}", staticFilesDirectory );
    }

    private void logConfigurationComplete()
    {
        logger.info( "Configuration complete, App ready on port {}", PORT );
    }
    
}
