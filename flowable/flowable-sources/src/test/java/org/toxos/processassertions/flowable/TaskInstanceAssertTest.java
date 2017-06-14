package org.toxos.processassertions.flowable;

import org.flowable.engine.task.Task;
import org.flowable.engine.task.TaskQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.toxos.processassertions.api.LogMessage;
import org.toxos.processassertions.api.internal.ApiCallback;
import org.toxos.processassertions.api.internal.ProcessInstanceAssertable;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Test cases for {@link TaskInstanceAssert}.
 *
 * @author Tiese Barrell
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskInstanceAssertTest {

    private TaskInstanceAssert classUnderTest;

    @Mock
    private ApiCallback apiCallbackMock;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ProcessAssertFlowableConfiguration configurationMock;

    @Mock
    private ProcessInstanceAssertable processInstanceAssertableMock;

    @Mock
    private Task taskInstanceMock1;

    @Mock
    private Task taskInstanceMock2;

    @Mock
    private TaskQuery taskQueryMock;

    private final String taskId = "task-123";
    private final String taskKey = "task-key-234";
    private final String processInstanceId = "process-instance-345";

    private List<Task> taskInstances;

    @Before
    public void before() {
        taskInstances = new ArrayList<>();
        taskInstances.add(taskInstanceMock1);

        when(configurationMock
                .getAssertFactory()
                .getProcessInstanceAssertable(apiCallbackMock))
                .thenReturn(processInstanceAssertableMock);

        when(configurationMock
                .getProcessEngine()
                .getTaskService()
                .createTaskQuery()).thenReturn(taskQueryMock);

        when(taskQueryMock.taskId(taskId)).thenReturn(taskQueryMock);
        when(taskQueryMock.active()).thenReturn(taskQueryMock);
        when(taskQueryMock.singleResult()).thenReturn(taskInstanceMock1);

        when(taskQueryMock.processInstanceId(processInstanceId)).thenReturn(taskQueryMock);
        when(taskQueryMock.taskDefinitionKey(taskKey)).thenReturn(taskQueryMock);
        when(taskQueryMock.active()).thenReturn(taskQueryMock);
        when(taskQueryMock.list()).thenReturn(taskInstances);

        when(taskInstanceMock1.getProcessInstanceId()).thenReturn(processInstanceId);

        classUnderTest = new TaskInstanceAssert(apiCallbackMock, configurationMock);
    }

    @Test
    public void taskIsUncompletedForTask() {
        classUnderTest.taskIsUncompleted(taskId);
        verify(apiCallbackMock, times(1)).trace(LogMessage.TASK_3, taskId);
    }

    @Test(expected = AssertionError.class)
    public void taskIsUncompletedForTaskFailsForNoTask() {
        when(taskQueryMock.singleResult()).thenReturn(null);
        classUnderTest.taskIsUncompleted(taskId);
    }

    @Test(expected = AssertionError.class)
    public void taskIsUncompletedForTaskFailsForNonActiveProcessInstance() {
        doThrow(AssertionError.class).when(processInstanceAssertableMock).processIsActive(processInstanceId);
        classUnderTest.taskIsUncompleted(taskId);
    }

    @Test
    public void taskIsUncompletedForTaskKey() {
        classUnderTest.taskIsUncompleted(processInstanceId, taskKey);
        verify(apiCallbackMock, times(1)).trace(LogMessage.TASK_4, taskKey, processInstanceId);
    }

    @Test
    public void taskIsUncompletedForTaskKeyForMultipleTasks() {
        taskInstances.add(taskInstanceMock2);
        classUnderTest.taskIsUncompleted(processInstanceId, taskKey);
        verify(apiCallbackMock, times(1)).trace(LogMessage.TASK_4, taskKey, processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void taskIsUncompletedForTaskKeyFailsForNonActiveProcessInstance() {
        doThrow(AssertionError.class).when(processInstanceAssertableMock).processIsActive(processInstanceId);
        classUnderTest.taskIsUncompleted(processInstanceId, taskKey);
    }

    @Test(expected = AssertionError.class)
    public void taskIsUncompletedForTaskKeyFailsForNoTasks() {
        taskInstances.clear();
        classUnderTest.taskIsUncompleted(processInstanceId, taskKey);
    }

    @Test(expected = AssertionError.class)
    public void taskIsUncompletedForTaskKeyFailsForNullTasks() {
        when(taskQueryMock.list()).thenReturn(null);
        classUnderTest.taskIsUncompleted(processInstanceId, taskKey);
    }

}
