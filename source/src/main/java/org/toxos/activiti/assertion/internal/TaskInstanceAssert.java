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
package org.toxos.activiti.assertion.internal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.toxos.activiti.assertion.internal.Assert.assertThat;

import java.util.List;

import org.activiti.engine.task.Task;
import org.toxos.activiti.assertion.LogMessage;
import org.toxos.activiti.assertion.ProcessAssertConfiguration;

/**
 * Provides assertions for task instances.
 * 
 * @author Tiese Barrell
 */
final class TaskInstanceAssert extends ProcessAssertableBase implements TaskInstanceAssertable {

    protected TaskInstanceAssert(ProcessAssertConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void taskIsUncompleted(final String taskId) {

        // Assert a task exists
        trace(LogMessage.TASK_3, taskId);
        final Task task = getTaskService().createTaskQuery().taskId(taskId).active().singleResult();
        assertThat(task, is(notNullValue()));

        // Assert the process is not completed
        new ProcessInstanceAssert(getConfiguration()).processIsActive(task.getProcessInstanceId());

    }

    @Override
    public void taskIsUncompleted(final String processInstanceId, final String taskDefinitionKey) {

        // Assert the process is not completed
        new ProcessInstanceAssert(getConfiguration()).processIsActive(processInstanceId);

        // Assert a task exists
        trace(LogMessage.TASK_4, taskDefinitionKey, processInstanceId);
        final List<Task> tasks = getTaskService().createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey(taskDefinitionKey).active().list();

        assertThat(tasks, is(notNullValue()));
        assertThat(tasks.isEmpty(), is(false));

    }

}
