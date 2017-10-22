# Process Assertions
This Java library provides an easy way to perform checks on expected integration test results when testing [BPMN][linkBpmn] processes with the [Activiti][linkActiviti] and [Flowable][linkFlowable] BPM frameworks. Instead of coding your own checks against your process engine's API, you perform simple functional assertions as you would with other test cases. For instance, you often wish to check whether a process has been started, it is waiting in a `UserTask` or has reached a certain end state.

More information is available on the [project's website][linkProject]

## Build status
Build status: ![Build status][buildStatus]

## Credits
Thanks to:
* @omaas for contributing the Czech translation.

## License
Copyright 2014 Tiese Barrell

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[linkActiviti]: http://activiti.org/ "Activiti"
[linkFlowable]: http://flowable.org/ "Flowable"
[linkBpmn]: http://bpmn.org "Business Process Model and Notation"
[linkProject]: http://toxos.org/process-assertions/ "Process Assertions project website"
[buildStatus]: https://travis-ci.org/tiesebarrell/process-assertions.svg?branch=develop  "Process Assertions Build Status"	

