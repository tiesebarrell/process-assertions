package org.toxos.processassertions.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.impl.ProcessEngineImpl;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.history.HistoryLevel;
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

    static ProcessAssertFlowableConfiguration INSTANCE;

    private ProcessEngine processEngine;

    private AssertFactory assertFactory = new AssertFactoryImpl();

    private MessageLogger messageLogger;

    public ProcessAssertFlowableConfiguration(final ProcessEngine processEngine) {
        super();
        Validate.notNull(processEngine);
        this.processEngine = processEngine;
        INSTANCE = this;
        initializeConfiguration();
    }

    public ProcessAssertFlowableConfiguration(final FlowableRule flowableRule) {
        super();
        Validate.notNull(flowableRule);
        this.processEngine = flowableRule.getProcessEngine();
        INSTANCE = this;
        initializeConfiguration();
    }

    public ProcessAssertFlowableConfiguration(final SupportedLocale supportedLocale, final ProcessEngine processEngine) {
        super(supportedLocale);
        Validate.notNull(processEngine);
        this.processEngine = processEngine;
        INSTANCE = this;
        initializeConfiguration();
    }

    public ProcessAssertFlowableConfiguration(final SupportedLocale supportedLocale, final FlowableRule flowableRule) {
        super(supportedLocale);
        Validate.notNull(flowableRule);
        this.processEngine = flowableRule.getProcessEngine();
        INSTANCE = this;
        initializeConfiguration();
    }

    public ProcessEngine getProcessEngine() {
        return processEngine;
    }

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
     *
     * @param flowableRule
     */
    public void setFlowableRule(final FlowableRule flowableRule) {
        Validate.notNull(flowableRule);
        setProcessEngine(flowableRule.getProcessEngine());
    }

    HistoryLevel getConfiguredHistoryLevel() {
        return doGetProcessEngineConfiguration().getHistoryLevel();
    }

    void deInitialize() {
        this.processEngine = null;
    }

    private void initializeConfiguration() {
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
