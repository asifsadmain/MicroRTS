package ai.synthesis.LocalSearch.TwoLevelSearch;

import ai.synthesis.LocalSearch.EvaluateGameState.Feature;
import ai.synthesis.LocalSearch.IAs2.Evaluation;
import ai.synthesis.LocalSearch.LS_CFG.Node_LS;
import ai.core.AI;
import rts.GameState;
import rts.units.UnitTypeTable;
import util.Pair;

public class SketchSearch {
	Evaluation ava;
	SketchBehavioral l1;
	Level2 l2;
	
	public SketchSearch(SketchBehavioral l1,Level2 l2,Evaluation ava) {
		// TODO Auto-generated constructor stub
		this.ava=ava;
		this.l1 = l1;
		this.l2 = l2;
	}

	
	public void run(GameState gs,int max) throws Exception {
		
		while(true) {
			
			UnitTypeTable utt = new UnitTypeTable(2);
			System.out.println("xxxxxxxxxxxxxxx");
			Node_LS j = ava.getIndividuo();
			Feature seed = this.l1.getSeed(gs,utt,max,j);
			System.out.println("Selecionado: "+seed);
			System.out.println("Script inicial "+j.translate());
			
			Node_LS  c0 = this.l2.run(gs, max, j,seed, this.ava,l1);
			
			
			
			ava.update(gs, max, c0);
			
		}
		
		
	}
}
