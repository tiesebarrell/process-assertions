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
package org.toxos.activiti.assertion;

/**
 * Constants for test cases.
 *
 * @author Tiese Barrell
 */
public interface TestConstants {
    /**
     * The BPMN process for the straight through process.
     */
    String BPMN_STRAIGHT_THROUGH = "diagrams/TestProcessStraightThrough.bpmn";

    /**
     * The BPMN process for the single usertask process.
     */
    String BPMN_SINGLE_USER_TASK = "diagrams/TestProcessSingleUserTask.bpmn";

    /**
     * The BPMN process for the two usertask process.
     */
    String BPMN_TWO_USER_TASKS = "diagrams/TestProcessTwoUserTasks.bpmn";

    /**
     * The BPMN process for the conditional subprocesses process.
     */
    String BPMN_CONDITIONAL_SUBPROCESSES = "diagrams/TestProcessConditionalSubProcesses.bpmn";

    /**
     * The BPMN process for the multi instance process.
     */
    String BPMN_MULTI_INSTANCE = "diagrams/TestProcessMultiInstance.bpmn";
}
