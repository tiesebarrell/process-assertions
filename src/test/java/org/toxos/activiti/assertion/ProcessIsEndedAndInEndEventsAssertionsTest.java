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

import static org.toxos.activiti.assertion.ProcessAssert.assertProcessEndedAndInEndEvents;

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
 * Tests for assertions that test a process is ended and has reached an exact
 * set of end events.
 * 
 * @author Tiese Barrell
 * 
 */
@ContextConfiguration("classpath:application-context.xml")
public class ProcessIsEndedAndInEndEventsAssertionsTest extends AbstractProcessAssertTest {

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessEndedAndInEndEventsFailureForProcessInstanceNotEnded() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessEndedAndInEndEvents(processInstance, SingleUserTaskProcessConstant.END_EVENT_ID.getValue());
    }

    @Test
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessEndedAndInSingleEndEventForProcessInstance() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        assertProcessEndedAndInEndEvents(processInstance, StraightThroughProcessConstant.END_EVENT_ID.getValue());
    }

    @Test
    @Deployment(resources = BPMN_CONDITIONAL_SUBPROCESSES)
    public void testProcessEndedAndInEndEventsForProcessInstanceExactSet() throws Exception {
        final Map<String, Object> variables2Not3 = new HashMap<String, Object>();
        variables2Not3.put("do2", true);
        variables2Not3.put("do3", false);
        final ProcessInstance processInstance2Not3 = runtimeService.startProcessInstanceByKey(ConditionalSubProcessesProcessConstant.PROCESS_KEY.getValue(),
                variables2Not3);
        assertProcessEndedAndInEndEvents(processInstance2Not3, ConditionalSubProcessesProcessConstant.END_SUBPROCESS_1_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_SUBPROCESS_2_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_PROCESS_EVENT_ID.getValue());

        final Map<String, Object> variables3Not2 = new HashMap<String, Object>();
        variables3Not2.put("do2", false);
        variables3Not2.put("do3", true);
        final ProcessInstance processInstance3Not2 = runtimeService.startProcessInstanceByKey(ConditionalSubProcessesProcessConstant.PROCESS_KEY.getValue(),
                variables3Not2);
        assertProcessEndedAndInEndEvents(processInstance3Not2, ConditionalSubProcessesProcessConstant.END_SUBPROCESS_1_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_SUBPROCESS_3_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_PROCESS_EVENT_ID.getValue());

        final Map<String, Object> variables2And3 = new HashMap<String, Object>();
        variables2And3.put("do2", true);
        variables2And3.put("do3", true);
        final ProcessInstance processInstance2And3 = runtimeService.startProcessInstanceByKey(ConditionalSubProcessesProcessConstant.PROCESS_KEY.getValue(),
                variables2And3);
        assertProcessEndedAndInEndEvents(processInstance2And3, ConditionalSubProcessesProcessConstant.END_SUBPROCESS_1_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_SUBPROCESS_2_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_SUBPROCESS_3_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_PROCESS_EVENT_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_CONDITIONAL_SUBPROCESSES)
    public void testProcessEndedAndInEndEventsFailureForOneMissingForProcessInstance() throws Exception {
        final Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("do2", true);
        variables.put("do3", false);
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ConditionalSubProcessesProcessConstant.PROCESS_KEY.getValue(),
                variables);

        // Missing: SUB_PROCESS_1_END_EVENT_ID
        assertProcessEndedAndInEndEvents(processInstance, ConditionalSubProcessesProcessConstant.END_SUBPROCESS_2_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_PROCESS_EVENT_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_CONDITIONAL_SUBPROCESSES)
    public void testProcessEndedAndInEndEventsFailureForOneTooManyForProcessInstance() throws Exception {
        final Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("do2", true);
        variables.put("do3", false);
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ConditionalSubProcessesProcessConstant.PROCESS_KEY.getValue(),
                variables);

        // Should not be expected: SUB_PROCESS_3_END_EVENT_ID
        assertProcessEndedAndInEndEvents(processInstance, ConditionalSubProcessesProcessConstant.END_SUBPROCESS_1_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_SUBPROCESS_2_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_SUBPROCESS_3_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_PROCESS_EVENT_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessEndedAndInEndEventsFailureForCorrectAmountWrongIdsForProcessInstance() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());

        assertProcessEndedAndInEndEvents(processInstance, ConditionalSubProcessesProcessConstant.END_PROCESS_EVENT_ID.getValue());
    }

    @Test(expected = NullPointerException.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessEndedAndInEndEventsFailureForNullProcessInstance() throws Exception {
        runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        final ProcessInstance nullInstance = null;
        assertProcessEndedAndInEndEvents(nullInstance, StraightThroughProcessConstant.END_EVENT_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_SINGLE_USER_TASK)
    public void testProcessEndedAndInEndEventsFailureForProcessInstanceIdNotEnded() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(SingleUserTaskProcessConstant.PROCESS_KEY.getValue());
        assertProcessEndedAndInEndEvents(processInstance.getId(), SingleUserTaskProcessConstant.END_EVENT_ID.getValue());
    }

    @Test
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessEndedAndInSingleEndEventForProcessInstanceId() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        assertProcessEndedAndInEndEvents(processInstance.getId(), StraightThroughProcessConstant.END_EVENT_ID.getValue());
    }

    @Test
    @Deployment(resources = BPMN_CONDITIONAL_SUBPROCESSES)
    public void testProcessEndedAndInEndEventsForProcessInstanceIdExactSet() throws Exception {
        final Map<String, Object> variables2Not3 = new HashMap<String, Object>();
        variables2Not3.put("do2", true);
        variables2Not3.put("do3", false);
        final ProcessInstance processInstance2Not3 = runtimeService.startProcessInstanceByKey(ConditionalSubProcessesProcessConstant.PROCESS_KEY.getValue(),
                variables2Not3);
        assertProcessEndedAndInEndEvents(processInstance2Not3.getId(), ConditionalSubProcessesProcessConstant.END_SUBPROCESS_1_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_SUBPROCESS_2_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_PROCESS_EVENT_ID.getValue());

        final Map<String, Object> variables3Not2 = new HashMap<String, Object>();
        variables3Not2.put("do2", false);
        variables3Not2.put("do3", true);
        final ProcessInstance processInstance3Not2 = runtimeService.startProcessInstanceByKey(ConditionalSubProcessesProcessConstant.PROCESS_KEY.getValue(),
                variables3Not2);
        assertProcessEndedAndInEndEvents(processInstance3Not2.getId(), ConditionalSubProcessesProcessConstant.END_SUBPROCESS_1_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_SUBPROCESS_3_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_PROCESS_EVENT_ID.getValue());

        final Map<String, Object> variables2And3 = new HashMap<String, Object>();
        variables2And3.put("do2", true);
        variables2And3.put("do3", true);
        final ProcessInstance processInstance2And3 = runtimeService.startProcessInstanceByKey(ConditionalSubProcessesProcessConstant.PROCESS_KEY.getValue(),
                variables2And3);
        assertProcessEndedAndInEndEvents(processInstance2And3.getId(), ConditionalSubProcessesProcessConstant.END_SUBPROCESS_1_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_SUBPROCESS_2_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_SUBPROCESS_3_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_PROCESS_EVENT_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_CONDITIONAL_SUBPROCESSES)
    public void testProcessEndedAndInEndEventsFailureForOneMissingForProcessInstanceId() throws Exception {
        final Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("do2", true);
        variables.put("do3", false);
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ConditionalSubProcessesProcessConstant.PROCESS_KEY.getValue(),
                variables);

        // Missing: SUB_PROCESS_1_END_EVENT_ID
        assertProcessEndedAndInEndEvents(processInstance.getId(), ConditionalSubProcessesProcessConstant.END_SUBPROCESS_2_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_PROCESS_EVENT_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_CONDITIONAL_SUBPROCESSES)
    public void testProcessEndedAndInEndEventsFailureForOneTooManyForProcessInstanceId() throws Exception {
        final Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("do2", true);
        variables.put("do3", false);
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ConditionalSubProcessesProcessConstant.PROCESS_KEY.getValue(),
                variables);

        // Should not be expected: SUB_PROCESS_3_END_EVENT_ID
        assertProcessEndedAndInEndEvents(processInstance.getId(), ConditionalSubProcessesProcessConstant.END_SUBPROCESS_1_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_SUBPROCESS_2_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_SUBPROCESS_3_EVENT_ID.getValue(),
                ConditionalSubProcessesProcessConstant.END_PROCESS_EVENT_ID.getValue());
    }

    @Test(expected = AssertionError.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessEndedAndInEndEventsFailureForCorrectAmountWrongIdsForProcessInstanceId() throws Exception {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());

        assertProcessEndedAndInEndEvents(processInstance.getId(), ConditionalSubProcessesProcessConstant.END_PROCESS_EVENT_ID.getValue());
    }

    @Test(expected = NullPointerException.class)
    @Deployment(resources = BPMN_STRAIGHT_THROUGH)
    public void testProcessEndedAndInEndEventsFailureForNullProcessInstanceId() throws Exception {
        runtimeService.startProcessInstanceByKey(StraightThroughProcessConstant.PROCESS_KEY.getValue());
        final String nullId = null;
        assertProcessEndedAndInEndEvents(nullId, StraightThroughProcessConstant.END_EVENT_ID.getValue());
    }

}
