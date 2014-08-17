#!/bin/bash

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

mvn clean test -P activiti-5.12;
mvn clean test -P activiti-5.12.1;
mvn clean test -P activiti-5.13;
mvn clean test -P activiti-5.14;
mvn clean test -P activiti-5.15;
mvn clean test -P activiti-5.15.1;
mvn clean test -P activiti-5.16;
mvn clean test -P activiti-5.16.1;

echo "****************************************";
echo "* Running pre-release deploy           *";
echo "****************************************";
echo "";

mvn clean deploy;

echo "****************************************";
echo "* Completed pre-release checks         *";
echo "****************************************";
echo "";