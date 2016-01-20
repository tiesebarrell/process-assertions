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

import static org.toxos.activiti.assertion.ProcessAssert.assertProcessEndedAndInExclusiveEndEvent;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.toxos.activiti.assertion.process.ConditionalSubProcessesProcessConstant;
import org.toxos.activiti.assertion.process.SingleUserTaskProcessConstant;
import org.toxos.activiti.assertion.process.StraightThroughProcessConstant;

/**
 * Tests for assertions that test a process is ended and has reached an
 * exclusive end event.
 * 
 * @author Tiese Barrell
 * 
 */
@ContextConfiguration("classpath:application-context.xml")
public class ProcessIsEndedAndInExclusiveEndEventAssertionsTest extends AbstractProcessAssertTest {

    @Test
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessEndedAndInExclusiveEndEventForProcessInstance() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        assertProcessEndedAndInExclusiveEndEvent(processInstance, StraightThroughProcessConstant.END_EVENT_ID.getValue());
    }

    @Test(expected = NullPointerException.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessEndedAndInExclusiveEndEventForNullProcessInstance() throws Exception {
        runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        final ProcessInstance nullInstance = null;
        assertProcessEndedAndInExclusiveEndEvent(nullInstance, StraightThroughProcessConstant.END_EVENT_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessEndedAndInExclusiveEndEventFailureForProcessInstanceNotEnded() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessEndedAndInExclusiveEndEvent(processInstance, SingleUserTaskProcessConstant.END_EVENT_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessEndedAndInExclusiveEndEventFailureForProcessInstanceIncorrectEndEvent() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        assertProcessEndedAndInExclusiveEndEvent(processInstance, ConditionalSubProcessesProcessConstant.END_PROCESS_EVENT_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_CONDITIONAL_SUBPROCESSES)
    public void testProcessEndedAndInExclusiveEndEventFailureForProcessInstanceNotExclusive() throws Exception {
        final Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("do2", true);
        variables.put("do3", false);
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ConditionalSubProcessesProcessConstant.PROCESS_KEY.getValue(),
                variables);
        assertProcessEndedAndInExclusiveEndEvent(processInstance, ConditionalSubProcessesProcessConstant.END_PROCESS_EVENT_ID.getValue());
    }

    @Test
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessEndedAndInExclusiveEndEventForProcessInstanceId() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        assertProcessEndedAndInExclusiveEndEvent(processInstance.getId(), StraightThroughProcessConstant.END_EVENT_ID.getValue());
    }

    @Test(expected = NullPointerException.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessEndedAndInExclusiveEndEventForNullProcessInstanceId() throws Exception {
        runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        final String nullProcessInstanceId = null;
        assertProcessEndedAndInExclusiveEndEvent(nullProcessInstanceId, StraightThroughProcessConstant.END_EVENT_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessEndedAndInExclusiveEndEventFailureForProcessInstanceIdNotEnded() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessEndedAndInExclusiveEndEvent(processInstance.getId(), SingleUserTaskProcessConstant.END_EVENT_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessEndedAndInExclusiveEndEventFailureForProcessInstanceIdIncorrectEndEvent() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        assertProcessEndedAndInExclusiveEndEvent(processInstance.getId(), ConditionalSubProcessesProcessConstant.END_PROCESS_EVENT_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_CONDITIONAL_SUBPROCESSES)
    public void testProcessEndedAndInExclusiveEndEventFailureForProcessInstanceIdNotExclusive() throws Exception {
        final Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("do2", true);
        variables.put("do3", false);
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ConditionalSubProcessesProcessConstant.PROCESS_KEY.getValue(),
                variables);
        assertProcessEndedAndInExclusiveEndEvent(processInstance.getId(), ConditionalSubProcessesProcessConstant.END_PROCESS_EVENT_ID.getValue());
    }

}
