package ai.synthesis.LocalSearch.IAs;

import ai.synthesis.Synthesis_Base.CFG.Node;
import rts.GameState;

public interface Search {
	Node run(GameState gs,int max_cicle,int lado) throws Exception;
}
