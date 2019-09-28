package org.rulevisualization.serializers;

import java.lang.reflect.Type;

import org.rulelearn.rules.Condition;
import org.rulelearn.rules.Rule;
import org.rulelearn.rules.RuleCharacteristics;
import org.rulelearn.rules.RuleSetWithCharacteristics;
import org.rulelearn.types.EvaluationField;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RuleSetWithCharacteristicsSerializer implements JsonSerializer<RuleSetWithCharacteristics> {

	/* (non-Javadoc)
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(RuleSetWithCharacteristics src, Type typeOfSrc, JsonSerializationContext context) {
		
		ConditionSerializer conditionSerializer = new ConditionSerializer();
		RuleCharacteristicsSerializer characteristicsSerializer = new RuleCharacteristicsSerializer();
		
		JsonArray jsonRules = new JsonArray(src.size());
		for (int i = 0; i < src.size(); i++) {
			Rule rule = src.getRule(i);
			Condition<EvaluationField>[] conditions = rule.getConditions();
			Condition<EvaluationField>[][] decisions = rule.getDecisions();
			RuleCharacteristics characteristics = src.getRuleCharacteristics(i);
			
			JsonObject jsonRule = new JsonObject();
			JsonArray jsonConditions = new JsonArray(conditions.length); 
			for (int j = 0; j < conditions.length; j++) {
				jsonConditions.add(conditionSerializer.serialize(conditions[j], null, null));
			}
			
			JsonArray jsonDecisions = new JsonArray(decisions.length); 
			for (int j = 0; j < decisions.length; j++) {
				Condition<EvaluationField>[] ORdecision = decisions[j];
				JsonArray jsonORdecision = new JsonArray(ORdecision.length);
				for (int k = 0; k < ORdecision.length; k++) {
					Condition<EvaluationField> condition = ORdecision[k];
					jsonORdecision.add(conditionSerializer.serialize(condition, null, null));
				}
				jsonDecisions.add(jsonORdecision);
			}			
			
			jsonRule.add("conditions", jsonConditions);
			jsonRule.add("decisions", jsonDecisions);
			jsonRule.add("characteristics", characteristicsSerializer.serialize(characteristics, null, null));
			
			jsonRules.add(jsonRule);
		}
		
		return jsonRules;
	}

}
