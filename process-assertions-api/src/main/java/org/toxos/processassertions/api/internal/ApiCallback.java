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

import org.toxos.processassertions.api.LogMessage;

/**
 * Callback interface to the API module. Provides access to logging features and failing of assertions.
 *
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
     * Logs the message and parameters at error level.
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
