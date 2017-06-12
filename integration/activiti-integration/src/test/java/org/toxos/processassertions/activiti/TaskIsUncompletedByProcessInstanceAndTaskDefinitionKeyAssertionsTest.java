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
package org.toxos.processassertions.activiti;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.toxos.processassertions.activiti.integration.configuration.AbstractProcessAssertTest;
import org.toxos.processassertions.integration.common.process.SingleUserTaskProcessConstant;
import org.toxos.processassertions.integration.common.process.StraightThroughProcessConstant;
import org.toxos.processassertions.integration.common.process.TwoUserTasksProcessConstant;

import static org.junit.Assert.assertNotNull;
import static org.toxos.processassertions.api.ProcessAssert.assertTaskUncompleted;

/**
 * Tests for assertions that test a task is uncompleted by the process instance
 * and the task definition key.
 * 
 * @author Tiese Barrell
 * 
 */
public class TaskIsUncompletedByProcessInstanceAndTaskDefinitionKeyAssertionsTest extends AbstractProcessAssertTest {

    @Test
    @Deployment(resources = BPMN_TWO_USER_TASKS)
    public void assertTaskUncompletedSuccess() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TwoUserTasksProcessConstant.PROCESS_KEY.getValue());
        assertNotNull(processInstance);
        assertTaskUncompleted(processInstance.getId(), TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue());

        // Move on to task two and test
        final Task task1 = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue())
                .singleResult();
        taskService.complete(task1.getId());

        assertTaskUncompleted(processInstance.getId(), TwoUserTasksProcessConstant.USER_TASK_2_ACTIVITY_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_TWO_USER_TASKS)
    public void assertTaskUncompletedFailureForCompletedTask() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TwoUserTasksProcessConstant.PROCESS_KEY.getValue());

        // Move on to task two and test
        final Task task1 = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue())
                .singleResult();
        taskService.complete(task1.getId());

        assertTaskUncompleted(processInstance.getId(), TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void assertTaskUncompletedFailureForCompletedProcess() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());

        // Move on to task two and test
        final Task task1 = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue())
                .singleResult();
        taskService.complete(task1.getId());

        assertTaskUncompleted(processInstance.getId(), SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void assertTaskUncompletedFailureForTaskThatNeverExisted() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        assertTaskUncompleted(processInstance.getId(), SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
    }

    @Test(expected = NullPointerException.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void assertTaskUncompletedFailureForNullProcessInstanceId() throws Exception {
        runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        final String nullProcessInstanceId = null;
        assertTaskUncompleted(nullProcessInstanceId, SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
    }

    @Test(expected = NullPointerException.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void assertTaskUncompletedFailureForNullKey() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        final String nullTaskDefinitionKey = null;
        assertTaskUncompleted(processInstance.getId(), nullTaskDefinitionKey);
    }

}
