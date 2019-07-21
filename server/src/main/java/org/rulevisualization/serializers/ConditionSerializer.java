package org.rulevisualization.serializers;

import java.lang.reflect.Type;

import org.rulelearn.rules.Condition;
import org.rulelearn.types.EvaluationField;

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
		json.addProperty("stringValue", src.getLimitingEvaluation().toString());
		return json;
	}

}
