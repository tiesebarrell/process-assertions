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

import static org.toxos.activiti.assertion.ProcessAssert.assertTaskUncompleted;

import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.toxos.activiti.assertion.process.SingleUserTaskProcessConstant;
import org.toxos.activiti.assertion.process.TwoUserTasksProcessConstant;

/**
 * Tests for assertions that test a task is uncompleted by the task.
 * 
 * @author Tiese Barrell
 * 
 */
@ContextConfiguration("classpath:application-context.xml")
public class TaskIsUncompletedByTaskAssertionsTest extends AbstractProcessAssertTest {

	@Test
	@Deployment(resources = BPMN_TWO_USER_TASKS)
	public void testTaskUncompletedForTaskObject() throws Exception {
		runtimeService.startProcessInstanceByKey(TwoUserTasksProcessConstant.PROCESS_KEY.getValue());
		final Task task1 = activitiRule.getTaskService().createTaskQuery()
				.taskDefinitionKey(TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue()).singleResult();
		assertTaskUncompleted(activitiRule, task1);

		// Move on to task two and test
		taskService.complete(task1.getId());
		final Task task2 = activitiRule.getTaskService().createTaskQuery()
				.taskDefinitionKey(TwoUserTasksProcessConstant.USER_TASK_2_ACTIVITY_ID.getValue()).singleResult();
		assertTaskUncompleted(activitiRule, task2);
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = BPMN_TWO_USER_TASKS)
	public void testTaskUncompletedForTaskObjectForCompletedTask() throws Exception {
		runtimeService.startProcessInstanceByKey(TwoUserTasksProcessConstant.PROCESS_KEY.getValue());
		final Task task1 = activitiRule.getTaskService().createTaskQuery()
				.taskDefinitionKey(TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue()).singleResult();
		taskService.complete(task1.getId());

		assertTaskUncompleted(activitiRule, task1);
	}

	@Test(expected = NullPointerException.class)
	@Deployment(resources = BPMN_SINGLE_USER_TASK)
	public void testTaskUncompletedFailureForNullTaskObject() throws Exception {
		runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
		final Task nullTask = null;
		assertTaskUncompleted(activitiRule, nullTask);
	}

	@Test
	@Deployment(resources = BPMN_TWO_USER_TASKS)
	public void testTaskUncompletedForTaskId() throws Exception {
		runtimeService.startProcessInstanceByKey(TwoUserTasksProcessConstant.PROCESS_KEY.getValue());
		final Task task1 = activitiRule.getTaskService().createTaskQuery()
				.taskDefinitionKey(TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue()).singleResult();
		assertTaskUncompleted(activitiRule, task1.getId());

		// Move on to task two and test
		taskService.complete(task1.getId());
		final Task task2 = activitiRule.getTaskService().createTaskQuery()
				.taskDefinitionKey(TwoUserTasksProcessConstant.USER_TASK_2_ACTIVITY_ID.getValue()).singleResult();
		assertTaskUncompleted(activitiRule, task2.getId());
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = BPMN_TWO_USER_TASKS)
	public void testTaskUncompletedForTaskIdForCompletedTask() throws Exception {
		runtimeService.startProcessInstanceByKey(TwoUserTasksProcessConstant.PROCESS_KEY.getValue());
		final Task task1 = activitiRule.getTaskService().createTaskQuery()
				.taskDefinitionKey(TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue()).singleResult();
		taskService.complete(task1.getId());

		assertTaskUncompleted(activitiRule, task1.getId());
	}

	@Test(expected = NullPointerException.class)
	@Deployment(resources = BPMN_SINGLE_USER_TASK)
	public void testTaskUncompletedFailureForNullTaskId() throws Exception {
		runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
		final String nullString = null;
		assertTaskUncompleted(activitiRule, nullString);
	}

}
