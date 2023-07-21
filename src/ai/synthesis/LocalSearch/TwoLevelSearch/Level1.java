package ai.synthesis.LocalSearch.TwoLevelSearch;

import ai.synthesis.LocalSearch.EvaluateGameState.FeatureFactory;
import ai.synthesis.LocalSearch.EvaluateGameState.Feature;
import ai.synthesis.LocalSearch.LS_CFG.Node_LS;
import util.Pair;

public interface Level1 {

	Pair<Feature,Node_LS> getSeed();
	void update(Node_LS j, Feature nov, double reward );
	FeatureFactory getFN();
	void imprimir();
}
