# Activiti Process Assertions
This Java library provides an easy way to perform checks on expected [JUnit][linkJUnit] test results when testing [BPMN][linkBpmn] processes with the [Activiti][linkActiviti] framework. Instead of coding the checks against Activiti's API, you perform simple functional assertions as you would with other JUnit tests. For instance, you often wish to check whether a process has been started, it is waiting in a `UserTask` or has reached a certain end state.

More information is available on the [project's website][linkProject]

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
[linkProject]: http://toxos.org/activiti-process-assertions/ "Activiti Process Assertions project website"