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
		String undefined = "undefined";
		
		if (src.isAConfirmationSet())
			json.addProperty("AConfirmation", src.getAConfirmation());
		else
			json.addProperty("AConfirmation", undefined);

		if (src.isC1ConfirmationSet())
			json.addProperty("C1Confirmation", src.getC1Confirmation());
		else
			json.addProperty("C1Confirmation", undefined);

		if (src.isConfidenceSet())
			json.addProperty("Confidence", src.getConfidence());
		else
			json.addProperty("Confidence", undefined);

		if (src.isCoverageSet())
			json.addProperty("Coverage", src.getCoverage());
		else
			json.addProperty("Coverage", undefined);

		if (src.isCoverageFactorSet())
			json.addProperty("CoverageFactor", src.getCoverageFactor());
		else
			json.addProperty("CoverageFactor", undefined);

		if (src.isEpsilonSet())
			json.addProperty("Epsilon", src.getEpsilon());
		else
			json.addProperty("Epsilon", undefined);

		if (src.isEpsilonPrimeSet())
			json.addProperty("EpsilonPrime", src.getEpsilonPrime());
		else
			json.addProperty("EpsilonPrime", undefined);

		if (src.isFConfirmationSet())
			json.addProperty("FConfirmation", src.getFConfirmation());
		else
			json.addProperty("FConfirmation", undefined);

		if (src.isLConfirmationSet()) {
			if (src.getLConfirmation() == Double.POSITIVE_INFINITY) {
				//Infinity is not a correct value in JSON, so a trick is applied to assign Double.MAX_VALUE instead.
				//In practice, Double.MAX_VALUE will never be obtained. We have:
				//a = getQuantityOfPositiveCoveredExamples();
				//b = getQuantityOfPositiveNotCoveredExamples();
				//c = getQuantityOfNegativeCoveredExamples();
				//d = getQuantityOfNegativeNotCoveredExamples();
				//lConfirmationMeasureValue = Math.log( (a / (a+b)) * ((c+d) / c) );
				//So, the value under the logarithm is maximal if b=0 and c=1. Then, we have Math.log(1+d).
				//To obtain value of natural logarithm equal to Double.MAX_VALUE == 1.7976931348623157E308,
				//1+d would have to be enormous - equal to e^Double.MAX_VALUE. We will never analyze this amount of data!
				json.addProperty("LConfirmation", Double.MAX_VALUE);
			} else {
				json.addProperty("LConfirmation", src.getLConfirmation());
			}
		}
		else
			json.addProperty("LConfirmation", undefined);

		if (src.isNegativeCoverageSet())
			json.addProperty("NegativeCoverage", src.getNegativeCoverage());
		else
			json.addProperty("NegativeCoverage", undefined);

		if (src.isSConfirmationSet())
			json.addProperty("SConfirmation", src.getSConfirmation());
		else
			json.addProperty("SConfirmation", undefined);

		if (src.isStrengthSet())
			json.addProperty("Strength", src.getStrength());
		else
			json.addProperty("Strength", undefined);

		if (src.isSupportSet())
			json.addProperty("Support", src.getSupport());
		else
			json.addProperty("Support", undefined);

		if (src.isZConfirmationSet())
			json.addProperty("ZConfirmation", src.getZConfirmation());
		else
			json.addProperty("ZConfirmation", undefined);
		
		return json;
	}

}
