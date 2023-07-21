package ai.synthesis.LocalSearch.TwoLevelSearch;

import java.util.Random;

import ai.synthesis.Synthesis_Base.CFG.Control;
import ai.synthesis.Synthesis_Base.CFG.Factory;
import ai.synthesis.Synthesis_Base.CFG.Node;
import ai.synthesis.LocalSearch.EvaluateGameState.FeatureFactory;
import ai.synthesis.LocalSearch.EvaluateGameState.Feature;
import ai.synthesis.LocalSearch.EvaluateGameState.Playout;
import ai.synthesis.LocalSearch.EvaluateGameState.SimplePlayout;
import ai.synthesis.LocalSearch.IAs.SketchSearch;
import ai.synthesis.LocalSearch.IAs.Search;

import ai.synthesis.LocalSearch.IAs2.Evaluation;
import ai.synthesis.LocalSearch.LS_CFG.Empty_LS;
import ai.synthesis.LocalSearch.LS_CFG.FactoryLS;
import ai.synthesis.LocalSearch.LS_CFG.Node_LS;
import ai.synthesis.LocalSearch.LS_CFG.S_LS;
import rts.GameState;
import rts.units.UnitTypeTable;
import util.Pair;

public class BehavioralCloning implements Level2 {


	

	boolean SA_activation;
	
	int tempo_limite;
	double T0_inicial;
	double alpha_inicial;
	double beta_inicial;
	double T0;
	double alpha;
	double beta;

	Factory f;
	Random r;
	
	
	
	
	
	public BehavioralCloning(int tempo,double T0, double alpha, double beta, boolean sa_act) {
		// TODO Auto-generated constructor stub
		this.SA_activation = sa_act;
		System.out.println("Busca CCB");
		//this.coac = coac;
		this.tempo_limite=tempo;
		
		this.T0_inicial = T0;
		this.alpha_inicial= alpha;
		this.beta_inicial = beta;
		f = new FactoryLS();
		r =new Random();
		
	}
	
	
	@Override
	public Node_LS run(GameState gs, int max, Node_LS j,Feature nov, Evaluation ava,Level1 l1) throws Exception {
		// TODO Auto-generated method stub
		this.resert();
		
		System.out.println("busca "+nov);
		Search se = new SketchSearch(l1,false,j,this.T0,this.alpha,this.beta,false,nov,ava,this.tempo_limite,this.SA_activation);
		Node_LS n=(Node_LS) se.run(gs, max, 0);
		return n;
	}
	
	

	
	public void resert() {
		// TODO Auto-generated method stub
		this.T0 = this.T0_inicial;
		this.alpha = this.alpha_inicial;
		this.beta = this.beta_inicial;
	}
	
	

}
