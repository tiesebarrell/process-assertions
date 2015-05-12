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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;
import static org.toxos.activiti.assertion.ProcessAssert.assertProcessInActivity;
import static org.toxos.activiti.assertion.internal.Assert.assertThat;

import java.util.List;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.toxos.activiti.assertion.process.MultiInstanceProcessConstant;
import org.toxos.activiti.assertion.process.SingleUserTaskProcessConstant;
import org.toxos.activiti.assertion.process.TwoUserTasksProcessConstant;

/**
 * Tests for assertions that test a process is in an activity.
 * 
 * @author Tiese Barrell
 * 
 */
@ContextConfiguration("classpath:application-context.xml")
public class ProcessIsInActivityAssertionsTest extends AbstractProcessAssertTest {

    @Test
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessInActivityForProcessInstanceObjectAndActivityId() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessInActivity(processInstance, SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
    }

    @Test
    @Deployment(resources = BPMN_MULTI_INSTANCE)
    public void testProcessInActivityForProcessInstanceObjectAndActivityIdForMultipleExecutions() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(MultiInstanceProcessConstant.PROCESS_KEY.getValue());
        assertProcessInActivity(processInstance, MultiInstanceProcessConstant.USER_TASK_ACTIVITY_ID.getValue());

        final List<Task> tasks = activitiRule.getTaskService().createTaskQuery()
                .taskDefinitionKey(MultiInstanceProcessConstant.USER_TASK_ACTIVITY_ID.getValue()).list();
        assertThat(tasks.size(), is(3));

        // End task 1
        taskService.complete(tasks.get(0).getId());
        assertProcessInActivity(processInstance, MultiInstanceProcessConstant.USER_TASK_ACTIVITY_ID.getValue());

        // End task 2
        taskService.complete(tasks.get(1).getId());
        assertProcessInActivity(processInstance, MultiInstanceProcessConstant.USER_TASK_ACTIVITY_ID.getValue());

        // End task 3
        taskService.complete(tasks.get(2).getId());
        try {
            assertProcessInActivity(processInstance, MultiInstanceProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
            fail("Expected AssertionError for final task");
        } catch (AssertionError ae) {
            // no-op, expected for final task
        }
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessInActivityFailureForProcessInstanceObjectAndActivityIdForOtherActivityId() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessInActivity(processInstance, TwoUserTasksProcessConstant.USER_TASK_2_ACTIVITY_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessInActivityFailureForProcessInstanceObjectAndActivityIdForOtherProcessInstance() throws Exception {
        final ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        // Move on to end process 1
        final Task task = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue())
                .singleResult();
        taskService.complete(task.getId());

        // start another instance
        final ProcessInstance processInstance2 = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessInActivity(processInstance2, SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());

        assertProcessInActivity(processInstance1, SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessInActivityFailureForProcessInstanceObjectAndActivityIdForSuspendedProcessInstance() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessInActivity(processInstance, SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());

        // suspend the process instance
        runtimeService.suspendProcessInstanceById(processInstance.getProcessInstanceId());
        assertProcessInActivity(processInstance, SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessInActivityFailureForProcessInstanceObjectAndActivityIdForEndedProcessInstance() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());

        // Move on to end process
        final Task task = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue())
                .singleResult();
        taskService.complete(task.getId());

        assertProcessInActivity(processInstance, SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessInActivityFailureForProcessInstanceObjectAndActivityIdForActivityThatNeverExisted() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());

        // Move on to end process
        final Task task = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue())
                .singleResult();
        taskService.complete(task.getId());

        assertProcessInActivity(processInstance, TwoUserTasksProcessConstant.USER_TASK_2_ACTIVITY_ID.getValue());
    }

    @Test(expected = NullPointerException.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessInActivityFailureForNullProcessInstanceObjectAndActivityId() throws Exception {
        runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        final ProcessInstance nullInstance = null;
        assertProcessInActivity(nullInstance, SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
    }

    @Test(expected = NullPointerException.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessInActivityFailureForProcessInstanceObjectAndNullActivityId() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        final String nullActivityId = null;
        assertProcessInActivity(processInstance, nullActivityId);
    }

    @Test
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessInActivityForProcessInstanceIdAndActivityId() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessInActivity(processInstance.getProcessInstanceId(), SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
    }

    @Test
    @Deployment(resources = BPMN_MULTI_INSTANCE)
    public void testProcessInActivityForProcessInstanceIdAndActivityIdForMultipleExecutions() throws Exception {
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
    public void testProcessInActivityFailureForProcessInstanceIdAndActivityIdForOtherActivityId() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessInActivity(processInstance.getProcessInstanceId(), TwoUserTasksProcessConstant.USER_TASK_2_ACTIVITY_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessInActivityFailureForProcessInstanceIdAndActivityIdForOtherProcessInstance() throws Exception {
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
    public void testProcessInActivityFailureForProcessInstanceIdAndActivityIdForSuspendedProcessInstance() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessInActivity(processInstance.getProcessInstanceId(), SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());

        // suspend the process instance
        runtimeService.suspendProcessInstanceById(processInstance.getProcessInstanceId());
        assertProcessInActivity(processInstance.getProcessInstanceId(), SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessInActivityFailureForProcessInstanceIdAndActivityIdForEndedProcessInstance() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());

        // Move on to end process
        final Task task = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue())
                .singleResult();
        taskService.complete(task.getId());

        assertProcessInActivity(processInstance.getProcessInstanceId(), SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessInActivityFailureForProcessInstanceIdAndActivityIdForActivityThatNeverExisted() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());

        // Move on to end process
        final Task task = activitiRule.getTaskService().createTaskQuery().taskDefinitionKey(SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue())
                .singleResult();
        taskService.complete(task.getId());

        assertProcessInActivity(processInstance.getProcessInstanceId(), TwoUserTasksProcessConstant.USER_TASK_2_ACTIVITY_ID.getValue());
    }

    @Test(expected = NullPointerException.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessInActivityFailureForNullProcessInstanceIdAndActivityId() throws Exception {
        runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        final ProcessInstance nullInstance = null;
        assertProcessInActivity(nullInstance, SingleUserTaskProcessConstant.USER_TASK_ACTIVITY_ID.getValue());
    }

    @Test(expected = NullPointerException.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessInActivityFailureForProcessInstanceIdAndNullActivityId() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        final String nullActivityId = null;
        assertProcessInActivity(processInstance.getProcessInstanceId(), nullActivityId);
    }

}
