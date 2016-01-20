echo "${prelude} Running test profiles";

activitiVersions=("5.16.3" "5.16.4" "5.17.0")

echo "${prelude} Supported Activiti versions:";
for activitiVersion in "${activitiVersions[@]}"
do
   	:
	echo "${prelude}   Activiti version ${activitiVersion}";
done

cd activiti-5_16_3-5_x;

for activitiVersion in "${activitiVersions[@]}"
do
   	:
	echo "${prelude} Running test profile for Activiti version ${activitiVersion}";
	mvn clean test -P activiti-${activitiVersion};
done

cd ../;