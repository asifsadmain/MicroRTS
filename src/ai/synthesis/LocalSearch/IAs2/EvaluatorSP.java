package ai.synthesis.LocalSearch.IAs2;

import java.util.ArrayList;
import java.util.List;

import ai.synthesis.Synthesis_Base.AIs.Interpreter;
import ai.synthesis.Synthesis_Base.CFG.Control;
import ai.synthesis.Synthesis_Base.CFG.Factory;
import ai.synthesis.Synthesis_Base.CFG.Node;
import ai.synthesis.LocalSearch.EvaluateGameState.BehavioralFeature;
import ai.synthesis.LocalSearch.EvaluateGameState.FeatureFactory;
import ai.synthesis.LocalSearch.EvaluateGameState.FeatureFactory1;
import ai.synthesis.LocalSearch.EvaluateGameState.Feature;
import ai.synthesis.LocalSearch.EvaluateGameState.SimplePlayout;
import ai.synthesis.LocalSearch.LS_CFG.Empty_LS;
import ai.synthesis.LocalSearch.LS_CFG.FactoryLS;
import ai.synthesis.LocalSearch.LS_CFG.Node_LS;
import ai.synthesis.LocalSearch.LS_CFG.S_LS;
import ai.synthesis.LocalSearch.TwoLevelSearch.Level1;
import ai.synthesis.LocalSearch.TwoLevelSearch.Level2;
import ai.core.AI;
import rts.GameState;
import rts.units.UnitTypeTable;
import util.Pair;

public class EvaluatorSP implements Evaluation {
	int budget;
	List<Node_LS> js;
	int n;
	SimplePlayout playout;
	Factory f;
	long time_ini=System.currentTimeMillis();
	double best;
	FeatureFactory fn;

	public EvaluatorSP(int n, FeatureFactory fabicaDeNovidade) {
		// TODO Auto-generated constructor stub
		
		this.fn = fabicaDeNovidade;
		System.out.println("N = "+n);
		this.playout = new SimplePlayout();
		this.f = new FactoryLS();
		this.n = n;
		this.js = new ArrayList();
		this.best =0.5;
		this.js.add(new S_LS(new Empty_LS()));
		System.out.println("Camp\t0.0"+"\t"+0+"\t"+
				Control.salve((Node)js.get(0)) );
		this.budget=0;
		
	}

	public EvaluatorSP(int n, FeatureFactory fn, List<Node_LS> js) {
		this.budget = 0;
		this.js = js;
		this.n = n;
		this.playout = new SimplePlayout();
		this.f = new FactoryLS();
		this.best = 0.5;
		this.fn = fn;
		System.out.println("Camp\t0.0"+"\t"+0+"\t"+
				Control.salve((Node)js.get(0)) );
	}

	public EvaluatorSP(int n) {
		// TODO Auto-generated constructor stub
		this.fn = null;
		System.out.println("N = "+n);
		this.playout = new SimplePlayout();
		this.f = new FactoryLS();
		this.n = n;
		this.js = new ArrayList();
		this.best =0.5;
		this.js.add(new S_LS(new Empty_LS()));
		System.out.println("Camp\t0.0"+"\t"+0+"\t"+
				Control.salve((Node)js.get(0)) );
		this.budget=0;
	}

	@Override
	public Pair<Double,Double> evaluation(GameState gs, int max, Node_LS j, Feature oraculo,Level1 l1) throws Exception {
		// TODO Auto-generated method stub
		UnitTypeTable utt = new UnitTypeTable(2);
		AI ai = new Interpreter(utt,j);
		Pair<Double,Double> r = new Pair(0.0,0.0);
		Pair<Feature,Feature> nov=new Pair(fn.create(),fn.create());;
		for(Node_LS adv :this.js) {
			AI ai2 = new Interpreter(utt,adv);
			Pair<Double,BehavioralFeature> aux0 = playout.run(gs,utt, 0, max, ai, ai2, false);
			Pair<Double,BehavioralFeature> aux1 = playout.run(gs,utt, 1, max, ai, ai2, false);
			if(aux0.m_a+aux1.m_a>=0) {
				this.budget+=1;
			}
			long paraou = System.currentTimeMillis()-this.time_ini;
			if(this.budget%1000==0) {
				this.budget+=1;
				System.out.println("Camp\t"+((paraou*1.0)/1000.0)+"\t"+this.budget+"\t"+
						Control.salve((Node)this.getBest()) );
			}
			r.m_a+=aux0.m_a + aux1.m_a;
			
			nov.m_a = fn.create(aux0.m_b);
			nov.m_b = fn.create(aux1.m_b);
			
		
		}
		if(r.m_a>=0) {
			l1.update(j, nov.m_a, r.m_a/(2*this.js.size()));
			l1.update(j, nov.m_b, r.m_a/(2*this.js.size()));
		}
		double copia = oraculo.semelhaca(nov.m_a)+oraculo.semelhaca(nov.m_b);
		//this.coac.Avalia(gs, max, j);
		return new Pair(r.m_a/2,copia/2);
	}

	
	@Override
	public double evaluation(GameState gs, int max, Node_LS j) throws Exception {
		// TODO Auto-generated method stub
		UnitTypeTable utt = new UnitTypeTable(2);
		AI ai = new Interpreter(utt,j);
		double r=0;
	
		for(Node_LS adv :this.js) {
			AI ai2 = new Interpreter(utt,adv);
			double r0= playout.run(gs,utt, 0, max, ai, ai2, false).m_a;
			double r1= playout.run(gs,utt, 1, max, ai, ai2, false).m_a;
			if(r0+r1>=0) {
				this.budget+=1;
			}
			r+=r0+r1;
			long paraou = System.currentTimeMillis()-this.time_ini;
			if(this.budget%1000==0) {
				System.out.println("Camp\t"+((paraou*1.0)/1000.0)+"\t"+this.budget+"\t"+
						Control.salve((Node)this.getBest()) );
			}
			
			
		
		}
		
		return r/2;
	}
	
	@Override
	public void update(GameState gs, int max, Node_LS j) throws Exception {
		// TODO Auto-generated method stub
		long paraou = System.currentTimeMillis()-this.time_ini;
		
		Node_LS camp= (Node_LS) j.Clone(f);
		double r =evaluation(gs,max,camp);
		System.out.println("result "+((paraou*1.0)/1000.0)+"     "+r+ ">" + this.best+" individuals = "+js.size() );
		if(r> this.best) {
			if(js.size()>=this.n) js.remove(0);
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" );
			System.out.println("upgrade "+r+ ">" + this.best+" individuals = "+js.size() );
			
			camp.clear(null, f);
			js.add((Node_LS) camp);
			
			this.best= evaluation(gs,max,camp);
			
		}
		System.out.println("Camp\t"+((paraou*1.0)/1000.0)+"\t"+this.budget+"\t"+
				Control.salve((Node)js.get(js.size()-1)) );
	}

	@Override
	public Node_LS getIndividuo() {
		// TODO Auto-generated method stub
		return (Node_LS) js.get(js.size()-1).Clone(f);
	}

	@Override
	public boolean stoppingCriterion(double d) {
		// TODO Auto-generated method stub
		return d>this.js.size()-0.1;
	}

	@Override
	public Node_LS getBest() {
		// TODO Auto-generated method stub
		return (Node_LS) js.get(js.size()-1).Clone(f);
	}

}
