package org.toxos.activiti.assertion;

import java.util.Locale;

import org.activiti.engine.EngineServices;

public interface ProcessAssertConfiguration {

    Locale getLocale();

    EngineServices getEngineServices();

}
