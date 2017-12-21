#!/usr/bin/env bash
echo "${prelude} Running Activiti integration tests";

cd integration/activiti/activiti-integration-5_12-6_x;

declare -a activitiVersions=(
                "5.12"
                "5.12.1"
                "5.13"
                "5.14"
                "5.15"
                "5.15.1"
                "5.16"
                "5.16.1"
                "5.16.2"
                "5.16.3"
                "5.16.4"
                "5.17.0"
                "5.18.0"
                "5.19.0"
                "5.19.0.1"
                "5.19.0.2"
                "5.19.0.3"
                "5.20.0"
                "5.21.0"
                "5.22.0"
                "6.0.0"
                )

echo "${prelude} Supported Activiti versions:";
for activitiVersion in "${activitiVersions[@]}"
do
   	:
	echo "${prelude}   Activiti version ${activitiVersion}";
done

for activitiVersion in "${activitiVersions[@]}"
do
   	:
	echo "${prelude} Running test profile for Activiti version ${activitiVersion}";
	mvn clean dependency:tree test -P activiti-${activitiVersion};
done

cd ../../../;