# Activiti Process Assertions
This Java library provides an easy way to perform checks on expected [JUnit][linkJUnit] test results when testing [BPMN][linkBpmn] processes with the [Activiti][linkActiviti] framework. Instead of coding the checks against Activiti's API, you perform simple functional assertions as you would with other JUnit tests. For instance, you often wish to check whether a process has been started, it is waiting in a `UserTask` or has reached a certain end state.

Take the example of checking whether a process has been started. When testing a BPMN process definition with Activiti, you typically start a process instance by invoking one of the `RuntimeService`'s start methods, hold a reference to the `ProcessInstance` it returns and then query the `RuntimeService` with that instance's id to see whether the process is still running (assuming the process reaches a wait state before completing). Starting the process will remain a part of your unit test, but performing the checks is a repetitive piece of code that clogs up the rest of your test code. Checking whether a process is started is a simple check, but some checks require quite a couple of lines to make sure everything is OK. Process assertions save you the time of writing these checks and make your unit test easier to understand.

## Simple example

To illustrate how process assertions work, let's look at an example:

```java
/**
 * Example test for MyProcess.
 * 
 */
public class MyProcessTest {

  @Rule
  public ActivitiRule activitiRule = new ActivitiRule("example/activiti.cfg.xml");

  @Test
  @Deployment(resources = "example/MyProcess.bpmn")
  public void testStartProcess() throws Exception {
    ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess");
    ProcessAssert.assertProcessActive(activitiRule, processInstance);
  }

}
```

The assertions are wrapped inside the `ProcessAssert.assertProcessActive()` method.

## Assertions
When you use `ProcessAssert`, it will throw regular JUnit `AssertError`s with decriptive messages so you can hunt down problems in your process definition (or in your test case!). This integrates with JUnit's normal way of handling exceptions and should provide you with clear information in your IDE. For example:

![Assertion error in Eclipse][imgAssertionErrorInEclipse]

You have to provide an `ActivitiRule` instance to `ProcessAssert`'s methods. This allows `ProcessAssert` to access the Activiti services (used to perform the checks) according to the way you set up  Activiti in your test. Most methods in `ProcessAssert` are overloaded, to allow you to provide either an object or an id of the main object of the assertion method. For instance, if you are performing checks on `ProcessInstance`s, you can provide either the instance object or the id of the object. When testing `UserTask`s, you can provide the `Task` instance or the id of the task. 

## Overview of assertions
Process assertion methods should be obvious from their name and JavaDocs, but here's an overview of the assertions supported:

### Asserting a process is active
`assertProcessActive(...);`
TODO

## Logging configuration
Process assertions use the [SLF4J][linkSlf4j] logging API to provide logging information. This allows you to route the logging to your logging framework of choice. Doing so requires you to ensure there is a binding for your logging framework on the classpath. SLF4J will then route the logging produced by process assertions to your logging framework. If there is no binding found, SLF4J will produce a message like the one shown below telling you that no binding was found and the logging will be routed to a no-operation logger, so you won't see any further logging.

```
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
```

### Example configuration with log4j
If you are using [Maven][linkMaven] and want to use [Log4j][linkLog4j] as logging framework, you need to add two dependencies: one for Log4j itself and one binding from SLF4J to Log4j. For example:

```xml
<dependency>
	<groupId>org.slf4j</groupId>
	<artifactId>slf4j-log4j12</artifactId>
	<version>${slf4j.version}</version>
</dependency>	
<dependency>
	<groupId>log4j</groupId>
	<artifactId>log4j</artifactId>
	<version>${log4j.version}</version>
</dependency>
```

You can then configure logging as part of the regular Log4j configuration, in either a property file or XML file. To enable DEBUG logging on process assertions, use something similar to this:

```
log4j.category.org.anemonos.activiti=DEBUG
```

You can find more information on configuring logging for SLF4J [here][linkSlf4jManual].

## License
Copyright 2013 Tiese Barrell

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[linkJUnit]: https://github.com/junit-team/junit "JUnit test framework"
[linkActiviti]: http://activiti.org/ "Activiti"
[linkBpmn]: http://bpmn.org "Business Process Model and Notation"
[linkSlf4j]: http://slf4j.org/ "Simple Logging Facade for Java"
[linkSlf4jManual]: http://slf4j.org/manual.html "SLF4J Manual"
[linkMaven]: http://maven.apache.org/ "Apache Maven"
[linkLog4j]: http://logging.apache.org/log4j "Log4J"

[imgAssertionErrorInEclipse]: https://raw.github.com/tiesebarrell/activiti-process-assertions/master/src/test/resources/example/images/assertionErrorInEclipse.png "Example of assertion error in Eclipse"