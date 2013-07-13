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

/**
 * Messages used in log statements.
 * 
 * @author Tiese Barrell
 * 
 */
public enum LogMessage {

	PROCESS_1,

	PROCESS_2,

	PROCESS_3,

	PROCESS_4,

	PROCESS_5,

	PROCESS_6,

	PROCESS_7,

	PROCESS_8,

	PROCESS_9,

	/**
	 * Exactly one historic activity instance of type endEvent and id matches.
	 */
	PROCESS_10,

	/**
	 * A task with definitionKey is uncompleted in process.
	 */
	TASK_1(),

	/**
	 * Task is uncompleted.
	 */
	TASK_2(),

	/**
	 * A task instance can be found.
	 */
	TASK_3(),

	/**
	 * A task instance can be found with definitionKey in process.
	 */
	TASK_4(),

	ERROR_ASSERTIONS_1(),

	ERROR_PROCESS_1(),

	ERROR_PROCESS_2(),

	ERROR_PROCESS_3(),

	ERROR_TASK_1(),

	ERROR_TASK_2();

	private final String bundleKey;

	private LogMessage() {
		this.bundleKey = name().replaceAll("_", ".").toLowerCase();
	}

	public String getBundleKey() {
		return bundleKey;
	}

}
