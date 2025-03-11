package za.co.bangoma.auth.web;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.AfterAll;
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
    void testServerIsRunningOnTestPort() 
    {
        assertEquals( 
            Environment.TEST, 
            appConfig.getEnvironment(), 
            "The testing environment should be the actual environment." 
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
    

    /**
     * Tests that the getEnvironment method returns the correct environment
     * that was set during the AppConfig instance creation.
     */
    @Test
    void testGetEnvironmentReturnsCorrectEnvironment() 
    {
        assertEquals(
            Environment.TEST, 
            appConfig.getEnvironment(),
            "getEnvironment should return the environment " +
            "set during instance creation"
        );
    }

    /**
     * Tests that getInstance() throws an IllegalArgumentException when given a null environment.
     * This test covers the path constraint where environment == null.
     */
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

    /**
     * Tests the isRunning method when the server has been stopped.
     * This test verifies that isRunning returns false after the server is explicitly stopped.
     */
    @Test
    void testIsRunningAfterServerStop() {
        appConfig.stop();
        assertFalse( appConfig.isRunning(), "isRunning should return false after the server is stopped");
    }

    /**
     * Tests that the isRunning method returns true when the server is started.
     * This test verifies that the app is not null and the Jetty server is not stopped.
     */
    @Test
    void testIsRunningWhenServerStarted() 
    {
        assertTrue(
            appConfig.isRunning(), 
            "The server should be running after start"
        );
    }

    /**
     * Tests the start method of AppConfig to ensure it successfully starts the server.
     * This test verifies that after calling start(), the server is running and
     * configured with the correct environment.
     */
    @Test
    void testStartServerAndVerifyRunning() 
    {
        AppConfig testConfig = AppConfig.getInstance( Environment.TEST );
        testConfig.start();

        assertTrue( 
            testConfig.
            isRunning(), 
            "Server should be running after start() is called"
        );
        assertEquals( 
            Environment.TEST, 
            testConfig.getEnvironment(), 
            "Environment should be TEST"
        );
    }

    /**
     * Tests the stop() method when the server is not running.
     * This test verifies that calling stop() on a non-running server
     * does not throw an exception and does not change the server state.
     */
    @Test
    void testStopWhenServerIsNotRunning() 
    {
        // Ensure the server is stopped
        appConfig.stop();
        assertFalse(appConfig.isRunning());

        // Attempt to stop the server again
        appConfig.stop();

        // Verify that the server is still not running
        assertFalse(appConfig.isRunning(), "Server should remain stopped after calling stop() on a non-running server");
    }

    /**
     * Tests the stop() method when the server is running.
     * This test verifies that the server stops running after calling the stop() method.
     */
    @Test
    void testStopWhenServerIsRunning() 
    {
        assertTrue(
            appConfig.isRunning(), 
            "Server should be running before stop is called"
        );
        appConfig.stop();

        assertFalse(
            appConfig.isRunning(), 
            "Server should not be running after stop is called"
        );
    }

    /**
     * Tests the stop() method when the server is not running.
     * This test verifies that calling stop() on a non-running server
     * does not change its state and doesn't throw any exceptions.
     */
    @Test
    void testStopWhenServerNotRunning() 
    {
        appConfig.stop(); // Stop the server if it's running
        assertFalse(
            appConfig.isRunning(), 
            "Server should not be running before test"
        );

        appConfig.stop(); 

        assertFalse( 
            appConfig.isRunning(), 
            "Server should still not be running after stop()"
        );
    }

    /**
     * Tests that getInstance returns the same instance for the same environment.
     * This test covers the path where the environment is not null and ensures
     * that the method returns a non-null instance and that subsequent calls
     * with the same environment return the same instance.
     */
    @Test
    void test_getInstance_returnsConsistentInstanceForSameEnvironment() 
    {
        Environment testEnv = Environment.TEST;
        AppConfig instance1 = AppConfig.getInstance( testEnv );
        AppConfig instance2 = AppConfig.getInstance( testEnv );

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
