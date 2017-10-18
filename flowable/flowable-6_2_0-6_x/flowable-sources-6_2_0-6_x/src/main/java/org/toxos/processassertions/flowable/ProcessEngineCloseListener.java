package org.toxos.processassertions.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toxos.processassertions.api.internal.MessageLogger;

/**
 * Close listener for process engines.
 */
class ProcessEngineCloseListener implements ProcessEngineLifecycleListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessAssertFlowableConfiguration.class);

    private final ProcessAssertFlowableConfiguration configuration;
    private final MessageLogger messageLogger;

    public ProcessEngineCloseListener(final ProcessAssertFlowableConfiguration configuration, final MessageLogger messageLogger) {
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