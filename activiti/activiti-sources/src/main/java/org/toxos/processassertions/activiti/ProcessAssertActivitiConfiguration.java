package org.toxos.processassertions.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineLifecycleListener;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.test.ActivitiRule;
import org.toxos.processassertions.api.DefaultProcessAssertConfiguration;
import org.toxos.processassertions.api.internal.AssertFactory;

import java.util.Locale;

/**
 * Created by tiesebarrell on 21/02/2017.
 */
public class ProcessAssertActivitiConfiguration extends DefaultProcessAssertConfiguration {

    static ProcessAssertActivitiConfiguration INSTANCE;

    private ProcessEngine processEngine;

    private AssertFactory assertFactory = new AssertFactoryImpl();

    public ProcessAssertActivitiConfiguration(final ProcessEngine processEngine) {
        super();
        this.processEngine = processEngine;
        INSTANCE = this;
    }

    public ProcessAssertActivitiConfiguration(final ActivitiRule activitiRule) {
        super();
        this.processEngine = activitiRule.getProcessEngine();
        INSTANCE = this;
    }

    public ProcessAssertActivitiConfiguration(final Locale locale, final ActivitiRule activitiRule) {
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
        this.processEngine = processEngine;
        registerProcessEngineCloseListener();
    }

    public void setActivitiRule(final ActivitiRule activitiRule) {
        setProcessEngine(activitiRule.getProcessEngine());
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

    @Override
    public AssertFactory getAssertFactory() {
        return assertFactory;
    }

    private final class ProcessEngineCloseListener implements ProcessEngineLifecycleListener {

        @Override
        public void onProcessEngineClosed(final ProcessEngine processEngine) {
            ProcessAssertActivitiConfiguration.this.processEngine = null;
        }

        @Override
        public void onProcessEngineBuilt(final ProcessEngine processEngine) {
            // no-op
        }
    }

}
