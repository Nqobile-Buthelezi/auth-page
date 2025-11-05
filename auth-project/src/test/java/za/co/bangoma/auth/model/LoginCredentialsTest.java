package za.co.bangoma.auth.model;

import io.javalin.http.BadRequestResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for LoginCredentials model class
 */
class LoginCredentialsTest {

    @Test
    void testLoginCredentialsConstructorWithValidParameters() {
        // Arrange
        String username = "testuser";
        String password = "password123";

        // Act
        LoginCredentials credentials = new LoginCredentials(username, password);

        // Assert
        assertNotNull(credentials);
        assertEquals(username, credentials.getUsername());
        assertEquals(password, credentials.getPassword());
    }

    @Test
    void testLoginCredentialsConstructorThrowsExceptionForNullUsername() {
        // Arrange & Act & Assert
        BadRequestResponse exception = assertThrows(
            BadRequestResponse.class,
            () -> new LoginCredentials(null, "password123")
        );
        assertEquals("Missing username, password", exception.getMessage());
    }

    @Test
    void testLoginCredentialsConstructorThrowsExceptionForNullPassword() {
        // Arrange & Act & Assert
        BadRequestResponse exception = assertThrows(
            BadRequestResponse.class,
            () -> new LoginCredentials("testuser", null)
        );
        assertEquals("Missing username, password", exception.getMessage());
    }

    @Test
    void testLoginCredentialsConstructorThrowsExceptionForBothNull() {
        // Arrange & Act & Assert
        BadRequestResponse exception = assertThrows(
            BadRequestResponse.class,
            () -> new LoginCredentials(null, null)
        );
        assertEquals("Missing username, password", exception.getMessage());
    }

    @Test
    void testHasEmptyFieldsReturnsTrueForEmptyUsername() {
        // Arrange
        LoginCredentials credentials = new LoginCredentials("", "password123");

        // Act
        boolean result = credentials.hasEmptyFields();

        // Assert
        assertTrue(result);
    }

    @Test
    void testHasEmptyFieldsReturnsTrueForEmptyPassword() {
        // Arrange
        LoginCredentials credentials = new LoginCredentials("testuser", "");

        // Act
        boolean result = credentials.hasEmptyFields();

        // Assert
        assertTrue(result);
    }

    @Test
    void testHasEmptyFieldsReturnsTrueForBothEmpty() {
        // Arrange
        LoginCredentials credentials = new LoginCredentials("", "");

        // Act
        boolean result = credentials.hasEmptyFields();

        // Assert
        assertTrue(result);
    }

    @Test
    void testHasEmptyFieldsReturnsFalseForValidFields() {
        // Arrange
        LoginCredentials credentials = new LoginCredentials("testuser", "password123");

        // Act
        boolean result = credentials.hasEmptyFields();

        // Assert
        assertFalse(result);
    }

    @Test
    void testGetUsername() {
        // Arrange
        String username = "testuser";
        LoginCredentials credentials = new LoginCredentials(username, "password123");

        // Act
        String result = credentials.getUsername();

        // Assert
        assertEquals(username, result);
    }

    @Test
    void testGetPassword() {
        // Arrange
        String password = "password123";
        LoginCredentials credentials = new LoginCredentials("testuser", password);

        // Act
        String result = credentials.getPassword();

        // Assert
        assertEquals(password, result);
    }
}
