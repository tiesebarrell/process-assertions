package org.toxos.activiti.assertion.internal;

public interface EndEventAssertable {

    void processEndedAndInExclusiveEndEvent(final String processInstanceId, final String endEventId);

    void processEndedAndInEndEvents(final String processInstanceId, final String... endEventIds);

}
