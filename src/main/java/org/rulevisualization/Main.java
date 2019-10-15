package org.rulevisualization;

import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {
    public static HttpServer startServer(String uri) {
        final ResourceConfig rc = new ResourceConfig().packages("org.rulevisualization");
        rc.register(MultiPartFeature.class);
        rc.register(CorsResponseFilter.class);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(uri), rc);
    }

    public static void main(String[] args) throws Exception {
    	String uri = "http://localhost:" +  (args.length > 0 ? args[0] : "8081") + "/";
        final HttpServer server = startServer(uri);
        
        System.out.println(String.format("Restful service for parsing rules input files started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", uri));
        System.in.read();
        server.shutdownNow();
    }
}
