package org.rulevisualization;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

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
import org.rulelearn.data.InformationTable;
import org.rulelearn.data.InformationTableBuilder;
import org.rulelearn.data.json.AttributeDeserializer;
import org.rulelearn.data.json.EvaluationAttributeSerializer;
import org.rulelearn.data.json.IdentificationAttributeSerializer;
import org.rulelearn.rules.RuleSetWithCharacteristics;
import org.rulelearn.rules.ruleml.RuleParser;
import org.rulevisualization.serializers.RuleSetWithCharacteristicsSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
			@FormDataParam("rules") FormDataContentDisposition rulesFileDisposition,
			@FormDataParam("examples") InputStream examplesStream,
			@FormDataParam("examples") FormDataContentDisposition examplesFileDisposition
	) throws IOException {
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Attribute.class, new AttributeDeserializer());
		gsonBuilder.registerTypeAdapter(IdentificationAttribute.class, new IdentificationAttributeSerializer());
		gsonBuilder.registerTypeAdapter(EvaluationAttribute.class, new EvaluationAttributeSerializer());
		Gson gson = gsonBuilder.create();
		
		InformationTable informationTable = null;
		Attribute [] attributes = null;
		RuleSetWithCharacteristics ruleSet = null;
		
		try (JsonReader reader = new JsonReader(new InputStreamReader(attributesStream));) {
			attributes = gson.fromJson(reader, Attribute[].class);
			ruleSet = new RuleParser(attributes).parseRulesWithCharacteristics(rulesStream).get(1);
		}
		catch (Exception ex) { 
			System.out.println(ex.toString()); 
		}
		RuleSetWithCharacteristicsSerializer serializer = new RuleSetWithCharacteristicsSerializer();
		JsonElement jsonRules = serializer.serialize(ruleSet, null, null);
		
		JsonArray examples = new JsonArray();
		if (examplesStream != null) {
			informationTable = loadInformationTable(attributes, examplesStream);
			for (int e = 0; e < informationTable.getNumberOfObjects(); e++) {
				JsonObject example = new JsonObject();
				JsonArray rules = new JsonArray();
				for (int i = 0; i < ruleSet.size(); i++) {
					if (ruleSet.getRule(i).covers(e, informationTable))
						rules.add(i);
				}
				example.addProperty("id", new Integer(e));
				example.add("rules", rules);
				examples.add(example);
			}
		}
		
		JsonObject json = new JsonObject();
		json.add("rules", jsonRules);
		json.add("examples", examples);
		return Response.ok(json.toString()).header("Access-Control-Allow-Origin", "*").build();
	}
	
	public InformationTable loadInformationTable(Attribute[] attributes, InputStream examplesStream) throws IOException, FileNotFoundException {
		List<String []> objects = null;
		InformationTableBuilder informationTableBuilder = null;
		
		try(JsonReader jsonObjectsReader = new JsonReader(new InputStreamReader(examplesStream))) {
			JsonParser jsonParser = new JsonParser();
			org.rulelearn.data.json.ObjectBuilder ob = new org.rulelearn.data.json.ObjectBuilder.Builder(attributes).build();
			objects = ob.getObjects(jsonParser.parse(jsonObjectsReader));
		}
		informationTableBuilder = new InformationTableBuilder(attributes, new String[] {org.rulelearn.data.json.ObjectBuilder.DEFAULT_MISSING_VALUE_STRING});
		if (objects != null) {
			for (int i = 0; i < objects.size(); i++) {
				informationTableBuilder.addObject(objects.get(i));
			}
			for (int i = 0; i < attributes.length; i++) {
				if (attributes[i] instanceof EvaluationAttribute) {
					((EvaluationAttribute) attributes[i]).getValueType().getCachingFactory().clearVolatileCache();
				}
			}
		}
		return informationTableBuilder.build();
	}

}
