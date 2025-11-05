package za.co.bangoma.auth.service.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for SignupCredentialCommand class
 */
class SignupCredentialCommandTest {

    @Test
    void testGetInstanceReturnsSameInstance() {
        // Arrange & Act
        SignupCredentialCommand instance1 = SignupCredentialCommand.getInstance();
        SignupCredentialCommand instance2 = SignupCredentialCommand.getInstance();

        // Assert
        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2, "getInstance should return the same singleton instance");
    }

    @Test
    void testGetInstanceReturnsNonNullInstance() {
        // Arrange & Act
        SignupCredentialCommand instance = SignupCredentialCommand.getInstance();

        // Assert
        assertNotNull(instance, "getInstance should never return null");
    }
}
