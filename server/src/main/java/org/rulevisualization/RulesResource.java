package org.rulevisualization;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.rulelearn.data.Attribute;
import org.rulelearn.data.EvaluationAttribute;
import org.rulelearn.data.IdentificationAttribute;
import org.rulelearn.data.json.AttributeDeserializer;
import org.rulelearn.data.json.EvaluationAttributeSerializer;
import org.rulelearn.data.json.IdentificationAttributeSerializer;
import org.rulelearn.rules.RuleSetWithCharacteristics;
import org.rulelearn.rules.ruleml.RuleParser;
import org.rulevisualization.serializers.RuleSetWithCharacteristicsSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

@Path("upload")
public class RulesResource {
	@GET
	@Produces({MediaType.TEXT_PLAIN})
	public Response test() {
		return Response.ok("Test OK.").build();
	}
	
	@POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
	@Produces({MediaType.APPLICATION_JSON})
	public Response load(
			@FormDataParam("attributes") InputStream attributesStream,
			@FormDataParam("attributes") FormDataContentDisposition attributesFileDisposition,
			@FormDataParam("rules") InputStream rulesStream,
			@FormDataParam("rules") FormDataContentDisposition rulesFileDisposition
	) throws IOException {
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Attribute.class, new AttributeDeserializer());
		gsonBuilder.registerTypeAdapter(IdentificationAttribute.class, new IdentificationAttributeSerializer());
		gsonBuilder.registerTypeAdapter(EvaluationAttribute.class, new EvaluationAttributeSerializer());
		Gson gson = gsonBuilder.create();
		
		Map<Integer, RuleSetWithCharacteristics> rules = null;
		try (JsonReader reader = new JsonReader(new InputStreamReader(attributesStream));) {
			Attribute [] attributes = gson.fromJson(reader, Attribute[].class);
			RuleParser ruleParser = new RuleParser(attributes);
			rules = ruleParser.parseRulesWithCharacteristics(rulesStream);
		}
		catch (FileNotFoundException ex) {
			System.out.println(ex.toString());
		}
		catch (IOException ex) {
			System.out.println(ex.toString());
		}
		
		RuleSetWithCharacteristicsSerializer serializer = new RuleSetWithCharacteristicsSerializer();
		JsonElement json = serializer.serialize(rules.get(1), null, null);
		return Response.ok(json.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

}
