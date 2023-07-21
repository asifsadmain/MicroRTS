package ai.synthesis.LocalSearch.EvaluateGameState;

public interface FeatureFactory {
	Feature create(BehavioralFeature cd);
	Feature create();

	Feature create(String novS);
}
