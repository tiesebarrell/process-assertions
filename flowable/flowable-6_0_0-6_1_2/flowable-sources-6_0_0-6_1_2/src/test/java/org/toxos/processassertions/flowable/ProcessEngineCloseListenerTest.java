/*******************************************************************************
 * Copyright 2017 Tiese Barrell
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

import org.flowable.engine.ProcessEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.toxos.processassertions.api.internal.MessageLogger;

import static org.mockito.Mockito.*;

/**
 * Test cases for {@link ProcessEngineCloseListener}.
 *
 * @author Tiese Barrell
 */
@RunWith(MockitoJUnitRunner.class)
public class ProcessEngineCloseListenerTest {

    @Mock
    private ProcessAssertFlowableConfiguration configurationMock;

    @Mock
    private MessageLogger messageLoggerMock;

    @Mock
    private ProcessEngine processEngineMock;

    @Mock
    private ProcessEngineCloseListener classUnderTest;

    private final String processEngineName = "process-engine-123";

    @Before
    public void before() {
        when(processEngineMock.getName()).thenReturn(processEngineName);
        classUnderTest = new ProcessEngineCloseListener(configurationMock, messageLoggerMock);
    }

    @Test
    public void onProcessEngineClosedDeInitializesConfiguration() throws Exception {
        classUnderTest.onProcessEngineClosed(processEngineMock);
        verify(configurationMock, times(1)).deInitialize();
        verify(messageLoggerMock, times(1)).logInfo(
                LoggerFactory.getLogger(ProcessEngineCloseListener.class),
                LogMessage.CONFIGURATION_2.getBundleKey(),
                processEngineName);
    }

    @Test
    public void onProcessEngineBuiltHasNoImplementation() throws Exception {
        classUnderTest.onProcessEngineBuilt(processEngineMock);
        verifyNoMoreInteractions(configurationMock);
        verifyNoMoreInteractions(messageLoggerMock);
    }

}