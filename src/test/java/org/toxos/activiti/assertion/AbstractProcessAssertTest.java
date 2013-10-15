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

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.toxos.activiti.assertion.ProcessAssert;

/**
 * Abstract base class for test classes that test {@link ProcessAssert}.
 * 
 * @author Tiese Barrell
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractProcessAssertTest {

	protected static final String DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN = "diagrams/TestProcessStraightThrough.bpmn";

	protected static final String DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN = "diagrams/TestProcessSingleUserTask.bpmn";

	protected static final String DIAGRAMS_TEST_PROCESS_TWO_USER_TASKS_BPMN = "diagrams/TestProcessTwoUserTasks.bpmn";

	protected static final String DIAGRAMS_TEST_PROCESS_CONDITIONAL_SUBPROCESSES_BPMN = "diagrams/TestProcessConditionalSubProcesses.bpmn";

	protected static final String TEST_PROCESS_STRAIGHT_THROUGH = "testProcessStraightThrough";

	protected static final String TEST_PROCESS_SINGLE_USER_TASK = "testProcessSingleUserTask";

	protected static final String TEST_PROCESS_TWO_USER_TASKS = "testProcessTwoUserTasks";

	protected static final String TEST_PROCESS_CONDITIONAL_SUBPROCESSES = "testProcessConditionalSubProcesses";

	@Autowired
	@Rule
	public ActivitiRule activitiRule;

	@Autowired
	protected RuntimeService runtimeService;

	@Autowired
	protected TaskService taskService;

}
