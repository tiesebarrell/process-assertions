#!/bin/bash

#Switch to the script's directory regardless of the path the script was invoked from
cd $(dirname $0);

source ../shared/shared.sh;

prelude="[PRERELEASE]";

echo "${prelude} Running pre-release build";

echo "${prelude} Running default test profile for project";
mvn clean test;

echo "${prelude} Testing javadoc generation";
mvn clean javadoc:javadoc -P release;

echo "${prelude} Running deploy";
mvn clean deploy -P release;

echo "${prelude} Completed pre-release build";