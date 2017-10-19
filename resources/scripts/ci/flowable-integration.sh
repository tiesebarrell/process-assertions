#!/usr/bin/env bash
echo "${prelude} Running Flowable integration tests";

cd integration/flowable/flowable-integration-6_0_0-6_1_2;

declare -a flowableVersions=(
                "6.0.0"
                "6.0.1"
                "6.1.0"
                "6.1.1"
                "6.1.2"
                )

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
	mvn clean dependency:tree test -P flowable-${flowableVersion};
done

cd ../flowable-integration-6_2_0-6_x;

declare -a flowableVersions=(
                "6.2.0"
                )

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
	mvn clean dependency:tree test -P flowable-${flowableVersion};
done

cd ../../../;