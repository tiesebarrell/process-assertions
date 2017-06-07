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
import org.toxos.processassertions.integration.common.process.MultiInstanceProcessConstant;
import org.toxos.processassertions.integration.common.process.SingleUserTaskProcessConstant;
import org.toxos.processassertions.integration.common.process.TwoUserTasksProcessConstant;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;
import static org.toxos.processassertions.api.ProcessAssert.assertProcessInActivity;
import static org.toxos.processassertions.api.internal.Assert.assertThat;

/**
 * Tests for assertions that test a process is in an activity.
 * 
 * @author Tiese Barrell
 * 
 */
public class ProcessIsInActivityAssertionsTest extends AbstractProcessAssertTest {

    @Test
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void assertProcessInActivitySuccess() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessInActivity(processInstance.getProcessInstanceId(), SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
    }

    @Test
    @Deployment(resources = BPMN_MULTI_INSTANCE)
    public void assertProcessInActivityForMultipleExecutions() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(MultiInstanceProcessConstant.PROCESS_KEY.getValue());
        assertProcessInActivity(processInstance.getProcessInstanceId(), MultiInstanceProcessConstant.USER_TASK_ACTIVITY_ID.getValue());

        final List<Task> tasks = activitiRule.getTaskService().createTaskQuery()
                .taskDefinitionKey(MultiInstanceProcessConstant.USER_TASK_ACTIVITY_ID.getValue()).list();
        assertThat(tasks.size(), is(3));

        // End task 1
        taskService.complete(tasks.get(0).getId());
        assertProcessInActivity(processInstance.getProcessInstanceId(), MultiInstanceProcessConstant.USER_TASK_ACTIVITY_ID.getValue());

        // End task 2
        taskService.complete(tasks.get(1).getId());
        assertProcessInActivity(processInstance.getProcessInstanceId(), MultiInstanceProcessConstant.USER_TASK_ACTIVITY_ID.getValue());

        // End task 3
        taskService.complete(tasks.get(2).getId());
        try {
            assertProcessInActivity(processInstance.getProcessInstanceId(), MultiInstanceProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
            fail("Expected AssertionError for final task");
        } catch (AssertionError ae) {
            // no-op, expected for final task
        }
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void assertProcessInActivityFailureForOtherActivityId() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessInActivity(processInstance.getProcessInstanceId(), TwoUserTasksProcessConstant.USER_TASK_2_ACTIVITY_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void assertProcessInActivityFailureForOtherProcessInstance() throws Exception {
        final ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        // Move on to end process 1
        final Task task = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue())
                .singleResult();
        taskService.complete(task.getId());

        // start another instance
        final ProcessInstance processInstance2 = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessInActivity(processInstance2.getProcessInstanceId(), SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());

        assertProcessInActivity(processInstance1.getProcessInstanceId(), SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void assertProcessInActivityFailureForSuspendedProcessInstance() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessInActivity(processInstance.getProcessInstanceId(), SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());

        // suspend the process instance
        runtimeService.suspendProcessInstanceById(processInstance.getProcessInstanceId());
        assertProcessInActivity(processInstance.getProcessInstanceId(), SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void assertProcessInActivityFailureForEndedProcessInstance() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());

        // Move on to end process
        final Task task = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue())
                .singleResult();
        taskService.complete(task.getId());

        assertProcessInActivity(processInstance.getProcessInstanceId(), SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void assertProcessInActivityFailureForActivityThatNeverExisted() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());

        // Move on to end process
        final Task task = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue())
                .singleResult();
        taskService.complete(task.getId());

        assertProcessInActivity(processInstance.getProcessInstanceId(), TwoUserTasksProcessConstant.USER_TASK_2_ACTIVITY_ID.getValue());
    }

}
