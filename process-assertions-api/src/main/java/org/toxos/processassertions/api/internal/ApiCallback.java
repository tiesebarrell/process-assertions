package org.toxos.processassertions.api.internal;

import org.toxos.processassertions.api.LogMessage;

/**
 * Created by tiesebarrell on 21/02/2017.
 */
public interface ApiCallback {

    /**
     * Logs the message and parameters at info level.
     *
     * @param message the log message
     * @param objects the parameters for substitution
     */
    public void info(final LogMessage message, final Object... objects);

    /**
     * Logs the message and parameters at trace level.
     *
     * @param message the log message
     * @param objects the parameters for substitution
     */
    public void error(final LogMessage message, final Object... objects);

    /**
     * Logs the message and parameters at debug level.
     *
     * @param message the log message
     * @param objects the parameters for substitution
     */
    public void debug(final LogMessage message, final Object... objects);

    /**
     * Logs the message and parameters at trace level.
     *
     * @param message the log message
     * @param objects the parameters for substitution
     */
    public void trace(final LogMessage message, final Object... objects);

    /**
     * Fails the assertions by throwing an AssertionError with the provided message and parameters.
     *
     * @param originalError the original error that prompted the failure
     * @param message the log message
     * @param objects the parameters for substitution
     */
    public void fail(final AssertionError originalError, final LogMessage message, final Object... objects);

    /**
     * Fails the assertions by throwing an AssertionError based on the exceptionClass with the provided message and parameters.
     *
     * @param exceptionClass the {@link ProcessAssertionsException} class to be created
     * @param message the log message
     * @param objects the parameters for substitution
     */
    public void fail(Class<? extends ProcessAssertionsException> exceptionClass, final LogMessage message, final Object... objects);

}
