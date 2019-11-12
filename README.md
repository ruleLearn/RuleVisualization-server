# ruleVisualization
Server of visualization of decision rules

Packaging server to one fat jar file:
1.  Create fat jar file by running:
    mvn clean install
2.  Ensure that /data folder and server.jar are on the same path.
3a. Run server by command:
    java -jar ./server.jar
or
3b. specify port number (default port is 8081):
	java -jar ./server.jar 8083
