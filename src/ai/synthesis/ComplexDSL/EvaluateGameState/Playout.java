package ai.synthesis.ComplexDSL.EvaluateGameState;

import ai.core.AI;
import rts.GameState;
import rts.PlayerAction;
import rts.units.UnitTypeTable;
import util.Pair;

import java.util.ArrayList;

public interface Playout {
	//Pair<Double,CabocoDagua2> run(GameState gs,int player,int max_cycle,AI ai1,AI ai2,boolean exibe) throws Exception;

	Pair<Double, CabocoDagua2> run(GameState gs, UnitTypeTable utt, int player, int max_cycle, AI ai1, AI ai2,
			boolean exibe) throws Exception;

	Pair<Double, ArrayList<PlayerAction>> runV2(GameState gs, UnitTypeTable utt, int player, int max_cycle, AI ai1, AI ai2, boolean exibe) throws Exception; // returns action sequences
}
