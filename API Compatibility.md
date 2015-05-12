# API Compatibility

##5.12 and 5.12.1
In Activiti 5.13, some API changes were made that make it slightly harder to support both 5.12, 5.12.1 and higher versions with the same code base. 

* The return type of ProcessEngineConfiguration#setProcessEngineLifecycleListener was changed to return the ProcessEngineConfiguration so as to support the fluent API for building the configuration, whereas before the method was void. This makes it impossible to create a subclass of ProcessEngineConfiguration that overrides the method that compiles against both 5.12(.1) and 5.13. A workaround was used in DefaultProcessAssertConfiguration.doGetProcessEngineConfiguration() to have a null default return value where initially a stub class was used that subclassed ProcessEngineConfiguration.
* The ProcessEngineConfiguration#getHistoryLevel() method was exposed from ProcessEngineConfigurationImpl. In order to support 5.12(.1), ProcessAssertConfiguration.getProcessEngineConfiguration currently returns a ProcessEngineConfigurationImpl instead of the preferable ProcessEngineConfiguration. This propagates to various other places in the code.

##5.16.2 to 5.16.3
In Activiti 5.16.3, a change was made to the TaskQuery interface, which made it binary incompatible with previous versions. This means that although Process Assertions supports Activiti versions 5.12 - 5.17.0 at the API level, the library will not be compatible with any Activiti versions lower than 5.16.3 from release version 0.6.0 onwards. Using the library with these versions of Activiti will yield `NoSuchMethodError`s. Unfortunately, this is not easily solved from a single library artifact, which means a solution will have to be found to build and -particularly- release multiple versions of Process Assertions that are each targeted for a range of Activiti versions. An [issue][issue-24] has been created for a future release to solve this.

[issue-24]: https://github.com/tiesebarrell/process-assertions/issues/24 "Issue 24" 