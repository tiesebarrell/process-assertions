Activiti Process Assertions
===========================

This Java library provides an easy way to perform checks on expected [JUnit](http://junit.org/ "JUnit test framework") test results when testing `BPMN` processes with the [Activiti](http://activiti.org/ "Activiti") framework. Instead of coding the checks against Activiti's `API`, you perform simple functional assertions as you would with other `JUnit` tests. For instance, you often wish to check whether a process has been started, it is waiting in a `UserTask` or has reached a certain end state.

Take the example of checking whether a process has been started. When testing a `BPMN` process definition with Activiti, you typically start a process instance by invoking one of the `RuntimeService`'s start methods, hold a reference to the `ProcessInstance` it returns and then query the `RuntimeService` with that instance's id to see whether the process is still running (assuming the process reaches a wait state before completing). Starting the process will remain a part of your unit test, but performing the checks is a repetitive piece of code that clogs up the rest of your test code. Checking whether a process is started is a simple check, but some checks require quite a couple of lines to make sure everything is OK. Process assertions save you the time of writing these checks and make your unit test easier to understand.

## Simple example

To illustrate how process assertions work, let's look at an example:

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
 	@Deployment(resources = "MyProcess.bpmn")
 	public void testStartProcess() throws Exception {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(TEST_PROCESS_SINGLE_TASK);
		assertProcessActive(activitiRule, processInstance);
	}
