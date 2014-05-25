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

import org.activiti.engine.EngineServices;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Before;
import org.mockito.Mock;
import org.toxos.activiti.assertion.ProcessAssertConfiguration;

/**
 * Base class for assertable tests.
 * 
 * @author Tiese Barrell
 * 
 */
public class AssertableTestBase {

    @Mock
    protected ProcessAssertConfiguration processAssertConfigurationMock;

    @Mock
    protected EngineServices engineServicesMock;

    @Mock
    protected RuntimeService runtimeServiceMock;

    @Mock
    protected HistoryService historyServiceMock;

    @Mock
    protected TaskService taskServiceMock;

    @Mock
    protected ProcessInstanceQuery processInstanceQueryMock;

    @Mock
    protected HistoricProcessInstanceQuery historicProcessInstanceQueryMock;

    @Mock
    protected HistoricActivityInstanceQuery historicActivityInstanceQueryMock;

    @Mock
    protected TaskQuery taskQueryMock;

    @Mock
    protected ProcessInstance processInstanceMock;

    @Mock
    protected HistoricProcessInstance historicProcessInstanceMock;

    @Mock
    protected Task taskMock;

    @Mock
    protected HistoricActivityInstance historicActivityInstanceMock1;

    @Mock
    protected HistoricActivityInstance historicActivityInstanceMock2;

    protected final String processInstanceId = "123";

    protected final String taskId = "456";

    @Before
    public void beforeAssertableTest() throws Exception {
        when(processAssertConfigurationMock.getEngineServices()).thenReturn(engineServicesMock);

        when(engineServicesMock.getRuntimeService()).thenReturn(runtimeServiceMock);
        when(engineServicesMock.getHistoryService()).thenReturn(historyServiceMock);
        when(engineServicesMock.getTaskService()).thenReturn(taskServiceMock);

        when(runtimeServiceMock.createProcessInstanceQuery()).thenReturn(processInstanceQueryMock);
        when(processInstanceQueryMock.processInstanceId(processInstanceId)).thenReturn(processInstanceQueryMock);

        when(taskServiceMock.createTaskQuery()).thenReturn(taskQueryMock);
        when(taskQueryMock.taskId(taskId)).thenReturn(taskQueryMock);
        when(taskQueryMock.active()).thenReturn(taskQueryMock);
        when(taskQueryMock.singleResult()).thenReturn(taskMock);

        when(taskMock.getProcessInstanceId()).thenReturn(processInstanceId);

        when(historyServiceMock.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQueryMock);
        when(historicProcessInstanceQueryMock.processInstanceId(processInstanceId)).thenReturn(historicProcessInstanceQueryMock);
        when(historicProcessInstanceQueryMock.singleResult()).thenReturn(historicProcessInstanceMock);

        when(historyServiceMock.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQueryMock);
        when(historicActivityInstanceQueryMock.processInstanceId(processInstanceId)).thenReturn(historicActivityInstanceQueryMock);
        when(historicActivityInstanceQueryMock.activityType("endEvent")).thenReturn(historicActivityInstanceQueryMock);
        when(historicActivityInstanceQueryMock.finished()).thenReturn(historicActivityInstanceQueryMock);
    }

}
