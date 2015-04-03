#!/bin/bash

#Switch to the script's directory regardless of the path the script was invoked from
cd $(dirname $0);

source ../shared/prepare-profile-build.sh;

prelude="[CIBUILD]";

echo "${prelude} Running CI build";

echo "${prelude} Running test profiles";

for activitiVersion in "${activitiVersions[@]}"
do
   	:
	echo "${prelude} Running test profile for Activiti version ${activitiVersion}";
	mvn clean test -P activiti-${activitiVersion};
done

echo "${prelude} Running deploy";
echo "${prelude}   Deploy currently disabled";

#mvn clean deploy;

echo "${prelude} Completed CI build";
