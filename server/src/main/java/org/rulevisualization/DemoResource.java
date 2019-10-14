package org.rulevisualization;

import java.io.File;
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
	public Response getList() {
		String path = ClassLoader.getSystemClassLoader().getResource(".").getPath() + "data";
		File folder = new File(path);
		
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
		java.nio.file.Path root = null, path = null;
		File f = null;
		try {
			String strPath = ClassLoader.getSystemClassLoader().getResource(".").getPath() + "data";
			root = Paths.get(new File(strPath).getCanonicalPath());
			path = Paths.get(new File(strPath + "/" + demoname + "/" + filename).getCanonicalPath());
			if (!path.startsWith(root))
				throw new Exception();
			f = new File(path.toString());
				
		} catch (Exception e) {
			throw new HttpException(404, "Demo dataset does not exist.");
		}
        return Response.ok(f).build();
		
	}

}
