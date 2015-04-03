#!/bin/bash

echo "****************************************";
echo "* Running CI           *";
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
echo "* Running CI test profiles    *";
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
echo "* Running ci deploy           *";
echo "****************************************";
echo "";

#mvn clean deploy;

echo "****************************************";
echo "* Completed CI         *";
echo "****************************************";
echo "";