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

import java.util.List;

import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Assert;

/**
 * Provides assertions for process instances.
 */
public final class HistoricVariableInstanceAssert {

  private HistoricVariableInstanceAssert() {
    super();
  }

  private static boolean historicProcessVariableLatestValueEquals(final ActivitiRule rule,
      final String processInstanceId, final String processVariableName, final Object expectedValue) {

    boolean result = false;

    try {
      final List<HistoricVariableUpdate> variableUpdates = AssertUtils.getDescendingVariableUpdates(rule,
          processInstanceId, processVariableName);

      if (variableUpdates.size() > 0) {
        final HistoricVariableUpdate latest = variableUpdates.get(0);
        Assert.assertNotNull(latest);
        Assert.assertEquals(processVariableName, latest.getVariableName());
        Assert.assertEquals(expectedValue, latest.getValue());
        result = true;
      } else {
        result = false;
      }

    } catch (final AssertionError ae) {
      result = false;
    }

    return result;
  }
}
