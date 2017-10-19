package org.toxos.processassertions.activiti.integration.configuration;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivitiTestActiviti5Configuration extends AbstractActivitiTestConfiguration {

    @Override
    protected ProcessEngineConfigurationImpl enrichProcessEngineConfigurationImpl(final SpringProcessEngineConfiguration result) {
        result.setJobExecutorActivate(false);
        return result;
    }

}
