package za.co.bangoma.auth.web;

import za.co.bangoma.auth.config.ConfigurationManager;

public class RoutePath {

    private static ConfigurationManager config = ConfigurationManager.getInstance();

    private RoutePath() 
    {
        throw new AssertionError( "Utility class - should not be instantiated" );
    }

    // Paths
    protected static String indexPath = config.getProperty( "index.path" );
    protected static String loginPath = config.getProperty( "login.path" );
    protected static String loginFailedPath = config.getProperty( "login-failed.path" );
    protected static String signupPath = config.getProperty( "signup.path" );
    protected static String signupFailedPath = config.getProperty( "signup-failed.path" );
    protected static String deletePath = config.getProperty( "delete.path" );

}
