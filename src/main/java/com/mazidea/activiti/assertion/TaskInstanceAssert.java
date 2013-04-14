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

import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Assert;

/**
 * Provides assertions for task instances.
 */
public final class TaskInstanceAssert {

  private TaskInstanceAssert() {
    super();
  }

  public static void taskIsUncompleted(final ActivitiRule rule, final String processInstanceId,
      final String taskDefinitionKey) {

    // Assert the process is not completed
    ProcessInstanceAssert.processIsActive(rule, processInstanceId);

    // Assert a task exists
    final Task task = rule.getTaskService().createTaskQuery().processInstanceId(processInstanceId)
        .taskDefinitionKey(taskDefinitionKey).singleResult();

    Assert.assertNotNull(task);

  }

}
