#!/bin/bash

#Switch to the script's directory regardless of the path the script was invoked from
cd $(dirname $0);

source ../shared/prepare-profile-build.sh;

echo "****************************************";
echo "* Running pre-release checks           *";
echo "****************************************";
echo "";

if [ -e "pom.xml" ]
then
  echo "Running from project root directory";
else
  echo "Switching to project root directory";
  cd ../../;
fi

echo "****************************************";
echo "* Running pre-release test profiles    *";
echo "****************************************";
echo "";

activitiVersions=("5.12" "5.12.1" "5.13" "5.14" "5.15" "5.15.1" "5.16" "5.16.1" "5.16.2" "5.16.3" "5.16.4")

for activitiVersion in "${activitiVersions[@]}"
do
   	:
   	echo "****************************************";
	echo "* Running test profile for ${activitiVersion} *";
	echo "****************************************";
	echo "";
	mvn clean test -P activiti-${activitiVersion};
done

echo "****************************************";
echo "* Running pre-release deploy           *";
echo "****************************************";
echo "";

mvn clean deploy;

echo "****************************************";
echo "* Completed pre-release checks         *";
echo "****************************************";
echo "";