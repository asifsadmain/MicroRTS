package ai.synthesis.LocalSearch.IAs2;

import ai.core.AI;
import ai.synthesis.LLM.ASTCreator;
import ai.synthesis.LLM.ASTCreatorForComplexDSL;
import ai.synthesis.LLM.GPT35Request;
import ai.synthesis.LocalSearch.EvaluateGameState.SimplePlayout;
import ai.synthesis.LocalSearch.LS_CFG.Node_LS;
import ai.synthesis.Synthesis_Base.AIs.Interpreter;
import rts.GameState;
import ai.synthesis.Synthesis_Base.CFG.Control;
import rts.PlayerAction;
import rts.units.UnitTypeTable;
import util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SelfPlay {

    Search2 sc;
    Evaluation ava;
    long tempo_ini;


    public SelfPlay(Search2 sc, Evaluation ava) {
        // TODO Auto-generated constructor stub
        this.ava = ava;
        this.sc = sc;

    }


    public void runWithLLM(GameState gs, int max, String mapNumber, boolean feedbackFlag, boolean explainDSL) throws Exception {
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
            String actionSeq = "";
            String failedCounterStrategy = "";

            strategyList.add(j.translateIndentation(1));

            List<String> lastThreeStrategies = new ArrayList<String>();

            if (strategyList.size() < 3) {
                lastThreeStrategies = strategyList;
            } else {
                lastThreeStrategies = strategyList.subList(strategyList.size() - 3, strategyList.size());
            }

//      System.out.println("At the beginning: " + Control.save(j));

            for (int i = 0; i < 5; i++) {
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
                        if (feedbackFlag) {
                            counterStrategy = GPT35Request.getBestResponseStrategy(j.translateIndentation(1), lastThreeStrategies, mapNumber, actionSeq, failedCounterStrategy, explainDSL);
                        } else {
                            counterStrategy = GPT35Request.getBestResponseStrategy(j.translateIndentation(1), lastThreeStrategies, mapNumber, "", failedCounterStrategy, explainDSL);
                        }
                        c0 = ASTCreator.createAST(counterStrategy);
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
                failedCounterStrategy = counterStrategy;
                AI ai1 = new Interpreter(utt, c0);
                AI ai3 = new Interpreter(utt, j);

                Pair<Double, ArrayList<PlayerAction>> scoreActionPair = playout.runV2(gs, utt,0, max, ai1, ai3, false);
                Pair<Double, ArrayList<PlayerAction>> scoreActionPair2 = playout.runV2(gs, utt,1, max, ai1, ai3, false);

                if (scoreActionPair.m_a != 1.0 && scoreActionPair2.m_a != 1.0) {
                    if (scoreActionPair.m_a <= scoreActionPair2.m_a) {
                        actionSeq = GPT35Request.mapActions(scoreActionPair.m_b);
                    } else {
                        actionSeq = GPT35Request.mapActions(scoreActionPair2.m_b);
                    }

//                    System.out.println(actionSeq);
                }

                double r0 = ava.evaluation(gs, max, c0);
                double r1 = ava.evaluation(gs, max, j);

                if (r0 > r1) {
                    ava.update(gs, max, c0);
                    foundInLLM = true;
//          System.out.println("LLM counter strategy: " + Control.save(c0));
                    break;
                }

                for (Node_LS individuo : individuos) {
                    AI ai2 = new Interpreter(utt, individuo);
//                    Pair<Double, ArrayList<PlayerAction>> scoreActionPair = playout.runV2(gs, utt,0, max, ai1, ai2, false);
//                    score += scoreActionPair.m_a;
                    score += playout.run(gs, utt,0, max, ai1, ai2, false).m_a;
                    score += playout.run(gs, utt,1, max, ai1, ai2, false).m_a;
                    score = score / 2.0;
//                    System.out.println(scoreActionPair.m_b);
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
            System.out.println(Control.salve(searchStart));
            System.out.println("Score = " + scoreLLM);
            System.out.println();

            Node_LS c0 = sc.run(gs, max, searchStart, ava);

            double r0 = ava.evaluation(gs, max, c0);
            double r1 = ava.evaluation(gs, max, j);

            if (r0 > r1) {
                ava.update(gs, max, c0);
//        System.out.println("At the end: " + Control.save(c0));
            }

        }
    }


    public void run(GameState gs, int max) throws Exception {


        while (true) {
            long paraou = System.currentTimeMillis() - this.tempo_ini;

            Node_LS j = ava.getIndividuo();


            Node_LS c0 = sc.run(gs, max, j, ava);

            double r0 = ava.evaluation(gs, max, c0);
            double r1 = ava.evaluation(gs, max, j);

            if (r0 > r1) ava.update(gs, max, c0);

        }


    }


}


	


