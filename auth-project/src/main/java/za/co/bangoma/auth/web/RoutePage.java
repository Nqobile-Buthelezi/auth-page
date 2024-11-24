package za.co.bangoma.auth.web;

import za.co.bangoma.auth.config.ConfigurationManager;

public class RoutePage {

    private static ConfigurationManager config = ConfigurationManager.getInstance();

    private RoutePage() {}

    // Pages
    protected static String INDEX_PAGE = config.getProperty( "index.page" );
    protected static String LOGIN_PAGE = config.getProperty( "login.page" );
    protected static String LOGIN_FAILED_PAGE = config.getProperty( "login-failed.page" );
    protected static String SIGNUP_PAGE = config.getProperty( "signup.page" );
    protected static String SIGNUP_FAILED_PAGE = config.getProperty( "signup-failed.page" );
    

}
