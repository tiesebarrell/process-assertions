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
package org.toxos.activiti.assertion;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.toxos.activiti.assertion.process.SingleUserTaskProcessConstant;
import org.toxos.activiti.assertion.process.StraightThroughProcessConstant;

import static org.toxos.processassertions.api.ProcessAssert.assertProcessActive;

/**
 * Tests for assertions that test a process is active.
 *
 * @author Tiese Barrell
 */
@ContextConfiguration("classpath:application-context.xml") public class ProcessIsActiveAssertionsTest extends AbstractProcessAssertTest {

    @Test @Deployment(resources = BPMN_SINGLE_USER_TASK) public void testProcessActive() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessActive(processInstance.getId());
    }

    @Test(expected = NullPointerException.class) @Deployment(resources = BPMN_SINGLE_USER_TASK) public void testProcessActiveFailureForNullId()
            throws Exception {
        runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        final String nullId = null;
        assertProcessActive(nullId);
    }

    @Test(expected = AssertionError.class) @Deployment(resources = BPMN_STRAIGHT_THROUGH) public void testProcessActiveFailure() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        assertProcessActive(processInstance.getId());
    }

}
