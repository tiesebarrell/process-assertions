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

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.test.ActivitiRule;

/**
 * Provides utilities for process assertions.
 */
public final class AssertUtils {

  private AssertUtils() {
    super();
  }

  static boolean historyLevelIsFull(final ActivitiRule rule) {

    boolean result = false;

    final ProcessEngine engine = rule.getProcessEngine();

    if (engine instanceof ProcessEngineImpl) {
      final ProcessEngineImpl engineImpl = (ProcessEngineImpl) engine;
      result = HistoryLevel.FULL.equals(engineImpl.getProcessEngineConfiguration().getHistoryLevel());
    }

    return result;
  }

  static List<HistoricVariableUpdate> getDescendingVariableUpdates(final ActivitiRule rule,
      final String processInstanceId, final String processVariableName) {

    final List<HistoricVariableUpdate> result = new ArrayList<HistoricVariableUpdate>();

    final List<HistoricDetail> historicDetails = rule.getHistoryService().createHistoricDetailQuery().variableUpdates()
        .processInstanceId(processInstanceId).orderByVariableName().asc().orderByTime().desc().list();

    boolean reachedTargetVariable = false;

    for (final HistoricDetail historicDetail : historicDetails) {

      if (historicDetail instanceof HistoricVariableUpdate) {
        final HistoricVariableUpdate historicVariableUpdate = (HistoricVariableUpdate) historicDetail;

        if (processVariableName.equals(historicVariableUpdate.getVariableName()) && !reachedTargetVariable) {
          reachedTargetVariable = true;
          result.add(historicVariableUpdate);
        } else if (processVariableName.equals(historicVariableUpdate.getVariableName())) {
          result.add(historicVariableUpdate);
        } else if (reachedTargetVariable) {
          break;
        }
      }
    }

    return result;
  }

}
