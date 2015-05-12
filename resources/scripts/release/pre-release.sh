#!/bin/bash

#Switch to the script's directory regardless of the path the script was invoked from
cd $(dirname $0);

source ../shared/prepare-profile-build.sh;

prelude="[PRERELEASE]";

echo "${prelude} Running pre-release build";

echo "${prelude} Running test profiles";

for activitiVersion in "${activitiVersions[@]}"
do
   	:
	echo "${prelude} Running test profile for Activiti version ${activitiVersion}";
	mvn clean test -P activiti-${activitiVersion};
done

echo "${prelude} Testing javadoc generation";
mvn clean javadoc:javadoc;

echo "${prelude} Running deploy";
mvn clean deploy;

echo "${prelude} Completed pre-release build";