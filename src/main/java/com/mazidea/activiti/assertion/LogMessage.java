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
package com.mazidea.activiti.assertion;

/**
 * Messages used in log statements.
 * 
 * @author Tiese Barrell
 * 
 */
public enum LogMessage {

  PROCESS_1("process.1"),

  PROCESS_2("process.2"),

  PROCESS_3("process.3"),

  PROCESS_4("process.4"),

  PROCESS_5("process.5"),

  PROCESS_6("process.6"),

  TASK_1("task.1"),

  ERROR_ASSERTIONS_1("error.assertions.1");

  private final String bundleKey;

  private LogMessage(final String bundleKey) {
    this.bundleKey = bundleKey;
  }

  public String getBundleKey() {
    return bundleKey;
  }

}
