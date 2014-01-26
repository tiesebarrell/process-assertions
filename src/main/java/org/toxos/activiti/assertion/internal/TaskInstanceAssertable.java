package org.toxos.activiti.assertion.internal;

public interface TaskInstanceAssertable {

    void taskIsUncompleted(final String taskId);

    void taskIsUncompleted(final String processInstanceId, final String taskDefinitionKey);

}
