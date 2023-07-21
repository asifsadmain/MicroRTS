package ai.synthesis.LocalSearch.Tests;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import ai.synthesis.Synthesis_Base.AIs.Interpreter;
import ai.synthesis.Synthesis_Base.CFG.Control;
import ai.synthesis.LocalSearch.LS_CFG.FactoryLS;
import ai.synthesis.LocalSearch.LS_CFG.Node_LS;
import Standard.StrategyTactics;
import ai.competition.mayariBot.mayari;
import ai.abstraction.HeavyRush;
import ai.competition.COAC.CoacAI;
import ai.core.AI;
import gui.PhysicalGameStatePanel;
import rts.GameState;
import rts.PhysicalGameState;
import rts.PlayerAction;
import rts.units.UnitTypeTable;
import util.Pair;
import java.util.Set;

public class GetCompetitorForTournament {
	static int max=7000;
	static FactoryLS f =new FactoryLS();;
	public static void ler0(String map,String ia,String teste,List<String> list_scripts,Set<String> set_scripts,boolean treinado) throws FileNotFoundException{
			
			set_scripts.add("S;Empty");
			String entrada = "r1/out_"+map+"_"+ia+"_"+teste+".txt";
			if (treinado) {
				entrada = "r1_1/out_"+map+"_"+ia+"_"+teste+".txt";
			}
			Scanner in = new Scanner(new FileReader(entrada));
			String a1="";
			String a2="";
			while (in.hasNextLine()) {
			    String line = in.nextLine();
			    String dados[] = line.split("\t");
			    if(dados[0].equals("Camp")) {
			    	float tempo = Float.parseFloat(dados[1]);
			    	
			    	if( !set_scripts.contains(dados[3])){
			    		set_scripts.add(dados[3]);
			    		list_scripts.add(dados[3]);
			    	}
				    	
			    	
			    }
			}
			
		in.close();
		return ;
	}
	
	
	

	
public static String getMap(String s) {
	
		
		
		if(s.equals("0")) return "./maps/16x16/TwoBasesBarracks16x16.xml";
		if(s.equals("1")) return "maps/24x24/basesWorkers24x24A.xml";
		if(s.equals("2")) return  "maps/32x32/basesWorkers32x32A.xml";
	    if(s.equals("3")) { max =15000;;return "maps/BroodWar/(4)BloodBath.scmB.xml";}
	   
	    
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String map = args[0];
		String ai = args[1];
		int test = Integer.parseInt(args[2]);
		
		int position = Integer.parseInt(args[3]);
		
		
		UnitTypeTable utt = new UnitTypeTable(2);
		String path_map = getMap(args[0]);
		PhysicalGameState pgs = PhysicalGameState.load(path_map, utt);
		GameState gs2 = new GameState(pgs, utt);
		
		
		
		List<String> opponents = new ArrayList<>();
		Set<String> set_scripts = new HashSet<String>();;
		 
		 for(int i=0;i<10;i++) {
			 
			 List<String> auxs = new ArrayList<>();
			ler0(map,ai,i+"",auxs,set_scripts,false);
			int minimo = Math.max(0,auxs.size()-20);
			for( int j=minimo;j<auxs.size();j++) {
				opponents.add(auxs.get(j));
			}
			auxs.clear();
			/*
			ler0(map,ia,i+"",auxs,set_scripts,true);
			 minimo = Math.max(0,auxs.size()-20);
			for( int j=minimo;j<auxs.size();j++) {
				advs.add(auxs.get(j));
			}
			auxs.clear();
;			*/
		   }
		
			
		 
		 System.out.println(opponents.size());
		 System.out.println("SS\t"+opponents.get(test));
		 Node_LS j = (Node_LS) Control.load(opponents.get(test), f);
		 testeCoacMayari( gs2,  utt,  j, position );
		
	   //String aux = "S;For_S;S;S_S;S;S_S;S;S_S;S;S_S;S;C;Build;Barracks;Left;2;S;C;Train;Heavy;Up;2;S;S_S;S;S_S;S;If_B_then_S_else_S;B;HasUnitThatKillsInOneAttack;S;S_S;S;If_B_then_S;B;OpponentHasUnitThatKillsUnitInOneAttack;S;C;Harvest;6;S;S_S;S;C;MoveToUnit;Ally;Strongest;S;C;MoveToUnit;Enemy;Weakest;S;C;Attack;Closest;S;If_B_then_S_else_S;B;IsBuilder;S;C;MoveToUnit;Enemy;Closest;S;C;Attack;LessHealthy;S;C;MoveToUnit;Ally;Farthest;S;S_S;S;C;Attack;Farthest;S;For_S;S;S_S;S;C;Idle;S;S_S;S;S_S;S;For_S;S;C;Train;Worker;Left;6;S;For_S;S;S_S;S;C;Train;Light;EnemyDir;6;S;C;Train;Heavy;Left;1;S;If_B_then_S;B;HasUnitWithinDistanceFromOpponent;5;S;S_S;S;C;MoveToUnit;Enemy;Closest;S;S_S;S;S_S;S;C;Harvest;20;S;C;MoveToUnit;Enemy;LessHealthy;S;S_S;S;C;MoveToUnit;Ally;Closest;S;If_B_then_S_else_S;B;IsBuilder;S;Empty;S;C;Attack;Farthest;S;For_S;S;If_B_then_S_else_S;B;HasNumberOfUnits;Base;20;S;C;MoveToUnit;Enemy;Farthest;S;S_S;S;C;Train;Ranged;Right;4;S;For_S;S;C;Harvest;5";
		
		 AI jai = new Interpreter(utt,j.Clone(f) );
		//AI jai0 = new StrategyTactics(utt,new AI[]{jai});
	    
		System.out.println("R\t"+Evaluation(gs2,utt,jai,opponents,position,1,false));
		//System.out.println("RSST\t"+Avalia(gs2,utt,jai0,advs,lado,1,true));	
	    

	}

private static void testeCoacMayari(GameState gs2, UnitTypeTable utt, Node_LS j, int lado) throws Exception {
		// TODO Auto-generated method stub
		AI jai = new Interpreter(utt,j.Clone(f) );
		AI coac = new CoacAI(utt);
		AI may = new mayari(utt); 
		AI jai0 = new StrategyTactics(utt,new AI[]{jai});
		double rL=0;
		for(int rep =0;rep<3;rep++)rL+=partida(gs2,utt,lado,max,jai,coac,false);
		System.out.println("Coac\t"+rL);
		rL=0;
		for(int rep =0;rep<3;rep++)rL+=partida(gs2,utt,lado,max,jai,may,false);
		System.out.println("May\t"+rL);
		rL=0;
		coac = new CoacAI(utt);
		//for(int rep =0;rep<3;rep++)rL+=partida(gs2,utt,lado,max,jai0,coac,false);
		//System.out.println("CoacSST\t"+rL);
		rL=0;
		//for(int rep =0;rep<3;rep++)rL+=partida(gs2,utt,lado,max,jai0,may,false);
		//System.out.println("MaySST\t"+rL);
}





public static double partida(GameState gs,UnitTypeTable utt, int player, int max_cycle, AI ai1, AI ai2, boolean exibe) throws Exception {
		
		
		
		ai1.reset(utt);
		ai2.reset(utt);
		GameState gs2 = gs.cloneChangingUTT(utt);
		boolean gameover = false;
		JFrame w=null;
		if(exibe) w = PhysicalGameStatePanel.newVisualizer(gs2,640,640,false,PhysicalGameStatePanel.COLORSCHEME_BLACK);
		boolean itbroke=false ;
		
	    do {
	    	PlayerAction pa1=null;
	    		try {
	    			pa1 = ai1.getAction(player, gs2);
	    		}catch(Exception e) {
	    		//	System.out.println(e);
	    			pa1 = new PlayerAction();
	    		}
	    	
	    		PlayerAction pa2=null;
	    		try {
	    			pa2 = ai2.getAction(1-player, gs2);
	    		}catch(Exception e) {
	    			pa2 = new PlayerAction();
	    		}
	            
	    	 gs2.issueSafe(pa1);
	    	 gs2.issueSafe(pa2);
	        
	            if(exibe) {
	            	w.repaint();
	            	Thread.sleep(1);
	            }
	            
	            gameover = gs2.cycle();
	           
	            
	
	    } while (!gameover && (gs2.getTime() < max_cycle));
		
	    if (gs2.winner()==player)return 1.0;
	    if (gs2.winner()==-1)return 0.5;
	    return 0;
	
}
	
private static double Evaluation(GameState gs2, UnitTypeTable utt, AI j,  List<String> advs, int lado, int n_partida,boolean stt) throws Exception {
	// TODO Auto-generated method stub
	double r=0;
	
	
	for(int i =0;i<advs.size();i++) {
		Node_LS aux = (Node_LS) Control.load(advs.get(i), f);
		AI adv = new Interpreter(utt,aux.Clone(f) );
		AI adv0 = new StrategyTactics(utt,new AI[]{adv});
		double rL=0;
		for(int rep =0;rep<n_partida;rep++) {
			if(stt)rL+=partida(gs2,utt,lado,max,j,adv0,false);
			else rL+=partida(gs2,utt,lado,max,j,adv,false);
		}
		
		System.out.println("VS\t"+"\t"+i+"\t"+rL);
		r+=rL;
	}
	return r;
}

}