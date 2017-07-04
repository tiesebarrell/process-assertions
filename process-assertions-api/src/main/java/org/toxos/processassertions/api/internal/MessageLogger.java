package org.toxos.processassertions.api.internal;

import org.slf4j.Logger;
import org.toxos.processassertions.api.SupportedLocale;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * Logs messages defined by patterns in resource bundles and applies parameter substitution.
 *
 * @author Tiese Barrell
 */
public class MessageLogger {

    private LogMessageProvider logMessageProvider;

    /**
     * Constructs a new MessageLogger using the provided base name of the bundle and the provided {@link SupportedLocale}.
     * @param bundleBaseName the base name of the bundle
     * @param locale the locale to apply to messages
     */
    public MessageLogger(final String bundleBaseName, final Locale locale) {
        this.logMessageProvider = new LogMessageProvider(bundleBaseName, locale);
    }

    /**
     * Logs a message by the provided key to the provided {@link Logger} at info level after substituting the parameters in the message by the provided objects.
     * @param logger the logger to log to
     * @param messageKey the key of the message in the bundle
     * @param objects the substitution parameters
     */
    public final void logInfo(final Logger logger, final String messageKey, final Object... objects) {
        logger.info(getMessage(messageKey, objects));
    }

    /**
     * Logs a message by the provided key to the provided {@link Logger} at trace level after substituting the parameters in the message by the provided objects.
     * @param logger the logger to log to
     * @param messageKey the key of the message in the bundle
     * @param objects the substitution parameters
     */
    public final void logTrace(final Logger logger, final String messageKey, final Object... objects) {
        logger.trace(getMessage(messageKey, objects));
    }

    /**
     * Logs a message by the provided key to the provided {@link Logger} at error level after substituting the parameters in the message by the provided objects.
     * @param logger the logger to log to
     * @param messageKey the key of the message in the bundle
     * @param objects the substitution parameters
     */
    public final void logError(final Logger logger, final String messageKey, final Object... objects) {
        logger.error(getMessage(messageKey, objects));
    }

    /**
     * Logs a message by the provided key to the provided {@link Logger} at debug level after substituting the parameters in the message by the provided objects.
     * @param logger the logger to log to
     * @param messageKey the key of the message in the bundle
     * @param objects the substitution parameters
     */
    public final void logDebug(final Logger logger, final String messageKey, final Object... objects) {
        logger.debug(getMessage(messageKey, objects));
    }

    /**
     * Gets the message as defined by the provided messageKey and substitutes the provided object values in the message.
     * @param messageKey the key of the message pattern
     * @param objects the object values to substitute
     * @return the formatted message
     */
    public String getMessage(final String messageKey, final Object... objects) {
        //TODO validate input
        return getFormattedMessage(getMessagePattern(messageKey), objects);
    }

    private static final String getFormattedMessage(final String message, final Object[] objects) {
        return MessageFormat.format(message, objects);
    }
    private final String getMessagePattern(final String messageKey) {
        return logMessageProvider.getMessageByKey(messageKey);
    }

}
