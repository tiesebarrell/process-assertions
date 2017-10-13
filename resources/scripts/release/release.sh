#!/bin/bash
 
# Script based on suggestions from: https://gist.github.com/nwinkler/9213085
# See README.md in this script's folder for attribution and reasoning.

#Switch to the script's directory regardless of the path the script was invoked from
cd $(dirname $0);

source ../shared/shared.sh;

prelude="[RELEASE]";

function performRelease {
	echo "${prelude} Performing release...";

	echo "${prelude} You provided: '$releaseVersion' -> '$developmentVersion' with '$scmCommentPrefix'";
	
	# Create a new release branch from develop
	git checkout -b release/$releaseVersion develop
 
	# Perform Maven release in batch mode 
	mvn -P release --batch-mode release:prepare release:perform -DscmCommentPrefix="$scmCommentPrefix" -DreleaseVersion=$releaseVersion -DdevelopmentVersion=$developmentVersion
 
	# Merge the new release + snapshot version commits back into develop
	git checkout develop
	git merge --no-ff -m "$scmCommentPrefix Merge release/$releaseVersion into develop" release/$releaseVersion

	# Merge the new release but NOT the snapshot version commits back into develop
	git checkout master
	git merge --no-ff -m "$scmCommentPrefix Merge previous version into master to avoid the increased version number" release/$releaseVersion~1

	# Remove the release branch that was created
	git branch -D release/$releaseVersion

	# Switch back to the develop branch
	git checkout develop

	# Push changes manually
	git push --all && git push --tags

	echo "${prelude} Release performed.";
}

# The version to be released
echo "${prelude} Type the version to release (e.g.: 0.7.0) and press [ENTER]: "
read releaseVersion;

# The next development version
echo "${prelude} Type the development version to switch to after release (e.g.: 0.7.0-SNAPSHOT) and press [ENTER]: "
read developmentVersion;

# Release comment prefix
echo "${prelude} Type the release comment prefix for the release (e.g.: issue-18: Release 0.7.0) and press [ENTER]: "
read scmCommentPrefix;

echo "${prelude} You provided: '$releaseVersion' -> '$developmentVersion' with '$scmCommentPrefix'";
while true; do
    read -p "${prelude} Is this correct? [y/n]: " performRelease;
    case $performRelease in
        [Yy]* ) performRelease; break;;
        [Nn]* ) echo "${prelude} Exiting build without performing release."; exit;;
        * ) echo "Please answer yes or no.";;
    esac
done
