package org.toxos.processassertions.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.toxos.processassertions.api.internal.*;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import static org.toxos.processassertions.api.internal.Assert.assertThat;

/**
 * Test cases for {@link ProcessAssert}.
 *
 * @author Tiese Barrell
 */
@RunWith(MockitoJUnitRunner.class)
public class ProcessAssertTest {

    private final String processInstanceId = "process-instance-123";
    private final String activityId = "activity-234";
    private final String taskId = "task-345";
    private final String taskKey = "task-key-456";
    private final String endEventId1 = "end-event-567";
    private final String endEventId2 = "end-event-678";
    private final String[] endEventIds = { endEventId1, endEventId2 };
    private final String variableName = "variable-name-789";
    private final String variableValue = "variable-value-890";
    @Mock private ApiCallback apiCallbackMock;
    @Mock private AssertFactory assertFactoryMock;
    @Mock private ProcessInstanceAssertable processInstanceAssertableMock;
    @Mock private TaskInstanceAssertable taskInstanceAssertableMock;
    @Mock private EndEventAssertable endEventAssertableMock;
    @Mock private HistoricVariableInstanceAssertable historicVariableInstanceAssertableMock;

    @Before public void before() {
        ProcessAssert.setConfiguration(new TestProcessAssertConfiguration(assertFactoryMock));
        ProcessAssert.setApiCallback(apiCallbackMock);
        when(assertFactoryMock.getProcessInstanceAssertable(apiCallbackMock)).thenReturn(processInstanceAssertableMock);
        when(assertFactoryMock.getTaskInstanceAssertable(apiCallbackMock)).thenReturn(taskInstanceAssertableMock);
        when(assertFactoryMock.getEndEventAssertable(apiCallbackMock)).thenReturn(endEventAssertableMock);
        when(assertFactoryMock.getHistoricVariableInstanceAssertable(apiCallbackMock)).thenReturn(historicVariableInstanceAssertableMock);
    }

    @Test public void setConfigurationHasNewConfiguration() {
        final ProcessAssertConfiguration initial = ProcessAssert.getConfiguration();
        assertThat(initial, is(notNullValue()));
        ProcessAssert.setConfiguration(new TestProcessAssertConfiguration());
        assertThat(ProcessAssert.getConfiguration(), is(allOf(notNullValue(), not(sameInstance(initial)))));
    }

    @Test public void assertProcessActive() {
        ProcessAssert.assertProcessActive(processInstanceId);
        verify(apiCallbackMock, times(1)).debug(LogMessage.PROCESS_1, processInstanceId);
        verify(processInstanceAssertableMock, times(1)).processIsActive(processInstanceId);
    }

    @Test public void assertProcessActiveFailsFromAssertable() {
        doThrow(AssertionError.class).when(processInstanceAssertableMock).processIsActive(processInstanceId);
        ProcessAssert.assertProcessActive(processInstanceId);
        verify(apiCallbackMock, times(1)).debug(LogMessage.PROCESS_1, processInstanceId);
        verify(processInstanceAssertableMock, times(1)).processIsActive(processInstanceId);
        verify(apiCallbackMock, times(1)).fail(LogMessage.ERROR_PROCESS_1, processInstanceId);
    }

    @Test(expected = NullPointerException.class) public void assertProcessActiveExceptionForNullProcessInstanceId() {
        ProcessAssert.assertProcessActive(null);
    }

    @Test public void assertProcessEnded() {
        ProcessAssert.assertProcessEnded(processInstanceId);
        verify(apiCallbackMock, times(1)).debug(LogMessage.PROCESS_5, processInstanceId);
        verify(processInstanceAssertableMock, times(1)).processIsEnded(processInstanceId);
    }

    @Test public void assertProcessEndedFailsFromAssertable() {
        doThrow(AssertionError.class).when(processInstanceAssertableMock).processIsEnded(processInstanceId);
        ProcessAssert.assertProcessEnded(processInstanceId);
        verify(apiCallbackMock, times(1)).debug(LogMessage.PROCESS_5, processInstanceId);
        verify(processInstanceAssertableMock, times(1)).processIsEnded(processInstanceId);
        verify(apiCallbackMock, times(1)).fail(LogMessage.ERROR_PROCESS_2, processInstanceId);
    }

    @Test(expected = NullPointerException.class) public void assertProcessEndedExceptionForNullProcessInstanceId() {
        ProcessAssert.assertProcessEnded(null);
    }

