package ai.synthesis.LocalSearch.TwoLevelSearch;

import ai.synthesis.LocalSearch.EvaluateGameState.Feature;
import ai.synthesis.LocalSearch.IAs2.Evaluation;
import ai.synthesis.LocalSearch.LS_CFG.Node_LS;
import rts.GameState;

public interface Level2 {

	Node_LS run(GameState gs, int max, Node_LS j,Feature nov, Evaluation ava,Level1 l1) throws Exception;

}
