package za.co.bangoma.auth;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import za.co.bangoma.auth.config.AppConfig;
import za.co.bangoma.auth.infrastructure.Environment;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance( TestInstance.Lifecycle.PER_CLASS )
class AppConfigTest {

    private AppConfig appConfig;
    private Environment testEnvironment = Environment.TEST;

    @BeforeAll
    void setUp() 
    {
        appConfig = AppConfig.getInstance( testEnvironment );
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
        AppConfig anotherConfigInstance = AppConfig.getInstance( testEnvironment );

        assertSame( 
            appConfig, 
            anotherConfigInstance, 
            "Get instance should return the same instance" 
        );
    }

    @Test
    void testServerIsRunningOnTestPort() 
    {
        assertEquals( 
            testEnvironment, 
            appConfig.getEnvironment(), 
            "The testing environment should be the actual environment." 
        );
    }

    @Test
    void testSingletonWithDifferentEnvironments() 
    {
        AppConfig integrationInstance = AppConfig.getInstance( Environment.INTEGRATION );

        assertNotEquals( 
            appConfig, 
            integrationInstance,
            "Instances should not be the same."
        );
    }

    @Test
    void testGetInstanceThrowsExceptionForNullEnvironment() 
    {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> {
            AppConfig.getInstance( null );
            }
        );
        assertEquals( "Environment cannot be null", exception.getMessage() );
    }

    @Test
    void testIsRunningAfterServerStop() {
        appConfig.stop();
        assertFalse( 
            appConfig.isRunning(), 
            "isRunning should return false after the server is stopped"
        );
    }

    @Test
    void test_getInstance_returnsConsistentInstanceForSameEnvironment() 
    {
        AppConfig instance1 = AppConfig.getInstance( testEnvironment );
        AppConfig instance2 = AppConfig.getInstance( testEnvironment );

        assertNotNull(
            instance1, 
            "getInstance should return a non-null instance"
        );

        assertSame(
            instance1, 
            instance2, 
            "getInstance should return the same instance for the same environment"
        );
    }
}
