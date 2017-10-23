/*******************************************************************************
 * Copyright 2017 Tiese Barrell
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
package org.toxos.processassertions.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toxos.processassertions.api.internal.MessageLogger;

/**
 * Close listener for process engines.
 */
class ProcessEngineCloseListener implements ProcessEngineLifecycleListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessAssertActivitiConfiguration.class);

    private final ProcessAssertActivitiConfiguration configuration;
    private final MessageLogger messageLogger;

    public ProcessEngineCloseListener(final ProcessAssertActivitiConfiguration configuration, final MessageLogger messageLogger) {
        this.configuration = configuration;
        this.messageLogger = messageLogger;
    }

    @Override
    public void onProcessEngineClosed(final ProcessEngine processEngine) {
        configuration.deInitialize();
        messageLogger.logInfo(LOGGER, LogMessage.CONFIGURATION_2.getBundleKey(), processEngine.getName());
    }

    @Override
    public void onProcessEngineBuilt(final ProcessEngine processEngine) {
        // no-op
    }
}