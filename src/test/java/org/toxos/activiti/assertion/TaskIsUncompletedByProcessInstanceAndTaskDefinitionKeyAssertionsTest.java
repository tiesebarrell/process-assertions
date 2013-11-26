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

import static org.junit.Assert.assertNotNull;
import static org.toxos.activiti.assertion.ProcessAssert.assertTaskUncompleted;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.toxos.activiti.assertion.process.SingleUserTaskProcessConstant;
import org.toxos.activiti.assertion.process.StraightThroughProcessConstant;
import org.toxos.activiti.assertion.process.TwoUserTasksProcessConstant;

/**
 * Tests for assertions that test a task is uncompleted by the process instance
 * and the task definition key.
 * 
 * @author Tiese Barrell
 * 
 */
@ContextConfiguration("classpath:application-context.xml")
public class TaskIsUncompletedByProcessInstanceAndTaskDefinitionKeyAssertionsTest extends AbstractProcessAssertTest {

	@Test
	@Deployment(resources = BPMN_TWO_USER_TASKS)
	public void testTaskUncompletedForProcessInstanceObjectAndKey() throws Exception {
		final ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(TwoUserTasksProcessConstant.PROCESS_KEY.getValue());
		assertNotNull(processInstance);
		assertTaskUncompleted(activitiRule, processInstance,
				TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue());

		// Move on to task two and test
		final Task task1 = activitiRule.getTaskService().createTaskQuery()
				.taskDefinitionKey(TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue()).singleResult();
		taskService.complete(task1.getId());

		assertTaskUncompleted(activitiRule, processInstance,
				TwoUserTasksProcessConstant.USER_TASK_2_ACTIVITY_ID.getValue());
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = BPMN_TWO_USER_TASKS)
	public void testTaskUncompletedFailureForProcessInstanceObjectAndKeyForCompletedTask() throws Exception {
		final ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(TwoUserTasksProcessConstant.PROCESS_KEY.getValue());

		// Move on to task two and test
		final Task task1 = activitiRule.getTaskService().createTaskQuery()
				.taskDefinitionKey(TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue()).singleResult();
		taskService.complete(task1.getId());

		assertTaskUncompleted(activitiRule, processInstance,
				TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue());
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = BPMN_SINGLE_USER_TASK)
	public void testTaskUncompletedFailureForProcessInstanceObjectAndKeyForCompletedProcess() throws Exception {
		final ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());

		// Move on to task two and test
		final Task task1 = activitiRule.getTaskService().createTaskQuery()
				.taskDefinitionKey(SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue()).singleResult();
		taskService.complete(task1.getId());

		assertTaskUncompleted(activitiRule, processInstance,
				SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = BPMN_STRAIGHT_THROUGH)
	public void testTaskUncompletedFailureForProcessInstanceObjectAndKeyForTaskThatNeverExisted() throws Exception {
		final ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
		assertTaskUncompleted(activitiRule, processInstance,
				SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
	}

	@Test(expected = NullPointerException.class)
	@Deployment(resources = BPMN_SINGLE_USER_TASK)
	public void testTaskUncompletedFailureForNullProcessInstanceObjectAndKey() throws Exception {
		runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
		final ProcessInstance nullProcessInstance = null;
		assertTaskUncompleted(activitiRule, nullProcessInstance,
				SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
	}

	@Test(expected = NullPointerException.class)
	@Deployment(resources = BPMN_SINGLE_USER_TASK)
	public void testTaskUncompletedFailureForProcessInstanceObjectAndNullKey() throws Exception {
		final ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
		final String nullTaskDefinitionKey = null;
		assertTaskUncompleted(activitiRule, processInstance, nullTaskDefinitionKey);
	}

	@Test
	@Deployment(resources = BPMN_TWO_USER_TASKS)
	public void testTaskUncompletedForProcessInstanceIdAndKey() throws Exception {
		final ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(TwoUserTasksProcessConstant.PROCESS_KEY.getValue());
		assertNotNull(processInstance);
		assertTaskUncompleted(activitiRule, processInstance.getId(),
				TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue());

		// Move on to task two and test
		final Task task1 = activitiRule.getTaskService().createTaskQuery()
				.taskDefinitionKey(TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue()).singleResult();
		taskService.complete(task1.getId());

		assertTaskUncompleted(activitiRule, processInstance.getId(),
				TwoUserTasksProcessConstant.USER_TASK_2_ACTIVITY_ID.getValue());
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = BPMN_TWO_USER_TASKS)
	public void testTaskUncompletedFailureForProcessInstanceIdAndKeyForCompletedTask() throws Exception {
		final ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(TwoUserTasksProcessConstant.PROCESS_KEY.getValue());

		// Move on to task two and test
		final Task task1 = activitiRule.getTaskService().createTaskQuery()
				.taskDefinitionKey(TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue()).singleResult();
		taskService.complete(task1.getId());

		assertTaskUncompleted(activitiRule, processInstance.getId(),
				TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue());
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = BPMN_SINGLE_USER_TASK)
	public void testTaskUncompletedFailureForProcessInstanceIdAndKeyForCompletedProcess() throws Exception {
		final ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());

		// Move on to task two and test
		final Task task1 = activitiRule.getTaskService().createTaskQuery()
				.taskDefinitionKey(SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue()).singleResult();
		taskService.complete(task1.getId());

		assertTaskUncompleted(activitiRule, processInstance.getId(),
				SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = BPMN_STRAIGHT_THROUGH)
	public void testTaskUncompletedFailureForProcessInstanceIdAndKeyForTaskThatNeverExisted() throws Exception {
		final ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
		assertTaskUncompleted(activitiRule, processInstance.getId(),
				SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
	}

	@Test(expected = NullPointerException.class)
	@Deployment(resources = BPMN_SINGLE_USER_TASK)
	public void testTaskUncompletedFailureForNullProcessInstanceIdAndKey() throws Exception {
		runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
		final String nullProcessInstanceId = null;
		assertTaskUncompleted(activitiRule, nullProcessInstanceId,
				SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
	}

	@Test(expected = NullPointerException.class)
	@Deployment(resources = BPMN_SINGLE_USER_TASK)
	public void testTaskUncompletedFailureForProcessInstanceIdAndNullKey() throws Exception {
		final ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
		final String nullTaskDefinitionKey = null;
		assertTaskUncompleted(activitiRule, processInstance.getId(), nullTaskDefinitionKey);
	}

}
