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
package com.mazidea.activiti.assertion;

import java.util.ResourceBundle;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

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

	private static ResourceBundle bundle;

	protected AbstractProcessAssert() {
		super();
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
		LOGGER.error(getMessage(message), objects);
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
		LOGGER.debug(getMessage(message), objects);
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
		LOGGER.trace(getMessage(message), objects);
	}

	/**
	 * Fails the assertions by throwing an AssertionError with the provided
	 * message and parameters.
	 * 
	 * Uses {@link String#format(String, Object...)} to format messages.
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

	/**
	 * Gets the formatted message by using SLF4J's implementation to allow for
	 * the same substitution specification in resource bundles.
	 * 
	 * @param message
	 *            the log message
	 * @param objects
	 *            the parameters for substitution
	 * @return the formatted message
	 */
	private static String getFormattedMessage(final LogMessage message, final Object[] objects) {
		final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(getMessage(message), objects);
		return formattingTuple.getMessage();
	}

	private static String getMessage(LogMessage message) {
		return getBundle().getString(message.getBundleKey());
	}

	private static ResourceBundle getBundle() {
		if (bundle == null) {
			bundle = ResourceBundle.getBundle(Constants.LOG_MESSAGES_BUNDLE_NAME);
		}
		return bundle;
	}
}
