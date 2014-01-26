package org.toxos.activiti.assertion.internal;

public interface ProcessInstanceAssertable {

    void processIsActive(final String processInstanceId);

    void processIsEnded(final String processInstanceId);

}
