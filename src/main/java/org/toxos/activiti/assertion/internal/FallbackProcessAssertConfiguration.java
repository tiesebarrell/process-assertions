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
package org.toxos.activiti.assertion.internal;

import java.util.Locale;

import org.activiti.engine.EngineServices;
import org.activiti.engine.ProcessEngines;
import org.toxos.activiti.assertion.Constants;
import org.toxos.activiti.assertion.DefaultProcessAssertConfiguration;
import org.toxos.activiti.assertion.ProcessAssertConfiguration;

/**
 * Fallback implementation of {@link ProcessAssertConfiguration}.
 * 
 * @author Tiese Barrell
 * 
 */
public class FallbackProcessAssertConfiguration extends DefaultProcessAssertConfiguration {

    public FallbackProcessAssertConfiguration() {
        super();
        this.locale = Constants.DEFAULT_LOCALE;
        this.engineServices = ProcessEngines.getDefaultProcessEngine();
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public EngineServices getEngineServices() {
        return engineServices;
    }

}
