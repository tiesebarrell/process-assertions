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
package org.toxos.activiti.assertion.process;

/**
 *
 */
public enum TwoUserTasksProcessConstant {

	/**
	 * The process' key.
	 */
	PROCESS_KEY("testProcessTwoUserTasks"),

	/**
	 * The id of the first usertask.
	 */
	USER_TASK_1_ACTIVITY_ID("userTask1"),

	/**
	 * The id of the second usertask.
	 */
	USER_TASK_2_ACTIVITY_ID("userTask2");

	private final String value;

	private TwoUserTasksProcessConstant(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
