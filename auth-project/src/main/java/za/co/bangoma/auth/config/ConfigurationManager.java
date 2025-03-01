package za.co.bangoma.auth.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    private static final ConfigurationManager INSTANCE = new ConfigurationManager();
    private final Properties properties;
    private final ConfigurationLogger configLogger;

    private ConfigurationManager() 
    {
        configLogger = ConfigurationLogger.getInstance();
        properties = loadProperties();
    }

    public static ConfigurationManager getInstance() 
    {
        return INSTANCE;
    }

    private Properties loadProperties() 
    {
        Properties props = new Properties();
        try 
        {
            configLogger.logConfigurationLoadStart();
            props = loadPropertiesFromFile();
            configLogger.logConfigurationLoadSuccess();
        } 
        catch ( IOException e )
        {
            handleConfigurationError( "Error loading configuration", e );
        }
        return props;
    }

    private Properties loadPropertiesFromFile() throws IOException 
    {
        Properties props = new Properties();
        try ( InputStream input = getConfigFileStream() ) 
        {
            validateConfigFile( input );
            props.load( input );
        }
        return props;
    }

    private InputStream getConfigFileStream() 
    {
        return getClass().getClassLoader().getResourceAsStream( "config.properties" );
    }

    private void validateConfigFile( InputStream input ) 
    {
        if ( input == null ) 
        {
            configLogger.logConfigFileNotFound();
            throw new RuntimeException( "Unable to find config.properties" );
        }
    }

    private void handleConfigurationError( String message, Exception e ) 
    {
        configLogger.logConfigurationLoadError( message, e );
        throw new RuntimeException( message, e );
    }

    public String getDatabaseDirectory() 
    {
        String directory = buildPath(
            System.getProperty( "user.dir" ),
            getPropertyWithSeparators( "database.directory" )
        );
        configLogger.logDatabaseDirectoryAccess( directory );
        return directory;
    }

    public String getDatabaseUrl() 
    {
        String url = properties.getProperty( "database.url" )
                .replace( "${database.directory}", getDatabaseDirectory() )
                .replace( "${database.name}", properties.getProperty( "database.name" ) )
                .replace( "${file.separator}", File.separator );
        configLogger.logDatabaseUrlGeneration( url );
        return url;
    }

    public String getStaticFilesDirectory() 
    {
        String directory = buildPath(
            System.getProperty( "user.dir" ),
            getPropertyWithSeparators( "static.files.directory" )
        );
        configLogger.logStaticFilesDirectory();
        return directory;
    }

    private String getPropertyWithSeparators( String propertyKey ) 
    {
        return properties.getProperty( propertyKey )
                .replace( "${file.separator}", File.separator );
    }

    private String buildPath( String basePath, String relativePath ) 
    {
        return basePath + relativePath;
    }

    public String getProperty( String key ) 
    {
        String value = properties.getProperty( key );
        if ( value != null ) 
        {
            configLogger.logPropertyRetrieval( key, value );
        } 
        else 
        {
            configLogger.logPropertyNotFound( key );
        }
        return value;
    }

    public int getPort() 
    {
        try 
        {
            int port = Integer.parseInt( getProperty( "port.development" ) );
            configLogger.logConfigurationComplete( port );
            return port;
        } 
        catch ( NumberFormatException e ) 
        {
            configLogger.logInvalidPortConfiguration( getProperty( "port" ), e );
            throw new NumberFormatException();
        }
    }

    
}
