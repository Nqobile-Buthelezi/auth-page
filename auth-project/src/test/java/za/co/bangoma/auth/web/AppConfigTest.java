package za.co.bangoma.auth.web;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import za.co.bangoma.auth.config.AppConfig;
import za.co.bangoma.auth.infrastructure.Environment;

@TestInstance( TestInstance.Lifecycle.PER_CLASS )
class AppConfigTest {

    private AppConfig appConfig;

    @BeforeAll
    void setUp() 
    {
        appConfig = AppConfig.getInstance( Environment.TEST );
        appConfig.start();

        assertTrue( appConfig.isRunning() );
    }
    
    @AfterAll
    void tearDown() 
    {
        if ( appConfig.isRunning() && appConfig != null ) 
        {
            appConfig.stop();
        }

        assertFalse( 
            appConfig.isRunning(), 
            "Server should not be running after stop." 
        );
    }

    @Test
    void testSingletonGetInstance()
    {
        AppConfig anotherInstance = AppConfig.getInstance( Environment.TEST );
        assertSame( 
            appConfig, 
            anotherInstance, 
            "Get instance should return the same instance" 
        );
    }

    @Test
    void testServerIsRunning() 
    {
        assertTrue(
            appConfig.isRunning(),
            "Server should be running after start."
        );
    }

    @Test
    void testEnvironmentConfiguration()
    {
        assertSame(
            Environment.TEST,
            appConfig.getEnvironment(),
            "Environment should be TEST"
        );
    }

    @Test
    void testSingletonWithDifferentEnvironments() 
    {
        AppConfig prodInstance = AppConfig.getInstance( Environment.INTEGRATION );

        assertNotEquals( 
            appConfig, 
            prodInstance,
            "getInstance should return different instances with different environments."
        );
    }
    
}
