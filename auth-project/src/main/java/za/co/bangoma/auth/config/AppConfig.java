package za.co.bangoma.auth.config;

import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.staticfiles.Location;
import io.javalin.http.staticfiles.StaticFileConfig;
import za.co.bangoma.auth.web.Routes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * Configuration class for the application that manages static file serving and server initialisation.
 * This class follows the Singleton pattern to ensure only one instance exists throughout the application.
 */
public class AppConfig {

    private static final ConfigurationManager config = ConfigurationManager.getInstance();
    private static final ConfigurationLogger configLogger = ConfigurationLogger.getInstance();
    private static final AppConfig INSTANCE = new AppConfig();

    private final String staticFilesDirectory;
    private final int port;
    private final Javalin app;

    /**
     * Private constructor to prevent direct instantiation.
     * Initialises the static files directory, port, and Javalin application instance.
     */
    private AppConfig() 
    {
        this.staticFilesDirectory = config.getStaticFilesDirectory();
        this.port = config.getPort();
        this.app = initialiseApp();
    }

    /**
     * @return The singleton instance of AppConfig
     */
    public static AppConfig getInstance() 
    {
        return INSTANCE;
    }

    /**
     * Starts the Javalin application server.
     * Configures routes and starts the server on the specified port.
     */
    public void start() 
    {
        Routes.configureRoutes( app );
        app.start( port );
        configLogger.logConfigurationComplete( port );
    }

    /**
     * Initialises the Javalin application with custom configuration.
     *
     * @return Configured Javalin instance
     */
    private Javalin initialiseApp() 
    {
        return Javalin.create( this::configureJavalin );
    }

    /**
     * Configures the Javalin application settings.
     *
     * @param config JavalinConfig instance to be configured
     */
    private void configureJavalin( JavalinConfig config ) 
    {
        configureStaticFiles( config );
    }

    /**
     * Sets up static file serving configuration.
     *
     * @param config JavalinConfig instance to configure static files
     */
    private void configureStaticFiles( JavalinConfig config ) 
    {
        config.staticFiles.add( this::setupStaticFileConfig );
    }

    /**
     * Configures static file serving settings including directory location and validation.
     *
     * @param staticFiles StaticFileConfig instance to be configured
     */
    private void setupStaticFileConfig( StaticFileConfig staticFiles ) 
    {
        validateAndPrepareStaticDirectory();
        setStaticFilesDirectoryLocation( staticFiles );
        configLogger.logStaticFilesDirectory();
    }

    /**
     * Validates and prepares the static files directory.
     * Creates the directory if it doesn't exist and validates access permissions.
     */
    private void validateAndPrepareStaticDirectory() 
    {
        try 
        {
            Path staticPath = getAndValidateStaticPath();
            ensureDirectoryExists( staticPath );
            validateDirectoryAccess( staticPath );
        } 
        catch ( IOException e ) 
        {
            handleDirectorySetupError( e );
        }
    }

    /**
     * Validates the static files path and ensures it's a directory if it exists.
     *
     * @return Path object representing the static files directory
     * @throws IllegalStateException if the path exists but is not a directory
     */
    private Path getAndValidateStaticPath() 
    {
        Path staticPath = Paths.get( staticFilesDirectory );
        if ( Files.exists( staticPath ) && !Files.isDirectory( staticPath ) ) 
        {
            throw new IllegalStateException(
                "Static files path exists but is not a directory: " + staticFilesDirectory
            );
        }
        return staticPath;
    }

    /**
     * Creates the static files directory if it doesn't exist.
     *
     * @param staticPath Path object representing the static files directory
     * @throws IOException if directory creation fails
     */
    private void ensureDirectoryExists( Path staticPath ) throws IOException 
    {
        if ( !Files.exists( staticPath ) ) 
        {
            Files.createDirectories( staticPath );
            configLogger.logCreationOfStaticDirectory();
        }
    }

    /**
     * Validates read access to the static files directory.
     *
     * @param staticPath Path object representing the static files directory
     * @throws IllegalStateException if the directory is not readable
     */
    private void validateDirectoryAccess( Path staticPath ) 
    {
        if ( !Files.isReadable( staticPath ) ) 
        {
            throw new IllegalStateException(
                "Static files directory is not readable: " + staticFilesDirectory
            );
        }
    }

    /**
     * Handles errors that occur during static directory setup.
     *
     * @param e IOException that occurred during directory setup
     * @throws RuntimeException wrapping the original IOException
     */
    private void handleDirectorySetupError( IOException e ) 
    {
        configLogger.logErrorInCreationOfStaticDirectory( e.getMessage() );
        throw new RuntimeException( e.getMessage(), e) ;
    }

    /**
     * Sets the static files directory location and type in the configuration.
     *
     * @param staticFiles StaticFileConfig instance to be configured
     */
    private void setStaticFilesDirectoryLocation( StaticFileConfig staticFiles ) 
    {
        staticFiles.directory = staticFilesDirectory;
        staticFiles.location = Location.EXTERNAL;
    }
}
