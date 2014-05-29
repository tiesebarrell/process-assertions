# API Compatibility

##5.12 and 5.12.1
In Activiti 5.13, some API changes were made that make it slightly harder to support both 5.12, 5.12.1 and higher versions with the same code base. 

* The return type of ProcessEngineConfiguration#setProcessEngineLifecycleListener was changed to return the ProcessEngineConfiguration so as to support the fluent API for building the configuration, whereas before the method was void. This makes it impossible to create a subclass of ProcessEngineConfiguration that overrides the method that compiles against both 5.12(.1) and 5.13. A workaround was used in DefaultProcessAssertConfiguration.doGetProcessEngineConfiguration() to have a null default return value where initially a stub class was used that subclassed ProcessEngineConfiguration.
* The ProcessEngineConfiguration#getHistoryLevel() method was exposed from ProcessEngineConfigurationImpl. In order to support 5.12(.1), ProcessAssertConfiguration.getProcessEngineConfiguration currently returns a ProcessEngineConfigurationImpl instead of the preferable ProcessEngineConfiguration. This propagates to various other places in the code.