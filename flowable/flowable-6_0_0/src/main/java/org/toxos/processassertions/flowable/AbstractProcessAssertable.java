package org.toxos.processassertions.flowable;

import org.flowable.engine.*;
import org.toxos.processassertions.api.internal.ApiCallback;

/**
 * Base class for Flowable process assertables. Provides access to services from configuration.
 *
 * @author Tiese Barrell
 */
public abstract class AbstractProcessAssertable {

    protected final ApiCallback callback;

    AbstractProcessAssertable(ApiCallback callback) {
        this.callback = callback;
    }

    protected RuntimeService getRuntimeService() {
        return ProcessAssertFlowableConfiguration.INSTANCE.getProcessEngine().getRuntimeService();
    }

    protected HistoryService getHistoryService() {
        return ProcessAssertFlowableConfiguration.INSTANCE.getProcessEngine().getHistoryService();
    }

    protected TaskService getTaskService() {
        return ProcessAssertFlowableConfiguration.INSTANCE.getProcessEngine().getTaskService();
    }

    protected ProcessEngineConfiguration getProcessEngineConfiguration() {
        return ProcessAssertFlowableConfiguration.INSTANCE.getProcessEngineConfiguration();
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
