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

  protected AbstractProcessAssert() {
    super();
  }

  /**
   * Logs the message and parameters at trace level if the logger has enabled
   * ERROR level.
   * 
   * Uses {@link String#format(String, Object...)} to format messages.
   * 
   * @param message
   *          the message with (optional) substitutable parameter placeholders
   * @param objects
   *          the parameters for substitution
   */
  protected static void error(final LogMessage message, final Object... objects) {
    if (LOGGER.isErrorEnabled()) {
      LOGGER.error(String.format(getMessage(message) + message, objects));
    }
  }

  /**
   * Logs the message and parameters at debug level if the logger has enabled
   * DEBUG level.
   * 
   * Uses {@link String#format(String, Object...)} to format messages.
   * 
   * @param message
   *          the message with (optional) substitutable parameter placeholders
   * @param objects
   *          the parameters for substitution
   */
  protected static void debug(final LogMessage message, final Object... objects) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(String.format(getMessage(message), objects));
    }
  }

  /**
   * Logs the message and parameters at trace level if the logger has enabled
   * TRACE level.
   * 
   * Uses {@link String#format(String, Object...)} to format messages.
   * 
   * @param message
   *          the message with (optional) substitutable parameter placeholders
   * @param objects
   *          the parameters for substitution
   */
  protected static void trace(final LogMessage message, final Object... objects) {
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace(String.format(getMessage(message) + message, objects));
    }
  }

  private static String getMessage(LogMessage message) {
    final ResourceBundle bundle = ResourceBundle.getBundle("messages.LogMessages");
    return bundle.getString(message.getBundleKey());
  }

  /**
   * Fails the assertions by throwing an AssertionError with the provided
   * message and parameters.
   * 
   * Uses {@link String#format(String, Object...)} to format messages.
   * 
   * @param message
   *          the message with (optional) substitutable parameter placeholders
   * @param objects
   *          the parameters for substitution
   */
  protected static void fail(final String message, final Object... objects) {
    final String formattedMessage = String.format(message, objects);
    error(LogMessage.ERROR_ASSERTIONS_1, formattedMessage);
    Assert.fail(formattedMessage);
  }
}
