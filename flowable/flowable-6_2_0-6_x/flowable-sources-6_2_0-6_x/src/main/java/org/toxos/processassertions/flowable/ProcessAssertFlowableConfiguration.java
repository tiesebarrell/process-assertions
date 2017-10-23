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
package org.toxos.processassertions.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.common.impl.history.HistoryLevel;
import org.flowable.engine.impl.ProcessEngineImpl;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.test.FlowableRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toxos.processassertions.api.DefaultProcessAssertConfiguration;
import org.toxos.processassertions.api.SupportedLocale;
import org.toxos.processassertions.api.internal.AssertFactory;
import org.toxos.processassertions.api.internal.MessageLogger;
import org.toxos.processassertions.api.internal.Validate;

/**
 * Configuration for Process Assertions with the Flowable Process Engine.
 *
 * @author Tiese Barrell
 */
public class ProcessAssertFlowableConfiguration extends DefaultProcessAssertConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessAssertFlowableConfiguration.class);

    private static final String LOG_MESSAGES_BUNDLE_NAME = "org.toxos.processassertions.flowable.messages.LogMessages";

    private ProcessEngine processEngine;

    private AssertFactory assertFactory;

    private MessageLogger messageLogger;

    /**
     * Constructs a new ProcessAssertFlowableConfiguration from the provided {@link ProcessEngine}.
     *
     * @param processEngine the process engine. May not be {@code null}
     */
    public ProcessAssertFlowableConfiguration(final ProcessEngine processEngine) {
        super();
        Validate.notNull(processEngine);
        this.processEngine = processEngine;
        initializeConfiguration();
    }

    /**
     * Constructs a new ProcessAssertFlowableConfiguration from the provided {@link ProcessEngine}.
     *
     * @param processEngine the process engine. May not be {@code null}
     */
    public static final ProcessAssertFlowableConfiguration from(final ProcessEngine processEngine) {
        return new ProcessAssertFlowableConfiguration(processEngine);
    }

    /**
     * Constructs a new ProcessAssertFlowableConfiguration from the provided {@link FlowableRule}.
     *
     * @param flowableRule the flowable rule. May not be {@code null}
     */
    public ProcessAssertFlowableConfiguration(final FlowableRule flowableRule) {
        super();
        Validate.notNull(flowableRule);
        this.processEngine = flowableRule.getProcessEngine();
        initializeConfiguration();
    }

    /**
     * Constructs a new ProcessAssertFlowableConfiguration from the provided {@link FlowableRule}.
     *
     * @param flowableRule the flowable rule. May not be {@code null}
     */
    public static final ProcessAssertFlowableConfiguration from(final FlowableRule flowableRule) {
        return new ProcessAssertFlowableConfiguration(flowableRule);
    }

    /**
     * Constructs a new ProcessAssertFlowableConfiguration from the provided {@link ProcessEngine} for the provided {@link SupportedLocale}.
     *
     * @param supportedLocale the locale to be used. May not be {@code null}
     * @param processEngine   the process engine. May not be {@code null}
     */
    public ProcessAssertFlowableConfiguration(final SupportedLocale supportedLocale, final ProcessEngine processEngine) {
        super(supportedLocale);
        Validate.notNull(processEngine);
        this.processEngine = processEngine;
        initializeConfiguration();
    }

    /**
     * Constructs a new ProcessAssertFlowableConfiguration from the provided {@link ProcessEngine} for the provided {@link SupportedLocale}.
     *
     * @param supportedLocale the locale to be used. May not be {@code null}
     * @param processEngine   the process engine. May not be {@code null}
     */
    public static final ProcessAssertFlowableConfiguration from(final SupportedLocale supportedLocale, final ProcessEngine processEngine) {
        return new ProcessAssertFlowableConfiguration(supportedLocale, processEngine);
    }

    /**
     * Constructs a new ProcessAssertFlowableConfiguration from the provided {@link FlowableRule} for the provided {@link SupportedLocale}.
     *
     * @param supportedLocale the locale to be used. May not be {@code null}
     * @param flowableRule    the flowable rule. May not be {@code null}
     */
    public ProcessAssertFlowableConfiguration(final SupportedLocale supportedLocale, final FlowableRule flowableRule) {
        super(supportedLocale);
        Validate.notNull(flowableRule);
        this.processEngine = flowableRule.getProcessEngine();
        initializeConfiguration();
    }

    /**
     * Constructs a new ProcessAssertFlowableConfiguration from the provided {@link FlowableRule} for the provided {@link SupportedLocale}.
     *
     * @param supportedLocale the locale to be used. May not be {@code null}
     * @param flowableRule    the flowable rule. May not be {@code null}
     */
    public static final ProcessAssertFlowableConfiguration from(final SupportedLocale supportedLocale, final FlowableRule flowableRule) {
        return new ProcessAssertFlowableConfiguration(supportedLocale, flowableRule);
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
     * Sets the {@link FlowableRule}.
     *
     * @param flowableRule the flowable rule to set. May not be {@code null}
     */
    public void setFlowableRule(final FlowableRule flowableRule) {
        Validate.notNull(flowableRule);
        setProcessEngine(flowableRule.getProcessEngine());
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
