package za.co.bangoma.auth.service.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for LoginCredentialCommand class
 */
class LoginCredentialCommandTest {

    @Test
    void testGetInstanceReturnsSameInstance() {
        // Arrange & Act
        LoginCredentialCommand instance1 = LoginCredentialCommand.getInstance();
        LoginCredentialCommand instance2 = LoginCredentialCommand.getInstance();

        // Assert
        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2, "getInstance should return the same singleton instance");
    }

    @Test
    void testGetInstanceReturnsNonNullInstance() {
        // Arrange & Act
        LoginCredentialCommand instance = LoginCredentialCommand.getInstance();

        // Assert
        assertNotNull(instance, "getInstance should never return null");
    }
}
