/*******************************************************************************
 * Copyright 2014 Tiese Barrell
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.toxos.activiti.assertion;

import java.util.Locale;

import org.activiti.engine.EngineServices;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.test.ActivitiRule;

/**
 * Base implementation for {@link ProcessAssertConfiguration} that provides
 * defaults for unspecified configuration items.
 * 
 * @author Tiese Barrell
 * 
 */
public class DefaultProcessAssertConfiguration implements ProcessAssertConfiguration {

    protected Locale locale;

    protected EngineServices engineServices;

    protected DefaultProcessAssertConfiguration() {
        super();
        this.locale = Constants.DEFAULT_LOCALE;
        this.engineServices = ProcessEngines.getDefaultProcessEngine();
    }

    public DefaultProcessAssertConfiguration(final Locale locale) {
        this();
        this.locale = locale;
    }

    public DefaultProcessAssertConfiguration(final EngineServices engineServices) {
        this();
        this.engineServices = engineServices;
    }

    public DefaultProcessAssertConfiguration(final ActivitiRule activitiRule) {
        this();
        this.engineServices = activitiRule.getProcessEngine();
    }

    public DefaultProcessAssertConfiguration(final Locale locale, final ActivitiRule activitiRule) {
        this();
        this.locale = locale;
        this.engineServices = activitiRule.getProcessEngine();
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public EngineServices getEngineServices() {
        return engineServices;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public void setEngineServices(final EngineServices engineServices) {
        this.engineServices = engineServices;
    }

    public void setActivitiRule(final ActivitiRule activitiRule) {
        this.engineServices = activitiRule.getProcessEngine();
    }

}
