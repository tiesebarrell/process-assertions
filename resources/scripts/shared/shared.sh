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

echo "${prelude} Profile build prepared.";