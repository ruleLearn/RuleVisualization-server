package org.rulevisualization;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonArray;

@Path("demo")
public class DemoResource {	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getList() throws URISyntaxException {
		URL url = ClassLoader.getSystemClassLoader().getResource(".");
		File folder = new File(Paths.get(url.toURI()).toFile(), "data");
		
		JsonArray list = new JsonArray();
        for (final File f : folder.listFiles())
			list.add(f.getName());
		return Response.ok(list.toString()).build();
	}
	
	@GET
	@Path("{demoname}/{filename}")
	@Produces({MediaType.MULTIPART_FORM_DATA})
	public Response getFile(
			@PathParam("demoname") String demoname,
			@PathParam("filename") String filename
	) {
		File f = null, root = null;
		try {
			URL url = ClassLoader.getSystemClassLoader().getResource(".");
			root = new File(Paths.get(url.toURI()).toFile(), "data");
			f = new File(new File(root, demoname), filename);
			if (!f.getCanonicalPath().startsWith(root.getCanonicalPath()))
				throw new Exception();
				
		} catch (Exception e) {
			throw new HttpException(404, "Demo dataset does not exist.");
		}
        return Response.ok(f).build();
		
	}

}
