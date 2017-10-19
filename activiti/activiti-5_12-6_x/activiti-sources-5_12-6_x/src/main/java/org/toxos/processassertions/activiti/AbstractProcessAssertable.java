package org.toxos.processassertions.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.history.HistoryLevel;
import org.toxos.processassertions.api.internal.ApiCallback;
import org.toxos.processassertions.api.internal.AssertFactory;

/**
 * Base class for Activiti process assertables. Provides access to services from configuration.
 *
 * @author Tiese Barrell
 */
public abstract class AbstractProcessAssertable {

    protected final ApiCallback callback;
    protected final ProcessAssertActivitiConfiguration configuration;

    AbstractProcessAssertable(final ApiCallback callback, final ProcessAssertActivitiConfiguration configuration) {
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
