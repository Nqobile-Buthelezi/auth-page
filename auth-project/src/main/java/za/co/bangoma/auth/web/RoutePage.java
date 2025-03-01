package za.co.bangoma.auth.web;

import za.co.bangoma.auth.config.ConfigurationManager;

public class RoutePage {

    private static ConfigurationManager config = ConfigurationManager.getInstance();

    private RoutePage() 
    {
        throw new AssertionError( "Utility class - should not be instantiated" );
    }

    // Pages
    protected static String indexPage = config.getProperty( "index.page" );
    protected static String loginPage = config.getProperty( "login.page" );
    protected static String loginFailedPage = config.getProperty( "login-failed.page" );
    protected static String signupPage = config.getProperty( "signup.page" );
    protected static String signupFailedPage = config.getProperty( "signup-failed.page" );
    

}
