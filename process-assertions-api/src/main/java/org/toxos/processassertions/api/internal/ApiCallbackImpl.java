package org.toxos.processassertions.api.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toxos.processassertions.api.LogMessage;
import org.toxos.processassertions.api.ProcessAssert;
import org.toxos.processassertions.api.ProcessAssertConfiguration;

/**
 * Created by tiesebarrell on 22/02/2017.
 */
public class ApiCallbackImpl implements ApiCallback {

    // Log to the ProcessAssert class' logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessAssert.class);

    private static final String LOG_MESSAGES_BUNDLE_NAME = "org.toxos.processassertions.messages.LogMessages";

    private final MessageLogger messageLogger;

    public ApiCallbackImpl(final ProcessAssertConfiguration configuration) {
        super();
        this.messageLogger = new MessageLogger(LOG_MESSAGES_BUNDLE_NAME, configuration.getLocale());
    }

    public void info(final LogMessage message, final Object... objects) {
        messageLogger.logInfo(LOGGER, message.getBundleKey(), objects);
    }

    public void trace(final LogMessage message, final Object... objects) {
        messageLogger.logTrace(LOGGER, message.getBundleKey(), objects);
    }

    public void error(final LogMessage message, final Object... objects) {
        messageLogger.logError(LOGGER, message.getBundleKey(), objects);
    }

    public void debug(final LogMessage message, final Object... objects) {
        messageLogger.logDebug(LOGGER, message.getBundleKey(), objects);
    }

    public void fail(final AssertionError originalError, final LogMessage message, final Object... objects) {
        final String detailMessage = messageLogger.getMessage(message.getBundleKey(), objects);
        final String failureMessage = messageLogger.getMessage(LogMessage.ERROR_ASSERTIONS_1.getBundleKey(), detailMessage);
        LOGGER.error(failureMessage);
        throw new AssertionError(failureMessage, originalError);
    }

    public void fail(Class<? extends ProcessAssertionsException> exceptionClass, final LogMessage message, final Object... objects) {
        final String detailMessage = messageLogger.getMessage(message.getBundleKey(), objects);
        final String failureMessage = messageLogger.getMessage(LogMessage.ERROR_ASSERTIONS_1.getBundleKey(), detailMessage);
        LOGGER.error(failureMessage);
        try {
            throw new AssertionError(exceptionClass.getDeclaredConstructor(String.class).newInstance(failureMessage));
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

}
