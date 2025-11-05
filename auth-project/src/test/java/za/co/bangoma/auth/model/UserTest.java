package za.co.bangoma.auth.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for User model class
 */
class UserTest {

    @Test
    void testUserConstructorWithValidParameters() {
        // Arrange
        String username = "testuser";
        String password = "password123";
        String email = "test@example.com";

        // Act
        User user = new User(username, password, email);

        // Assert
        assertNotNull(user);
        assertEquals(username, user.username);
        assertEquals(password, user.password);
        assertEquals(email, user.email);
    }

    @Test
    void testUserConstructorThrowsExceptionForNullUsername() {
        // Arrange & Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new User(null, "password123", "test@example.com")
        );
        assertEquals("Missing username, password, or email", exception.getMessage());
    }

    @Test
    void testUserConstructorThrowsExceptionForNullPassword() {
        // Arrange & Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new User("testuser", null, "test@example.com")
        );
        assertEquals("Missing username, password, or email", exception.getMessage());
    }

    @Test
    void testUserConstructorThrowsExceptionForNullEmail() {
        // Arrange & Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new User("testuser", "password123", null)
        );
        assertEquals("Missing username, password, or email", exception.getMessage());
    }

    @Test
    void testUserConstructorThrowsExceptionForAllNullParameters() {
        // Arrange & Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new User(null, null, null)
        );
        assertEquals("Missing username, password, or email", exception.getMessage());
    }

    @Test
    void testUserWithEmptyStrings() {
        // Arrange
        String username = "";
        String password = "";
        String email = "";

        // Act
        User user = new User(username, password, email);

        // Assert - Empty strings should be allowed (validation should be elsewhere)
        assertNotNull(user);
        assertEquals(username, user.username);
        assertEquals(password, user.password);
        assertEquals(email, user.email);
    }
}
