package org.toxos.processassertions.api.internal;

/**
 * Base exception for exceptions within Process Assertions that indicate configuration exceptions.
 *
 * @author Tiese Barrell
 */
public class ConfigurationException extends ProcessAssertionsException {

    /**
     * Constructs a new {@link ConfigurationException} with the provided message.
     *
     * @param message the message
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link ConfigurationException} with the provided message and cause.
     *
     * @param message the message
     * @param cause the cause
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
