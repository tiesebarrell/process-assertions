#!/bin/bash

prelude="[PREPARE]";

echo "${prelude} Preparing profile build...";

#TODO: traverse the path up to a pom.xml file
if [ -e "pom.xml" ]
then
  echo "${prelude} Running from project root directory";
else
  echo "${prelude} Running from subdirectory. Switching to project root directory";
  cd ../../../;
fi

echo "${prelude} Configuring to exit after any command fails";
set -e;

activitiVersions=("5.12" "5.12.1" "5.13" "5.14" "5.15" "5.15.1" "5.16" "5.16.1" "5.16.2" "5.16.3" "5.16.4" "5.17.0")

echo "${prelude} Supported Activiti versions:";

for activitiVersion in "${activitiVersions[@]}"
do
   	:
	echo "${prelude}   Activiti version ${activitiVersion}";
done

echo "${prelude} Profile build prepared.";