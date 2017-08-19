package org.toxos.processassertions.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.toxos.processassertions.api.internal.*;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.isA;
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
    @Mock
    private AssertFactory assertFactoryMock;
    @Mock
    private ProcessInstanceAssertable processInstanceAssertableMock;
    @Mock
    private TaskInstanceAssertable taskInstanceAssertableMock;
    @Mock
    private EndEventAssertable endEventAssertableMock;
    @Mock
    private HistoricVariableInstanceAssertable historicVariableInstanceAssertableMock;

    @Before
    public void before() {
        new TestProcessAssertConfiguration(assertFactoryMock).register();
        when(assertFactoryMock.getProcessInstanceAssertable(isA(ApiCallback.class))).thenReturn(processInstanceAssertableMock);
        when(assertFactoryMock.getTaskInstanceAssertable(isA(ApiCallback.class))).thenReturn(taskInstanceAssertableMock);
        when(assertFactoryMock.getEndEventAssertable(isA(ApiCallback.class))).thenReturn(endEventAssertableMock);
        when(assertFactoryMock.getHistoricVariableInstanceAssertable(isA(ApiCallback.class))).thenReturn(historicVariableInstanceAssertableMock);
    }

    @Test
    public void setConfigurationHasNewConfiguration() {
        final ProcessAssertConfiguration initial = ProcessAssert.getConfiguration();
        assertThat(initial, is(notNullValue()));
        ProcessAssert.setConfiguration(new TestProcessAssertConfiguration());
        assertThat(ProcessAssert.getConfiguration(), is(allOf(notNullValue(), not(sameInstance(initial)))));
    }

    @Test
    public void assertProcessActive() {
        ProcessAssert.assertProcessActive(processInstanceId);
        verify(processInstanceAssertableMock, times(1)).processIsActive(processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void assertProcessActiveFailsFromAssertable() {
        doThrow(AssertionError.class).when(processInstanceAssertableMock).processIsActive(processInstanceId);
        ProcessAssert.assertProcessActive(processInstanceId);
    }

    @Test(expected = NullPointerException.class)
    public void assertProcessActiveExceptionForNullProcessInstanceId() {
        ProcessAssert.assertProcessActive(null);
    }

    @Test
    public void assertProcessEnded() {
        ProcessAssert.assertProcessEnded(processInstanceId);
        verify(processInstanceAssertableMock, times(1)).processIsEnded(processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void assertProcessEndedFailsFromAssertable() {
        doThrow(AssertionError.class).when(processInstanceAssertableMock).processIsEnded(processInstanceId);
        ProcessAssert.assertProcessEnded(processInstanceId);
    }

    @Test(expected = NullPointerException.class)
    public void assertProcessEndedExceptionForNullProcessInstanceId() {
        ProcessAssert.assertProcessEnded(null);
    }

    @Test
    public void assertProcessInActivity() {
        ProcessAssert.assertProcessInActivity(processInstanceId, activityId);
        verify(processInstanceAssertableMock, times(1)).processIsInActivity(processInstanceId, activityId);
    }

    @Test(expected = AssertionError.class)
    public void assertProcessInActivityFailsFromAssertable() {
        doThrow(AssertionError.class).when(processInstanceAssertableMock).processIsInActivity(processInstanceId, activityId);
        ProcessAssert.assertProcessInActivity(processInstanceId, activityId);
    }

    @Test(expected = NullPointerException.class)
    public void assertProcessInActivityExceptionForNullProcessInstanceId() {
        ProcessAssert.assertProcessInActivity(null, activityId);
    }

    @Test(expected = NullPointerException.class)
    public void assertProcessInActivityExceptionForNullActivityId() {
        ProcessAssert.assertProcessInActivity(processInstanceId, null);
    }

    @Test
    public void assertTaskUncompletedByTaskId() {
        ProcessAssert.assertTaskUncompleted(taskId);
        verify(taskInstanceAssertableMock, times(1)).taskIsUncompleted(taskId);
    }

    @Test(expected = AssertionError.class)
    public void assertTaskUncompletedByTaskIdFailsFromAssertable() {
        doThrow(AssertionError.class).when(taskInstanceAssertableMock).taskIsUncompleted(taskId);
        ProcessAssert.assertTaskUncompleted(taskId);
    }

    @Test(expected = NullPointerException.class)
    public void assertTaskUncompletedByTaskIdExceptionForNullTaskId() {
        ProcessAssert.assertTaskUncompleted(null);
    }

    @Test
    public void assertTaskUncompletedByTaskKey() {
        ProcessAssert.assertTaskUncompleted(processInstanceId, taskKey);
        verify(taskInstanceAssertableMock, times(1)).taskIsUncompleted(processInstanceId, taskKey);
    }

    @Test(expected = AssertionError.class)
    public void assertTaskUncompletedByTaskKeyFailsFromAssertable() {
        doThrow(AssertionError.class).when(taskInstanceAssertableMock).taskIsUncompleted(processInstanceId, taskKey);
        ProcessAssert.assertTaskUncompleted(processInstanceId, taskKey);
    }

    @Test(expected = NullPointerException.class)
    public void assertTaskUncompletedByTaskKeyExceptionForNullProcessInstanceId() {
        ProcessAssert.assertTaskUncompleted(null, taskKey);
    }

    @Test(expected = NullPointerException.class)
    public void assertTaskUncompletedByTaskKeyExceptionForNullTaskKey() {
        ProcessAssert.assertTaskUncompleted(processInstanceId, null);
    }

    @Test
    public void assertProcessEndedAndInExclusiveEndEvent() {
        ProcessAssert.assertProcessEndedAndInExclusiveEndEvent(processInstanceId, endEventId1);
        verify(endEventAssertableMock, times(1)).processEndedAndInExclusiveEndEvent(processInstanceId, endEventId1);
    }

    @Test(expected = AssertionError.class)
    public void assertProcessEndedAndInExclusiveEndEventFailsFromAssertable() {
        doThrow(AssertionError.class).when(endEventAssertableMock).processEndedAndInExclusiveEndEvent(processInstanceId, endEventId1);
        ProcessAssert.assertProcessEndedAndInExclusiveEndEvent(processInstanceId, endEventId1);
    }

    @Test(expected = NullPointerException.class)
    public void assertProcessEndedAndInExclusiveEndEventExceptionForNullProcessInstanceId() {
        ProcessAssert.assertProcessEndedAndInExclusiveEndEvent(null, endEventId1);
    }

    @Test(expected = NullPointerException.class)
    public void assertProcessEndedAndInExclusiveEndEventExceptionForNullEndEventId() {
        ProcessAssert.assertProcessEndedAndInExclusiveEndEvent(processInstanceId, null);
    }

    @Test
    public void assertProcessEndedAndInEndEvents() {
        ProcessAssert.assertProcessEndedAndInEndEvents(processInstanceId, endEventIds);
        verify(endEventAssertableMock, times(1)).processEndedAndInEndEvents(processInstanceId, endEventIds);
    }

    @Test(expected = AssertionError.class)
    public void assertProcessEndedAndInEndEventsFailsFromAssertable() {
        doThrow(AssertionError.class).when(endEventAssertableMock).processEndedAndInEndEvents(processInstanceId, endEventIds);
        ProcessAssert.assertProcessEndedAndInEndEvents(processInstanceId, endEventIds);
    }

    @Test(expected = NullPointerException.class)
    public void assertProcessEndedAndInEndEventsExceptionForNullProcessInstanceId() {
        ProcessAssert.assertProcessEndedAndInEndEvents(null, endEventIds);
    }

    @Test(expected = NullPointerException.class)
    public void assertProcessEndedAndInEndEventsExceptionForNullEndEventIds() {
        ProcessAssert.assertProcessEndedAndInEndEvents(processInstanceId, null);
    }

    @Test
    public void assertProcessVariableLatestValueEquals() throws Exception {
        ProcessAssert.assertProcessVariableLatestValueEquals(processInstanceId, variableName, variableValue);
        verify(historicVariableInstanceAssertableMock, times(1)).historicProcessVariableLatestValueEquals(processInstanceId, variableName, variableValue);
    }

    @Test(expected = AssertionError.class)
    public void assertProcessVariableLatestValueEqualsFailsFromAssertable() throws Exception {
        doThrow(AssertionError.class).when(historicVariableInstanceAssertableMock)
                .historicProcessVariableLatestValueEquals(processInstanceId, variableName, variableValue);
        ProcessAssert.assertProcessVariableLatestValueEquals(processInstanceId, variableName, variableValue);
    }

    @Test(expected = AssertionError.class)
    public void assertProcessVariableLatestValueEqualsFailsFromAssertableProcessAssertionsException() throws Exception {
        doThrow(ProcessAssertionsException.class).when(historicVariableInstanceAssertableMock)
                .historicProcessVariableLatestValueEquals(processInstanceId, variableName, variableValue);
        ProcessAssert.assertProcessVariableLatestValueEquals(processInstanceId, variableName, variableValue);
    }

    @Test(expected = NullPointerException.class)
    public void assertProcessVariableLatestValueEqualsExceptionForNullProcessInstanceId() {
        ProcessAssert.assertProcessVariableLatestValueEquals(null, variableName, variableValue);
    }

    @Test(expected = NullPointerException.class)
    public void assertProcessVariableLatestValueEqualsExceptionForNullVariableName() {
        ProcessAssert.assertProcessVariableLatestValueEquals(processInstanceId, null, variableValue);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void assertProcessEndedAndReachedEndStateLastException() {
        ProcessAssert.assertProcessEndedAndReachedEndStateLast(processInstanceId, endEventId1);
    }

}
