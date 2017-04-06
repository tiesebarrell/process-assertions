package org.toxos.processassertions.api.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toxos.processassertions.api.LogMessage;
import org.toxos.processassertions.api.ProcessAssert;

import java.text.MessageFormat;

/**
 * Created by tiesebarrell on 22/02/2017.
 */
public class ApiCallbackImpl implements ApiCallback {

    // Log to the ProcessAssert class' logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessAssert.class);

    private static LogMessageProvider logMessageProvider = new LogMessageProvider();

    /**
     * Logs the message and parameters at trace level.
     *
     * @param message the log message
     * @param objects the parameters for substitution
     */
    public void trace(final LogMessage message, final Object... objects) {
        LOGGER.trace(getFormattedMessage(message, objects));
    }

    /**
     * Logs the message and parameters at trace level.
     *
     * @param message the log message
     * @param objects the parameters for substitution
     */
    public void error(final LogMessage message, final Object... objects) {
        LOGGER.error(getFormattedMessage(message, objects));
    }

    /**
     * Logs the message and parameters at debug level.
     *
     * @param message the log message
     * @param objects the parameters for substitution
     */
    public void debug(final LogMessage message, final Object... objects) {
        LOGGER.debug(getFormattedMessage(message, objects));
    }

    /**
     * Fails the assertions by throwing an AssertionError with the provided message and parameters.
     *
     * @param message the log message
     * @param objects the parameters for substitution
     */
    public void fail(final LogMessage message, final Object... objects) {
        final String substitutedMessage = getFormattedMessage(message, objects);
        final String failureMessage = getFormattedMessage(LogMessage.ERROR_ASSERTIONS_1, new String[] { substitutedMessage });
        LOGGER.error(failureMessage);
        throw new AssertionError(failureMessage);
    }

    /**
     * Flushes any cached information used when making assertions.
     */
    public final void flush() {
        logMessageProvider.flush();
    }

    private static String getFormattedMessage(final LogMessage message, final Object[] objects) {
        return MessageFormat.format(getMessage(message), objects);
    }

    private static String getMessage(final LogMessage message) {
        return logMessageProvider.getMessageByKey(message.getBundleKey());
    }

}
