package ai.synthesis.LocalSearch.IAs2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ai.synthesis.Synthesis_Base.AIs.Interpreter;
import ai.synthesis.Synthesis_Base.CFG.Control;
import ai.synthesis.Synthesis_Base.CFG.Factory;
import ai.synthesis.LocalSearch.EvaluateGameState.Playout;
import ai.synthesis.LocalSearch.EvaluateGameState.SimplePlayout;
import ai.synthesis.LocalSearch.LS_CFG.FactoryLS;
import ai.synthesis.LocalSearch.LS_CFG.Node_LS;
import ai.core.AI;
import rts.GameState;
import rts.units.UnitTypeTable;

public class SA implements Search2 {

	int time_limit;
	double T0_initial;
	double alpha_initial;
	double beta_initial;
	double T0;
	double alpha;
	double beta;

	Factory f;
	Random r;
	
	public SA( int time,double T0, double alpha, double beta) {
		// TODO Auto-generated constructor stub
		System.out.println("Search SA");
		//this.coac = coac;
		this.time_limit=time;
		
		this.T0_initial = T0;
		this.alpha_initial= alpha;
		this.beta_initial = beta;
		f = new FactoryLS();
		r =new Random();
	}

	
	
	
	@Override
	public Node_LS run(GameState gs, int max, Node_LS best,Evaluation ava) throws Exception {
		// TODO Auto-generated method stub
		this.resert();
		double v = ava.evaluation(gs, max, best);
		long Tini = System.currentTimeMillis();
		long time = System.currentTimeMillis()-Tini;
	
		int count=0;
		Node_LS local= (Node_LS) best.Clone(f);
		double v_local= v;
		while( (time*1.0)/1000.0 <time_limit && !ava.stoppingCriterion(v)) {
			double T = this.T0/(1+count*this.alpha);
			Node_LS melhor_neighbor = null ;
			double v_neighbor = -111111;
			for(int i= 0;i<2;i++) {
				
				Node_LS aux = (Node_LS) (local.Clone(f));
				List<Node_LS> list =new ArrayList<>();
				for(int ii=0;ii<1;ii++) {
					
					aux.countNode(list);
					int custo = r.nextInt(9)+1;
					int no = r.nextInt(list.size());
			
					list.get(no).mutation(0, custo, false);
					
				}
				double v2 = ava.evaluation(gs, max, aux);
				//System.out.println(v2.m_b+" "+aux.translate());
				//this.coac.Avalia(gs, max, aux);
			
				if(v_neighbor<v2) {
					melhor_neighbor = (Node_LS) aux.Clone(f);
					v_neighbor=v2;	
				}
				
				time = System.currentTimeMillis()-Tini;
				if((time*1.0)/1000.0 >time_limit || ava.stoppingCriterion(v_neighbor))break;
		
			}

			if(accept(v_local,v_neighbor,T)) {
				
				local=(Node_LS) melhor_neighbor.Clone(f);
				v_local=v_neighbor;
				
			}
			//System.out.println(v_neighbor.m_b+"   t2\t"+melhor_neighbor.translate());
			time = System.currentTimeMillis()-Tini;
			
			
			if(v<v_neighbor) {
				System.out.println("improve "+v+"<"+v_neighbor);
				best = (Node_LS) melhor_neighbor.Clone(f);
				v_local=v_neighbor;
				v= v_neighbor;		
			
			}
			
			count++;
			
		}
		return best;
	}

	private boolean accept(double v, double v_neighbor, double t) {
		// TODO Auto-generated method stub
		double aux2 = Math.exp(this.beta*((v_neighbor - v)/t));
		
		aux2 = Math.min(1,aux2);
		if(r.nextFloat()<aux2)return true;
		
		
		return false;
	}


	@Override
	public void resert() {
		// TODO Auto-generated method stub
		this.T0 = this.T0_initial;
		this.alpha = this.alpha_initial;
		this.beta = this.beta_initial;
		
	}

}
