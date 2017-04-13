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
package org.toxos.processassertions.activiti.process;

/**
 * Constants for the conditional subprocess process.
 * 
 * @author Tiese Barrell
 */
public enum ConditionalSubProcessesProcessConstant {

    /**
     * The process' key.
     */
    PROCESS_KEY("testProcessConditionalSubProcesses"),

    /**
     * The id of the end event for the whole process.
     */
    END_PROCESS_EVENT_ID("endProcess"),

    /**
     * The id of the end event for the first sub process.
     */
    END_SUBPROCESS_1_EVENT_ID("endSubProcess1"),

    /**
     * The id of the end event for the second sub process.
     */
    END_SUBPROCESS_2_EVENT_ID("endSubProcess2"),

    /**
     * The id of the end event for the third sub process.
     */
    END_SUBPROCESS_3_EVENT_ID("endSubProcess3");

    private final String value;

    private ConditionalSubProcessesProcessConstant(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
