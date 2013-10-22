/*******************************************************************************
 * Copyright 2013 Tiese Barrell
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

/**
 * Tests for assertions that test a process is ended and has reached an exact
 * set of end events.
 * 
 * @author Tiese Barrell
 * 
 */
@ContextConfiguration("classpath:application-context.xml")
public class ProcessIsEndedAndInEndEventsAssertionsTest extends AbstractProcessAssertTest {

	private static final String SINGLE_USER_TASK_END_EVENT_ID = "endCompleted";
	private static final String STRAIGHT_THROUGH_END_EVENT_ID = "endCompleted";
	private static final String SUB_PROCESS_1_END_EVENT_ID = "endSubProcess1";
	private static final String SUB_PROCESS_2_END_EVENT_ID = "endSubProcess2";
	private static final String SUB_PROCESS_3_END_EVENT_ID = "endSubProcess3";
	private static final String CONDITIONAL_END_PROCESS_EVENT_ID = "endProcess";

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN)
	public void testProcessEndedAndInEndEventsFailureForProcessInstanceNotEnded() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_USER_TASK);
		assertProcessEndedAndInEndEvents(activitiRule, processInstance, SINGLE_USER_TASK_END_EVENT_ID);
	}

	@Test
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
	public void testProcessEndedAndInSingleEndEventForProcessInstance() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
		assertProcessEndedAndInEndEvents(activitiRule, processInstance, STRAIGHT_THROUGH_END_EVENT_ID);
	}

	@Test
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_CONDITIONAL_SUBPROCESSES_BPMN)
	public void testProcessEndedAndInEndEventsForProcessInstanceExactSet() throws Exception {
		final Map<String, Object> variables2Not3 = new HashMap<String, Object>();
		variables2Not3.put("do2", true);
		variables2Not3.put("do3", false);
		final ProcessInstance processInstance2Not3 = runtimeService.startProcessInstanceByKey(
				TEST_PROCESS_CONDITIONAL_SUBPROCESSES, variables2Not3);
		assertProcessEndedAndInEndEvents(activitiRule, processInstance2Not3, SUB_PROCESS_1_END_EVENT_ID,
				SUB_PROCESS_2_END_EVENT_ID, CONDITIONAL_END_PROCESS_EVENT_ID);

		final Map<String, Object> variables3Not2 = new HashMap<String, Object>();
		variables3Not2.put("do2", false);
		variables3Not2.put("do3", true);
		final ProcessInstance processInstance3Not2 = runtimeService.startProcessInstanceByKey(
				TEST_PROCESS_CONDITIONAL_SUBPROCESSES, variables3Not2);
		assertProcessEndedAndInEndEvents(activitiRule, processInstance3Not2, SUB_PROCESS_1_END_EVENT_ID,
				SUB_PROCESS_3_END_EVENT_ID, CONDITIONAL_END_PROCESS_EVENT_ID);

		final Map<String, Object> variables2And3 = new HashMap<String, Object>();
		variables2And3.put("do2", true);
		variables2And3.put("do3", true);
		final ProcessInstance processInstance2And3 = runtimeService.startProcessInstanceByKey(
				TEST_PROCESS_CONDITIONAL_SUBPROCESSES, variables2And3);
		assertProcessEndedAndInEndEvents(activitiRule, processInstance2And3, SUB_PROCESS_1_END_EVENT_ID,
				SUB_PROCESS_2_END_EVENT_ID, SUB_PROCESS_3_END_EVENT_ID, CONDITIONAL_END_PROCESS_EVENT_ID);
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_CONDITIONAL_SUBPROCESSES_BPMN)
	public void testProcessEndedAndInEndEventsFailureForOneMissingForProcessInstance() throws Exception {
		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("do2", true);
		variables.put("do3", false);
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
				TEST_PROCESS_CONDITIONAL_SUBPROCESSES, variables);

		// Missing: SUB_PROCESS_1_END_EVENT_ID
		assertProcessEndedAndInEndEvents(activitiRule, processInstance, SUB_PROCESS_2_END_EVENT_ID,
				CONDITIONAL_END_PROCESS_EVENT_ID);
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_CONDITIONAL_SUBPROCESSES_BPMN)
	public void testProcessEndedAndInEndEventsFailureForOneTooManyForProcessInstance() throws Exception {
		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("do2", true);
		variables.put("do3", false);
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
				TEST_PROCESS_CONDITIONAL_SUBPROCESSES, variables);

		// Should not be expected: SUB_PROCESS_3_END_EVENT_ID
		assertProcessEndedAndInEndEvents(activitiRule, processInstance, SUB_PROCESS_1_END_EVENT_ID,
				SUB_PROCESS_2_END_EVENT_ID, SUB_PROCESS_3_END_EVENT_ID, CONDITIONAL_END_PROCESS_EVENT_ID);
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
	public void testProcessEndedAndInEndEventsFailureForCorrectAmountWrongIdsForProcessInstance() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);

		assertProcessEndedAndInEndEvents(activitiRule, processInstance, CONDITIONAL_END_PROCESS_EVENT_ID);
	}

	@Test(expected = NullPointerException.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
	public void testProcessEndedAndInEndEventsFailureForNullProcessInstance() throws Exception {
		runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
		final ProcessInstance nullInstance = null;
		assertProcessEndedAndInEndEvents(activitiRule, nullInstance, STRAIGHT_THROUGH_END_EVENT_ID);
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN)
	public void testProcessEndedAndInEndEventsFailureForProcessInstanceIdNotEnded() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_USER_TASK);
		assertProcessEndedAndInEndEvents(activitiRule, processInstance.getId(), SINGLE_USER_TASK_END_EVENT_ID);
	}

	@Test
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
	public void testProcessEndedAndInSingleEndEventForProcessInstanceId() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
		assertProcessEndedAndInEndEvents(activitiRule, processInstance.getId(), STRAIGHT_THROUGH_END_EVENT_ID);
	}

	@Test
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_CONDITIONAL_SUBPROCESSES_BPMN)
	public void testProcessEndedAndInEndEventsForProcessInstanceIdExactSet() throws Exception {
		final Map<String, Object> variables2Not3 = new HashMap<String, Object>();
		variables2Not3.put("do2", true);
		variables2Not3.put("do3", false);
		final ProcessInstance processInstance2Not3 = runtimeService.startProcessInstanceByKey(
				TEST_PROCESS_CONDITIONAL_SUBPROCESSES, variables2Not3);
		assertProcessEndedAndInEndEvents(activitiRule, processInstance2Not3.getId(), SUB_PROCESS_1_END_EVENT_ID,
				SUB_PROCESS_2_END_EVENT_ID, CONDITIONAL_END_PROCESS_EVENT_ID);

		final Map<String, Object> variables3Not2 = new HashMap<String, Object>();
		variables3Not2.put("do2", false);
		variables3Not2.put("do3", true);
		final ProcessInstance processInstance3Not2 = runtimeService.startProcessInstanceByKey(
				TEST_PROCESS_CONDITIONAL_SUBPROCESSES, variables3Not2);
		assertProcessEndedAndInEndEvents(activitiRule, processInstance3Not2.getId(), SUB_PROCESS_1_END_EVENT_ID,
				SUB_PROCESS_3_END_EVENT_ID, CONDITIONAL_END_PROCESS_EVENT_ID);

		final Map<String, Object> variables2And3 = new HashMap<String, Object>();
		variables2And3.put("do2", true);
		variables2And3.put("do3", true);
		final ProcessInstance processInstance2And3 = runtimeService.startProcessInstanceByKey(
				TEST_PROCESS_CONDITIONAL_SUBPROCESSES, variables2And3);
		assertProcessEndedAndInEndEvents(activitiRule, processInstance2And3.getId(), SUB_PROCESS_1_END_EVENT_ID,
				SUB_PROCESS_2_END_EVENT_ID, SUB_PROCESS_3_END_EVENT_ID, CONDITIONAL_END_PROCESS_EVENT_ID);
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_CONDITIONAL_SUBPROCESSES_BPMN)
	public void testProcessEndedAndInEndEventsFailureForOneMissingForProcessInstanceId() throws Exception {
		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("do2", true);
		variables.put("do3", false);
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
				TEST_PROCESS_CONDITIONAL_SUBPROCESSES, variables);

		// Missing: SUB_PROCESS_1_END_EVENT_ID
		assertProcessEndedAndInEndEvents(activitiRule, processInstance.getId(), SUB_PROCESS_2_END_EVENT_ID,
				CONDITIONAL_END_PROCESS_EVENT_ID);
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_CONDITIONAL_SUBPROCESSES_BPMN)
	public void testProcessEndedAndInEndEventsFailureForOneTooManyForProcessInstanceId() throws Exception {
		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("do2", true);
		variables.put("do3", false);
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
				TEST_PROCESS_CONDITIONAL_SUBPROCESSES, variables);

		// Should not be expected: SUB_PROCESS_3_END_EVENT_ID
		assertProcessEndedAndInEndEvents(activitiRule, processInstance.getId(), SUB_PROCESS_1_END_EVENT_ID,
				SUB_PROCESS_2_END_EVENT_ID, SUB_PROCESS_3_END_EVENT_ID, CONDITIONAL_END_PROCESS_EVENT_ID);
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
	public void testProcessEndedAndInEndEventsFailureForCorrectAmountWrongIdsForProcessInstanceId() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);

		assertProcessEndedAndInEndEvents(activitiRule, processInstance.getId(), CONDITIONAL_END_PROCESS_EVENT_ID);
	}

	@Test(expected = NullPointerException.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
	public void testProcessEndedAndInEndEventsFailureForNullProcessInstanceId() throws Exception {
		runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
		final String nullId = null;
		assertProcessEndedAndInEndEvents(activitiRule, nullId, STRAIGHT_THROUGH_END_EVENT_ID);
	}

}
