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

import static org.toxos.activiti.assertion.ProcessAssert.assertProcessEndedAndInExclusiveEndEvent;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * Tests for assertions that test a process is ended and has reached an
 * exclusive end event.
 * 
 * @author Tiese Barrell
 * 
 */
@ContextConfiguration("classpath:application-context.xml")
public class ProcessIsEndedAndInExclusiveEndEventAssertionsTest extends AbstractProcessAssertTest {

	private static final String TARGET_END_EVENT_ID = "endCompleted";
	private static final String CONDITIONAL_END_PROCESS_EVENT_ID = "endProcess";

	@Test
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
	public void testProcessEndedAndInExclusiveEndEventForProcessInstance() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
		assertProcessEndedAndInExclusiveEndEvent(activitiRule, processInstance, TARGET_END_EVENT_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
	public void testProcessEndedAndInExclusiveEndEventForNullProcessInstance() throws Exception {
		runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
		final ProcessInstance nullInstance = null;
		assertProcessEndedAndInExclusiveEndEvent(activitiRule, nullInstance, TARGET_END_EVENT_ID);
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN)
	public void testProcessEndedAndInExclusiveEndEventFailureForProcessInstanceNotEnded() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_USER_TASK);
		assertProcessEndedAndInExclusiveEndEvent(activitiRule, processInstance, TARGET_END_EVENT_ID);
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
	public void testProcessEndedAndInExclusiveEndEventFailureForProcessInstanceIncorrectEndEvent() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
		assertProcessEndedAndInExclusiveEndEvent(activitiRule, processInstance, CONDITIONAL_END_PROCESS_EVENT_ID);
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_CONDITIONAL_SUBPROCESSES_BPMN)
	public void testProcessEndedAndInExclusiveEndEventFailureForProcessInstanceNotExclusive() throws Exception {
		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("do2", true);
		variables.put("do3", false);
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
				TEST_PROCESS_CONDITIONAL_SUBPROCESSES, variables);
		assertProcessEndedAndInExclusiveEndEvent(activitiRule, processInstance, CONDITIONAL_END_PROCESS_EVENT_ID);
	}

	@Test
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
	public void testProcessEndedAndInExclusiveEndEventForProcessInstanceId() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
		assertProcessEndedAndInExclusiveEndEvent(activitiRule, processInstance.getId(), TARGET_END_EVENT_ID);
	}

	@Test(expected = IllegalArgumentException.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
	public void testProcessEndedAndInExclusiveEndEventForNullProcessInstanceId() throws Exception {
		runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
		final String nullProcessInstanceId = null;
		assertProcessEndedAndInExclusiveEndEvent(activitiRule, nullProcessInstanceId, TARGET_END_EVENT_ID);
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_SINGLE_USER_TASK_BPMN)
	public void testProcessEndedAndInExclusiveEndEventFailureForProcessInstanceIdNotEnded() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_USER_TASK);
		assertProcessEndedAndInExclusiveEndEvent(activitiRule, processInstance.getId(), TARGET_END_EVENT_ID);
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_STRAIGHT_THROUGH_BPMN)
	public void testProcessEndedAndInExclusiveEndEventFailureForProcessInstanceIdIncorrectEndEvent() throws Exception {
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_STRAIGHT_THROUGH);
		assertProcessEndedAndInExclusiveEndEvent(activitiRule, processInstance.getId(),
				CONDITIONAL_END_PROCESS_EVENT_ID);
	}

	@Test(expected = AssertionError.class)
	@Deployment(resources = DIAGRAMS_TEST_PROCESS_CONDITIONAL_SUBPROCESSES_BPMN)
	public void testProcessEndedAndInExclusiveEndEventFailureForProcessInstanceIdNotExclusive() throws Exception {
		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("do2", true);
		variables.put("do3", false);
		final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
				TEST_PROCESS_CONDITIONAL_SUBPROCESSES, variables);
		assertProcessEndedAndInExclusiveEndEvent(activitiRule, processInstance.getId(),
				CONDITIONAL_END_PROCESS_EVENT_ID);
	}

}
