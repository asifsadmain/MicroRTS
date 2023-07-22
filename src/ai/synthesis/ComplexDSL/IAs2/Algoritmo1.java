package ai.synthesis.ComplexDSL.IAs2;

import ai.core.AI;
import ai.synthesis.ComplexDSL.EvaluateGameState.SimplePlayout;
import ai.synthesis.ComplexDSL.LS_CFG.Node_LS;
import ai.synthesis.ComplexDSL.Synthesis_Base.AIs.Interpreter;
import ai.synthesis.ComplexDSL.Synthesis_Base.CFG.Control;
import ai.synthesis.LLM.ASTCreator;
import ai.synthesis.LLM.ASTCreatorForComplexDSL;
import ai.synthesis.LLM.GPT35Request;
import rts.GameState;
import rts.units.UnitTypeTable;

import java.util.ArrayList;
import java.util.List;

public class Algoritmo1 {

	Search2 sc;
	Avaliador ava;
	long tempo_ini;


	
	
	
	public Algoritmo1(Search2 sc, Avaliador ava) {
		// TODO Auto-generated constructor stub
		this.ava=ava;
		this.sc = sc;
	
	}


	public void runWithLLM(GameState gs, int max, String mapNumber) throws Exception {
		List<String> strategyList = new ArrayList<String>();
		List<Node_LS> individuos = ava.getIndividuos();

		for (Node_LS individuo : individuos) {
			strategyList.add(individuo.translateIndentation(1));
		}

		while (true) {
			long paraou = System.currentTimeMillis() - this.tempo_ini;
			boolean foundInLLM = false;

			Node_LS j = ava.getIndividuo();
			Node_LS searchStart = j;
			double scoreLLM = -99999.0;

			strategyList.add(j.translateIndentation(1));

			List<String> lastFiveStrategies = new ArrayList<String>();

			if (strategyList.size() < 5) {
				lastFiveStrategies = strategyList;
			} else {
				lastFiveStrategies = strategyList.subList(strategyList.size() - 5, strategyList.size());
			}

//      System.out.println("At the beginning: " + Control.save(j));

			for (int i = 0; i < 7; i++) {
//        System.out.println("i = " + i);
//        System.out.println();
//        System.out.println();
//        System.out.println("------------Individuo-----------");
//        System.out.println(j.translateIndentation(1));
//        System.out.println();
//        System.out.println();
				boolean isSuccess = false;
				String counterStrategy = "";
				Node_LS c0 = null;
				while (!isSuccess) {
					try {
						counterStrategy = GPT35Request.getBestResponseStrategy(j.translateIndentation(1), lastFiveStrategies, mapNumber);
						c0 = ASTCreatorForComplexDSL.createAST(counterStrategy);
//            System.out.println();
//            System.out.println("-------- Counter Strategy ---------");
//            System.out.println(counterStrategy);
//            System.out.println();
//            System.out.println("-------- AST of CS ----------");
//            System.out.println(c0.translateIndentation(1));
//            System.out.println();
						isSuccess = true;
					} catch (Exception e) {
//            System.out.println(e.toString());
					}
				}

				SimplePlayout playout = new SimplePlayout();
				UnitTypeTable utt = new UnitTypeTable(2);
				double score = 0.0;
				AI ai1 = new Interpreter(utt, c0);

				for (Node_LS individuo : individuos) {
					AI ai2 = new Interpreter(utt, individuo);
					score += playout.run(gs, utt,0, max, ai1, ai2, false).m_a;
				}

				double r0 = ava.Avalia(gs, max, c0);
				double r1 = ava.Avalia(gs, max, j);

				if (r0 > r1) {
					ava.update(gs, max, c0);
					foundInLLM = true;
//          System.out.println("LLM counter strategy: " + Control.save(c0));
					break;
				}

				if (score > scoreLLM) {
					scoreLLM = score;
					searchStart = c0;
				}
			}
			if (foundInLLM) {
				System.out.println();
				System.out.println();
				System.out.println("------- Found in LLM --------");
				System.out.println();
				System.out.println();
				continue;
			}
			System.out.println();
			System.out.println("Starting search from this:");
			System.out.println(Control.save(searchStart));
			System.out.println("Score = " + scoreLLM);
			System.out.println();

			Node_LS c0 = sc.run(gs, max, searchStart, ava);

			double r0 = ava.Avalia(gs, max, c0);
			double r1 = ava.Avalia(gs, max, j);

			if (r0 > r1) {
				ava.update(gs, max, c0);
//        System.out.println("At the end: " + Control.save(c0));
			}

		}
	}
	
	
	public void run(GameState gs,int max) throws Exception {
		
		
		while(ava.getBudget() <= 250000) {
			long paraou = System.currentTimeMillis()-this.tempo_ini;
			
			Node_LS j = ava.getIndividuo(); 
		
			
			Node_LS  c0 = sc.run(gs, max, j, ava);

			double r0 = ava.Avalia(gs, max, c0);
			double r1 = ava.Avalia(gs, max, j);
			
			if(r0>r1)ava.update(gs, max, c0);
			
		}
		
		
	}


	
		
		
}


	


