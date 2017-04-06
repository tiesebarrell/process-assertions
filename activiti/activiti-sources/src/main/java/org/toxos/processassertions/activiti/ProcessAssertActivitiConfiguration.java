package org.toxos.processassertions.activiti;

import org.activiti.engine.EngineServices;
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

    private EngineServices engineServices;

    private AssertFactory assertFactory = new AssertFactoryImpl();

    public ProcessAssertActivitiConfiguration(final EngineServices engineServices) {
        super();
        this.engineServices = engineServices;
        INSTANCE = this;
    }

    public ProcessAssertActivitiConfiguration(final ActivitiRule activitiRule) {
        super();
        this.engineServices = activitiRule.getProcessEngine();
        INSTANCE = this;
    }

    public ProcessAssertActivitiConfiguration(final Locale locale, final ActivitiRule activitiRule) {
        super();
        this.locale = locale;
        this.engineServices = activitiRule.getProcessEngine();
        INSTANCE = this;
    }

    public EngineServices getEngineServices() {
        initializeEngineServices();
        return engineServices;
    }

    public void setEngineServices(final EngineServices engineServices) {
        this.engineServices = engineServices;
        registerProcessEngineCloseListener();
    }

    public void setActivitiRule(final ActivitiRule activitiRule) {
        setEngineServices(activitiRule.getProcessEngine());
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
        if (this.engineServices instanceof ProcessEngineConfigurationImpl) {
            configuration = (ProcessEngineConfigurationImpl) this.engineServices;
        } else if (this.engineServices instanceof ProcessEngineImpl) {
            configuration = ((ProcessEngineImpl) this.engineServices).getProcessEngineConfiguration();
        }
        return configuration;
    }

    private void initializeEngineServices() {
        if (this.engineServices == null) {
            setEngineServices(ProcessEngines.getDefaultProcessEngine());
        }
    }

    @Override public AssertFactory getAssertFactory() {
        return assertFactory;
    }

    private final class ProcessEngineCloseListener implements ProcessEngineLifecycleListener {

        @Override public void onProcessEngineClosed(final ProcessEngine processEngine) {
            engineServices = null;
        }

        @Override public void onProcessEngineBuilt(final ProcessEngine processEngine) {
            // no-op
        }
    }

}
