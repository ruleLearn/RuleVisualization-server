package org.rulevisualization.serializers;

import java.lang.reflect.Type;

import org.rulelearn.rules.Condition;
import org.rulelearn.types.EnumerationField;
import org.rulelearn.types.EvaluationField;
import org.rulelearn.types.IntegerField;
import org.rulelearn.types.RealField;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ConditionSerializer implements JsonSerializer<Condition<? extends EvaluationField>>{

	@Override
	public JsonElement serialize(Condition<? extends EvaluationField> src, Type typeOfSrc,	JsonSerializationContext context) {
		JsonObject json = new JsonObject();
		json.addProperty("name", src.getAttributeWithContext().getAttributeName());
		json.addProperty("operator", src.getRelationSymbol());
		
		EvaluationField field = src.getLimitingEvaluation();
		String str = field.toString();
		
		try {
			if (field instanceof IntegerField) {
				json.addProperty("value", Integer.parseInt(str));
			}
			else if (field instanceof RealField) {
				json.addProperty("value", Double.parseDouble(str));
			}
			else if (field instanceof EnumerationField) {
				int index = ((EnumerationField) field).getValue();
				json.addProperty("value", index);
			}
			else {
				json.addProperty("value", str);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.addProperty("value", str);
		}
		json.addProperty("description", field.toString());
		
		return json;
	}

}
