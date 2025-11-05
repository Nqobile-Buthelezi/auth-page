package za.co.bangoma.auth.model;

import io.javalin.http.BadRequestResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for SignUpCredentials model class
 */
class SignUpCredentialsTest {

    @Test
    void testSignUpCredentialsConstructorWithValidParameters() {
        // Arrange
        String username = "testuser";
        String password = "password123";
        String email = "test@example.com";

        // Act
        SignUpCredentials credentials = new SignUpCredentials(username, password, email);

        // Assert
        assertNotNull(credentials);
        assertEquals(username, credentials.getUsername());
        assertEquals(password, credentials.getPassword());
        assertEquals(email, credentials.getEmail());
    }

    @Test
    void testSignUpCredentialsConstructorThrowsExceptionForNullUsername() {
        // Arrange & Act & Assert
        BadRequestResponse exception = assertThrows(
            BadRequestResponse.class,
            () -> new SignUpCredentials(null, "password123", "test@example.com")
        );
        assertEquals("Missing username, password, or email", exception.getMessage());
    }

    @Test
    void testSignUpCredentialsConstructorThrowsExceptionForNullPassword() {
        // Arrange & Act & Assert
        BadRequestResponse exception = assertThrows(
            BadRequestResponse.class,
            () -> new SignUpCredentials("testuser", null, "test@example.com")
        );
        assertEquals("Missing username, password, or email", exception.getMessage());
    }

    @Test
    void testSignUpCredentialsConstructorThrowsExceptionForNullEmail() {
        // Arrange & Act & Assert
        BadRequestResponse exception = assertThrows(
            BadRequestResponse.class,
            () -> new SignUpCredentials("testuser", "password123", null)
        );
        assertEquals("Missing username, password, or email", exception.getMessage());
    }

    @Test
    void testSignUpCredentialsConstructorThrowsExceptionForAllNull() {
        // Arrange & Act & Assert
        BadRequestResponse exception = assertThrows(
            BadRequestResponse.class,
            () -> new SignUpCredentials(null, null, null)
        );
        assertEquals("Missing username, password, or email", exception.getMessage());
    }

    @Test
    void testHasEmptyFieldsReturnsTrueForEmptyUsername() {
        // Arrange
        SignUpCredentials credentials = new SignUpCredentials("", "password123", "test@example.com");

        // Act
        boolean result = credentials.hasEmptyFields();

        // Assert
        assertTrue(result);
    }

    @Test
    void testHasEmptyFieldsReturnsTrueForEmptyPassword() {
        // Arrange
        SignUpCredentials credentials = new SignUpCredentials("testuser", "", "test@example.com");

        // Act
        boolean result = credentials.hasEmptyFields();

        // Assert
        assertTrue(result);
    }

    @Test
    void testHasEmptyFieldsReturnsTrueForEmptyEmail() {
        // Arrange
        SignUpCredentials credentials = new SignUpCredentials("testuser", "password123", "");

        // Act
        boolean result = credentials.hasEmptyFields();

        // Assert
        assertTrue(result);
    }

    @Test
    void testHasEmptyFieldsReturnsTrueForAllEmpty() {
        // Arrange
        SignUpCredentials credentials = new SignUpCredentials("", "", "");

        // Act
        boolean result = credentials.hasEmptyFields();

        // Assert
        assertTrue(result);
    }

    @Test
    void testHasEmptyFieldsReturnsFalseForValidFields() {
        // Arrange
        SignUpCredentials credentials = new SignUpCredentials("testuser", "password123", "test@example.com");

        // Act
        boolean result = credentials.hasEmptyFields();

        // Assert
        assertFalse(result);
    }

    @Test
    void testGetUsername() {
        // Arrange
        String username = "testuser";
        SignUpCredentials credentials = new SignUpCredentials(username, "password123", "test@example.com");

        // Act
        String result = credentials.getUsername();

        // Assert
        assertEquals(username, result);
    }

    @Test
    void testGetPassword() {
        // Arrange
        String password = "password123";
        SignUpCredentials credentials = new SignUpCredentials("testuser", password, "test@example.com");

        // Act
        String result = credentials.getPassword();

        // Assert
        assertEquals(password, result);
    }

    @Test
    void testGetEmail() {
        // Arrange
        String email = "test@example.com";
        SignUpCredentials credentials = new SignUpCredentials("testuser", "password123", email);

        // Act
        String result = credentials.getEmail();

        // Assert
        assertEquals(email, result);
    }
}
