#!/bin/bash

SECONDS=0

#Switch to the script's directory regardless of the path the script was invoked from
cd $(dirname $0);

source ../shared/shared.sh;

prelude="[CIBUILD]";

echo "${prelude} Running CI build";

echo "${prelude} Running default test profile for project";

mvn clean test -T 2C;

source resources/scripts/ci/activiti-integration.sh;

source resources/scripts/ci/flowable-integration.sh;

duration=$SECONDS
durationDisplay="$(($duration / 60)) minutes and $(($duration % 60)) seconds."

echo "${prelude} Completed CI build in ${durationDisplay}";