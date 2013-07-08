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
package org.anemonos.activiti.assertion;

import static org.junit.Assert.assertNotNull;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Assert;

/**
 * Provides assertions for integration test cases that execute processes with
 * Activiti.
 */
public final class ProcessAssert extends AbstractProcessAssert {

	private ProcessAssert() {
		super();
	}

	//
	// Assertions for active process instances
	//

	/**
	 * Asserts the provided process instance is active, i.e. the process
	 * instance is not ended.
	 * 
	 * @param rule
	 *            the {@link ActivitiRule} to access the process engine's
	 *            services
	 * @param processInstance
	 *            the process instance to check for
	 */
	public static final void assertProcessActive(final ActivitiRule rule, final ProcessInstance processInstance) {
		assertNotNull(processInstance);
		assertProcessActive(rule, processInstance.getId());
	}

	/**
	 * Asserts the process instance with the provided id is active, i.e. the
	 * process instance is not ended.
	 * 
	 * @param rule
	 *            the {@link ActivitiRule} to access the process engine's
	 *            services
	 * @param processInstanceId
	 *            the process instance's id to check for
	 */
	public static final void assertProcessActive(final ActivitiRule rule, final String processInstanceId) {
		assertNotNull(processInstanceId);
		debug(LogMessage.PROCESS_1, processInstanceId);
		try {
			ProcessInstanceAssert.processIsActive(rule, processInstanceId);
		} catch (AssertionError ae) {
			fail(LogMessage.ERROR_PROCESS_1, processInstanceId);
		}
	}

	//
	// Assertions for ended process instances
	//

	/**
	 * Asserts the provided process instance is ended.
	 * 
	 * @param rule
	 *            the {@link ActivitiRule} to access the process engine's
	 *            services
	 * @param processInstance
	 *            the process instance to check for
	 */
	public static final void assertProcessEnded(final ActivitiRule rule, final ProcessInstance processInstance) {
		assertNotNull(processInstance);
		assertProcessEnded(rule, processInstance.getId());
	}

	/**
	 * Asserts the process instance with the provided id is ended.
	 * 
	 * @param rule
	 *            the {@link ActivitiRule} to access the process engine's
	 *            services
	 * @param processInstanceId
	 *            the process instance's id to check for
	 */
	public static final void assertProcessEnded(final ActivitiRule rule, final String processInstanceId) {
		assertNotNull(processInstanceId);
		debug(LogMessage.PROCESS_5, processInstanceId);
		try {
			ProcessInstanceAssert.processIsEnded(rule, processInstanceId);
		} catch (AssertionError ae) {
			fail(LogMessage.ERROR_PROCESS_2, processInstanceId);
		}
	}

	//
	// Assertions for tasks
	//

	/**
	 * Asserts the provided task is pending completion.
	 * 
	 * @param rule
	 *            the {@link ActivitiRule} to access the process engine's
	 *            services
	 * @param task
	 *            the task to check for
	 */
	public static final void assertTaskUncompleted(final ActivitiRule rule, final Task task) {
		assertNotNull(task);
		assertTaskUncompleted(rule, task.getId());
	}

	/**
	 * Asserts the task with the provided id is pending completion.
	 * 
	 * @param rule
	 *            the {@link ActivitiRule} to access the process engine's
	 *            services
	 * @param taskId
	 *            the task's id to check for
	 */
	public static final void assertTaskUncompleted(final ActivitiRule rule, final String taskId) {
		assertNotNull(taskId);

		debug(LogMessage.TASK_2, taskId);
		try {
			TaskInstanceAssert.taskIsUncompleted(rule, taskId);
		} catch (AssertionError ae) {
			fail(LogMessage.ERROR_TASK_2, taskId);
		}
	}

	/**
	 * Asserts a task with the provided taskDefinitionKey is pending completion
	 * in the provided process instance.
	 * 
	 * @param rule
	 *            the {@link ActivitiRule} to access the process engine's
	 *            services
	 * @param processInstance
	 *            the process instance to check for
	 * @param taskDefinitionKey
	 *            the task's definition key to check for
	 */
	public static final void assertTaskUncompleted(final ActivitiRule rule, final ProcessInstance processInstance, final String taskDefinitionKey) {
		assertNotNull(processInstance);
		assertNotNull(taskDefinitionKey);
		assertTaskUncompleted(rule, processInstance.getId(), taskDefinitionKey);
	}

