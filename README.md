# ruleVisualization
Server of visualization of decision rules

Packaging server to one fat jar file:
1. Ensure that ruleLearn.jar file exists in /src/lib path.
2. Add ruleLearn.jar file to local maven repository (if it is first packaging on computer):
    mvn install:install-file -Dpath=./src/lib/ruleLearn.jar -DartifactId=ruleLearn -DgroupId=ruleLearn -Dversion=<version> -Dpackaging=jar
3. Create fat jar file by running:
    mvn clean install
4. Run server by command:
    java -jar ./target/server.jar
4A. Or specify port number (default port 8081):
	java -jar ./target/server.jar 8083
