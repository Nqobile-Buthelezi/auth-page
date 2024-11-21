package za.co.bangoma.auth.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Manages application configuration properties using a Singleton pattern.
 * Loads and provides access to configuration settings from a properties file.
 * This class is thread-safe and ensures only one instance exists throughout
 * the application lifecycle.
 */
public class ConfigurationManager {

    private static final Logger logger = LogManager.getLogger( ConfigurationManager.class );
    private static final ConfigurationManager INSTANCE = new ConfigurationManager();
    private final Properties properties;

    /**
     * Private constructor that initializes the configuration properties.
     * Follows the Singleton pattern to prevent multiple instances.
     */
    private ConfigurationManager() 
    {
        properties = loadProperties();
    }

    /**
     * Returns the singleton instance of the ConfigurationManager.
     * 
     * @return The singleton instance of ConfigurationManager
     */
    public static ConfigurationManager getInstance() {
        return INSTANCE;
    }

    /**
     * Loads configuration properties from the config.properties file.
     * Throws RuntimeException if the file cannot be found or loaded.
     * 
     * @return Properties object containing the configuration settings
     * @throws RuntimeException if the properties file cannot be found or loaded
     */
    private Properties loadProperties() 
    {
        Properties props = new Properties();
        
        try ( InputStream input = getClass().getClassLoader()
                .getResourceAsStream( "config.properties" ) ) 
        {
            if ( input == null ) 
            {
                logger.error( "Unable to find config.properties" );
                throw new RuntimeException( "Unable to find config.properties" );
            }
            props.load( input );
            logger.info( "Configuration loaded successfully" );
        } 
        catch ( IOException e ) 
        {
            logger.error( "Error loading configuration: {}", e.getMessage(), e );
            throw new RuntimeException( "Error loading configuration", e );
        }

        return props;
    }

    /**
     * Gets the database directory path by combining the user directory
     * with the configured database directory property.
     * 
     * @return The complete path to the database directory
     */
    public String getDatabaseDirectory() 
    {
        return System.getProperty( "user.dir" ) + properties.getProperty( "database.directory" );
    }

    /**
     * Constructs and returns the complete database URL by replacing placeholders
     * with actual values from the configuration.
     * 
     * @return The complete database URL with resolved placeholders
     */
    public String getDatabaseUrl() 
    {
        return properties.getProperty( "database.url" )
                .replace( "${database.directory}", getDatabaseDirectory() )
                .replace( "${database.name}", properties.getProperty( "database.name" ) );
    }

    /**
     * Gets the static files directory path by combining the user directory
     * with the configured static files directory property.
     * 
     * @return The complete path to the static files directory
     */
    public String getStaticFilesDirectory() 
    {
        return System.getProperty( "user.dir" ) + properties.getProperty( "static.files.directory" );
    }

    /**
     * Retrieves a property value by its key.
     * 
     * @param key The property key to look up
     * @return The value associated with the key, or null if not found
     */
    public String getProperty( String key ) 
    {
        return properties.getProperty( key );
    }

    /**
     * Gets the configured port number for the application.
     * 
     * @return The port number as an integer
     * @throws NumberFormatException if the port property cannot be parsed as an integer
     */
    public int getPort() 
    {
        return Integer.parseInt( properties.getProperty( "port" ) );
    }
}