	/**
	 * Asserts a task with the provided taskDefinitionKey is pending completion
	 * in the process instance with the provided processInstanceId.
	 * 
	 * @param rule
	 *            the {@link ActivitiRule} to access the process engine's
	 *            services
	 * @param processInstanceId
	 *            the process instance's id to check for
	 * @param taskDefinitionKey
	 *            the task's definition key to check for
	 */
	public static final void assertTaskUncompleted(final ActivitiRule rule, final String processInstanceId, final String taskDefinitionKey) {
		assertNotNull(processInstanceId);
		assertNotNull(taskDefinitionKey);

		debug(LogMessage.TASK_1, taskDefinitionKey, processInstanceId);
		try {
			TaskInstanceAssert.taskIsUncompleted(rule, processInstanceId, taskDefinitionKey);
		} catch (AssertionError ae) {
			fail(LogMessage.ERROR_TASK_1, taskDefinitionKey, processInstanceId);
		}
	}

	// Marker

	//
	// Assertions for ended process instances and end states
	//

	/**
	 * Asserts the provided process instance is ended and has reached
	 * <strong>only</strong> the end event with the provided key.
	 * 
	 * <p>
	 * <strong>Note:</strong> this assertion should be used for processes that
	 * have exclusive end events and no intermediate end events. This generally
	 * only applies to simple processes. If the process definition branches into
	 * non-exclusive branches with distinct end events or the process
	 * potentially has multiple end events that are reached, this method should
	 * not be used.
	 * 
	 * <p>
	 * To test that a process ended in an end event and that particular end
	 * event was the final event reached, use
	 * {@link #assertProcessEndedAndReachedEndStateLast(ActivitiRule, ProcessInstance, String)}
	 * instead.
	 * 
	 * <p>
	 * To assert that a process ended in an exact set of end events, use
	 * {@link #assertProcessEndedAndInEndStates(ActivitiRule, ProcessInstance, String...)}
	 * instead.
	 * 
	 * @see #assertProcessEndedAndReachedEndStateLast(ActivitiRule,
	 *      ProcessInstance, String)
	 * @see #assertProcessEndedAndInEndStates(ActivitiRule, ProcessInstance,
	 *      String...)
	 * 
	 * @param rule
	 *            the {@link ActivitiRule} to access the process engine's
	 *            services
	 * @param processInstance
	 *            the process instance to check for
	 * @param endEventId
	 *            the end event's id to check for
	 */
	public static final void assertProcessEndedAndInExclusiveEndEvent(final ActivitiRule rule, final ProcessInstance processInstance,
			final String endEventId) {
		assertProcessEndedAndInExclusiveEndEvent(rule, processInstance.getId(), endEventId);
	}

	public static final void assertProcessEndedAndReachedEndStateLast(final ActivitiRule rule, final ProcessInstance processInstance,
			final String endStateKey) {
		throw new UnsupportedOperationException("This process assertion has not been implemented yet");
	}

	public static final void assertProcessEndedAndReachedEndStateLast(final ActivitiRule rule, final String processInstanceId,
			final String endStateKey) {
		throw new UnsupportedOperationException("This process assertion has not been implemented yet");
	}

	/**
	 * Asserts the process instance with the provided id is ended and has
	 * reached the end state with the provided key.
	 * 
	 * <p>
	 * <strong>Note:</strong> this assertion assumes the process has exclusive
	 * endstates. If the process definition branches into non-exclusive branches
	 * with individual endstates or the process potentially has multiple
	 * endstates, this method should not be used.
	 * </p>
	 * 
	 * @see #assertProcessEndedAndInEndStates(ActivitiRule, String, String...)
	 * 
	 * @param rule
	 *            the {@link ActivitiRule} to access the process engine's
	 *            services
	 * @param processInstanceId
	 *            the process instance id to check for
	 * @param endStateKey
	 *            the key of the end state to check for
	 */
	public static void assertProcessEndedAndInExclusiveEndEvent(final ActivitiRule rule, final String processInstanceId, final String endStateKey) {
		// Assert.assertTrue(processEndedAndInExclusiveEndState(rule,
		// processInstanceId, endStateKey));
	}

