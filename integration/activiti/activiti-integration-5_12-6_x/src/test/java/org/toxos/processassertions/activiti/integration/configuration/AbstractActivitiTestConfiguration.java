/*******************************************************************************
 * Copyright 2017 Tiese Barrell
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
package org.toxos.processassertions.activiti.integration.configuration;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.h2.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

public abstract class AbstractActivitiTestConfiguration {

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource result = new SimpleDriverDataSource();
        result.setDriverClass(Driver.class);
        result.setUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=1000");
        result.setUsername("sa");
        result.setPassword("");
        return result;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public ProcessEngineConfigurationImpl processEngineConfiguration() {
        final SpringProcessEngineConfiguration result = new SpringProcessEngineConfiguration();
        result.setProcessEngineName("activiti-integration-test-engine");
        result.setDataSource(dataSource());
        result.setTransactionManager(transactionManager());
        result.setDatabaseSchemaUpdate("true");
        result.setHistoryLevel(HistoryLevel.FULL);
        result.setHistory(HistoryLevel.FULL.getKey());
        return enrichProcessEngineConfigurationImpl(result);
    }

    protected abstract ProcessEngineConfigurationImpl enrichProcessEngineConfigurationImpl(final SpringProcessEngineConfiguration result);

    @Bean
    public ProcessEngineFactoryBean processEngine() {
        final ProcessEngineFactoryBean result = new ProcessEngineFactoryBean();
        result.setProcessEngineConfiguration(processEngineConfiguration());
        return result;
    }

    @Bean
    public RepositoryService repositoryService() throws Exception {
        return processEngine().getObject().getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService() throws Exception {
        return processEngine().getObject().getRuntimeService();
    }

    @Bean
    public TaskService taskService() throws Exception {
        return processEngine().getObject().getTaskService();
    }

    @Bean
    public HistoryService historyService() throws Exception {
        return processEngine().getObject().getHistoryService();
    }

    @Bean
    public ManagementService managementService() throws Exception {
        return processEngine().getObject().getManagementService();
    }

    @Bean
    public ActivitiRule activitiRule() throws Exception {
        // Workaround for quirk in earlier Activiti versions where an ActivitiRule
        // instantiated using a ProcessEngine does not correctly configure the
        // services from the ProcessEngine, resulting in NPEs when retrieving
        // services from the rule.
        final ActivitiRule result = new ActivitiRule();
        result.setProcessEngine(processEngine().getObject());
        return result;
    }

}
