package org.rulevisualization;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {
    public static final String BASE_URI = "http://localhost:8081/";
    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("org.rulevisualization");
        rc.register(MultiPartFeature.class);
        rc.register(CorsResponseFilter.class);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }
    
    public static void addStaticFiles(HttpServer server) throws URISyntaxException {
    	File dir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
        File resources = new File(dir, "data");
        StaticHttpHandler staticHttpHandler = new StaticHttpHandler(resources.getPath());
        server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/data/");
    }

    public static void main(String[] args) throws Exception {
        final HttpServer server = startServer();
        addStaticFiles(server);
        
        System.out.println(String.format("Restful service for parsing rules input files started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdownNow();
    }
}
