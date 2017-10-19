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
package org.toxos.processassertions.activiti;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.toxos.processassertions.activiti.integration.configuration.AbstractProcessAssertTest;
import org.toxos.processassertions.integration.common.process.DiagramConstants;
import org.toxos.processassertions.integration.common.process.SingleUserTaskProcessConstant;
import org.toxos.processassertions.integration.common.process.StraightThroughProcessConstant;

import static org.toxos.processassertions.api.ProcessAssert.assertProcessActive;

/**
 * Tests for assertions that test a process is active.
 *
 * @author Tiese Barrell
 */
public class ProcessIsActiveAssertionsTest extends AbstractProcessAssertTest {

    @Test
    @Deployment(resources = DiagramConstants.BPMN_SINGLE_USER_TASK)
    public void assertProcessActiveSuccess() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessActive(processInstance.getId());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = DiagramConstants.BPMN_STRAIGHT_THROUGH)
    public void assertProcessActiveFailure() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        assertProcessActive(processInstance.getId());
    }

}
