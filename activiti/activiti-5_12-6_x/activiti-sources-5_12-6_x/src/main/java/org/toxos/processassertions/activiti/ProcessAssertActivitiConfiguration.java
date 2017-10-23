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
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.test.ActivitiRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toxos.processassertions.api.DefaultProcessAssertConfiguration;
import org.toxos.processassertions.api.SupportedLocale;
import org.toxos.processassertions.api.internal.AssertFactory;
import org.toxos.processassertions.api.internal.MessageLogger;
import org.toxos.processassertions.api.internal.Validate;

/**
 * Configuration for Process Assertions with the Activiti Process Engine.
 *
 * @author Tiese Barrell
 */
public class ProcessAssertActivitiConfiguration extends DefaultProcessAssertConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessAssertActivitiConfiguration.class);

    private static final String LOG_MESSAGES_BUNDLE_NAME = "org.toxos.processassertions.activiti.messages.LogMessages";

    private ProcessEngine processEngine;

    private AssertFactory assertFactory;

    private MessageLogger messageLogger;

    /**
     * Constructs a new ProcessAssertActivitiConfiguration from the provided {@link ProcessEngine}.
     *
     * @param processEngine the process engine. May not be {@code null}
     */
    public ProcessAssertActivitiConfiguration(final ProcessEngine processEngine) {
        super();
        Validate.notNull(processEngine);
        this.processEngine = processEngine;
        initializeConfiguration();
    }

    /**
     * Constructs a new ProcessAssertActivitiConfiguration from the provided {@link ProcessEngine}.
     *
     * @param processEngine the process engine. May not be {@code null}
     */
    public static final ProcessAssertActivitiConfiguration from(final ProcessEngine processEngine) {
        return new ProcessAssertActivitiConfiguration(processEngine);
    }

    /**
     * Constructs a new ProcessAssertActivitiConfiguration from the provided {@link ActivitiRule}.
     *
     * @param activitiRule the activiti rule. May not be {@code null}
     */
    public ProcessAssertActivitiConfiguration(final ActivitiRule activitiRule) {
        super();
        Validate.notNull(activitiRule);
        this.processEngine = activitiRule.getProcessEngine();
        initializeConfiguration();
    }

    /**
     * Constructs a new ProcessAssertActivitiConfiguration from the provided {@link ActivitiRule}.
     *
     * @param activitiRule the activiti rule. May not be {@code null}
     */
    public static final ProcessAssertActivitiConfiguration from(final ActivitiRule activitiRule) {
        return new ProcessAssertActivitiConfiguration(activitiRule);
    }

    /**
     * Constructs a new ProcessAssertActivitiConfiguration from the provided {@link ProcessEngine} with the provided {@link SupportedLocale}.
     *
     * @param supportedLocale the locale to be used. May not be {@code null}
     * @param processEngine   the process engine. May not be {@code null}
     */
    public ProcessAssertActivitiConfiguration(final SupportedLocale supportedLocale, final ProcessEngine processEngine) {
        super(supportedLocale);
        Validate.notNull(processEngine);
        this.processEngine = processEngine;
        initializeConfiguration();
    }

    /**
     * Constructs a new ProcessAssertActivitiConfiguration from the provided {@link ProcessEngine} with the provided {@link SupportedLocale}.
     *
     * @param supportedLocale the locale to be used. May not be {@code null}
     * @param processEngine   the process engine. May not be {@code null}
     */
    public static final ProcessAssertActivitiConfiguration from(final SupportedLocale supportedLocale, final ProcessEngine processEngine) {
        return new ProcessAssertActivitiConfiguration(supportedLocale, processEngine);
    }

    /**
     * Constructs a new ProcessAssertActivitiConfiguration from the provided {@link ActivitiRule} with the provided {@link SupportedLocale}.
     *
     * @param supportedLocale the locale to be used. May not be {@code null}
     * @param activitiRule    the activiti rule. May not be {@code null}
     */
    public ProcessAssertActivitiConfiguration(final SupportedLocale supportedLocale, final ActivitiRule activitiRule) {
        super(supportedLocale);
        Validate.notNull(activitiRule);
        this.processEngine = activitiRule.getProcessEngine();
        initializeConfiguration();
    }

    /**
     * Constructs a new ProcessAssertActivitiConfiguration from the provided {@link ActivitiRule} with the provided {@link SupportedLocale}.
     *
     * @param supportedLocale the locale to be used. May not be {@code null}
     * @param activitiRule    the activiti rule. May not be {@code null}
     */
    public static final ProcessAssertActivitiConfiguration from(final SupportedLocale supportedLocale, final ActivitiRule activitiRule) {
        return new ProcessAssertActivitiConfiguration(supportedLocale, activitiRule);
    }

    /**
     * Gets the configured {@link ProcessEngine}.
     *
     * @return the ProcessEngine.
     */
    public ProcessEngine getProcessEngine() {
        return processEngine;
    }

    /**
     * Sets the {@link ProcessEngine}.
     *
     * @param processEngine the process engine to set. May not be {@code null}
     */
    public void setProcessEngine(final ProcessEngine processEngine) {
        Validate.notNull(processEngine);
        this.processEngine = processEngine;
        initializeConfiguration();
    }

    @Override
    public AssertFactory getAssertFactory() {
        return assertFactory;
    }

    /**
     * Sets the {@link ActivitiRule}.
     *
     * @param activitiRule the activiti rule to set. May not be {@code null}
     */
    public void setActivitiRule(final ActivitiRule activitiRule) {
        Validate.notNull(activitiRule);
        setProcessEngine(activitiRule.getProcessEngine());
    }

    /**
     * Gets the configured {@link HistoryLevel}.
     *
     * @return the HistoryLevel
     */
    HistoryLevel getConfiguredHistoryLevel() {
        return doGetProcessEngineConfiguration().getHistoryLevel();
    }

    void deInitialize() {
        this.processEngine = null;
    }

    private void initializeConfiguration() {
        this.assertFactory = new AssertFactoryImpl(this);

        this.messageLogger = new MessageLogger(LOG_MESSAGES_BUNDLE_NAME, getLocale());

        if (this.processEngine == null) {
            this.processEngine = ProcessEngines.getDefaultProcessEngine();
        }
        registerProcessEngineCloseListener();

        this.messageLogger.logInfo(LOGGER, LogMessage.CONFIGURATION_1.getBundleKey(), this.processEngine.getName());
    }

    private void registerProcessEngineCloseListener() {
        doGetProcessEngineConfiguration().setProcessEngineLifecycleListener(new ProcessEngineCloseListener(this, messageLogger));
    }

    private ProcessEngineConfigurationImpl doGetProcessEngineConfiguration() {
        ProcessEngineConfigurationImpl configuration = null;
        if (this.processEngine instanceof ProcessEngineImpl) {
            configuration = ((ProcessEngineImpl) this.processEngine).getProcessEngineConfiguration();
        }
        return configuration;
    }

}
