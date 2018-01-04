# Release procedure for Process Assertions

This describes the procedure for creating a new release of Process Assertions. The project has the following characteristics that require a customized approach:
* The project is listed in Maven Central and therefore follows the guidelines and requirements for projects in Maven Central. There are preconfigured options for various plugins in this setup;
* The project uses gitflow to manage its branches wrt the workflow. Gitflow doesn't seamlessly integrate with the maven-release-plugin as far as tagging is concerned. The release plugin is not used in this project

## Attribution
Based on suggestions from the following sources:
* https://gist.github.com/nwinkler/9213085
* https://gist.github.com/searls/1043970
* http://central.sonatype.org/pages/apache-maven.html#gpg-signed-components

## Prerequisites
To be able to perform release in batch mode (as is the case for the release script), the passphrase for signing artifacts is needed. This can be solved by configuring the Maven settings file. For example:

    <profile>
        <id>ossrh</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <passphrase><!--passphrase goes here--></passphrase>
        </properties>
    </profile>

Also, add the following to the server section to allow uploads:

    <server>
      <id>ossrh</id>
      <username><!--username goes here--></username>
      <password><!--password goes here--></password>
    </server>

Where `ossrh` corresponds to the id of the repositories configured in the root pom in the `distributionManagement` section.

## Procedure
1. Run the default CI scripts if they have not been run yet.
2. Run release.sh. The script is configured to fail if any of the individual steps fail. The first step, performed automatically, will run a default verification on the project. After this is complete, a prompt follows for the release version and the new development version. Confirmation of the recorded values is required before the release is actually performed. The release consists of the following actions:
    * Create a release branch according to gitflow's conventions and checkout the new branch;
    * Update the versions in the pom files to the release version;
    * Perform verification of the project with these changes;
    * Commit the changes to the release branch;
    * Create a tag (following a name convention) of the HEAD of the release branch;
    * Update the versions in the pom files to the new development version;
    * Commit the changes to the release branch;
    * Checkout the develop branch and merge the commits on the release branch into it;
    * Checkout the master branch and merge all but the last commit on the release branch into it;
    * Perform a `clean deploy` run using the release profile. This performs verification, artifact creation and upload of the artifacts to Maven Central;
    * Checkout the develop branch;
    * Remove the release branch;
    * Push all branches and tags;
3. The release artifacts are now in the OSS staging repo. The actions to perform the release are as follows:
    * Go to: https://oss.sonatype.org and log in.
    * Click Staging Repositories, scroll to bottom and look for orgtoxosXXX which should have been created
    * Select the release and click close. Provide comment of the form: process-assertions-<version>
    * Refresh the release page after checks have been performed. Now select the release and click release. Provide the same comment as before. 

Synchronization to Maven Central typically occurs within 2-4 hours. Note that listing on http://search.maven.org may take longer, as the indexing for the search site seems to be done asynchronously.