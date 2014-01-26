package org.toxos.activiti.assertion.internal;

import org.toxos.activiti.assertion.ProcessAssertConfiguration;

public class AssertFactoryImpl implements AssertFactory {

    @Override
    public ProcessInstanceAssertable getProcessInstanceAssertable(final ProcessAssertConfiguration configuration) {
        return new ProcessInstanceAssert(configuration);
    }

    @Override
    public EndEventAssertable getEndEventAssertable(final ProcessAssertConfiguration configuration) {
        return new EndEventAssert(configuration);
    }

    @Override
    public TaskInstanceAssertable getTaskInstanceAssertable(final ProcessAssertConfiguration configuration) {
        return new TaskInstanceAssert(configuration);
    }

    @Override
    public HistoricVariableInstanceAssertable getHistoricVariableInstanceAssertable(ProcessAssertConfiguration configuration) {
        return new HistoricVariableInstanceAssert(configuration);
    }

}
