package ai.synthesis.LocalSearch.Tests;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import ai.synthesis.Synthesis_Base.AIs.Interpreter;
import ai.synthesis.Synthesis_Base.CFG.Control;
import ai.synthesis.LocalSearch.LS_CFG.FactoryLS;
import ai.synthesis.LocalSearch.LS_CFG.Node_LS;
//import MentalSeal.MentalSeal;
import Standard.StrategyTactics;
import ai.RandomBiasedAI;
//import ai.Rojo;
//import ai.UMSBot;
import ai.competition.mayariBot.mayari;
//import ai.UTS_Imass_2019.UTS_Imass;
import ai.abstraction.HeavyRush;
import ai.abstraction.RangedRush;
import ai.abstraction.partialobservability.POLightRush;
import ai.abstraction.partialobservability.POWorkerRush;
import ai.asymmetric.PGS.LightPGSSCriptChoice;
import ai.asymmetric.SSS.LightSSSmRTSScriptChoice;
import ai.competition.COAC.CoacAI;
//import ai.competition.GRojoA3N.GuidedRojoA3N;
import ai.competition.dropletGNS.Droplet;
import ai.configurablescript.BasicExpandedConfigurableScript;
import ai.configurablescript.ScriptsCreator;
import ai.core.AI;
import ai.evaluation.SimpleSqrtEvaluationFunction3;
import ai.mcts.naivemcts.NaiveMCTS;
import gui.PhysicalGameStatePanel;
import rts.GameState;
import rts.PhysicalGameState;
import rts.PlayerAction;
import rts.units.UnitTypeTable;
import util.Pair;

public class ExternalEvaluation {
	static int max=7000;
	static FactoryLS f =new FactoryLS();;
	
	
	public static long getLimete(int i) {
		
		if(i==0)return 600;
		if(i==1)return 1800;
		if(i==2)return 3600;
		if(i==3)return 5400;
		if(i==4)return 3600*2;
		if(i==5)return 3600*4;
		if(i==6)return 3600*6;;
		if(i==7)return 3600*10;;
		if(i==8)return 3600*14;;
		if(i==9)return 3600*18;;
		if(i==10)return 3600*24;;
		if(i==11)return 3600*30;
		if(i==12)return 3600*36;
		if(i==13)return 3600*42;
		if(i==14)return 3600*48;
		return (Long) null;
		
	}
	
	public static AI getAdv(GameState gs,String s,UnitTypeTable utt) throws Exception {
		
		if(s.equals("0")) return new RandomBiasedAI();
		if(s.equals("1")) return new NaiveMCTS(100, -1, 100,10,0.3f, 1.0f, 0.0f, 1.0f, 0.4f, 1.0f, new RandomBiasedAI(), new SimpleSqrtEvaluationFunction3(),false);				
		if(s.equals("2")) return new POWorkerRush(utt);
		if(s.equals("3"))  return new POWorkerRush(utt); //new GuidedRojoA3N(utt);
		if(s.equals("4")) return new POLightRush(utt);
		//if(s.equals("5")) return new Rojo(utt);
		//if(s.equals("6")) return new UMSBot(utt);
		if(s.equals("7")) {
			//MentalSeal m = new MentalSeal(utt);
			//m.preGameAnalysis(gs, 100);
			//return m;
			return new RangedRush(utt);
		}
		if(s.equals("8")) return new CoacAI(utt);
		//if(s.equals("9")) return new UTS_Imass(utt);
		
		if(s.equals("9")) return new mayari(utt);
		if(s.equals("10")) return new HeavyRush(utt);
		if(s.equals("11")) return new RangedRush(utt);
		if(s.equals("12")) return new Droplet(utt);
		if(s.equals("13")) return new LightPGSSCriptChoice(utt, decodeScripts(utt, "0;1;2;3;"), 200, "PGSR_LIGHT");
		if(s.equals("14")) return new LightSSSmRTSScriptChoice(utt, decodeScripts(utt, "0;1;2;3;"), 200, "SSSR_LIGHT");
		if(s.equals("15")) return new StrategyTactics(utt);
		return null;
	}
	
	
	
	
	public static Pair<Node_LS,Node_LS> ler0(String map,String ia,String teste,float limite) throws FileNotFoundException{
		
		
			String entrada = "out_"+map+"_"+ia+"_"+teste+".txt";
			Scanner in = new Scanner(new FileReader("r1/"+entrada));
			String a1="";
			String a2="";
			while (in.hasNextLine()) {
			    String line = in.nextLine();
			    String dados[] = line.split("\t");
			    if(dados[0].equals("Camp")) {
			    	float tempo = Float.parseFloat(dados[1]);
			    	
			    	if(tempo<limite) {
				    	a1=dados[3];
				    	a2=dados[3];
			    	}else {
			    		break;
			    	}
			    }
			}
			
		in.close();
		
		return new Pair<>((Node_LS)Control.load(a1,f),(Node_LS)Control.load(a2,f));
	}
	