    @Test public void assertProcessInActivity() {
        ProcessAssert.assertProcessInActivity(processInstanceId, activityId);
        verify(apiCallbackMock, times(1)).debug(LogMessage.PROCESS_15, processInstanceId, activityId);
        verify(processInstanceAssertableMock, times(1)).processIsInActivity(processInstanceId, activityId);
    }

    @Test public void assertProcessInActivityFailsFromAssertable() {
        doThrow(AssertionError.class).when(processInstanceAssertableMock).processIsInActivity(processInstanceId, activityId);
        ProcessAssert.assertProcessInActivity(processInstanceId, activityId);
        verify(apiCallbackMock, times(1)).debug(LogMessage.PROCESS_15, processInstanceId, activityId);
        verify(processInstanceAssertableMock, times(1)).processIsInActivity(processInstanceId, activityId);
        verify(apiCallbackMock, times(1)).fail(LogMessage.ERROR_PROCESS_6, processInstanceId, activityId);
    }

    @Test(expected = NullPointerException.class) public void assertProcessInActivityExceptionForNullProcessInstanceId() {
        ProcessAssert.assertProcessInActivity(null, activityId);
    }

    @Test(expected = NullPointerException.class) public void assertProcessInActivityExceptionForNullActivityId() {
        ProcessAssert.assertProcessInActivity(processInstanceId, null);
    }

    @Test public void assertTaskUncompletedByTaskId() {
        ProcessAssert.assertTaskUncompleted(taskId);
        verify(apiCallbackMock, times(1)).debug(LogMessage.TASK_2, taskId);
        verify(taskInstanceAssertableMock, times(1)).taskIsUncompleted(taskId);
    }

    @Test public void assertTaskUncompletedByTaskIdFailsFromAssertable() {
        doThrow(AssertionError.class).when(taskInstanceAssertableMock).taskIsUncompleted(taskId);
        ProcessAssert.assertTaskUncompleted(taskId);
        verify(apiCallbackMock, times(1)).debug(LogMessage.TASK_2, taskId);
        verify(taskInstanceAssertableMock, times(1)).taskIsUncompleted(taskId);
        verify(apiCallbackMock, times(1)).fail(LogMessage.ERROR_TASK_2, taskId);
    }

    @Test(expected = NullPointerException.class) public void assertTaskUncompletedByTaskIdExceptionForNullTaskId() {
        ProcessAssert.assertTaskUncompleted(null);
    }

    @Test public void assertTaskUncompletedByTaskKey() {
        ProcessAssert.assertTaskUncompleted(processInstanceId, taskKey);
        verify(apiCallbackMock, times(1)).debug(LogMessage.TASK_1, taskKey, processInstanceId);
        verify(taskInstanceAssertableMock, times(1)).taskIsUncompleted(processInstanceId, taskKey);
    }

    @Test public void assertTaskUncompletedByTaskKeyFailsFromAssertable() {
        doThrow(AssertionError.class).when(taskInstanceAssertableMock).taskIsUncompleted(processInstanceId, taskKey);
        ProcessAssert.assertTaskUncompleted(processInstanceId, taskKey);
        verify(apiCallbackMock, times(1)).debug(LogMessage.TASK_1, taskKey, processInstanceId);
        verify(taskInstanceAssertableMock, times(1)).taskIsUncompleted(processInstanceId, taskKey);
        verify(apiCallbackMock, times(1)).fail(LogMessage.ERROR_TASK_1, taskKey, processInstanceId);
    }

    @Test(expected = NullPointerException.class) public void assertTaskUncompletedByTaskKeyExceptionForNullProcessInstanceId() {
        ProcessAssert.assertTaskUncompleted(null, taskKey);
    }

    @Test(expected = NullPointerException.class) public void assertTaskUncompletedByTaskKeyExceptionForNullTaskKey() {
        ProcessAssert.assertTaskUncompleted(processInstanceId, null);
    }

    @Test public void assertProcessEndedAndInExclusiveEndEvent() {
        ProcessAssert.assertProcessEndedAndInExclusiveEndEvent(processInstanceId, endEventId1);
        verify(apiCallbackMock, times(1)).debug(LogMessage.PROCESS_9, processInstanceId, endEventId1);
        verify(endEventAssertableMock, times(1)).processEndedAndInExclusiveEndEvent(processInstanceId, endEventId1);
    }

