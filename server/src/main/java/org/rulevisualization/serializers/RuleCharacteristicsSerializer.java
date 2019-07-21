package org.rulevisualization.serializers;

import java.lang.reflect.Type;

import org.rulelearn.rules.RuleCharacteristics;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RuleCharacteristicsSerializer implements JsonSerializer<RuleCharacteristics> {
	

	@Override
	public JsonElement serialize(RuleCharacteristics src, Type typeOfSrc, JsonSerializationContext context) {
		
		JsonObject json = new JsonObject();
		
		if (src.isAConfirmationSet())
			json.addProperty("AConfirmation", src.getAConfirmation());
		else
			json.addProperty("AConfirmation", "UNKNOWN");

		if (src.isC1ConfirmationSet())
			json.addProperty("C1Confirmation", src.getC1Confirmation());
		else
			json.addProperty("C1Confirmation", "UNKNOWN");

		if (src.isConfidenceSet())
			json.addProperty("Confidence", src.getConfidence());
		else
			json.addProperty("Confidence", "UNKNOWN");

		if (src.isCoverageSet())
			json.addProperty("Coverage", src.getCoverage());
		else
			json.addProperty("Coverage", "UNKNOWN");

		if (src.isCoverageFactorSet())
			json.addProperty("CoverageFactor", src.getCoverageFactor());
		else
			json.addProperty("CoverageFactor", "UNKNOWN");

		if (src.isEpsilonSet())
			json.addProperty("Epsilon", src.getEpsilon());
		else
			json.addProperty("Epsilon", "UNKNOWN");

		if (src.isEpsilonPrimeSet())
			json.addProperty("EpsilonPrime", src.getEpsilonPrime());
		else
			json.addProperty("EpsilonPrime", "UNKNOWN");

		if (src.isFConfirmationSet())
			json.addProperty("FConfirmation", src.getFConfirmation());
		else
			json.addProperty("FConfirmation", "UNKNOWN");

		if (src.isLConfirmationSet())
			json.addProperty("LConfirmation", src.getLConfirmation());
		else
			json.addProperty("LConfirmation", "UNKNOWN");

		if (src.isNegativeCoverageSet())
			json.addProperty("NegativeCoverage", src.getNegativeCoverage());
		else
			json.addProperty("NegativeCoverage", "UNKNOWN");

		if (src.isSConfirmationSet())
			json.addProperty("SConfirmation", src.getSConfirmation());
		else
			json.addProperty("SConfirmation", "UNKNOWN");

		if (src.isStrengthSet())
			json.addProperty("Strength", src.getStrength());
		else
			json.addProperty("Strength", "UNKNOWN");

		if (src.isSupportSet())
			json.addProperty("Support", src.getSupport());
		else
			json.addProperty("Support", "UNKNOWN");

		if (src.isZConfirmationSet())
			json.addProperty("ZConfirmation", src.getZConfirmation());
		else
			json.addProperty("ZConfirmation", "UNKNOWN");
		
		return json;
	}

}
