package ai.synthesis.LocalSearch.IAs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ai.synthesis.Synthesis_Base.AIs.Interpreter;
import ai.synthesis.Synthesis_Base.CFG.Control;
import ai.synthesis.Synthesis_Base.CFG.Factory;
import ai.synthesis.Synthesis_Base.CFG.Node;
import ai.synthesis.LocalSearch.EvaluateGameState.BehavioralFeature;

import ai.synthesis.LocalSearch.EvaluateGameState.FeatureFactory;
import ai.synthesis.LocalSearch.EvaluateGameState.FeatureFactory1;

import ai.synthesis.LocalSearch.EvaluateGameState.Feature;
import ai.synthesis.LocalSearch.EvaluateGameState.Playout;
import ai.synthesis.LocalSearch.EvaluateGameState.SimplePlayout;

import ai.synthesis.LocalSearch.IAs2.Evaluation;
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




public class SketchSearch implements Search {

	Factory f;
	boolean use_cleanr;
	boolean SA_activation;
	boolean cego;
	
	double T0;
	double alpha;
	double beta;
	Random r =new Random();
	Node_LS best;
	Pair<Double,Double> best_v;
	long time_ini;

	int limit_cloning;
	
	Feature  oracle;
	
	Evaluation ava;

	Level1 l1;
	

	public SketchSearch(Level1 l1,boolean clear,Node_LS jj,double T0,double alpha,
									double beta,boolean cego,Feature  oraculo, 
									Evaluation ava,int limite_tempo, boolean sa_act) {
		// TODO Auto-generated constructor stub
		this.SA_activation = sa_act;
		this.l1=l1;

		this.f = new FactoryLS();
		this.use_cleanr = clear;
		this.limit_cloning = limite_tempo;
		this.T0=T0;
		this.alpha = alpha;
		this.beta= beta;
		this.best = jj;
		this.ava=ava;
		this.cego = cego;
	
		this.oracle=oraculo;
		this.time_ini = System.currentTimeMillis();

		
		UnitTypeTable utt = new UnitTypeTable();
	
		

		
		
	}
	
	
	
	public boolean if_best(Pair<Double,Double> v1 ,Pair<Double,Double>  v2) {
		if(v2.m_a>v1.m_a)return true;
	
		boolean aux = Math.abs(v2.m_a - v1.m_a) <0.1;
		if(aux && v2.m_b > v1.m_b) return true;
		return false;
	}
	
	public boolean accept(Pair<Double,Double> v1 ,Pair<Double,Double>  v2, double temperatura) {
		if(v2.m_a>v1.m_a)return true;
	
		boolean aux = Math.abs(v2.m_a - v1.m_a) <0.1;
		if(aux ) {
			//np.exp(self.beta * (next_score - current_score)/self.current_temperature)
			double aux2 = Math.exp(this.beta*(v2.m_b - v1.m_b)/temperatura);
			aux2 = Math.min(1,aux2);
			if(r.nextFloat()<aux2)return true;
		}
		return false;
	}
	
	
	public boolean if_best2(Pair<Double,Double> v1 ,Pair<Double,Double>  v2) {
		
		if( v2.m_b > v1.m_b) return true;
		return false;
	}
	
	public boolean accept2(Pair<Double,Double> v1 ,Pair<Double,Double>  v2, double temperatura) {
		
	
			//np.exp(self.beta * (next_score - current_score)/self.current_temperature)
		double aux2 = Math.exp(this.beta*(v2.m_b - v1.m_b)/temperatura);
		aux2 = Math.min(1,aux2);
		if(r.nextFloat()<aux2)return true;
		
		return false;
	}
	
				
	
	boolean stop(Pair<Double,Double> v1 ) {
		return false;
	}
	
	
	public Node SearchSketch(GameState gs, int max_cicle) throws Exception {
		// TODO Auto-generated method stub
		Node_LS atual =  new S_LS(new Empty_LS());
		Pair<Double,Double> v = new Pair<>(-1.0,-1.0);
		long Tini = System.currentTimeMillis();
		long paraou = System.currentTimeMillis()-Tini;
	
		int cont=0;
		while( (paraou*1.0)/1000.0 <(this.limit_cloning*0.1)) {
			double T = this.T0/(1+cont*this.alpha);
			Node_LS best_neighbor = null ;
			Pair<Double,Double> v_neighbor = new Pair<>(-1111.0,-1111.0);
			for(int i= 0;i<20;i++) {
				
				Node_LS aux = (Node_LS) (atual.Clone(f));
				List<Node_LS> list =new ArrayList<>();
				for(int ii=0;ii<1;ii++) {
					
					aux.countNode(list);
					int custo = r.nextInt(9)+1;
					int no = r.nextInt(list.size());
			
					list.get(no).mutation(0, custo, false);
					
				}
				Pair<Double,Double> v2 = ava.evaluation(gs,max_cicle,aux,oracle,l1);
				//System.out.println("\t"+v2.m_a+" "+v2.m_b+" "+aux.translate());
				if(if_best2(v_neighbor,v2)) {
					if(this.use_cleanr||true)aux.clear(null, f);
					best_neighbor = (Node_LS) aux.Clone(f);
					v_neighbor=v2;
				}
				
				
				paraou = System.currentTimeMillis()-Tini;
				if((paraou*1.0)/1000.0 >(this.limit_cloning)*0.1) {
					
					break;	
				}
			}
		
			
		
			if((this.accept2(v,v_neighbor,T) && this.SA_activation )|| this.if_best2(this.best_v,v_neighbor)) {
				atual=(Node_LS) best_neighbor.Clone(f);
				v = v_neighbor;
				
			}
			//System.out.println(v_neighbor.m_b+"   t\t"+best_neighbor.translate());
			paraou = System.currentTimeMillis()-Tini;
			
			
			if(this.if_best2(this.best_v,v_neighbor)) {
				this.best = (Node_LS) best_neighbor.Clone(f);
				this.best_v = v_neighbor;
				long paraou2 = System.currentTimeMillis()-this.time_ini;
				System.out.println("atual2\t"+((paraou2*1.0)/1000.0)+"\t"+best_v.m_a+"\t"+best_v.m_b+"\t"+
						Control.salve(best)+"\t");
				
				
			}
			if(ava.stoppingCriterion(best_v.m_a)&&false) {
				this.best = (Node_LS) best_neighbor.Clone(f);
				this.best_v = v_neighbor;
				long paraou2 = System.currentTimeMillis()-this.time_ini;
				System.out.println("atual2\t"+((paraou2*1.0)/1000.0)+"\t"+best_v.m_a+"\t"+best_v.m_b+"\t"+
						Control.salve(best)+"\t");
				break;
			}
			cont++;
			
			
			
		}
		
		
		return this.best;
	}

