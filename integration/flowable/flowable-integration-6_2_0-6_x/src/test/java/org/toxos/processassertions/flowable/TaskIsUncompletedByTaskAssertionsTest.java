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
package org.toxos.processassertions.flowable;

import org.flowable.engine.test.Deployment;
import org.flowable.task.api.Task;
import org.junit.Test;
import org.toxos.processassertions.flowable.integration.configuration.AbstractProcessAssertTest;
import org.toxos.processassertions.integration.common.process.SingleUserTaskProcessConstant;
import org.toxos.processassertions.integration.common.process.TwoUserTasksProcessConstant;

import static org.toxos.processassertions.api.ProcessAssert.assertTaskUncompleted;

/**
 * Tests for assertions that test a task is uncompleted by the task.
 * 
 * @author Tiese Barrell
 * 
 */
public class TaskIsUncompletedByTaskAssertionsTest extends AbstractProcessAssertTest {

    @Test
    @Deployment(resources = BPMN_TWO_USER_TASKS)
    public void assertTaskUncompletedSuccess() throws Exception {
        runtimeService.startProcessInstanceByKey(TwoUserTasksProcessConstant.PROCESS_KEY.getValue());
        final Task task1 = taskService.createTaskQuery().taskDefinitionKey(TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue())
                .singleResult();
        assertTaskUncompleted(task1.getId());

        // Move on to task two and test
        taskService.complete(task1.getId());
        final Task task2 = taskService.createTaskQuery().taskDefinitionKey(TwoUserTasksProcessConstant.USER_TASK_2_ACTIVITY_ID.getValue())
                .singleResult();
        assertTaskUncompleted(task2.getId());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_TWO_USER_TASKS)
    public void assertTaskUncompletedForCompletedTask() throws Exception {
        runtimeService.startProcessInstanceByKey(TwoUserTasksProcessConstant.PROCESS_KEY.getValue());
        final Task task1 = taskService.createTaskQuery().taskDefinitionKey(TwoUserTasksProcessConstant.USER_TASK_1_ACTIVITY_ID.getValue())
                .singleResult();
        taskService.complete(task1.getId());

        assertTaskUncompleted(task1.getId());
    }

    @Test(expected = NullPointerException.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void assertTaskUncompletedFailureForNullTaskId() throws Exception {
        runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        final String nullString = null;
        assertTaskUncompleted(nullString);
    }

}
