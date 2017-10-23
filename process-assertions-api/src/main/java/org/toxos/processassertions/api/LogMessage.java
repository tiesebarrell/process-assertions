/*******************************************************************************
 * Copyright 2014 Tiese Barrell
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
package org.toxos.processassertions.api;

/**
 * Messages used in log statements.
 * 
 * @author Tiese Barrell
 * 
 */
public enum LogMessage {

    /**
     * Process instance is active.
     */
    PROCESS_1,

    /**
     * Process instance can be found.
     */
    PROCESS_2,

    /**
     * Historic process instance can be found and is not ended.
     */
    PROCESS_3,

    /**
     * Historic process instance can be found and is ended.
     */
    PROCESS_4,

    /**
     * Process instance is ended.
     */
    PROCESS_5,

    /**
     * No running process instance.
     */
    PROCESS_6,

    /**
     * Process instance is not ended.
     */
    PROCESS_7,

    /**
     * Process instance is not suspended.
     */
    PROCESS_8,

    /**
     * Process instance is ended and in exclusive end event.
     */
    PROCESS_9,

    /**
     * Exactly one historic activity instance of type endEvent and id matches.
     */
    PROCESS_10,

    /**
     * The exact set of end events has been reached.
     */
    PROCESS_11,

    /**
     * The exact amount and set of end events has been reached.
     */
    PROCESS_12,

    /**
     * Historic process instance can be found.
     */
    PROCESS_13,

    /**
     * Process instance has execution in activity.
     */
    PROCESS_14,

    /**
     * Process instance is in activity.
     */
    PROCESS_15,

    /**
     * A task with definitionKey is uncompleted in process.
     */
    TASK_1,

    /**
     * Task is uncompleted.
     */
    TASK_2,

    /**
     * A task instance can be found.
     */
    TASK_3,

    /**
     * A task instance can be found with definitionKey in process.
     */
    TASK_4,

    /**
     * Process assertions failed.
     */
    ERROR_ASSERTIONS_1,

    /**
     * Process instance is not active.
     */
    ERROR_PROCESS_1,

    /**
     * Process instance is not ended.
     */
    ERROR_PROCESS_2,

    /**
     * Process instance is not ended in exclusive end event.
     */
    ERROR_PROCESS_3,

    /**
     * Process instance is not ended in the exact set of end events.
     */
    ERROR_PROCESS_4,

    /**
     * Process instance is not ended in the exact set of end events. Lists detailed expectations and actual end event
     * ids.
     */
    ERROR_PROCESS_5,

    /**
     * Process instance is not in activity.
     */
    ERROR_PROCESS_6,

    /**
     * No task instance pending completion.
     */
    ERROR_TASK_1,

    /**
     * Task instance is not pending completion.
     */
    ERROR_TASK_2,

    /**
     * History level is configured to full.
     */
    CONFIGURATION_1,

    /**
     * Process Assertions configuration is initialized.
     */
    CONFIGURATION_2,

    /**
     * Deinitialized configuration because of process engine closing down.
     */
    CONFIGURATION_3,

    /**
     * History level not set to full.
     */
    ERROR_CONFIGURATION_1,

    /**
     * Process variable exists.
     */
    VARIABLE_1,

    /**
     * Process variable has expected value.
     */
    VARIABLE_2,

    /**
     * The process variable has the expected value.
     */
    VARIABLE_3,

    /**
     * The process variable does not have the expected value.
     */
    ERROR_VARIABLE_1;

    private final String bundleKey;

    private LogMessage() {
        this.bundleKey = name().replaceAll("_", ".").toLowerCase();
    }

    /**
     * Gets the bundle key for the LogMessage.
     * 
     * @return the bundle key
     */
    public String getBundleKey() {
        return bundleKey;
    }

}
