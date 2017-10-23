# Release procedure for Process Assertions

This describes the procedure for creating a new release of Process Assertions. The project has the following characteristics that require a customized approach:
* The project is listed in Maven Central and therefore follows the guidelines and requirements for projects in Maven Central. There are preconfigured options for various plugins in this setup;
* The project uses gitflow to manage its branches wrt the workflow. Gitflow doesn't seamlessly integrate with the maven-release-plugin as far as tagging is concerned. 

## Attribution
Based on suggestions from the following sources:
* https://gist.github.com/nwinkler/9213085
* https://gist.github.com/searls/1043970
* http://central.sonatype.org/pages/apache-maven.html#gpg-signed-components

## Prerequisites
To be able to perform release in batch mode (as is the case for the release script), the passphrase for signing artifacts is needed. This can be solved by configuring a profile with the passphrase in the Maven settings file. The profile must have the same id as the release profile from the parent pom. For example:

    <profile>
        <id>ossrh</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <passphrase><!--passphrase goes here--></passphrase>
        </properties>
    </profile>

## Procedure
1. Open prepare-profile-build.sh in an editor and make sure all profiles for supported Activiti and Flowable versions are listed.
2. Run pre-release.sh. 
    This will run test profiles for all versions of Activiti that are supported, perform a mvn javadoc build and perform a mvn deploy on the latest version, to make sure all is well before actually releasing.
    These checks are effectively a way of performing pre-release checks without having a CI server available to run the profiles one last time or without having to trigger a build on such a server.
3. Run release.sh. It will prompt for the release version, new development version and release commit comment prefix and confirmation of the recorded values before actually performing the release. The release consists of the following actions:
    * Create a release branch according to gitflow's conventions;
    * Perform mvn release on the release branch;
    * Merge the commits performed on the release branch into develop and master, where master doesn't receive the final commit, so it sticks at the release version and not the next development version;
    * Delete the release branch;
    * Switch back to develop branch and push all of the changes.
5. The release artifacts are now in the OSS staging repo. The following steps are taken from this resource: https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide#SonatypeOSSMavenRepositoryUsageGuide-8.a.1.ClosingaStagingRepository
6. Go to: https://oss.sonatype.org and log in.
7. Click Staging Repositories, scroll to bottom and look for orgtoxosXXX which should have been created
8. Select the release and click close. Provide comment of the form: process-assertions-<version>
9. Refresh the release page after the confirmation mail has been received. Now select the release and click release. Provide the same comment as before. 
10. Wait for confirmation email. After confirmation is received, sync to Maven Central typically occurs within 2 hours. Note that listing on http://search.maven.org may take longer, as the indexing for the search site seems to be done asynchronously.