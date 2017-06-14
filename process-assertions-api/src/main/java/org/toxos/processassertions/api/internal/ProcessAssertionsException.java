package org.toxos.processassertions.api.internal;

/**
 * Base exception for exceptions within Process Assertions.
 *
 * Created by tiesebarrell on 13/06/2017.
 */
public class ProcessAssertionsException extends Exception {

    public ProcessAssertionsException(String message) {
        super(message);
    }


    public ProcessAssertionsException(String message, Throwable cause) {
        super(message, cause);
    }
}
