package org.toxos.processassertions.flowable.integration.configuration;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.history.HistoryLevel;
import org.flowable.engine.test.FlowableRule;
import org.flowable.spring.ProcessEngineFactoryBean;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.h2.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration public class FlowableTestConfiguration {

    @Bean public DataSource dataSource() {
        final SimpleDriverDataSource result = new SimpleDriverDataSource();
        result.setDriverClass(Driver.class);
        result.setUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=1000");
        result.setUsername("sa");
        result.setPassword("");
        return result;
    }

    @Bean public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean public ProcessEngineConfigurationImpl processEngineConfiguration() {
        final SpringProcessEngineConfiguration result = new SpringProcessEngineConfiguration();
        result.setDataSource(dataSource());
        result.setTransactionManager(transactionManager());
        result.setDatabaseSchemaUpdate("true");
        result.setAsyncExecutorActivate(false);
        result.setHistoryLevel(HistoryLevel.FULL);
        return result;
    }

    @Bean public ProcessEngineFactoryBean processEngine() {
        final ProcessEngineFactoryBean result = new ProcessEngineFactoryBean();
        result.setProcessEngineConfiguration(processEngineConfiguration());
        return result;
    }

    @Bean public RepositoryService repositoryService() throws Exception {
        return processEngine().getObject().getRepositoryService();
    }

    @Bean public RuntimeService runtimeService() throws Exception {
        return processEngine().getObject().getRuntimeService();
    }

    @Bean public TaskService taskService() throws Exception {
        return processEngine().getObject().getTaskService();
    }

    @Bean public HistoryService historyService() throws Exception {
        return processEngine().getObject().getHistoryService();
    }

    @Bean public ManagementService managementService() throws Exception {
        return processEngine().getObject().getManagementService();
    }

    @Bean public FlowableRule flowableRule() throws Exception {
        return new FlowableRule(processEngine().getObject());
    }

}
