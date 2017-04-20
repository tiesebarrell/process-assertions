package org.toxos.processassertions.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineLifecycleListener;
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

/**
 * Configuration for Process Assertions with the Activiti Process Engine.
 *
 * @author Tiese Barrell
 */
public class ProcessAssertActivitiConfiguration extends DefaultProcessAssertConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessAssertActivitiConfiguration.class);

    private static final String LOG_MESSAGES_BUNDLE_NAME = "org.toxos.processassertions.activiti.messages.LogMessages";

    static ProcessAssertActivitiConfiguration INSTANCE;

    private ProcessEngine processEngine;

    private AssertFactory assertFactory = new AssertFactoryImpl();

    private MessageLogger messageLogger;

    public ProcessAssertActivitiConfiguration(final ProcessEngine processEngine) {
        super();
        this.processEngine = processEngine;
        INSTANCE = this;
        initializeConfiguration();
    }

    public ProcessAssertActivitiConfiguration(final ActivitiRule activitiRule) {
        super();
        this.processEngine = activitiRule.getProcessEngine();
        INSTANCE = this;
        initializeConfiguration();
    }

    public ProcessAssertActivitiConfiguration(final SupportedLocale supportedLocale, final ProcessEngine processEngine) {
        super(supportedLocale);
        this.processEngine = processEngine;
        INSTANCE = this;
        initializeConfiguration();
    }

    public ProcessAssertActivitiConfiguration(final SupportedLocale supportedLocale, final ActivitiRule activitiRule) {
        super(supportedLocale);
        this.processEngine = activitiRule.getProcessEngine();
        INSTANCE = this;
        initializeConfiguration();
    }

    public ProcessEngine getProcessEngine() {
        return processEngine;
    }

    public void setProcessEngine(final ProcessEngine processEngine) {
        this.processEngine = processEngine;
        initializeConfiguration();
    }

    @Override
    public AssertFactory getAssertFactory() {
        return assertFactory;
    }

    public void setActivitiRule(final ActivitiRule activitiRule) {
        setProcessEngine(activitiRule.getProcessEngine());
    }

    public HistoryLevel getConfiguredHistoryLevel() {
        return doGetProcessEngineConfiguration().getHistoryLevel();
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
        doGetProcessEngineConfiguration().setProcessEngineLifecycleListener(new ProcessEngineCloseListener());
    }

    private ProcessEngineConfigurationImpl doGetProcessEngineConfiguration() {
        ProcessEngineConfigurationImpl configuration = null;
        if (this.processEngine instanceof ProcessEngineConfigurationImpl) {
            configuration = (ProcessEngineConfigurationImpl) this.processEngine;
        } else if (this.processEngine instanceof ProcessEngineImpl) {
            configuration = ((ProcessEngineImpl) this.processEngine).getProcessEngineConfiguration();
        }
        return configuration;
    }

    private final class ProcessEngineCloseListener implements ProcessEngineLifecycleListener {

        @Override
        public void onProcessEngineClosed(final ProcessEngine processEngine) {
            ProcessAssertActivitiConfiguration.this.processEngine = null;
            messageLogger.logInfo(LOGGER, LogMessage.CONFIGURATION_2.getBundleKey(), processEngine.getName());
        }

        @Override
        public void onProcessEngineBuilt(final ProcessEngine processEngine) {
            // no-op
        }
    }

}
