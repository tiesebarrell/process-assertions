/*******************************************************************************
 * Copyright 2017 Tiese Barrell
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
package org.toxos.processassertions.api.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toxos.processassertions.api.LogMessage;
import org.toxos.processassertions.api.ProcessAssert;
import org.toxos.processassertions.api.ProcessAssertConfiguration;

/**
 * Implementation of {@link ApiCallback}.
 */
public class ApiCallbackImpl implements ApiCallback {

    // Log to the ProcessAssert class' logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessAssert.class);

    private static final String LOG_MESSAGES_BUNDLE_NAME = "org.toxos.processassertions.messages.LogMessages";

    private final MessageLogger messageLogger;

    /**
     * Constructs a new {@link ApiCallbackImpl} based on the provided {@link ProcessAssertConfiguration}.
     *
     * @param configuration the configuration. May not be {@code null}
     */
    public ApiCallbackImpl(final ProcessAssertConfiguration configuration) {
        super();
        Validate.notNull(configuration);
        this.messageLogger = new MessageLogger(LOG_MESSAGES_BUNDLE_NAME, configuration.getLocale());
    }

    @Override
    public void info(final LogMessage message, final Object... objects) {
        messageLogger.logInfo(LOGGER, message.getBundleKey(), objects);
    }

    @Override
    public void trace(final LogMessage message, final Object... objects) {
        messageLogger.logTrace(LOGGER, message.getBundleKey(), objects);
    }

    @Override
    public void error(final LogMessage message, final Object... objects) {
        messageLogger.logError(LOGGER, message.getBundleKey(), objects);
    }

    @Override
    public void debug(final LogMessage message, final Object... objects) {
        messageLogger.logDebug(LOGGER, message.getBundleKey(), objects);
    }

    @Override
    public void fail(final AssertionError originalError, final LogMessage message, final Object... objects) {
        final String detailMessage = messageLogger.getMessage(message.getBundleKey(), objects);
        final String failureMessage = messageLogger.getMessage(LogMessage.ERROR_ASSERTIONS_1.getBundleKey(), detailMessage);
        LOGGER.error(failureMessage);
        throw new AssertionError(failureMessage, originalError);
    }

    @Override
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
