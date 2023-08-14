package ai.synthesis.LocalSearch.Tests;

import ai.core.AI;
import ai.synthesis.ComplexDSL.LS_CFG.FactoryLS;
import ai.synthesis.ComplexDSL.LS_CFG.Node_LS;
import ai.synthesis.ComplexDSL.Synthesis_Base.AIs.Interpreter;
import ai.synthesis.ComplexDSL.Synthesis_Base.CFG.Control;
import ai.synthesis.LLM.ASTCreator;
import ai.synthesis.LLM.ASTCreatorForComplexDSL;
import ai.synthesis.LLM.GPT35Request;
import ai.synthesis.LocalSearch.EvaluateGameState.SimplePlayout;
import rts.GameState;
import rts.PhysicalGameState;
import rts.units.UnitTypeTable;

import java.util.ArrayList;
import java.util.List;

public class GPTCompare {

    public GPTCompare() {

    }
    public static List<String> strategyList = new ArrayList<String>();
    public static FactoryLS f = new FactoryLS();


    public static void main(String[] args) throws Exception {
        // LL_8
//        strategyList.add("S;S_S;S;S_S;S;For_S;S;C;Train;Worker;Left;4;S;If_B_then_S;B;HasNumberOfUnits;Worker;9;S;C;MoveToUnit;Ally;Strongest;S;For_S;S;S_S;S;C;Attack;LessHealthy;S;For_S;S;S_S;S;C;Harvest;15;S;C;Idle");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;For_S;S;C;Idle;S;If_B_then_S;B;OpponentHasUnitThatKillsUnitInOneAttack;S;S_S;S;C;MoveToUnit;Ally;Weakest;S;C;MoveToUnit;Ally;Farthest;S;S_S;S;For_S;S;C;Harvest;2;S;S_S;S;S_S;S;C;Train;Worker;Down;3;S;C;MoveToUnit;Ally;Farthest;S;S_S;S;C;MoveToUnit;Ally;Closest;S;For_S;S;C;Attack;Weakest");
//        strategyList.add("S;For_S;S;S_S;S;C;Train;Worker;EnemyDir;4;S;For_S;S;For_S;S;S_S;S;C;Attack;Closest;S;If_B_then_S;B;HasNumberOfUnits;Ranged;2;S;C;MoveToUnit;Enemy;Strongest");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;For_S;S;C;Idle;S;C;Harvest;5;S;S_S;S;C;Train;Worker;Left;100;S;C;Attack;Weakest");
//        strategyList.add("S;S_S;S;For_S;S;C;Idle;S;For_S;S;S_S;S;S_S;S;C;MoveToUnit;Enemy;Strongest;S;S_S;S;C;Harvest;2;S;C;Harvest;8;S;C;Train;Worker;Down;4");
//        strategyList.add("S;S_S;S;For_S;S;S_S;S;If_B_then_S_else_S;B;HasUnitWithinDistanceFromOpponent;4;S;C;Attack;Strongest;S;C;Harvest;10;S;For_S;S;C;Train;Worker;Left;7;S;For_S;S;C;MoveToUnit;Enemy;Weakest");
//        strategyList.add("S;For_S;S;S_S;S;If_B_then_S_else_S;B;HasUnitWithinDistanceFromOpponent;4;S;C;Attack;Strongest;S;C;Harvest;50;S;S_S;S;S_S;S;For_S;S;C;Idle;S;S_S;S;For_S;S;S_S;S;C;Harvest;1;S;C;Train;Worker;Down;7;S;C;MoveToUnit;Ally;Farthest;S;C;MoveToUnit;Ally;LessHealthy");
//        strategyList.add("S;For_S;S;S_S;S;If_B_then_S_else_S;B;HasUnitWithinDistanceFromOpponent;3;S;C;Attack;Strongest;S;C;Harvest;50;S;S_S;S;S_S;S;For_S;S;C;Idle;S;S_S;S;For_S;S;C;Train;Worker;Down;7;S;C;MoveToUnit;Enemy;LessHealthy;S;C;MoveToUnit;Ally;LessHealthy");
//        strategyList.add("S;For_S;S;S_S;S;For_S;S;C;Train;Worker;Up;7;S;S_S;S;S_S;S;If_B_then_S;B;CanHarvest;S;S_S;S;S_S;S;C;Harvest;20;S;S_S;S;S_S;S;C;Idle;S;C;MoveToUnit;Enemy;LessHealthy;S;S_S;S;C;MoveToUnit;Ally;Strongest;S;C;MoveToUnit;Enemy;Closest;S;C;MoveToUnit;Ally;Closest;S;S_S;S;For_S;S;S_S;S;If_B_then_S;B;CanAttack;S;C;MoveToUnit;Enemy;Strongest;S;C;Idle;S;S_S;S;C;MoveToUnit;Enemy;Closest;S;C;MoveToUnit;Enemy;LessHealthy;S;C;MoveToUnit;Enemy;Strongest");
//        strategyList.add("S;For_S;S;S_S;S;For_S;S;C;Train;Worker;Up;7;S;S_S;S;S_S;S;S_S;S;If_B_then_S;B;CanHarvest;S;S_S;S;S_S;S;C;Harvest;20;S;S_S;S;C;Idle;S;C;MoveToUnit;Enemy;LessHealthy;S;C;MoveToUnit;Ally;Closest;S;S_S;S;For_S;S;S_S;S;If_B_then_S;B;CanAttack;S;C;MoveToUnit;Enemy;Closest;S;C;Idle;S;C;MoveToUnit;Enemy;Closest;S;C;Attack;Closest;S;C;MoveToUnit;Enemy;Strongest");
        // LL_7
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;C;Train;Worker;Up;4;S;C;Harvest;1;S;S_S;S;C;MoveToUnit;Enemy;Closest;S;C;Attack;Strongest");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;C;Train;Worker;Up;6;S;C;Harvest;20;S;S_S;S;S_S;S;C;MoveToUnit;Enemy;Closest;S;C;Attack;Closest;S;For_S;S;C;Idle");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;S_S;S;C;Train;Worker;Up;6;S;C;Harvest;20;S;For_S;S;C;Idle;S;For_S;S;C;MoveToUnit;Enemy;Closest");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;S_S;S;C;Train;Worker;Left;6;S;C;Harvest;20;S;For_S;S;C;Idle;S;For_S;S;C;MoveToUnit;Enemy;Closest");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;S_S;S;C;Train;Worker;Up;6;S;C;Attack;LessHealthy;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Idle;S;C;MoveToUnit;Enemy;Strongest;S;C;MoveToUnit;Enemy;MostHealthy;S;For_S;S;C;MoveToUnit;Enemy;Farthest;S;For_S;S;S_S;S;C;MoveToUnit;Enemy;Closest;S;C;Harvest;25");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;C;Train;Worker;Up;6;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Idle;S;C;MoveToUnit;Enemy;Strongest;S;C;MoveToUnit;Enemy;MostHealthy;S;For_S;S;C;Train;Worker;Up;10;S;For_S;S;S_S;S;C;MoveToUnit;Enemy;Closest;S;C;Harvest;25");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;S_S;S;C;Train;Worker;Up;6;S;C;Attack;LessHealthy;S;S_S;S;S_S;S;For_S;S;C;Idle;S;C;MoveToUnit;Enemy;Strongest;S;C;MoveToUnit;Enemy;MostHealthy;S;For_S;S;S_S;S;C;MoveToUnit;Enemy;Closest;S;C;Harvest;25");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;C;Train;Worker;Up;6;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Idle;S;C;MoveToUnit;Enemy;Strongest;S;C;MoveToUnit;Enemy;MostHealthy;S;For_S;S;C;Train;Worker;Up;10;S;For_S;S;S_S;S;C;MoveToUnit;Enemy;Closest;S;C;Harvest;25");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;C;Train;Worker;Up;6;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Idle;S;S_S;S;C;Attack;Farthest;S;C;MoveToUnit;Enemy;Strongest;S;C;MoveToUnit;Ally;Strongest;S;For_S;S;C;Train;Worker;Up;10;S;For_S;S;S_S;S;C;MoveToUnit;Enemy;Closest;S;C;Harvest;6");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;C;Train;Worker;Up;6;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Idle;S;S_S;S;C;Attack;Farthest;S;C;MoveToUnit;Enemy;Strongest;S;C;MoveToUnit;Ally;Strongest;S;For_S;S;C;Train;Worker;Up;7;S;For_S;S;S_S;S;C;MoveToUnit;Enemy;Closest;S;C;Harvest;6");
        //LL_5