    @Test public void assertProcessEndedAndInExclusiveEndEventFailsFromAssertable() {
        doThrow(AssertionError.class).when(endEventAssertableMock).processEndedAndInExclusiveEndEvent(processInstanceId, endEventId1);
        ProcessAssert.assertProcessEndedAndInExclusiveEndEvent(processInstanceId, endEventId1);
        verify(apiCallbackMock, times(1)).debug(LogMessage.PROCESS_9, processInstanceId, endEventId1);
        verify(endEventAssertableMock, times(1)).processEndedAndInExclusiveEndEvent(processInstanceId, endEventId1);
        verify(apiCallbackMock, times(1)).fail(LogMessage.ERROR_PROCESS_3, processInstanceId, endEventId1);
    }

    @Test(expected = NullPointerException.class) public void assertProcessEndedAndInExclusiveEndEventExceptionForNullProcessInstanceId() {
        ProcessAssert.assertProcessEndedAndInExclusiveEndEvent(null, endEventId1);
    }

    @Test(expected = NullPointerException.class) public void assertProcessEndedAndInExclusiveEndEventExceptionForNullEndEventId() {
        ProcessAssert.assertProcessEndedAndInExclusiveEndEvent(processInstanceId, null);
    }

    @Test public void assertProcessEndedAndInEndEvents() {
        ProcessAssert.assertProcessEndedAndInEndEvents(processInstanceId, endEventIds);
        verify(apiCallbackMock, times(1)).debug(LogMessage.PROCESS_11, processInstanceId, AssertUtils.arrayToString(endEventIds));
        verify(endEventAssertableMock, times(1)).processEndedAndInEndEvents(processInstanceId, endEventIds);
    }

    @Test public void assertProcessEndedAndInEndEventsFailsFromAssertable() {
        doThrow(AssertionError.class).when(endEventAssertableMock).processEndedAndInEndEvents(processInstanceId, endEventIds);
        ProcessAssert.assertProcessEndedAndInEndEvents(processInstanceId, endEventIds);
        verify(apiCallbackMock, times(1)).debug(LogMessage.PROCESS_11, processInstanceId, AssertUtils.arrayToString(endEventIds));
        verify(endEventAssertableMock, times(1)).processEndedAndInEndEvents(processInstanceId, endEventIds);
        verify(apiCallbackMock, times(1)).fail(LogMessage.ERROR_PROCESS_4, processInstanceId, AssertUtils.arrayToString(endEventIds));
    }

    @Test(expected = NullPointerException.class) public void assertProcessEndedAndInEndEventsExceptionForNullProcessInstanceId() {
        ProcessAssert.assertProcessEndedAndInEndEvents(null, endEventIds);
    }

    @Test(expected = NullPointerException.class) public void assertProcessEndedAndInEndEventsExceptionForNullEndEventIds() {
        ProcessAssert.assertProcessEndedAndInEndEvents(processInstanceId, null);
    }

    @Test public void assertProcessVariableLatestValueEquals() {
        ProcessAssert.assertProcessVariableLatestValueEquals(processInstanceId, variableName, variableValue);
        verify(apiCallbackMock, times(1)).debug(LogMessage.VARIABLE_3, variableName, processInstanceId, variableValue);
        verify(historicVariableInstanceAssertableMock, times(1)).historicProcessVariableLatestValueEquals(processInstanceId, variableName, variableValue);
    }

    @Test public void assertProcessVariableLatestValueEqualsFailsFromAssertable() {
        doThrow(AssertionError.class).when(historicVariableInstanceAssertableMock)
                .historicProcessVariableLatestValueEquals(processInstanceId, variableName, variableValue);
        ProcessAssert.assertProcessVariableLatestValueEquals(processInstanceId, variableName, variableValue);
        verify(apiCallbackMock, times(1)).debug(LogMessage.VARIABLE_3, variableName, processInstanceId, variableValue);
        verify(historicVariableInstanceAssertableMock, times(1)).historicProcessVariableLatestValueEquals(processInstanceId, variableName, variableValue);
        verify(apiCallbackMock, times(1)).fail(LogMessage.ERROR_VARIABLE_1, variableName, processInstanceId, variableValue);
    }

    @Test(expected = NullPointerException.class) public void assertProcessVariableLatestValueEqualsExceptionForNullProcessInstanceId() {
        ProcessAssert.assertProcessVariableLatestValueEquals(null, variableName, variableValue);
    }

    @Test(expected = NullPointerException.class) public void assertProcessVariableLatestValueEqualsExceptionForNullVariableName() {
        ProcessAssert.assertProcessVariableLatestValueEquals(processInstanceId, null, variableValue);
    }

    @Test(expected = UnsupportedOperationException.class) public void assertProcessEndedAndReachedEndStateLastException() {
        ProcessAssert.assertProcessEndedAndReachedEndStateLast(processInstanceId, endEventId1);
    }

}
