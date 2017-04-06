package org.toxos.processassertions.api.internal;

import org.toxos.processassertions.api.LogMessage;

/**
 * Created by tiesebarrell on 21/02/2017.
 */
public interface ApiCallback {

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
     * @param message the log message
     * @param objects the parameters for substitution
     */
    public void fail(final LogMessage message, final Object... objects);

}
