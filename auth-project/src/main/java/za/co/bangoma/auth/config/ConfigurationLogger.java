package za.co.bangoma.auth.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigurationLogger {
    private static final Logger logger = LogManager.getLogger(ConfigurationLogger.class);
    private static final ConfigurationLogger INSTANCE = new ConfigurationLogger();

    private ConfigurationLogger() {}

    public static ConfigurationLogger getInstance() {
        return INSTANCE;
    }

    // Configuration Loading Logs
    public void logConfigurationLoadStart() {
        logger.info("Starting configuration loading process");
    }

    public void logConfigurationLoadSuccess() {
        logger.info("Configuration loaded successfully");
    }

    public void logConfigurationLoadError(String message, Exception e) {
        logger.error("Configuration loading error: {}", message, e);
    }

    // Property Access Logs
    public void logPropertyRetrieval(String key, String value) {
        logger.debug("Retrieved property: {} = {}", key, value);
    }

    public void logPropertyNotFound(String key) {
        logger.warn("Property not found: {}", key);
    }

    // Port Configuration Logs
    public void logConfigurationComplete(int port) {
        logger.info("Configuration complete, App ready on port {}", port);
    }

    public void logInvalidPortConfiguration(String port, Exception e) {
        logger.error("Invalid port configuration '{}': {}", port, e.getMessage());
    }

    // Static Files Logs
    public void logStaticFilesDirectory() {
        logger.info("Static files directory configured");
    }

    public void logCreationOfStaticDirectory() {
        logger.info("Static files directory created");
    }

    public void logErrorInCreationOfStaticDirectory(String errorMessage) {
        logger.error("Error creating static files directory: {}", errorMessage);
    }

    // Database Logs
    public void logDatabaseDirectoryAccess(String directory) {
        logger.debug("Accessing database directory: {}", directory);
    }

    public void logDatabaseUrlGeneration(String url) {
        logger.debug("Generated database URL: {}", url);
    }

    // Validation Logs
    public void logConfigFileNotFound() {
        logger.error("Unable to find config.properties");
    }

    public void logValidationError(String message) {
        logger.error("Validation error: {}", message);
    }
}
