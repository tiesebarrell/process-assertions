#!/bin/bash

#Switch to the script's directory regardless of the path the script was invoked from
cd $(dirname $0);

source ../shared/shared.sh;

prelude="[CIBUILD]";

echo "${prelude} Running CI build";

source $(dirname $0)/ci-activiti-5_12-5_16_2.sh;
source $(dirname $0)/ci-activiti-5_16_3-5_x.sh;

echo "${prelude} Running default test profile for project";

mvn clean test;

echo "${prelude} Completed CI build";