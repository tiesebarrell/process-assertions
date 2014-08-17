#!/bin/bash
 
# Script based on suggestions from: https://gist.github.com/nwinkler/9213085
# See README.md in this script's folder for attribution and reasoning.

#
# Change the values below for each release
#

# The version to be released
releaseVersion=0.6.0

# The next development version
developmentVersion=0.6.1-SNAPSHOT

# Release comment prefix
scmCommentPrefix="issue-xxx: "

#
# No changes required from this point for a new release
#
 
# Create a new release branch from develop
git checkout -b release/$releaseVersion develop
 
# Perform Maven release in batch mode 
mvn --batch-mode release:prepare release:perform -DscmCommentPrefix="$scmCommentPrefix" -DreleaseVersion=$releaseVersion -DdevelopmentVersion=$developmentVersion
 
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