#!/bin/bash

SECONDS=0

#Switch to the script's directory regardless of the path the script was invoked from
cd $(dirname $0);

source ../shared/shared.sh;

prelude="[CIBUILD]";

echo "${prelude} Running CI sources build";

echo "${prelude} Running default test profile for project and installing to local repository";

mvn clean install -T 2C;

duration=$SECONDS
durationDisplay="$(($duration / 60)) minutes and $(($duration % 60)) seconds."

echo "${prelude} Completed CI sources build in ${durationDisplay}";