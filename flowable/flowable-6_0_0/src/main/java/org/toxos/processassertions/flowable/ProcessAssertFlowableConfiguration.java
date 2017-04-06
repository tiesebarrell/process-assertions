package org.toxos.processassertions.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineLifecycleListener;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.impl.ProcessEngineImpl;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.test.FlowableRule;
import org.toxos.processassertions.api.DefaultProcessAssertConfiguration;
import org.toxos.processassertions.api.internal.AssertFactory;

import java.util.Locale;

/**
 * Created by tiesebarrell on 21/02/2017.
 */
public class ProcessAssertFlowableConfiguration extends DefaultProcessAssertConfiguration {

    static ProcessAssertFlowableConfiguration INSTANCE;

    private ProcessEngine processEngine;

    private AssertFactory assertFactory = new AssertFactoryImpl();

    public ProcessAssertFlowableConfiguration(final ProcessEngine processEngine) {
        super();
        this.processEngine = processEngine;
        INSTANCE = this;
    }

    public ProcessAssertFlowableConfiguration(final FlowableRule flowableRule) {
        super();
        this.processEngine = flowableRule.getProcessEngine();
        INSTANCE = this;
    }

    public ProcessAssertFlowableConfiguration(final Locale locale, final FlowableRule activitiRule) {
        super();
        this.locale = locale;
        this.processEngine = activitiRule.getProcessEngine();
        INSTANCE = this;
    }

    public ProcessEngine getProcessEngine() {
        initializeEngineServices();
        return processEngine;
    }

    public void setProcessEngine(final ProcessEngine processEngine) {
        this.processEngine = this.processEngine;
        registerProcessEngineCloseListener();
    }

    public void setFlowableRule(final FlowableRule flowableRule) {
        setProcessEngine(flowableRule.getProcessEngine());
    }

    private void registerProcessEngineCloseListener() {
        getProcessEngineConfiguration().setProcessEngineLifecycleListener(new ProcessEngineCloseListener());
    }

    public ProcessEngineConfigurationImpl getProcessEngineConfiguration() {
        initializeEngineServices();
        return doGetProcessEngineConfiguration();
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

    private void initializeEngineServices() {
        if (this.processEngine == null) {
            setProcessEngine(ProcessEngines.getDefaultProcessEngine());
        }
    }

    @Override public AssertFactory getAssertFactory() {
        return assertFactory;
    }

    private final class ProcessEngineCloseListener implements ProcessEngineLifecycleListener {

        @Override public void onProcessEngineClosed(final ProcessEngine processEngine) {
            ProcessAssertFlowableConfiguration.this.processEngine = null;
        }

        @Override public void onProcessEngineBuilt(final ProcessEngine processEngine) {
            // no-op
        }
    }

}
