package org.toxos.processassertions.activiti;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.toxos.processassertions.api.internal.ApiCallback;

/**
 * Base class for Activiti process assertables. Provides access to services from configuration.
 *
 * @author Tiese Barrell
 */
public abstract class AbstractProcessAssertable {

    protected final ApiCallback callback;

    AbstractProcessAssertable(ApiCallback callback) {
        this.callback = callback;
    }

    protected RuntimeService getRuntimeService() {
        return ProcessAssertActivitiConfiguration.INSTANCE.getEngineServices().getRuntimeService();
    }

    protected HistoryService getHistoryService() {
        return ProcessAssertActivitiConfiguration.INSTANCE.getEngineServices().getHistoryService();
    }

    protected TaskService getTaskService() {
        return ProcessAssertActivitiConfiguration.INSTANCE.getEngineServices().getTaskService();
    }

    protected ProcessEngineConfigurationImpl getProcessEngineConfiguration() {
        return ProcessAssertActivitiConfiguration.INSTANCE.getProcessEngineConfiguration();
    }

    /**
     * Gets the {@link ProcessEngine}.
     *
     * @return the process engine
     */
    protected final ProcessEngine getProcessEngine() {
        return ProcessEngines.getDefaultProcessEngine();
    }

}
