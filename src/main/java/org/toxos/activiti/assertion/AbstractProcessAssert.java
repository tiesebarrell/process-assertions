/*******************************************************************************
 * Copyright 2013 Tiese Barrell
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.toxos.activiti.assertion;

import java.text.MessageFormat;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class for process assertions. Provides easy access to logger
 * for printing messages.
 * 
 * @author Tiese Barrell
 * 
 */
public abstract class AbstractProcessAssert {

    // Log to the ProcessAssert class' logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessAssert.class);

    private static LogMessageProvider logMessageProvider = new LogMessageProvider();

    protected AbstractProcessAssert() {
        super();
    }

    /**
     * Flushes any cached information used when making assertions.
     */
    protected static final void flush() {
        logMessageProvider.flush();
    }

    /**
     * Logs the message and parameters at trace level.
     * 
     * @param message
     *            the log message
     * @param objects
     *            the parameters for substitution
     */
    protected static void error(final LogMessage message, final Object... objects) {
        LOGGER.error(getFormattedMessage(message, objects));
    }

    /**
     * Logs the message and parameters at debug level.
     * 
     * @param message
     *            the log message
     * @param objects
     *            the parameters for substitution
     */
    protected static void debug(final LogMessage message, final Object... objects) {
        LOGGER.debug(getFormattedMessage(message, objects));
    }

    /**
     * Logs the message and parameters at trace level.
     * 
     * @param message
     *            the log message
     * @param objects
     *            the parameters for substitution
     */
    protected static void trace(final LogMessage message, final Object... objects) {
        LOGGER.trace(getFormattedMessage(message, objects));
    }

    /**
     * Fails the assertions by throwing an AssertionError with the provided
     * message and parameters.
     * 
     * @param message
     *            the log message
     * @param objects
     *            the parameters for substitution
     */
    protected static void fail(final LogMessage message, final Object... objects) {
        final String substitutedMessage = getFormattedMessage(message, objects);
        final String failureMessage = getFormattedMessage(LogMessage.ERROR_ASSERTIONS_1, new String[] { substitutedMessage });
        LOGGER.error(failureMessage);
        Assert.fail(failureMessage);
    }

    private static String getFormattedMessage(final LogMessage message, final Object[] objects) {
        return MessageFormat.format(getMessage(message), objects);
    }

    private static String getMessage(final LogMessage message) {
        return logMessageProvider.getMessageByKey(message.getBundleKey());
    }

}
