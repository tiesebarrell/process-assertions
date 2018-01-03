#!/bin/bash
 
# Script based on suggestions from: https://gist.github.com/nwinkler/9213085
# See README.md in this script's folder for attribution and reasoning.

#Switch to the script's directory regardless of the path the script was invoked from
cd $(dirname $0);

source ../shared/shared.sh;

prelude="[RELEASE]";
separatorLine="${prelude} ------------------------------------------------------------------------";
separatorBlock="${prelude}\n${separatorLine}";
tagPrefix="process-assertions";

echo -e ${separatorLine};
echo "${prelude} Performing release...";
echo -e ${separatorLine};

function performRelease {

	# Create a new release branch from develop
	echo -e $separatorBlock;
    echo "${prelude} Checking out new release branch release/$releaseVersion";
    git checkout -b release/$releaseVersion develop

    # Let Maven transform POM versions recursively to release version
    echo "${prelude} Bumping versions to release version ${releaseVersion}...";
    mvn clean versions:set -DnewVersion=$releaseVersion -DgenerateBackupPoms=false;
    echo -e $separatorBlock;

    # Verify current state
    echo -e $separatorBlock;
    echo "${prelude} Performing verify of release version on release branch";
    mvn clean verify;
    echo -e $separatorBlock;

    # Commit changes
    echo -e $separatorBlock;
    echo "${prelude} Staging and committing updated files";
    git add -u;
    git status;
    git commit -m "$scmCommentPrefix - Prepare release $releaseVersion";
    echo -e $separatorBlock;

    # Create release tag as the current state in the release version
    echo -e $separatorBlock;
    echo "${prelude} Tagging release version ${releaseVersion}...";
    git tag "$tagPrefix-$releaseVersion";
    echo "${prelude} Created tag $tagPrefix-$releaseVersion.";
    echo -e $separatorBlock;

    # Let Maven transform POM versions recursively to new development version
    echo -e $separatorBlock;
    echo "${prelude} Bumping versions to development version ${developmentVersion}...";
    mvn clean versions:set -DnewVersion=$developmentVersion -DgenerateBackupPoms=false;

    # Commit changes
    echo "${prelude} Staging and committing updated files";
    git add -u;
    git status;
    git commit -m "$scmCommentPrefix - Prepare for development version $developmentVersion";
    echo -e $separatorBlock;

    # Merge the new release + snapshot version commits back into develop
    echo -e $separatorBlock;
    echo "${prelude} Merging release branch into develop";
    git checkout develop
    git merge --no-ff -m "$scmCommentPrefix - Merge release/$releaseVersion into develop" release/$releaseVersion
    echo -e $separatorBlock;

    # Merge the new release but NOT the snapshot version commits back into master
    echo -e $separatorBlock;
    echo "${prelude} Merging release branch into master";
    git checkout master
    git merge --no-ff -m "$scmCommentPrefix - Merge release/$releaseVersion~1 into master" release/$releaseVersion~1
    echo -e $separatorBlock;

    # Release to Maven Central by performing deploy on the tagged version
    echo -e $separatorBlock;
    echo "Releasing new release version to Maven Central";
    mvn clean deploy -P release -DskipTests --batch-mode;
    echo -e $separatorBlock;

    # Switch back to the develop branch
    echo -e $separatorBlock;
    echo "${prelude} Switching back to develop branch";
    git checkout develop

    # Remove the release branch that was created
    echo "${prelude} Removing release branch release/$releaseVersion";
    git branch -D release/$releaseVersion
    echo -e $separatorBlock;

    # Push changes manually
    echo -e $separatorBlock;
    echo "${prelude} Pushing branches and tags";
    git push --all && git push --tags
    echo -e $separatorBlock;

    echo "${prelude} Release performed.";
}

# Currently all releases start from develop branch
echo "${prelude} Checking out develop branch";
git checkout develop;

# Check workspace for dirty files
echo -e ${separatorLine};
echo "${prelude} Checking workspace state for outstanding changes blocking release...";
trap 'echo -e "\033[0;31mFAILED\033[0m"' ERR
git diff-index --quiet HEAD --
trap - ERR
echo -e "\033[0;32mCLEAN\033[0m"

echo "${prelude} Check complete. Ready to proceed";

echo -e $separatorBlock;
echo "${prelude} Performing verify of current state on branch develop";
mvn clean verify;
echo -e $separatorBlock;

echo "${prelude} Determining release version from Maven context";
mvnReleaseVersion=$(mvn -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec);
mvnReleaseVersion=${mvnReleaseVersion%-SNAPSHOT};

echo -e $separatorLine;
read -p "${prelude} Version to release [${mvnReleaseVersion}]: " releaseVersion;
scmCommentPrefix="[Release $releaseVersion]";

read -p "${prelude} Version to continue development for [ > ${mvnReleaseVersion}]: " developmentVersion;

echo -e $separatorBlock;
echo "${prelude} You provided: FROM '$mvnReleaseVersion', RELEASE '$releaseVersion' -> DEVELOP '$developmentVersion'";
echo "${prelude}   SCM comments will be prefixed with '$scmCommentPrefix'";
echo "${prelude}   Git tag will be prefixed with '$tagPrefix'";
while true; do
    read -p "${prelude} Is this correct? [y/n]: " performRelease;
    case $performRelease in
        [Yy]* ) performRelease; break;;
        [Nn]* ) echo "${prelude} Exiting build without performing release."; exit;;
        * ) echo "Please answer yes or no.";;
    esac
done