	public Node SearchBR(GameState gs, int max_cicle, Node aux2) throws Exception {
		// TODO Auto-generated method stub
		Node_LS atual =  (Node_LS) aux2.Clone(f);
		Pair<Double,Double> v = this.best_v;
		long Tini = System.currentTimeMillis();
		long paraou = System.currentTimeMillis()-Tini;
	
		int cont=0;
		while( (paraou*1.0)/1000.0 <this.limit_cloning*1.0 && !ava.stoppingCriterion(v.m_a)) {
			double T = this.T0/(1+cont*this.alpha);
			Node_LS best_neighbor = null ;
			Pair<Double,Double> v_neighbor = new Pair<>(-1.0,-1.0);
			for(int i= 0;i<20;i++) {
				
				Node_LS aux = (Node_LS) (atual.Clone(f));
				List<Node_LS> list =new ArrayList<>();
				for(int ii=0;ii<1;ii++) {
					aux.countNode(list);
					int custo = r.nextInt(9)+1;
					int no = r.nextInt(list.size());
		
					list.get(no).mutation(0, custo, false);
				}
				Pair<Double,Double> v2 = ava.evaluation(gs, max_cicle,aux,oracle,l1);
				//System.out.println("\t"+v2.m_a+" "+v2.m_b+" "+aux.translate());
		
				
				if(if_best(v_neighbor,v2)) {
						if(this.use_cleanr)aux.clear(null, f);
						best_neighbor = (Node_LS) aux.Clone(f);
						v_neighbor=v2;	
				}
				paraou = System.currentTimeMillis()-Tini;
				if((paraou*1.0)/1000.0 >(this.limit_cloning)*1.0 || this.ava.stoppingCriterion(v_neighbor.m_a) )break;
				
			}
		
			

				if((accept(v,v_neighbor,T) && this.SA_activation) || this.if_best(this.best_v,v_neighbor)) {
					atual=(Node_LS) best_neighbor.Clone(f);
					v = v_neighbor;
					
				}
			//System.out.println(v_neighbor.m_b+"   t2\t"+best_neighbor.translate());
			paraou = System.currentTimeMillis()-Tini;
			
			
			if(this.if_best(this.best_v,v_neighbor)) {
				this.best = (Node_LS) best_neighbor.Clone(f);
				this.best_v = v_neighbor;
				long paraou2 = System.currentTimeMillis()-this.time_ini;
				System.out.println("atual2\t"+((paraou2*1.0)/1000.0)+"\t"+best_v.m_a+"\t"+best_v.m_b+"\t"+
							Control.salve(best)+"\t");
				
			}
			if(this.ava.stoppingCriterion(best_v.m_a)) {
				this.best = (Node_LS) best_neighbor.Clone(f);
				this.best_v = v_neighbor;
				long paraou2 = System.currentTimeMillis()-this.time_ini;
				System.out.println("atual2\t"+((paraou2*1.0)/1000.0)+"\t"+best_v.m_a+"\t"+best_v.m_b+"\t"+
						Control.salve(best)+"\t");
				break;
			}
			cont++;
			
			
			
		}
		
		
		return this.best;
	}
	
	@Override
	public Node run(GameState gs, int max_cicle,int location) throws Exception {
		// TODO Auto-generated method stub
		
		
		
		long paraou = System.currentTimeMillis()-this.time_ini;
		this.best_v = ava.evaluation(gs, max_cicle, best,oracle,l1);
		System.out.println("atual2\t"+0.0+"\t"+best_v.m_a+"\t"+best_v.m_b+"\t"+
				Control.salve(best)+"\t");
		
		System.out.println("SearchSketch");
		Node n=	this.SearchSketch(gs, max_cicle);
	
		System.out.println("SearchBR");
		//l1.update(null, this.oraculo, this.best_v.m_a);
		return this.SearchBR(gs, max_cicle,n);

		
	}



	
	
}