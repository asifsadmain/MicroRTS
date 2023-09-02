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
import rts.PlayerAction;
import rts.units.UnitTypeTable;
import util.Pair;

import java.util.ArrayList;
import java.util.List;

public class GPTCompare {

    public GPTCompare() {

    }
    public static List<String> strategyList = new ArrayList<String>();
    public static FactoryLS f = new FactoryLS();


    public static void main(String[] args) throws Exception {
        double SCORE1_SUM = 0.0;
        int ONE_SIDE_WIN_COUNT = 0;
        int IBR_FOUND_COUNT = 0;
        int LL_FOUND_COUNT = 0;

        boolean feedBackFlag = false;
        if (args[0].equals("1"))
            feedBackFlag = true;

        // 9x8LL_8
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
        // 9x8LL_7
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
        // 9x8LL_5
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

        // 16x16LL_205
//        strategyList.add("S;S_S;S;For_S;S;S_S;S;C;Train;Worker;Up;10;S;S_S;S;C;Idle;S;C;Harvest;8;S;For_S;S;C;Attack;Strongest");
//        strategyList.add("S;S_S;S;For_S;S;If_B_then_S_else_S;B;CanAttack;S;C;Harvest;1;S;C;Train;Worker;Down;4;S;For_S;S;C;Attack;Strongest");
//        strategyList.add("S;For_S;S;For_S;S;S_S;S;S_S;S;C;Harvest;1;S;C;Attack;LessHealthy;S;S_S;S;C;Train;Worker;Right;7;S;C;MoveToUnit;Ally;Strongest");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;If_B_then_S;B;OpponentHasUnitThatKillsUnitInOneAttack;S;C;Harvest;2;S;For_S;S;C;Idle;S;S_S;S;C;Train;Worker;Right;7;S;S_S;S;C;Attack;LessHealthy;S;C;Train;Worker;Right;9");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Idle;S;C;Harvest;2;S;C;Attack;Weakest;S;S_S;S;C;Train;Worker;EnemyDir;7;S;C;Train;Worker;Right;9");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;C;Harvest;2;S;S_S;S;C;MoveToUnit;Enemy;Closest;S;For_S;S;If_B_then_S;B;is_Type;Worker;S;C;Idle;S;S_S;S;C;Train;Worker;EnemyDir;6;S;C;MoveToUnit;Enemy;MostHealthy");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;S_S;S;S_S;S;C;Harvest;2;S;C;Build;Barracks;Right;1;S;S_S;S;C;Idle;S;For_S;S;If_B_then_S;B;is_Type;Worker;S;C;Idle;S;S_S;S;C;Attack;LessHealthy;S;S_S;S;C;Train;Worker;EnemyDir;6;S;C;MoveToUnit;Enemy;Closest;S;C;Train;Ranged;EnemyDir;20");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;S_S;S;C;Harvest;2;S;C;Build;Barracks;Right;1;S;S_S;S;C;Idle;S;For_S;S;S_S;S;C;Train;Ranged;Right;7;S;C;Harvest;1;S;S_S;S;C;Attack;Strongest;S;S_S;S;C;Train;Worker;EnemyDir;6;S;C;MoveToUnit;Enemy;Closest");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;C;Idle;S;S_S;S;S_S;S;C;Harvest;2;S;C;Build;Barracks;Right;1;S;S_S;S;For_S;S;S_S;S;C;Train;Ranged;Right;7;S;C;Harvest;1;S;C;MoveToUnit;Ally;LessHealthy;S;S_S;S;C;Attack;Strongest;S;S_S;S;C;Train;Worker;EnemyDir;6;S;C;MoveToUnit;Enemy;Closest");
//        strategyList.add("S;For_S;S;S_S;S;S_S;S;C;Idle;S;S_S;S;S_S;S;S_S;S;C;Harvest;2;S;C;Build;Barracks;Right;1;S;S_S;S;C;Harvest;4;S;C;MoveToUnit;Enemy;Strongest;S;S_S;S;For_S;S;S_S;S;C;Train;Ranged;Left;7;S;C;Idle;S;C;MoveToUnit;Ally;LessHealthy;S;S_S;S;C;Attack;Strongest;S;S_S;S;C;Train;Worker;EnemyDir;6;S;C;MoveToUnit;Enemy;Closest");
        //16x16LL_282
//        strategyList.add("S;S_S;S;For_S;S;If_B_then_S;B;IsBuilder;S;S_S;S;For_S;S;C;Idle;S;C;Build;Base;Down;6;S;S_S;S;For_S;S;S_S;S;C;Harvest;2;S;C;Attack;Weakest;S;For_S;S;C;Train;Worker;Down;10");
//        strategyList.add("S;S_S;S;For_S;S;If_B_then_S;B;IsBuilder;S;For_S;S;C;Idle;S;S_S;S;For_S;S;S_S;S;S_S;S;C;Harvest;2;S;C;Attack;Weakest;S;For_S;S;S_S;S;C;Build;Barracks;Up;3;S;C;Train;Heavy;Right;50;S;For_S;S;C;Train;Worker;Down;10");
//        strategyList.add("S;S_S;S;For_S;S;S_S;S;C;Idle;S;C;Train;Ranged;Left;100;S;S_S;S;For_S;S;S_S;S;S_S;S;C;Harvest;2;S;C;Attack;MostHealthy;S;For_S;S;C;Build;Barracks;Up;3;S;For_S;S;C;Train;Worker;Left;3");
//        strategyList.add("S;S_S;S;For_S;S;S_S;S;C;Train;Light;Right;10;S;C;Idle;S;For_S;S;S_S;S;C;Attack;Weakest;S;For_S;S;S_S;S;C;Build;Barracks;Left;20;S;C;Harvest;50");
//        strategyList.add("S;S_S;S;For_S;S;C;Idle;S;For_S;S;S_S;S;S_S;S;S_S;S;C;MoveToUnit;Enemy;LessHealthy;S;C;Train;Light;Right;7;S;S_S;S;C;Attack;LessHealthy;S;For_S;S;C;Train;Worker;Down;2;S;For_S;S;S_S;S;C;Build;Barracks;Up;20;S;C;Harvest;50");
//        strategyList.add("S;S_S;S;For_S;S;C;Idle;S;For_S;S;S_S;S;S_S;S;S_S;S;C;MoveToUnit;Ally;MostHealthy;S;S_S;S;C;Attack;LessHealthy;S;For_S;S;C;Train;Worker;Left;2;S;For_S;S;C;Train;Light;Left;25;S;For_S;S;S_S;S;S_S;S;S_S;S;C;Build;Barracks;Down;20;S;C;Harvest;50;S;C;Attack;Strongest;S;C;MoveToUnit;Enemy;Weakest");
//        strategyList.add("S;S_S;S;For_S;S;C;Idle;S;For_S;S;S_S;S;S_S;S;S_S;S;C;MoveToUnit;Ally;MostHealthy;S;For_S;S;C;Train;Light;EnemyDir;15;S;For_S;S;If_B_then_S_else_S;B;HasNumberOfUnits;Light;6;S;C;MoveAway;S;C;Train;Worker;Left;8;S;For_S;S;S_S;S;S_S;S;S_S;S;C;Build;Barracks;EnemyDir;20;S;C;Harvest;5;S;C;Attack;Strongest;S;C;MoveToUnit;Enemy;Weakest");
//        strategyList.add("S;S_S;S;For_S;S;C;Idle;S;For_S;S;S_S;S;S_S;S;C;Attack;Weakest;S;If_B_then_S;B;OpponentHasUnitInPlayerRange;S;For_S;S;If_B_then_S_else_S;B;CanHarvest;S;For_S;S;C;MoveToUnit;Ally;Weakest;S;C;MoveAway;S;For_S;S;S_S;S;S_S;S;S_S;S;S_S;S;C;Build;Barracks;EnemyDir;20;S;C;Harvest;5;S;C;Attack;Strongest;S;C;Train;Heavy;Right;1;S;S_S;S;C;Train;Light;Left;50;S;C;Train;Worker;Left;3");
//        strategyList.add("S;S_S;S;For_S;S;C;Idle;S;For_S;S;S_S;S;S_S;S;C;Attack;Weakest;S;If_B_then_S;B;OpponentHasUnitInPlayerRange;S;For_S;S;If_B_then_S_else_S;B;CanHarvest;S;For_S;S;C;MoveToUnit;Ally;Weakest;S;C;MoveAway;S;For_S;S;S_S;S;S_S;S;S_S;S;S_S;S;C;Build;Barracks;EnemyDir;20;S;C;Harvest;2;S;C;Attack;MostHealthy;S;C;Train;Heavy;Right;1;S;S_S;S;C;Train;Light;EnemyDir;50;S;C;Train;Worker;Left;3");
//        strategyList.add("S;S_S;S;For_S;S;C;Idle;S;For_S;S;S_S;S;For_S;S;S_S;S;S_S;S;S_S;S;S_S;S;C;Build;Barracks;EnemyDir;20;S;C;Harvest;7;S;C;MoveToUnit;Enemy;Strongest;S;C;Train;Heavy;Right;1;S;S_S;S;C;Train;Light;EnemyDir;50;S;C;Train;Worker;Left;3;S;For_S;S;C;Attack;Weakest");
        // 16x16LL_312
        strategyList.add("S;For_S;S;S_S;S;C;Attack;Strongest;S;C;Train;Worker;Left;9");
        strategyList.add("S;For_S;S;S_S;S;S_S;S;For_S;S;C;Harvest;1;S;C;Attack;Strongest;S;C;Train;Worker;Up;20");
        strategyList.add("S;For_S;S;S_S;S;S_S;S;For_S;S;If_B_then_S_else_S;B;OpponentHasUnitInPlayerRange;S;For_S;S;C;Idle;S;C;Harvest;4;S;C;Attack;Strongest;S;C;Train;Worker;Left;20");
        strategyList.add("S;For_S;S;S_S;S;S_S;S;S_S;S;For_S;S;If_B_then_S_else_S;B;OpponentHasUnitInPlayerRange;S;For_S;S;C;Attack;Closest;S;S_S;S;C;Build;Base;Right;2;S;C;Harvest;4;S;C;Attack;LessHealthy;S;C;MoveToUnit;Enemy;Weakest;S;C;Train;Worker;Left;20");
        strategyList.add("S;For_S;S;S_S;S;S_S;S;C;Harvest;1;S;S_S;S;C;Attack;LessHealthy;S;C;MoveToUnit;Ally;Strongest;S;C;Train;Worker;Left;20");
        strategyList.add("S;For_S;S;S_S;S;S_S;S;S_S;S;C;Harvest;1;S;C;Attack;Closest;S;C;Train;Worker;Down;6;S;C;Train;Worker;EnemyDir;20");
        strategyList.add("S;For_S;S;S_S;S;S_S;S;S_S;S;C;Idle;S;C;Train;Light;EnemyDir;2;S;C;Train;Worker;Up;100;S;S_S;S;C;Build;Barracks;Down;100;S;S_S;S;S_S;S;S_S;S;C;Harvest;3;S;C;Train;Light;Up;15;S;S_S;S;For_S;S;C;Idle;S;C;Attack;Closest;S;If_B_then_S_else_S;B;OpponentHasUnitInPlayerRange;S;Empty;S;C;MoveToUnit;Ally;Weakest");
        strategyList.add("S;For_S;S;S_S;S;S_S;S;S_S;S;C;Idle;S;C;Train;Light;EnemyDir;2;S;C;Train;Worker;Up;5;S;S_S;S;C;Build;Barracks;EnemyDir;100;S;S_S;S;S_S;S;C;Harvest;3;S;S_S;S;C;MoveToUnit;Enemy;Farthest;S;C;Train;Light;Up;15;S;S_S;S;For_S;S;C;Idle;S;C;Attack;Closest");
        strategyList.add("S;For_S;S;S_S;S;S_S;S;S_S;S;C;Idle;S;C;Train;Light;EnemyDir;2;S;C;Train;Worker;EnemyDir;5;S;S_S;S;C;Build;Barracks;EnemyDir;100;S;S_S;S;S_S;S;C;Harvest;3;S;S_S;S;C;MoveToUnit;Enemy;Farthest;S;C;Train;Light;Up;15;S;S_S;S;For_S;S;C;Idle;S;C;Attack;Closest");
        strategyList.add("S;For_S;S;S_S;S;S_S;S;S_S;S;C;Idle;S;C;Train;Light;EnemyDir;100;S;C;Train;Worker;Down;5;S;S_S;S;C;Build;Barracks;EnemyDir;100;S;S_S;S;S_S;S;C;Harvest;15;S;C;MoveToUnit;Enemy;Closest;S;S_S;S;For_S;S;C;Idle;S;C;Attack;MostHealthy");

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

            String actionSeq = "";
            String failedCounterStrategy = "";

            for (int k = 0; k < 5; k++) {
                System.out.println("k = " + k);
                boolean isSuccess = false;
                String counterStrategy = "";
                Node_LS c0 = null;
                while (!isSuccess) {
                    try {
                        if (feedBackFlag) {
                            counterStrategy = GPT35Request.getBestResponseStrategy(pre_ai.translateIndentation(1), lastThree, "5", actionSeq, failedCounterStrategy);
                        } else {
                            counterStrategy = GPT35Request.getBestResponseStrategy(pre_ai.translateIndentation(1), lastThree, "5", "", failedCounterStrategy);
                        }
                        c0 = ASTCreatorForComplexDSL.createAST(counterStrategy);
                        isSuccess = true;
                    } catch (Exception e) {
//            System.out.println(e.toString());
                    }
                }

                SimplePlayout playout = new SimplePlayout();
                UnitTypeTable utt = new UnitTypeTable(2);
                failedCounterStrategy = counterStrategy;
//                PhysicalGameState pgs = PhysicalGameState.load("maps/NoWhereToRun9x8.xml", utt);
                PhysicalGameState pgs = PhysicalGameState.load("maps/16x16/basesWorkers16x16A.xml", utt);
                GameState gs = new GameState(pgs, utt);
                double score1 = 0.0;
                double score2 = 0.0;

                AI ai1 = new Interpreter(utt, c0);
                AI ai3 = new Interpreter(utt, pre_ai);

                Pair<Double, ArrayList<PlayerAction>> scoreActionPair = playout.runV2(gs, utt, 0, 6000, ai1, ai3, false);
                double scoreLLM = scoreActionPair.m_a;
                Pair<Double, ArrayList<PlayerAction>> scoreActionPair2 = playout.runV2(gs, utt, 1, 6000, ai1, ai3, false);
                scoreLLM += scoreActionPair2.m_a;
                System.out.println("scoreLLM = " + scoreLLM);

                if (scoreLLM != 2) {
                    if (scoreActionPair.m_a <= scoreActionPair2.m_a) {
                        actionSeq = GPT35Request.mapActions(scoreActionPair.m_b);
                    } else {
                        actionSeq = GPT35Request.mapActions(scoreActionPair2.m_b);
                    }

//					System.out.println(actionSeq);
                }

                if (scoreLLM == 2.0) {
                    System.out.println("----------- LLM's strategy better than previous (IBR) -------------");
                    IBR_FOUND_COUNT += 1;
                } else if (scoreLLM >= 1.0) {
                    ONE_SIDE_WIN_COUNT += 1;
                }

                for (int j = 0; j < i; j++) {
                    Node_LS pre_ai2 = (Node_LS) Control.load(strategyList.get(j), f);
                    AI ai2 = new Interpreter(utt, pre_ai2);

                    score1 += playout.run(gs, utt, 0, 6000, ai1, ai2, false).m_a;
                    score1 += playout.run(gs, utt, 1, 6000, ai1, ai2, false).m_a;
                }

                for (int j = 0; j < i; j++) {
                    Node_LS pre_ai2 = (Node_LS) Control.load(strategyList.get(j), f);
                    AI ai2 = new Interpreter(utt, pre_ai2);

                    score2 += playout.run(gs, utt, 0, 6000, ai3, ai2, false).m_a;
                    score2 += playout.run(gs, utt, 1, 6000, ai3, ai2, false).m_a;
                }

                if (score1 > 0.0)
                    SCORE1_SUM += score1;
                System.out.println("score1 = " + score1);
                System.out.println("score2 = " + score2);

                if (score1 >= score2) {
                    System.out.println("--------- Found in LLM ----------");
                    LL_FOUND_COUNT += 1;
//                    break;
                }
            }
        }
        System.out.println();
        System.out.println("#########################################");
        System.out.println("Number of wins in one side = " + ONE_SIDE_WIN_COUNT);
        System.out.println("Score 1 sum = " + SCORE1_SUM);
        System.out.println("Found by LLM(IBR)= " + IBR_FOUND_COUNT);
        System.out.println("Found by LLM = " + LL_FOUND_COUNT);
        System.out.println("#########################################");
        System.out.println();
    }
}