//        strategyList.add("S;For_S;S;S_S;S;For_S;S;S_S;S;C;Harvest;7;S;C;MoveToUnit;Ally;Closest;S;S_S;S;S_S;S;For_S;S;C;Idle;S;For_S;S;C;Attack;Weakest;S;S_S;S;C;Train;Worker;Right;3;S;C;MoveToUnit;Ally;Closest");
//        strategyList.add("S;For_S;S;S_S;S;For_S;S;S_S;S;C;Harvest;7;S;C;Build;Base;Down;2;S;S_S;S;S_S;S;For_S;S;C;Idle;S;For_S;S;C;Attack;Weakest;S;S_S;S;C;Train;Worker;Right;3;S;C;MoveToUnit;Ally;Closest");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;For_S;S;C;Idle;S;For_S;S;S_S;S;C;MoveToUnit;Ally;LessHealthy;S;C;Attack;Weakest;S;S_S;S;For_S;S;C;Train;Worker;Down;15;S;C;MoveToUnit;Ally;Closest");
//        strategyList.add("S;For_S;S;S_S;S;C;Attack;MostHealthy;S;S_S;S;For_S;S;S_S;S;C;Idle;S;C;Harvest;8;S;For_S;S;C;Train;Worker;Left;4");
//        strategyList.add("S;For_S;S;S_S;S;C;Harvest;6;S;For_S;S;S_S;S;S_S;S;C;Idle;S;C;Train;Worker;Right;6;S;C;MoveToUnit;Enemy;Strongest");
//        strategyList.add("S;For_S;S;For_S;S;S_S;S;C;MoveToUnit;Enemy;LessHealthy;S;S_S;S;For_S;S;S_S;S;C;Train;Worker;EnemyDir;7;S;C;Idle;S;S_S;S;C;Harvest;15;S;S_S;S;C;Attack;Weakest;S;S_S;S;C;Train;Light;EnemyDir;9;S;For_S;S;C;Build;Barracks;Right;8");
//        strategyList.add("S;For_S;S;S_S;S;C;MoveToUnit;Enemy;LessHealthy;S;S_S;S;For_S;S;S_S;S;C;Train;Worker;EnemyDir;7;S;C;Idle;S;S_S;S;S_S;S;C;Harvest;15;S;S_S;S;C;Attack;Weakest;S;For_S;S;C;Build;Barracks;Right;8;S;C;Train;Ranged;Right;20");
//        strategyList.add("S;For_S;S;S_S;S;C;MoveToUnit;Enemy;Farthest;S;S_S;S;For_S;S;S_S;S;C;Train;Worker;EnemyDir;7;S;C;Idle;S;S_S;S;S_S;S;S_S;S;C;Harvest;15;S;S_S;S;C;Attack;Weakest;S;For_S;S;C;Build;Barracks;Right;8;S;C;Train;Ranged;Right;20;S;S_S;S;C;MoveToUnit;Enemy;MostHealthy;S;C;MoveToUnit;Enemy;Weakest");
//        strategyList.add("S;For_S;S;S_S;S;C;MoveToUnit;Enemy;LessHealthy;S;S_S;S;For_S;S;S_S;S;C;Train;Worker;EnemyDir;7;S;C;Idle;S;S_S;S;S_S;S;C;Harvest;15;S;S_S;S;C;Attack;Weakest;S;For_S;S;C;Build;Barracks;Right;8;S;C;Train;Ranged;Right;20");
//        strategyList.add("S;For_S;S;S_S;S;C;MoveToUnit;Enemy;Weakest;S;S_S;S;For_S;S;S_S;S;C;Train;Worker;EnemyDir;7;S;C;Idle;S;S_S;S;S_S;S;C;Harvest;15;S;S_S;S;C;Attack;Weakest;S;For_S;S;C;Build;Barracks;Right;8;S;S_S;S;If_B_then_S;B;HasUnitInOpponentRange;S;For_S;S;If_B_then_S;B;CanAttack;S;If_B_then_S_else_S;B;HasUnitThatKillsInOneAttack;S;Empty;S;For_S;S;C;MoveAway;S;C;Train;Ranged;Right;20");

        strategyList.add("S;S_S;S;For_S;S;S_S;S;C;Train;Worker;Up;10;S;S_S;S;C;Idle;S;C;Harvest;8;S;For_S;S;C;Attack;Strongest");
        strategyList.add("S;S_S;S;For_S;S;If_B_then_S_else_S;B;CanAttack;S;C;Harvest;1;S;C;Train;Worker;Down;4;S;For_S;S;C;Attack;Strongest");
        strategyList.add("S;For_S;S;For_S;S;S_S;S;S_S;S;C;Harvest;1;S;C;Attack;LessHealthy;S;S_S;S;C;Train;Worker;Right;7;S;C;MoveToUnit;Ally;Strongest");
        strategyList.add("S;For_S;S;S_S;S;S_S;S;If_B_then_S;B;OpponentHasUnitThatKillsUnitInOneAttack;S;C;Harvest;2;S;For_S;S;C;Idle;S;S_S;S;C;Train;Worker;Right;7;S;S_S;S;C;Attack;LessHealthy;S;C;Train;Worker;Right;9");
        strategyList.add("S;For_S;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Idle;S;C;Harvest;2;S;C;Attack;Weakest;S;S_S;S;C;Train;Worker;EnemyDir;7;S;C;Train;Worker;Right;9");
        strategyList.add("S;For_S;S;S_S;S;S_S;S;C;Harvest;2;S;S_S;S;C;MoveToUnit;Enemy;Closest;S;For_S;S;If_B_then_S;B;is_Type;Worker;S;C;Idle;S;S_S;S;C;Train;Worker;EnemyDir;6;S;C;MoveToUnit;Enemy;MostHealthy");
        strategyList.add("S;For_S;S;S_S;S;S_S;S;S_S;S;S_S;S;C;Harvest;2;S;C;Build;Barracks;Right;1;S;S_S;S;C;Idle;S;For_S;S;If_B_then_S;B;is_Type;Worker;S;C;Idle;S;S_S;S;C;Attack;LessHealthy;S;S_S;S;C;Train;Worker;EnemyDir;6;S;C;MoveToUnit;Enemy;Closest;S;C;Train;Ranged;EnemyDir;20");
        strategyList.add("S;For_S;S;S_S;S;S_S;S;S_S;S;C;Harvest;2;S;C;Build;Barracks;Right;1;S;S_S;S;C;Idle;S;For_S;S;S_S;S;C;Train;Ranged;Right;7;S;C;Harvest;1;S;S_S;S;C;Attack;Strongest;S;S_S;S;C;Train;Worker;EnemyDir;6;S;C;MoveToUnit;Enemy;Closest");
        strategyList.add("S;For_S;S;S_S;S;S_S;S;C;Idle;S;S_S;S;S_S;S;C;Harvest;2;S;C;Build;Barracks;Right;1;S;S_S;S;For_S;S;S_S;S;C;Train;Ranged;Right;7;S;C;Harvest;1;S;C;MoveToUnit;Ally;LessHealthy;S;S_S;S;C;Attack;Strongest;S;S_S;S;C;Train;Worker;EnemyDir;6;S;C;MoveToUnit;Enemy;Closest");
        strategyList.add("S;For_S;S;S_S;S;S_S;S;C;Idle;S;S_S;S;S_S;S;S_S;S;C;Harvest;2;S;C;Build;Barracks;Right;1;S;S_S;S;C;Harvest;4;S;C;MoveToUnit;Enemy;Strongest;S;S_S;S;For_S;S;S_S;S;C;Train;Ranged;Left;7;S;C;Idle;S;C;MoveToUnit;Ally;LessHealthy;S;S_S;S;C;Attack;Strongest;S;S_S;S;C;Train;Worker;EnemyDir;6;S;C;MoveToUnit;Enemy;Closest");


        List<String> strats = new ArrayList<String>();

        for (int i = 1; i < strategyList.size(); i++) {
            System.out.println("i = " + i);
            Node_LS pre_ai = (Node_LS) Control.load(strategyList.get(i), f);
            boolean foundInLLM = false;
            List<String> lastThree = new ArrayList<String>();

            strats.add(pre_ai.translateIndentation(1));

            if (strats.size() < 3) {
                lastThree = strats;
            } else {
                lastThree = strats.subList(strats.size() - 3, strats.size());
            }

            for (int k = 0; k < 5; k++) {
                System.out.println("k = " + k);
                boolean isSuccess = false;
                String counterStrategy = "";
                Node_LS c0 = null;
                while (!isSuccess) {
                    try {
                        counterStrategy = GPT35Request.getBestResponseStrategy(pre_ai.translateIndentation(1), lastThree, "1");
                        c0 = ASTCreatorForComplexDSL.createAST(counterStrategy);
                        isSuccess = true;
                    } catch (Exception e) {
//            System.out.println(e.toString());
                    }
                }

                SimplePlayout playout = new SimplePlayout();
                UnitTypeTable utt = new UnitTypeTable(2);
//                PhysicalGameState pgs = PhysicalGameState.load("maps/NoWhereToRun9x8.xml", utt);
                PhysicalGameState pgs = PhysicalGameState.load("maps/16x16/basesWorkers16x16A.xml", utt);
                GameState gs = new GameState(pgs, utt);
                double score1 = 0.0;
                double score2 = 0.0;

                AI ai1 = new Interpreter(utt, c0);
                for (int j = 0; j < i; j++) {
                    Node_LS pre_ai2 = (Node_LS) Control.load(strategyList.get(j), f);
                    AI ai2 = new Interpreter(utt, pre_ai2);

                    score1 += playout.run(gs, utt, 0, 6000, ai1, ai2, false).m_a;
                    score1 += playout.run(gs, utt, 1, 6000, ai1, ai2, false).m_a;
                }

                AI ai3 = new Interpreter(utt, pre_ai);
                for (int j = 0; j < i; j++) {
                    Node_LS pre_ai2 = (Node_LS) Control.load(strategyList.get(j), f);
                    AI ai2 = new Interpreter(utt, pre_ai2);

                    score2 += playout.run(gs, utt, 0, 6000, ai3, ai2, false).m_a;
                    score2 += playout.run(gs, utt, 1, 6000, ai3, ai2, false).m_a;
                }

                System.out.println("score1 = " + score1);
                System.out.println("score2 = " + score2);

                if (score1 >= score2) {
                    System.out.println("--------- Found in LLM ----------");
                    break;
                }

                double scoreLLM = playout.run(gs, utt, 0, 6000, ai1, ai3, false).m_a;
                scoreLLM += playout.run(gs, utt, 0, 6000, ai1, ai3, false).m_a;
                System.out.println("scoreLLM = " + scoreLLM);

                if (scoreLLM == 2.0) {
                    System.out.println("----------- LLM's strategy better than previous (IBR) -------------");
                }
            }
        }
    }
}
