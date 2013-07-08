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
package org.anemonos.activiti.assertion;

import static org.anemonos.activiti.assertion.ProcessAssert.assertTaskUncompleted;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * Tests for assertions that test a task is uncompleted by the process instance
 * and the task definition key.
 * 
 * @author Tiese Barrell
 * 
 */
@ContextConfiguration("classpath:application-context.xml")
public class TaskIsUncompletedByProcessInstanceAndTaskDefinitionKeyAssertionsTest extends AbstractProcessAssertTest {

	private static final String SINGLE_USER_TASK = "singleUserTask";

	private static final String USER_TASK_1 = "userTask1";

	private static final String USER_TASK_2 = "userTask2";

	@Test
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_TWO_USER_TASKS_BPMN)
	public void testTaskUncompletedForProcessInstanceObjectAndKey() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_TWO_USER_TASKS);
		assertNotNull(processInstance);
		assertTaskUncompleted(activitiRule, processInstance, USER_TASK_1);

		// Move on to task two and test
		Task task1 = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(USER_TASK_1).singleResult();
		taskService.complete(task1.getId());

		assertTaskUncompleted(activitiRule, processInstance, USER_TASK_2);
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_TWO_USER_TASKS_BPMN)
	public void testTaskUncompletedFailureForProcessInstanceObjectAndKeyForCompletedTask() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_TWO_USER_TASKS);

		// Move on to task two and test
		Task task1 = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(USER_TASK_1).singleResult();
		taskService.complete(task1.getId());

		assertTaskUncompleted(activitiRule, processInstance, USER_TASK_1);
		fail("Expected exception for task object with task definition key because task is already completed");
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN)
	public void testTaskUncompletedFailureForProcessInstanceObjectAndKeyForCompletedProcess() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_USER_TASK);

		// Move on to task two and test
		Task task1 = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(SINGLE_USER_TASK).singleResult();
		taskService.complete(task1.getId());

		assertTaskUncompleted(activitiRule, processInstance, SINGLE_USER_TASK);
		fail("Expected exception for task object with task definition key in completed process instance");
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
	public void testTaskUncompletedFailureForProcessInstanceObjectAndKeyForTaskThatNeverExisted() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
		assertTaskUncompleted(activitiRule, processInstance, SINGLE_USER_TASK);
		fail("Expected exception for task object with task definition key that is not in the process");
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN)
	public void testTaskUncompletedFailureForNullProcessInstanceObjectAndKey() throws Exception {
		runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_USER_TASK);
		final ProcessInstance nullProcessInstance = null;
		assertTaskUncompleted(activitiRule, nullProcessInstance, SINGLE_USER_TASK);
		fail("Expected exception for null process instance object");
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN)
	public void testTaskUncompletedFailureForProcessInstanceObjectAndNullKey() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_USER_TASK);
		final String nullTaskDefinitionKey = null;
		assertTaskUncompleted(activitiRule, processInstance, nullTaskDefinitionKey);
		fail("Expected exception for null process instance object");
	}

	@Test
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_TWO_USER_TASKS_BPMN)
	public void testTaskUncompletedForProcessInstanceIdAndKey() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_TWO_USER_TASKS);
		assertNotNull(processInstance);
		assertTaskUncompleted(activitiRule, processInstance.getId(), USER_TASK_1);

		// Move on to task two and test
		Task task1 = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(USER_TASK_1).singleResult();
		taskService.complete(task1.getId());

		assertTaskUncompleted(activitiRule, processInstance.getId(), USER_TASK_2);
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_TWO_USER_TASKS_BPMN)
	public void testTaskUncompletedFailureForProcessInstanceIdAndKeyForCompletedTask() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_TWO_USER_TASKS);

		// Move on to task two and test
		Task task1 = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(USER_TASK_1).singleResult();
		taskService.complete(task1.getId());

		assertTaskUncompleted(activitiRule, processInstance.getId(), USER_TASK_1);
		fail("Expected exception for task object with task definition key because task is already completed");
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN)
	public void testTaskUncompletedFailureForProcessInstanceIdAndKeyForCompletedProcess() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_USER_TASK);

		// Move on to task two and test
		Task task1 = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(SINGLE_USER_TASK).singleResult();
		taskService.complete(task1.getId());

		assertTaskUncompleted(activitiRule, processInstance.getId(), SINGLE_USER_TASK);
		fail("Expected exception for task object with task definition key in completed process instance");
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
	public void testTaskUncompletedFailureForProcessInstanceIdAndKeyForTaskThatNeverExisted() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
		assertTaskUncompleted(activitiRule, processInstance.getId(), SINGLE_USER_TASK);
		fail("Expected exception for task object with task definition key that is not in the process");
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN)
	public void testTaskUncompletedFailureForNullProcessInstanceIdAndKey() throws Exception {
		runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_USER_TASK);
		final String nullProcessInstanceId = null;
		assertTaskUncompleted(activitiRule, nullProcessInstanceId, SINGLE_USER_TASK);
		fail("Expected exception for null process instance object");
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN)
	public void testTaskUncompletedFailureForProcessInstanceIdAndNullKey() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_USER_TASK);
		final String nullTaskDefinitionKey = null;
		assertTaskUncompleted(activitiRule, processInstance.getId(), nullTaskDefinitionKey);
		fail("Expected exception for null process instance object");
	}

}
