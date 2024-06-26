package ai.synthesis.ComplexDSL.LS_Condition;

import ai.synthesis.ComplexDSL.LS_CFG.Node_LS;
import ai.synthesis.ComplexDSL.Synthesis_Base.CFG.N;
import ai.synthesis.ComplexDSL.Synthesis_Base.CFG_Condition.HasUnitWithinDistanceFromOpponent;

import java.util.List;
import java.util.Random;

public class HasUnitWithinDistanceFromOpponent_LS extends HasUnitWithinDistanceFromOpponent implements Node_LS {

	public HasUnitWithinDistanceFromOpponent_LS() {
		// TODO Auto-generated constructor stub
	}

	public HasUnitWithinDistanceFromOpponent_LS(N n) {
		super(n);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void sample(int budget) {
		// TODO Auto-generated method stub
	
		N n = new N();

		Random gerador = new Random();

		List<String> l2 = n.Rules();
		int g = gerador.nextInt(l2.size());
		n.setN(l2.get(g));
		this.setN(n);
	}

	@Override
	public void countNode(List<Node_LS> list) {
		// TODO Auto-generated method stub
		list.add(this);
	}
	
	public void mutation(int node_atual, int budget,boolean desc) {
		// TODO Auto-generated method stub
		
		if(desc) {
			System.out.println("Mutacao \t "+this.getName());
			System.out.println("Anterior \t"+this.translate());
		}
		this.sample(budget);
		
		if(desc) {
			System.out.println("Atual \t"+this.translate());
		}
	}
}