	public static Pair<Node_LS,Node_LS> ler1(String map,String ia,String teste,float limite) throws FileNotFoundException{
		
		
		String entrada = "out_"+map+"_"+ia+"_"+teste+".txt";
		Scanner in = new Scanner(new FileReader("r11/"+entrada));
		String a1="";
		String a2="";
		while (in.hasNextLine()) {
		    String line = in.nextLine();
		    String dados[] = line.split("\t");
		    if(dados[0].equals("Camp")) {
		    	float tempo = Float.parseFloat(dados[1]);
		    	
		    	if(tempo<limite) {
			    	a1=dados[2];
			    	a2=dados[2];
		    	}else {
		    		break;
		    	}
		    }
		}
		
	in.close();
	
	return new Pair<>((Node_LS)Control.load(a1,f),(Node_LS)Control.load(a2,f));
}
	
public static String getMap(String s) {
		
		
	if(s.equals("0")) return "maps/8x8/basesWorkers8x8A.xml";
	if(s.equals("1")) return "maps/24x24/basesWorkers24x24A.xml";
	if(s.equals("2")) return "maps/32x32/basesWorkers32x32A.xml";
    if(s.equals("3")) { max =15000;;return "maps/BroodWar/(4)BloodBath.scmB.xml";}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String map = args[0];
		String teste = args[1];
		String diass = args[2];
		String id_IA = args[3];
		
		
		UnitTypeTable utt = new UnitTypeTable(2);
		String path_map = getMap(args[0]);
		PhysicalGameState pgs = PhysicalGameState.load(path_map, utt);
		GameState gs2 = new GameState(pgs, utt);
		
		
		int horas = Integer.parseInt(diass);
		
		for(int i =0;i<5;i++) {
			float limite=  getLimete(horas*5+i);
			
			List<Pair<Node_LS,Node_LS>> list = new ArrayList<>();
			
			
			System.out.println("Leitura completa3");
			
				double r = Avalia(gs2,utt,ler0(map,id_IA,teste,limite),16,limite,Integer.parseInt(id_IA));
				System.out.println("Camp\t"+id_IA+"\t"+r);
				
			
			
			
		}
		

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
	
	private static double Avalia(GameState gs2, UnitTypeTable utt,Pair<Node_LS, Node_LS> j,int n, float limite, int ini) throws Exception {
		// TODO Auto-generated method stub
		double r=0;
		AI j0 = new Interpreter(utt,j.m_a.Clone(f) );
		AI j1 = new Interpreter(utt,j.m_b.Clone(f) );
		for(int i =0;i<n;i++) {
			AI adv0 = getAdv(gs2,""+i,utt);
			
			double rL=0;
			for(int rep =0;rep<5;rep++)rL+=partida(gs2,utt,0,max,j0,adv0,false);
			for(int rep =0;rep<5;rep++)rL+=partida(gs2,utt,1,max,j1,adv0,false);
			System.out.println("VS\t"+limite+"\t"+ini+"\t"+i+"\t"+rL);
			r+=rL;
		}
		return r;
	}
	
	public static List<AI> decodeScripts(UnitTypeTable utt, String sScripts) {

		//decompõe a tupla
		ArrayList<Integer> iScriptsAi1 = new ArrayList<>();
		String[] itens = sScripts.split(";");

		for (String element : itens) {
			iScriptsAi1.add(Integer.decode(element));
		}

		List<AI> scriptsAI = new ArrayList<>();

		ScriptsCreator sc = new ScriptsCreator(utt, 300);
		ArrayList<BasicExpandedConfigurableScript> scriptsCompleteSet = sc.getScriptsMixReducedSet();

		iScriptsAi1.forEach((idSc) -> {
			scriptsAI.add(scriptsCompleteSet.get(idSc));
		});

		return scriptsAI;
	}

}
