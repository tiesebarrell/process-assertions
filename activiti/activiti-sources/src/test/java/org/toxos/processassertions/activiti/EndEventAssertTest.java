package org.toxos.processassertions.activiti;

import org.activiti.engine.history.HistoricActivityInstance;
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
 * Test cases for {@link EndEventAssert}.
 *
 * @author Tiese Barrell
 */
@RunWith(MockitoJUnitRunner.class)
public class EndEventAssertTest {

    private EndEventAssert classUnderTest;

    @Mock
    private ApiCallback apiCallbackMock;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ProcessAssertActivitiConfiguration configurationMock;

    @Mock
    private ProcessInstanceAssertable processInstanceAssertableMock;

    @Mock
    private HistoricActivityInstance historicActivityInstanceMock1;

    private final String processInstanceId = "process-instance-123";

    private final String endEventId = "end-event-234";

    private List<HistoricActivityInstance> historicActivityInstances;

    @Before
    public void before() {
        historicActivityInstances = new ArrayList<>();
        historicActivityInstances.add(historicActivityInstanceMock1);

        when(historicActivityInstanceMock1.getActivityId()).thenReturn(endEventId);

        when(configurationMock
                .getAssertFactory()
                .getProcessInstanceAssertable(apiCallbackMock))
                .thenReturn(processInstanceAssertableMock);

        when(configurationMock
                .getProcessEngine()
                .getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult())
                .thenReturn(null);

        when(configurationMock
                .getProcessEngine()
                .getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .activityType("endEvent")
                .finished()
                .list())
                .thenReturn(historicActivityInstances);

        classUnderTest = new EndEventAssert(apiCallbackMock, configurationMock);
    }

    @Test
    public void processEndedAndInExclusiveEndEvent() {
        classUnderTest.processEndedAndInExclusiveEndEvent(processInstanceId, endEventId);
        verify(apiCallbackMock, times(1)).trace(LogMessage.PROCESS_10, processInstanceId, endEventId);
    }

    @Test(expected = AssertionError.class)
    public void processEndedAndInExclusiveEndEventFailsForNonEndedProcessInstance() {
        doThrow(AssertionError.class).when(processInstanceAssertableMock).processIsEnded(processInstanceId);
        classUnderTest.processEndedAndInExclusiveEndEvent(processInstanceId, endEventId);
    }

    @Test(expected = AssertionError.class)
    public void processEndedAndInExclusiveEndEventFailsForNoEndEvents() {
        historicActivityInstances.clear();
        classUnderTest.processEndedAndInExclusiveEndEvent(processInstanceId, endEventId);
    }

    @Test(expected = AssertionError.class)
    public void processEndedAndInExclusiveEndEventFailsForOtherEndEventId() {
        when(historicActivityInstanceMock1.getActivityId()).thenReturn("otherEventId");
        classUnderTest.processEndedAndInExclusiveEndEvent(processInstanceId, endEventId);
    }

    @Test
    public void processEndedAndInEndEvents() {

    }

}
