package org.toxos.processassertions.flowable;


import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.history.HistoryLevel;
import org.toxos.processassertions.api.internal.ApiCallback;
import org.toxos.processassertions.api.internal.AssertFactory;

/**
 * Base class for Flowable process assertables. Provides access to services from configuration.
 *
 * @author Tiese Barrell
 */
public abstract class AbstractProcessAssertable {

    protected final ApiCallback callback;
    protected final ProcessAssertFlowableConfiguration configuration;

    AbstractProcessAssertable(final ApiCallback callback, final ProcessAssertFlowableConfiguration configuration) {
        this.callback = callback;
        this.configuration = configuration;
    }

    protected RuntimeService getRuntimeService() {
        return configuration.getProcessEngine().getRuntimeService();
    }

    protected HistoryService getHistoryService() {
        return configuration.getProcessEngine().getHistoryService();
    }

    protected TaskService getTaskService() {
        return configuration.getProcessEngine().getTaskService();
    }

    protected HistoryLevel getConfiguredHistoryLevel() {
        return configuration.getConfiguredHistoryLevel();
    }

    protected AssertFactory getAssertFactory() {
        return configuration.getAssertFactory();
    }

}
