package org.toxos.processassertions.api.internal;

/**
 * Base exception for exceptions within Process Assertions.
 *
 * @author Tiese Barrell
 */
public class ProcessAssertionsException extends Exception {

    /**
     * Constructs a new {@link ProcessAssertionsException} with the provided message.
     *
     * @param message the message
     */
    public ProcessAssertionsException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link ConfigurationException} with the provided message and cause.
     *
     * @param message the message
     * @param cause the cause
     */
    public ProcessAssertionsException(String message, Throwable cause) {
        super(message, cause);
    }
}
