package za.co.bangoma.auth;

import za.co.bangoma.auth.config.AppConfig;
import za.co.bangoma.auth.infrastructure.Environment;

/**
 * Main application class that serves as the entry point 
 * for the authentication web application service. This class
 * initialises and starts the application configuration.
 */
public class App {

    /**
     * The main method is the entry point of the application.
     * It creates an instance of the AppConfig class and starts the 
     * web application by calling the start() method.
     * @param args Command line arguments (not used in this application)
     */
    public static void main( String[] args ) 
    {
        AppConfig.getInstance( Environment.DEVELOPMENT ).start();
    }

}
