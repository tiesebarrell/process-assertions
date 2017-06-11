#!/usr/bin/env bash
echo "${prelude} Running Flowable integration tests";

cd integration/flowable-integration;
flowableVersions=("6.0.0")

echo "${prelude} Supported Flowable versions:";
for flowableVersion in "${flowableVersions[@]}"
do
   	:
	echo "${prelude}   Flowable version ${flowableVersion}";
done

for flowableVersion in "${flowableVersions[@]}"
do
   	:
	echo "${prelude} Running test profile for Flowable version ${flowableVersion}";
	#mvn clean dependency:tree test -P flowable-${flowableVersion};
done

cd ../../;