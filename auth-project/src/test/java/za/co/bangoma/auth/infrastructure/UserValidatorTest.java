package za.co.bangoma.auth.infrastructure;

import io.javalin.http.BadRequestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.bangoma.auth.model.LoginCredentials;
import za.co.bangoma.auth.model.SignUpCredentials;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for UserValidator class
 */
class UserValidatorTest {

    private UserValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UserValidator();
    }

    @Test
    void testValidateCredentialsWithValidLoginCredentials() {
        // Arrange
        LoginCredentials credentials = new LoginCredentials("testuser", "password123");

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> validator.validateCredentials(credentials));
    }

    @Test
    void testValidateCredentialsWithValidSignUpCredentials() {
        // Arrange
        SignUpCredentials credentials = new SignUpCredentials("testuser", "password123", "test@example.com");

        // Act & Assert - should not throw exception
        assertDoesNotThrow(() -> validator.validateCredentials(credentials));
    }

    @Test
    void testValidateCredentialsThrowsExceptionForEmptyUsernameInLogin() {
        // Arrange
        LoginCredentials credentials = new LoginCredentials("", "password123");

        // Act & Assert
        BadRequestResponse exception = assertThrows(
            BadRequestResponse.class,
            () -> validator.validateCredentials(credentials)
        );
        assertEquals("Missing username, password, or email", exception.getMessage());
    }

    @Test
    void testValidateCredentialsThrowsExceptionForEmptyPasswordInLogin() {
        // Arrange
        LoginCredentials credentials = new LoginCredentials("testuser", "");

        // Act & Assert
        BadRequestResponse exception = assertThrows(
            BadRequestResponse.class,
            () -> validator.validateCredentials(credentials)
        );
        assertEquals("Missing username, password, or email", exception.getMessage());
    }

    @Test
    void testValidateCredentialsThrowsExceptionForEmptyFieldsInSignUp() {
        // Arrange
        SignUpCredentials credentials = new SignUpCredentials("", "password123", "test@example.com");

        // Act & Assert
        BadRequestResponse exception = assertThrows(
            BadRequestResponse.class,
            () -> validator.validateCredentials(credentials)
        );
        assertEquals("Missing username, password, or email", exception.getMessage());
    }

    @Test
    void testValidateCredentialsThrowsExceptionForEmptyEmailInSignUp() {
        // Arrange
        SignUpCredentials credentials = new SignUpCredentials("testuser", "password123", "");

        // Act & Assert
        BadRequestResponse exception = assertThrows(
            BadRequestResponse.class,
            () -> validator.validateCredentials(credentials)
        );
        assertEquals("Missing username, password, or email", exception.getMessage());
    }

    @Test
    void testValidateCredentialsThrowsExceptionForAllEmptyFieldsInSignUp() {
        // Arrange
        SignUpCredentials credentials = new SignUpCredentials("", "", "");

        // Act & Assert
        BadRequestResponse exception = assertThrows(
            BadRequestResponse.class,
            () -> validator.validateCredentials(credentials)
        );
        assertEquals("Missing username, password, or email", exception.getMessage());
    }
}
