# Activiti Process Assertions
This Java library provides an easy way to perform checks on expected [JUnit](https://github.com/junit-team/junit "JUnit test framework") test results when testing `BPMN` processes with the [Activiti](http://activiti.org/ "Activiti") framework. Instead of coding the checks against Activiti's `API`, you perform simple functional assertions as you would with other `JUnit` tests. For instance, you often wish to check whether a process has been started, it is waiting in a `UserTask` or has reached a certain end state.

Take the example of checking whether a process has been started. When testing a `BPMN` process definition with Activiti, you typically start a process instance by invoking one of the `RuntimeService`'s start methods, hold a reference to the `ProcessInstance` it returns and then query the `RuntimeService` with that instance's id to see whether the process is still running (assuming the process reaches a wait state before completing). Starting the process will remain a part of your unit test, but performing the checks is a repetitive piece of code that clogs up the rest of your test code. Checking whether a process is started is a simple check, but some checks require quite a couple of lines to make sure everything is OK. Process assertions save you the time of writing these checks and make your unit test easier to understand.

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

The assertions are wrapped inside the ProcessAssert.assertProcessActive() method.

## Assertions
ProcessAssert will throw regular JUnit AssertErrors with decriptive messages so you can hunt down problems in your process definition. This integrates with JUnit's normal way of handling exceptions and should provide you with clear information in your IDE.

You have to provide an `ActivitiRule` instance to `ProcessAssert`'s methods. This allows ProcessAssert to access the Activiti services in the way you setup your test to perform the checks. Most methods are overloaded, in most cases to allow you to provide either an object or an id of the main object of the test. For instance, if you are performing checks on `ProcessInstance`s, you can provide either the instance object or the id of the object. When testing `UserTask`s, you can provide the `Task` instance or the id of the task. 

## Overview of assertions
Process assertion methods should be obvious from their name and JavaDocs, but here's an overview of the assertions supported:

### Asserting a process is active
`assertProcessActive(...);`
TODO