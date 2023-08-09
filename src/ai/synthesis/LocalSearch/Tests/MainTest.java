package ai.synthesis.LocalSearch.Tests;

import ai.synthesis.ComplexDSL.CS.CS_Default;
import ai.synthesis.ComplexDSL.DO.DO;
import ai.synthesis.ComplexDSL.IAs2.Algoritmo1;
import ai.synthesis.LLM.ASTCreator;
import ai.synthesis.LLM.ASTCreatorForComplexDSL;
import ai.synthesis.LLM.GPT35Request;
import ai.synthesis.LocalSearch.IAs2.*;
import ai.synthesis.LocalSearch.IAs2.Evaluation;
import ai.synthesis.LocalSearch.LS_CFG.Node_LS;
import ai.synthesis.LocalSearch.TwoLevelSearch.*;
import rts.GameState;
import rts.PhysicalGameState;
import rts.units.UnitTypeTable;

import java.util.ArrayList;
import java.util.List;

public class MainTest {
	static int max=6000;
	static  int time=2000;
	static int change_NS =24;
	public MainTest() {
		// TODO Auto-generated constructor stub
	}

	public static String getMap(String s) {
		if(s.equals("0")) return "./maps/16x16/TwoBasesBarracks16x16.xml";
		if(s.equals("1")) return "maps/24x24/basesWorkers24x24A.xml";
		if(s.equals("2")) return "maps/32x32/basesWorkers32x32A.xml";
	    if(s.equals("3")) {change_NS=24;time=5000; max =15000;return "maps/BroodWar/(4)BloodBath.scmB.xml";}
		if(s.equals("4")) {return "maps/NoWhereToRun9x8.xml";}
	   return null;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		UnitTypeTable utt = new UnitTypeTable(2);
		String path_map = getMap(args[0]);
		PhysicalGameState pgs = PhysicalGameState.load(path_map, utt);
		GameState gs2 = new GameState(pgs, utt);

		System.out.println("Map: " + path_map);
		
	
		TwoLevelsearch se =null;
		
		
		if(args[1].equals("0")) {
			System.out.println("Algorithm: Naive IBR");
			SelfPlay se2 = new SelfPlay(new HC(2000), new EvaluatorSP(1,null));
			se2.run(gs2, max);
		}
				
		if(args[1].equals("1")) {
			System.out.println("Algorithm: Naive FP");
			SelfPlay se2 = new SelfPlay(new HC(2000), new EvaluatorSP(1000,null));
			se2.run(gs2, max);
		}

		if(args[1].equals("2")) {
			System.out.println("Algorithm: LL");
			Algoritmo1 se2 = new Algoritmo1(new ai.synthesis.ComplexDSL.IAs2.HC(2000), new CS_Default());
//			SelfPlay se2 = new SelfPlay(new HC(2000), new CS_Default());
			se2.run(gs2, max);
		}

		if(args[1].equals("3")) {
			System.out.println("Algorithm: IBR with LLM");

			List<Node_LS> js = new ArrayList<Node_LS>();

			boolean isSuccess = false;
			while (!isSuccess) {
				try {
					String strategy = GPT35Request.getStartingStrategy(args[0]);
					Node_LS j1 = ASTCreator.createAST(strategy);
					js.add(j1);
					isSuccess = true;
				} catch (Exception e) {
//        System.out.println(e.toString());
				}
			}

			SelfPlay se2 = new SelfPlay(new HC(2000), new EvaluatorSP(1,null, js));
			se2.runWithLLM(gs2, max, args[0]);
		}

		if(args[1].equals("4")) {
			System.out.println("Algorithm: FP with LLM");

			List<Node_LS> js = new ArrayList<Node_LS>();

			boolean isSuccess = false;
			while (!isSuccess) {
				try {
					String strategy = GPT35Request.getStartingStrategy(args[0]);
					Node_LS j1 = ASTCreator.createAST(strategy);
					js.add(j1);
					isSuccess = true;
				} catch (Exception e) {
//        System.out.println(e.toString());
				}
			}

			SelfPlay se2 = new SelfPlay(new HC(2000), new EvaluatorSP(1000,null, js));
			se2.runWithLLM(gs2, max, args[0]);
		}

		if(args[1].equals("5")) {
			System.out.println("Algorithm: LL with LLM");

			List<ai.synthesis.ComplexDSL.LS_CFG.Node_LS> js = new ArrayList<ai.synthesis.ComplexDSL.LS_CFG.Node_LS>();

			boolean isSuccess = false;
			while (!isSuccess) {
				try {
					String strategy = GPT35Request.getStartingStrategy(args[0]);
					ai.synthesis.ComplexDSL.LS_CFG.Node_LS j1 = ASTCreatorForComplexDSL.createAST(strategy);
					js.add(j1);
					isSuccess = true;
				} catch (Exception e) {
//        System.out.println(e.toString());
				}
			}

			Algoritmo1 se2 = new Algoritmo1(new ai.synthesis.ComplexDSL.IAs2.HC(2000), new CS_Default(js));
//			SelfPlay se2 = new SelfPlay(new HC(2000), new CS_Default());
			se2.runWithLLM(gs2, max, args[0]);
		}

		if(args[1].equals("6")) {
			System.out.println("Algorithm: DO");
			Algoritmo1 se2 = new Algoritmo1(new ai.synthesis.ComplexDSL.IAs2.HC(2000), new DO());
//			SelfPlay se2 = new SelfPlay(new HC(2000), new CS_Default());
			se2.run(gs2, max);
		}
	}

}
