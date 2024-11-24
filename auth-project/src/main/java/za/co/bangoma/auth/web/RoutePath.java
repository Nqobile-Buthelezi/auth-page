package za.co.bangoma.auth.web;

import za.co.bangoma.auth.config.ConfigurationManager;

public class RoutePath {

    private static ConfigurationManager config = ConfigurationManager.getInstance();

    private RoutePath() {}

    // Paths
    protected static String INDEX_PATH = config.getProperty( "index.path" );
    protected static String LOGIN_PATH = config.getProperty( "login.path" );
    protected static String LOGIN_FAILED_PATH = config.getProperty( "login-failed.path" );
    protected static String SIGNUP_PATH = config.getProperty( "signup.path" );
    protected static String SIGNUP_FAILED_PATH = config.getProperty( "signup-failed.path" );
    protected static String DELETE_PATH = config.getProperty( "delete.path" );

}
