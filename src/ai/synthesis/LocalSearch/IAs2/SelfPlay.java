package ai.synthesis.LocalSearch.IAs2;

import ai.synthesis.LLM.ASTCreator;
import ai.synthesis.LLM.GPT35Request;
import ai.synthesis.LocalSearch.LS_CFG.Node_LS;
import rts.GameState;
import ai.synthesis.Synthesis_Base.CFG.Control;

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


    public void runWithLLM(GameState gs, int max) throws Exception {
        List<String> strategyList = new ArrayList<String>();
        while (true) {
            long paraou = System.currentTimeMillis() - this.tempo_ini;
            boolean foundInLLM = false;

            Node_LS j = ava.getIndividuo();
            Node_LS searchStart = j;
            double rLLM = -99999.0;

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
                        counterStrategy = GPT35Request.getBestResponseStrategy(j.translateIndentation(1), lastFiveStrategies);
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

                double r0 = ava.evaluation(gs, max, c0);
                double r1 = ava.evaluation(gs, max, j);

                if (r0 > r1) {
                    ava.update(gs, max, c0);
                    foundInLLM = true;
//          System.out.println("LLM counter strategy: " + Control.save(c0));
                    break;
                }

                if (r0 > rLLM) {
                    rLLM = r0;
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


	


