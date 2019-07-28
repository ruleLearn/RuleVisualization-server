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
		String unknown = "UNKNOWN";
		
		if (src.isAConfirmationSet())
			json.addProperty("AConfirmation", src.getAConfirmation());
		else
			json.addProperty("AConfirmation", unknown);

		if (src.isC1ConfirmationSet())
			json.addProperty("C1Confirmation", src.getC1Confirmation());
		else
			json.addProperty("C1Confirmation", unknown);

		if (src.isConfidenceSet())
			json.addProperty("Confidence", src.getConfidence());
		else
			json.addProperty("Confidence", unknown);

		if (src.isCoverageSet())
			json.addProperty("Coverage", src.getCoverage());
		else
			json.addProperty("Coverage", unknown);

		if (src.isCoverageFactorSet())
			json.addProperty("CoverageFactor", src.getCoverageFactor());
		else
			json.addProperty("CoverageFactor", unknown);

		if (src.isEpsilonSet())
			json.addProperty("Epsilon", src.getEpsilon());
		else
			json.addProperty("Epsilon", unknown);

		if (src.isEpsilonPrimeSet())
			json.addProperty("EpsilonPrime", src.getEpsilonPrime());
		else
			json.addProperty("EpsilonPrime", unknown);

		if (src.isFConfirmationSet())
			json.addProperty("FConfirmation", src.getFConfirmation());
		else
			json.addProperty("FConfirmation", unknown);

		if (src.isLConfirmationSet())
			json.addProperty("LConfirmation", src.getLConfirmation());
		else
			json.addProperty("LConfirmation", unknown);

		if (src.isNegativeCoverageSet())
			json.addProperty("NegativeCoverage", src.getNegativeCoverage());
		else
			json.addProperty("NegativeCoverage", unknown);

		if (src.isSConfirmationSet())
			json.addProperty("SConfirmation", src.getSConfirmation());
		else
			json.addProperty("SConfirmation", unknown);

		if (src.isStrengthSet())
			json.addProperty("Strength", src.getStrength());
		else
			json.addProperty("Strength", unknown);

		if (src.isSupportSet())
			json.addProperty("Support", src.getSupport());
		else
			json.addProperty("Support", unknown);

		if (src.isZConfirmationSet())
			json.addProperty("ZConfirmation", src.getZConfirmation());
		else
			json.addProperty("ZConfirmation", unknown);
		
		return json;
	}

}
