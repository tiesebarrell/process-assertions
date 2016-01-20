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

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link TaskInstanceAssert#taskIsUncompleted(String, String)}.
 * 
 * @author Tiese Barrell
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskInstanceAssertTaskIsUncompletedByDefinitionIdTest extends TaskInstanceAssertTestBase {

    private final String taskDefinitionKey = "testTask";

    @Before
    public void beforeTest() throws Exception {
        when(taskQueryMock.processInstanceId(processInstanceId)).thenReturn(taskQueryMock);
        when(taskQueryMock.taskDefinitionKey(taskDefinitionKey)).thenReturn(taskQueryMock);

        final List<Task> tasks = new ArrayList<Task>(1);
        tasks.add(taskMock);

        when(taskQueryMock.list()).thenReturn(tasks);
    }

    @Test
    public void testTaskIsUncompleted() throws Exception {
        classUnderTest.taskIsUncompleted(processInstanceId, taskDefinitionKey);
    }

    @Test(expected = AssertionError.class)
    public void testTaskIsUncompleted_NullTasks() throws Exception {
        when(taskQueryMock.list()).thenReturn(null);
        classUnderTest.taskIsUncompleted(processInstanceId, taskDefinitionKey);
    }

    @Test(expected = AssertionError.class)
    public void testTaskIsUncompleted_NoActiveTasks() throws Exception {
        when(taskQueryMock.list()).thenReturn(new ArrayList<Task>());
        classUnderTest.taskIsUncompleted(processInstanceId, taskDefinitionKey);
    }

    @Test(expected = AssertionError.class)
    public void testTaskIsUncompleted_ProcessInstanceNotActive() throws Exception {
        when(processInstanceQueryMock.singleResult()).thenReturn(null);
        classUnderTest.taskIsUncompleted(processInstanceId, taskDefinitionKey);
    }

}
