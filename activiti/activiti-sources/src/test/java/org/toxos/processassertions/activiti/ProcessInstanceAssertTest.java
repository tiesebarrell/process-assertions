package org.toxos.processassertions.activiti;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.toxos.processassertions.api.LogMessage;
import org.toxos.processassertions.api.internal.ApiCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Test cases for {@link ProcessInstanceAssert}.
 *
 * @author Tiese Barrell
 */
@RunWith(MockitoJUnitRunner.class)
public class ProcessInstanceAssertTest {

    private ProcessInstanceAssert classUnderTest;

    @Mock
    private ApiCallback apiCallbackMock;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ProcessAssertActivitiConfiguration configurationMock;

    @Mock
    private ProcessInstance processInstanceMock;

    @Mock
    private HistoricProcessInstance historicProcessInstanceMock;

    @Mock
    private Execution executionMock;

    private final String processInstanceId = "process-instance-123";
    private final String activityId = "activity-234";

    private List<Execution> executions;

    @Before
    public void before() {
        executions = new ArrayList<>();
        executions.add(executionMock);

        when(configurationMock
                .getProcessEngine()
                .getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult())
                .thenReturn(processInstanceMock);

        when(configurationMock
                .getProcessEngine()
                .getRuntimeService()
                .createExecutionQuery()
                .processInstanceId(processInstanceId)
                .activityId(activityId)
                .list())
                .thenReturn(executions);

        when(configurationMock
                .getProcessEngine()
                .getHistoryService()
                .createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult())
                .thenReturn(historicProcessInstanceMock);

        when(processInstanceMock.isEnded()).thenReturn(false);
        when(processInstanceMock.isSuspended()).thenReturn(false);

        when(historicProcessInstanceMock.getEndTime()).thenReturn(null);

        classUnderTest = new ProcessInstanceAssert(apiCallbackMock, configurationMock);
    }

    @Test
    public void processIsActive() {
        classUnderTest.processIsActive(processInstanceId);
        verify(apiCallbackMock, times(1)).trace(LogMessage.PROCESS_2, processInstanceId);
        verify(apiCallbackMock, times(1)).trace(LogMessage.PROCESS_7, processInstanceId);
        verify(apiCallbackMock, times(1)).trace(LogMessage.PROCESS_8, processInstanceId);
        verify(apiCallbackMock, times(1)).trace(LogMessage.PROCESS_3, processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void processIsActiveFailsForNoProcessInstanceFound() {
        when(configurationMock
                .getProcessEngine()
                .getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult())
                .thenReturn(null);
        classUnderTest.processIsActive(processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void processIsActiveFailsForEndedProcessInstance() {
        when(processInstanceMock.isEnded()).thenReturn(true);
        classUnderTest.processIsActive(processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void processIsActiveFailsForSuspendedProcessInstance() {
        when(processInstanceMock.isSuspended()).thenReturn(true);
        classUnderTest.processIsActive(processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void processIsActiveFailsForNoHistoricProcessInstance() {
        when(configurationMock
                .getProcessEngine()
                .getHistoryService()
                .createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult())
                .thenReturn(null);
        classUnderTest.processIsActive(processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void processIsActiveFailsForEndedHistoricProcessInstance() {
        when(historicProcessInstanceMock.getEndTime()).thenReturn(new Date());
        classUnderTest.processIsActive(processInstanceId);
    }

    @Test
    public void processIsEnded() {
        when(configurationMock
                .getProcessEngine()
                .getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult())
                .thenReturn(null);
        when(historicProcessInstanceMock.getEndTime()).thenReturn(new Date());
        classUnderTest.processIsEnded(processInstanceId);
        verify(apiCallbackMock, times(1)).trace(LogMessage.PROCESS_6, processInstanceId);
        verify(apiCallbackMock, times(1)).trace(LogMessage.PROCESS_4, processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void processIsEndedFailsForActiveProcessInstance() {
        classUnderTest.processIsEnded(processInstanceId);
    }

    @Test(expected = AssertionError.class)
    public void processIsEndedFailsForHistoricProcessInstanceNotEnded() {
        when(configurationMock
                .getProcessEngine()
                .getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult())
                .thenReturn(null);
        when(historicProcessInstanceMock.getEndTime()).thenReturn(null);
        classUnderTest.processIsEnded(processInstanceId);
    }

    @Test
    public void processIsInActivity() {
        classUnderTest.processIsInActivity(processInstanceId, activityId);
        verify(apiCallbackMock, times(1)).trace(LogMessage.PROCESS_14, processInstanceId, activityId);
    }

    @Test(expected = AssertionError.class)
    public void processIsInActivityFailsForNoProcessInstanceFound() {
        when(configurationMock
                .getProcessEngine()
                .getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult())
                .thenReturn(null);
        classUnderTest.processIsInActivity(processInstanceId, activityId);
    }

    @Test(expected = AssertionError.class)
    public void processIsInActivityFailsForEmptyExecutions() {
        executions.clear();
        classUnderTest.processIsInActivity(processInstanceId, activityId);
    }

}
