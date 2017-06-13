package org.toxos.processassertions.api.internal;

/**
 * Base exception for exceptions within Process Assertions that indicate configuration exceptions.
 *
 * Created by tiesebarrell on 13/06/2017.
 */
public class ConfigurationException extends ProcessAssertionsException {

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