	/**
	 * Asserts the provided process instance is ended and has reached all end
	 * states with the provided keys.
	 * 
	 * <p>
	 * <strong>Note:</strong> this assertion assumes the process has one or more
	 * endstates and that all of them have been reached (in other words, the
	 * exact set of provided endstates is checked against).
	 * </p>
	 * 
	 * @see #assertProcessEndedAndInExclusiveEndEvent(ActivitiRule,
	 *      ProcessInstance, String)
	 * 
	 * @param rule
	 *            the {@link ActivitiRule} to access the process engine's
	 *            services
	 * @param processInstance
	 *            the process instance to check for
	 * @param endStateKeys
	 *            the keys of the end states to check for
	 */
	public static final void assertProcessEndedAndInEndStates(final ActivitiRule rule, final ProcessInstance processInstance,
			final String... endStateKeys) {
		assertProcessEndedAndInEndStates(rule, processInstance.getId(), endStateKeys);
	}

	/**
	 * Asserts the provided process instance is ended and has reached all end
	 * states with the provided keys.
	 * 
	 * <p>
	 * <strong>Note:</strong> this assertion assumes the process has one or more
	 * endstates and that all of them have been reached (in other words, the
	 * exact set of provided endstates is checked against).
	 * </p>
	 * 
	 * @see #assertProcessEndedAndInExclusiveEndEvent(ActivitiRule, String,
	 *      String)
	 * 
	 * @param rule
	 *            the {@link ActivitiRule} to access the process engine's
	 *            services
	 * @param processInstanceId
	 *            the process instance id to check for
	 * @param endStateKeys
	 *            the keys of the end states to check for
	 */
	public static void assertProcessEndedAndInEndStates(final ActivitiRule rule, final String processInstanceId, final String... endStateKeys) {
		// Assert.assertTrue(processEndedAndInEndStates(rule, processInstanceId,
		// endStateKeys));
	}

	//
	// Assertions for historic values of process variables
	//

	/**
	 * Asserts that the process variable with the provided name is available in
	 * the provided process instance's history and that the <em>latest</em>
	 * value of that variable matches the provided expected value.
	 * 
	 * <p>
	 * <strong>Note:</strong> the latest value for the variable is used to check
	 * against. This does not imply the variable has not had other values during
	 * the execution of the process.
	 * </p>
	 * 
	 * @param rule
	 *            the {@link ActivitiRule} to access the process engine's
	 *            services
	 * @param processInstance
	 *            the process instance to check for
	 * @param processVariableName
	 *            the name of the process variable to check
	 * @param expectedValue
	 *            the expected value for the process variable
	 */
	public static void assertHistoricProcessVariableLatestValueEquals(final ActivitiRule rule, final ProcessInstance processInstance,
			final String processVariableName, final Object expectedValue) {
		assertHistoricProcessVariableLatestValueEquals(rule, processInstance.getId(), processVariableName, expectedValue);
	}

	/**
	 * Asserts that the process variable with the provided name is available in
	 * the provided process instance's history and that the <em>latest</em>
	 * value of that variable matches the provided expected value.
	 * 
	 * <p>
	 * <strong>Note:</strong> the latest value for the variable is used to check
	 * against. This does not imply the variable has not had other values during
	 * the execution of the process.
	 * </p>
	 * 
	 * @param rule
	 *            the {@link ActivitiRule} to access the process engine's
	 *            services
	 * @param processInstanceId
	 *            the id of the process instance to check for
	 * @param processVariableName
	 *            the name of the process variable to check
	 * @param expectedValue
	 *            the expected value for the process variable
	 */
	public static void assertHistoricProcessVariableLatestValueEquals(final ActivitiRule rule, final String processInstanceId,
			final String processVariableName, final Object expectedValue) {

		if (!AssertUtils.historyLevelIsFull(rule)) {
			Assert.fail("To check for latest historic values of process variables, the history level of the Activiti ProcessEngine must be set to full.");
		}

		// Assert.assertTrue(historicProcessVariableLatestValueEquals(rule,
		// processInstanceId, processVariableName,
		// expectedValue));
	}

}
