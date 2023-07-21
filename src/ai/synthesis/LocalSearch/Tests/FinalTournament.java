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

public class FinalTournament {
	static int max=7000;
	static FactoryLS f =new FactoryLS();;
	
	
	
	
	public static AI getAdv(GameState gs,String s,UnitTypeTable utt,int lado,String map) throws Exception {
		
		if(s.equals("0")) return new RandomBiasedAI();
		if(s.equals("1")) return new NaiveMCTS(100, -1, 100,10,0.3f, 1.0f, 0.0f, 1.0f, 0.4f, 1.0f, new RandomBiasedAI(), new SimpleSqrtEvaluationFunction3(),false);				
		if(s.equals("2")) return new POWorkerRush(utt);
		//if(s.equals("3"))  return new GuidedRojoA3N(utt);
		if(s.equals("4")) return new POLightRush(utt);
		//if(s.equals("5")) return new Rojo(utt);
		//if(s.equals("6")) return new UMSBot(utt);
		//if(s.equals("7")) {
		//	MentalSeal m = new MentalSeal(utt);
		//	m.preGameAnalysis(gs, 100);
		//	return m;
		//}
		//if(s.equals("8")) return new CoacAI(utt);
		//if(s.equals("9")) return new UTS_Imass(utt);
		
		if(s.equals("9")) return new mayari(utt);
		if(s.equals("10")) return new HeavyRush(utt);
		if(s.equals("11")) return new RangedRush(utt);
		if(s.equals("12")) return new Droplet(utt);
		if(s.equals("13")) return new LightPGSSCriptChoice(utt, decodeScripts(utt, "0;1;2;3;"), 200, "PGSR_LIGHT");
		if(s.equals("14")) return new LightSSSmRTSScriptChoice(utt, decodeScripts(utt, "0;1;2;3;"), 200, "SSSR_LIGHT");
		if(s.equals("15")) return new StrategyTactics(utt);
		if(s.equals("16")) {
			String j="";
			if(map.equals("1")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Idle;S;S_S;S;If_B_then_S_else_S;B;HasUnitWithinDistanceFromOpponent;10;S;S_S;S;S_S;S;C;Harvest;4;S;C;Harvest;5;S;S_S;S;C;Train;Ranged;EnemyDir;6;S;S_S;S;S_S;S;For_S;S;C;Harvest;4;S;C;Attack;Closest;S;S_S;S;For_S;S;C;Train;Heavy;EnemyDir;10;S;If_B_then_S_else_S;B;HasUnitInOpponentRange;S;Empty;S;C;MoveToUnit;Enemy;MostHealthy;S;S_S;S;For_S;S;C;Build;Base;Down;9;S;S_S;S;C;Build;Barracks;Right;50;S;C;Harvest;100;S;S_S;S;C;Attack;Farthest;S;For_S;S;C;Train;Worker;Right;5;S;If_B_then_S_else_S;B;HaveQtdUnitsAttacking;10;S;C;MoveToUnit;Enemy;MostHealthy;S;Empty;S;For_S;S;S_S;S;C;Harvest;2;S;S_S;S;C;Train;Heavy;Up;6;S;C;Harvest;3";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;C;Build;Barracks;Left;7;S;S_S;S;S_S;S;C;Idle;S;C;Harvest;8;S;S_S;S;C;Attack;Closest;S;C;MoveToUnit;Enemy;Strongest;S;S_S;S;C;Train;Worker;Up;3;S;For_S;S;For_S;S;C;Train;Heavy;Left;10";
			}else if(map.equals("2")) {
				if(lado==0) j = "S;S_S;S;For_S;S;If_B_then_S_else_S;B;is_Type;Barracks;S;Empty;S;C;Train;Worker;Left;2;S;S_S;S;For_S;S;S_S;S;C;Harvest;7;S;S_S;S;C;Train;Worker;Right;3;S;S_S;S;For_S;S;For_S;S;C;Idle;S;S_S;S;S_S;S;C;Train;Worker;Right;4;S;C;Attack;Weakest;S;S_S;S;For_S;S;S_S;S;C;Train;Heavy;EnemyDir;20;S;If_B_then_S;B;HaveQtdUnitsAttacking;3;S;If_B_then_S;B;CanHarvest;S;C;MoveToUnit;Ally;Closest;S;For_S;S;S_S;S;If_B_then_S;B;OpponentHasUnitInPlayerRange;S;If_B_then_S_else_S;B;HasUnitThatKillsInOneAttack;S;Empty;S;S_S;S;For_S;S;C;Attack;Farthest;S;C;MoveToUnit;Enemy;MostHealthy;S;C;Build;Barracks;EnemyDir;50;S;For_S;S;If_B_then_S_else_S;B;HasNumberOfUnits;Ranged;20;S;C;MoveToUnit;Enemy;Farthest;S;Empty";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;C;Harvest;100;S;C;Idle;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Train;Heavy;EnemyDir;15;S;C;Attack;Closest;S;If_B_then_S;B;HasUnitThatKillsInOneAttack;S;C;Train;Heavy;Down;100;S;S_S;S;C;Train;Worker;EnemyDir;4;S;S_S;S;For_S;S;C;Build;Barracks;Right;50;S;C;MoveToUnit;Enemy;Farthest";
			} else if (map.equals("3")) {
				if(lado==0) j = "S;S_S;S;For_S;S;S_S;S;S_S;S;S_S;S;S_S;S;C;Build;Barracks;EnemyDir;9;S;S_S;S;S_S;S;C;Harvest;20;S;S_S;S;C;Attack;Closest;S;S_S;S;C;MoveToUnit;Ally;Strongest;S;If_B_then_S;B;HasUnitThatKillsInOneAttack;S;C;Train;Heavy;Right;25;S;C;MoveToUnit;Ally;Farthest;S;For_S;S;C;Idle;S;For_S;S;C;Harvest;4;S;S_S;S;C;MoveToUnit;Enemy;Farthest;S;S_S;S;C;Train;Worker;Up;6;S;S_S;S;C;Train;Heavy;EnemyDir;15;S;If_B_then_S_else_S;B;CanAttack;S;Empty;S;For_S;S;If_B_then_S;B;HasUnitInOpponentRange;S;C;Attack;Weakest;S;For_S;S;C;Train;Ranged;Right;9";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;C;Harvest;6;S;S_S;S;S_S;S;C;Idle;S;S_S;S;S_S;S;C;Attack;Closest;S;S_S;S;C;MoveToUnit;Ally;LessHealthy;S;If_B_then_S;B;IsBuilder;S;For_S;S;C;Train;Heavy;Down;4;S;S_S;S;S_S;S;For_S;S;C;Harvest;1;S;C;Train;Heavy;Up;10;S;For_S;S;S_S;S;For_S;S;C;Build;Barracks;Right;2;S;If_B_then_S_else_S;B;HasUnitInOpponentRange;S;C;Idle;S;C;Harvest;2;S;S_S;S;C;MoveToUnit;Ally;Strongest;S;S_S;S;C;MoveToUnit;Enemy;LessHealthy;S;C;Train;Worker;EnemyDir;6;S;C;Train;Ranged;Right;15";
				
			}
			
				Node_LS n = (Node_LS)Control.load(j,f);
			return new Interpreter(utt,n );
		}
		if(s.equals("17")) {
			String j="";
			if(map.equals("1")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;S_S;S;S_S;S;C;Train;Worker;EnemyDir;3;S;C;Harvest;2;S;C;Build;Barracks;Left;15;S;S_S;S;For_S;S;C;Train;Heavy;Right;10;S;C;Harvest;7;S;S_S;S;C;Attack;Closest;S;If_B_then_S_else_S;B;HasUnitThatKillsInOneAttack;S;For_S;S;C;Train;Ranged;EnemyDir;7;S;Empty";
				if(lado ==1)j ="S;For_S;S;S_S;S;If_B_then_S_else_S;B;HasUnitWithinDistanceFromOpponent;0;S;C;MoveToUnit;Ally;Closest;S;C;Idle;S;S_S;S;S_S;S;C;Build;Barracks;Down;50;S;For_S;S;S_S;S;C;Train;Heavy;Up;25;S;For_S;S;S_S;S;C;Harvest;3;S;C;Train;Worker;Up;4;S;C;Attack;Closest";
			}else if(map.equals("2")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;S_S;S;S_S;S;C;Build;Barracks;EnemyDir;8;S;If_B_then_S;B;is_Type;Base;S;C;MoveToUnit;Enemy;LessHealthy;S;S_S;S;C;Idle;S;For_S;S;C;Train;Worker;EnemyDir;4;S;S_S;S;S_S;S;C;Harvest;4;S;S_S;S;C;Attack;Closest;S;If_B_then_S;B;IsBuilder;S;For_S;S;For_S;S;C;Harvest;9;S;C;MoveToUnit;Ally;Farthest;S;S_S;S;For_S;S;C;Train;Heavy;Left;9;S;C;Train;Worker;Right;5";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;S_S;S;S_S;S;S_S;S;C;Build;Barracks;Up;8;S;If_B_then_S;B;is_Type;Base;S;C;MoveToUnit;Enemy;LessHealthy;S;For_S;S;C;Train;Worker;EnemyDir;4;S;For_S;S;C;Idle;S;S_S;S;S_S;S;C;Harvest;4;S;S_S;S;C;Attack;Closest;S;If_B_then_S;B;IsBuilder;S;For_S;S;For_S;S;C;Harvest;9;S;C;MoveToUnit;Ally;Farthest;S;S_S;S;For_S;S;S_S;S;C;Build;Barracks;Left;1;S;C;Train;Heavy;Left;9;S;C;Train;Worker;Right;5";
			} else if (map.equals("3")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Train;Heavy;Up;10;S;C;Train;Worker;Right;7;S;For_S;S;C;Idle;S;For_S;S;S_S;S;If_B_then_S_else_S;B;HasUnitWithinDistanceFromOpponent;50;S;Empty;S;C;Harvest;3;S;C;Harvest;2;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Train;Light;Left;6;S;C;Build;Barracks;Down;1;S;C;Harvest;7;S;C;Attack;Closest";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;C;Idle;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Train;Heavy;Up;10;S;C;Train;Worker;Right;7;S;For_S;S;C;Idle;S;If_B_then_S;B;HasUnitWithinDistanceFromOpponent;4;S;C;MoveToUnit;Enemy;Closest;S;S_S;S;S_S;S;C;Build;Barracks;Down;1;S;C;Harvest;7;S;C;Attack;MostHealthy";
				
			}
			Node_LS n = (Node_LS)Control.load(j,f);
			return new Interpreter(utt,n );
		}
		if(s.equals("18")) {
			String j="";
			if(map.equals("1")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;S_S;S;C;Train;Worker;Left;2;S;S_S;S;For_S;S;C;Build;Barracks;EnemyDir;4;S;S_S;S;For_S;S;C;Idle;S;For_S;S;C;Train;Heavy;Down;9;S;C;Attack;Closest;S;For_S;S;If_B_then_S_else_S;B;CanAttack;S;For_S;S;If_B_then_S;B;is_Type;Worker;S;C;Harvest;4;S;Empty";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;S_S;S;C;Train;Worker;Left;2;S;S_S;S;For_S;S;C;Build;Barracks;EnemyDir;4;S;S_S;S;For_S;S;C;Idle;S;For_S;S;C;Train;Heavy;Down;9;S;C;Attack;Closest;S;For_S;S;If_B_then_S_else_S;B;CanAttack;S;For_S;S;If_B_then_S;B;is_Type;Worker;S;C;Harvest;4;S;Empty";
			}else if(map.equals("2")) {
				if(lado==0) j = "S;For_S;S;S_S;S;For_S;S;C;Train;Heavy;Up;50;S;For_S;S;S_S;S;C;Build;Barracks;Up;2;S;S_S;S;If_B_then_S_else_S;B;OpponentHasUnitInPlayerRange;S;C;Attack;MostHealthy;S;Empty;S;S_S;S;C;Train;Worker;Down;3;S;S_S;S;S_S;S;If_B_then_S;B;IsBuilder;S;For_S;S;S_S;S;C;Harvest;20;S;C;Idle;S;C;Attack;Weakest;S;For_S;S;If_B_then_S_else_S;B;HaveQtdUnitsAttacking;1;S;For_S;S;C;Attack;MostHealthy;S;C;Idle";
				if(lado ==1)j ="S;S_S;S;For_S;S;S_S;S;S_S;S;S_S;S;C;Idle;S;C;Harvest;6;S;C;Attack;MostHealthy;S;S_S;S;S_S;S;S_S;S;C;Train;Worker;EnemyDir;9;S;C;MoveToUnit;Ally;Closest;S;C;MoveToUnit;Ally;LessHealthy;S;For_S;S;C;Build;Barracks;EnemyDir;1;S;For_S;S;S_S;S;C;MoveToUnit;Enemy;MostHealthy;S;For_S;S;S_S;S;C;MoveToUnit;Enemy;Strongest;S;S_S;S;C;Train;Heavy;Down;9;S;C;Train;Heavy;Up;10";
			} else if (map.equals("3")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;C;Train;Heavy;Up;4;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Idle;S;For_S;S;S_S;S;C;Build;Barracks;EnemyDir;10;S;C;Harvest;20;S;S_S;S;S_S;S;S_S;S;For_S;S;S_S;S;C;Attack;Closest;S;C;MoveToUnit;Enemy;LessHealthy;S;C;MoveToUnit;Enemy;Closest;S;C;MoveToUnit;Enemy;Weakest;S;If_B_then_S_else_S;B;OpponentHasUnitThatKillsUnitInOneAttack;S;C;MoveToUnit;Enemy;LessHealthy;S;Empty;S;C;Train;Worker;Up;6;S;S_S;S;C;MoveToUnit;Enemy;Farthest;S;S_S;S;C;Train;Light;Right;5;S;C;MoveToUnit;Enemy;LessHealthy";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;C;Train;Heavy;Up;4;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Idle;S;For_S;S;S_S;S;C;Build;Barracks;EnemyDir;10;S;C;Harvest;20;S;S_S;S;S_S;S;S_S;S;For_S;S;S_S;S;C;Attack;Closest;S;C;MoveToUnit;Enemy;LessHealthy;S;If_B_then_S_else_S;B;CanAttack;S;S_S;S;C;MoveToUnit;Ally;Closest;S;C;MoveToUnit;Ally;Strongest;S;Empty;S;C;MoveToUnit;Enemy;Weakest;S;S_S;S;C;MoveToUnit;Enemy;LessHealthy;S;S_S;S;C;MoveToUnit;Ally;Weakest;S;C;Train;Heavy;Left;15;S;C;Train;Worker;Up;6;S;S_S;S;C;MoveToUnit;Enemy;Farthest;S;C;Train;Light;Right;5";
				
			}
			Node_LS n = (Node_LS)Control.load(j,f);
			return new Interpreter(utt,n );
		}
		if(s.equals("19")) {
			String j="";
			if(map.equals("1")) {
				if(lado==0) j = "S;S_S;S;S_S;S;For_S;S;S_S;S;S_S;S;C;Train;Heavy;Right;50;S;C;Build;Barracks;Down;1;S;C;Train;Worker;Up;4;S;For_S;S;C;Build;Barracks;Up;9;S;S_S;S;For_S;S;C;Harvest;5;S;S_S;S;For_S;S;C;Attack;Closest;S;S_S;S;For_S;S;S_S;S;C;MoveToUnit;Enemy;LessHealthy;S;For_S;S;C;MoveToUnit;Ally;MostHealthy;S;For_S;S;S_S;S;C;MoveToUnit;Enemy;MostHealthy;S;S_S;S;For_S;S;C;MoveToUnit;Ally;MostHealthy;S;If_B_then_S;B;HasNumberOfWorkersHarvesting;20;S;C;MoveToUnit;Ally;Farthest";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;C;Attack;Farthest;S;C;MoveToUnit;Enemy;Farthest;S;For_S;S;If_B_then_S_else_S;B;IsBuilder;S;S_S;S;C;Build;Barracks;EnemyDir;9;S;C;Harvest;3;S;S_S;S;C;Train;Heavy;Down;7;S;S_S;S;C;Attack;Strongest;S;For_S;S;S_S;S;C;Train;Worker;Left;2;S;C;Idle";
			}else if(map.equals("2")) {
				if(lado==0) j = "S;S_S;S;For_S;S;C;Train;Heavy;Left;100;S;For_S;S;S_S;S;For_S;S;C;Idle;S;S_S;S;S_S;S;For_S;S;S_S;S;C;Harvest;2;S;S_S;S;C;Build;Barracks;Right;15;S;C;Harvest;50;S;For_S;S;C;MoveToUnit;Enemy;MostHealthy;S;S_S;S;C;Train;Worker;Down;4;S;C;Attack;Weakest";
				if(lado ==1)j ="S;S_S;S;S_S;S;For_S;S;If_B_then_S_else_S;B;HasUnitWithinDistanceFromOpponent;1;S;C;Idle;S;Empty;S;For_S;S;S_S;S;C;Build;Barracks;Down;6;S;S_S;S;S_S;S;C;Harvest;20;S;C;MoveToUnit;Enemy;Strongest;S;S_S;S;If_B_then_S;B;CanAttack;S;S_S;S;C;Attack;Strongest;S;C;MoveToUnit;Enemy;Strongest;S;C;Train;Heavy;Right;9;S;For_S;S;C;MoveToUnit;Enemy;Closest";
			} else if (map.equals("3")) {
				if(lado==0) j = "S;S_S;S;For_S;S;S_S;S;C;Train;Worker;Down;6;S;S_S;S;If_B_then_S;B;OpponentHasUnitInPlayerRange;S;For_S;S;C;MoveToUnit;Enemy;Strongest;S;S_S;S;For_S;S;C;Build;Barracks;EnemyDir;6;S;S_S;S;S_S;S;If_B_then_S;B;OpponentHasUnitInPlayerRange;S;C;MoveToUnit;Ally;Weakest;S;If_B_then_S_else_S;B;OpponentHasUnitThatKillsUnitInOneAttack;S;C;MoveToUnit;Ally;LessHealthy;S;C;MoveToUnit;Enemy;LessHealthy;S;S_S;S;C;MoveToUnit;Enemy;Closest;S;For_S;S;S_S;S;S_S;S;If_B_then_S_else_S;B;IsBuilder;S;Empty;S;C;Attack;Closest;S;C;Harvest;15;S;C;Train;Heavy;Down;9;S;For_S;S;C;MoveToUnit;Enemy;Farthest";
				if(lado ==1)j ="S;S_S;S;For_S;S;S_S;S;C;Train;Worker;Down;6;S;S_S;S;If_B_then_S;B;OpponentHasUnitInPlayerRange;S;For_S;S;C;MoveToUnit;Enemy;Strongest;S;S_S;S;For_S;S;C;Build;Barracks;EnemyDir;6;S;S_S;S;S_S;S;If_B_then_S;B;OpponentHasUnitInPlayerRange;S;C;MoveToUnit;Ally;Weakest;S;If_B_then_S_else_S;B;OpponentHasUnitThatKillsUnitInOneAttack;S;C;MoveToUnit;Ally;LessHealthy;S;C;MoveToUnit;Enemy;LessHealthy;S;S_S;S;C;MoveToUnit;Enemy;Closest;S;For_S;S;S_S;S;S_S;S;If_B_then_S_else_S;B;IsBuilder;S;Empty;S;C;Attack;Closest;S;C;Harvest;15;S;C;Train;Heavy;Down;9;S;For_S;S;C;MoveToUnit;Enemy;Farthest";
				
			}
			Node_LS n = (Node_LS)Control.load(j,f);
			return new Interpreter(utt,n );
		}
		if(s.equals("20")) {
			String j="";
			if(map.equals("1")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;For_S;S;S_S;S;C;Harvest;2;S;C;Idle;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Train;Heavy;EnemyDir;20;S;S_S;S;S_S;S;C;Train;Ranged;Right;2;S;C;Build;Barracks;Left;7;S;C;Train;Worker;Right;2;S;S_S;S;S_S;S;C;Harvest;9;S;If_B_then_S;B;HasUnitInOpponentRange;S;C;Attack;LessHealthy;S;C;MoveToUnit;Enemy;LessHealthy;S;C;Train;Worker;EnemyDir;3;S;S_S;S;If_B_then_S_else_S;B;HasUnitThatKillsInOneAttack;S;C;MoveToUnit;Enemy;MostHealthy;S;Empty;S;C;Attack;LessHealthy";
				if(lado ==1)j ="S;For_S;S;S_S;S;C;Build;Barracks;Down;25;S;S_S;S;C;Attack;LessHealthy;S;S_S;S;S_S;S;C;MoveToUnit;Enemy;Closest;S;C;Train;Worker;EnemyDir;7;S;S_S;S;For_S;S;C;Train;Heavy;Left;100;S;For_S;S;S_S;S;If_B_then_S;B;CanHarvest;S;S_S;S;For_S;S;C;Idle;S;For_S;S;C;Train;Light;Down;15;S;C;Harvest;3";
				
			}else if(map.equals("2")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;C;Train;Worker;Left;2;S;S_S;S;S_S;S;S_S;S;C;Build;Barracks;Down;2;S;C;Train;Heavy;Down;5;S;S_S;S;C;Train;Heavy;Left;100;S;If_B_then_S_else_S;B;HasUnitWithinDistanceFromOpponent;5;S;Empty;S;C;Harvest;7;S;C;Attack;Closest;S;C;Train;Worker;EnemyDir;6";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;S_S;S;S_S;S;S_S;S;C;Train;Worker;EnemyDir;6;S;If_B_then_S_else_S;B;CanHarvest;S;If_B_then_S;B;HasUnitInOpponentRange;S;C;MoveToUnit;Enemy;LessHealthy;S;C;Attack;Closest;S;S_S;S;C;Harvest;8;S;S_S;S;For_S;S;C;Idle;S;S_S;S;S_S;S;C;MoveToUnit;Enemy;LessHealthy;S;If_B_then_S;B;IsBuilder;S;C;MoveToUnit;Ally;MostHealthy;S;For_S;S;If_B_then_S;B;HasUnitThatKillsInOneAttack;S;C;Train;Heavy;Up;25;S;For_S;S;C;Build;Barracks;Right;100;S;S_S;S;For_S;S;If_B_then_S;B;HasUnitInOpponentRange;S;For_S;S;C;Train;Ranged;Down;50;S;C;MoveToUnit;Ally;Weakest;S;If_B_then_S_else_S;B;HasUnitThatKillsInOneAttack;S;Empty;S;C;MoveToUnit;Ally;Closest";
			} else if (map.equals("3")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;S_S;S;If_B_then_S;B;HasNumberOfUnits;Worker;2;S;C;Train;Worker;Down;4;S;S_S;S;C;Build;Barracks;Left;1;S;S_S;S;C;Idle;S;If_B_then_S;B;HasUnitThatKillsInOneAttack;S;C;Harvest;25;S;S_S;S;For_S;S;C;Train;Heavy;Down;10;S;S_S;S;C;Attack;Weakest;S;C;Train;Worker;EnemyDir;6;S;C;MoveToUnit;Enemy;LessHealthy";
				if(lado ==1)j ="S;S_S;S;For_S;S;C;Build;Barracks;EnemyDir;1;S;For_S;S;S_S;S;For_S;S;C;Idle;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Harvest;8;S;For_S;S;C;Train;Heavy;EnemyDir;20;S;C;Attack;Weakest;S;C;Train;Worker;Down;4";
				
			}
			Node_LS n = (Node_LS)Control.load(j,f);
			return new Interpreter(utt,n );
		}
		if(s.equals("21")) {
			String j="";
			if(map.equals("1")) {
				if(lado==0) j = "S;S_S;S;For_S;S;C;Idle;S;S_S;S;S_S;S;For_S;S;S_S;S;For_S;S;C;Train;Heavy;EnemyDir;7;S;S_S;S;S_S;S;C;Harvest;1;S;C;Train;Worker;EnemyDir;5;S;C;Harvest;2;S;For_S;S;S_S;S;If_B_then_S_else_S;B;HasUnitWithinDistanceFromOpponent;25;S;S_S;S;C;Build;Barracks;Up;50;S;C;Train;Ranged;EnemyDir;2;S;S_S;S;C;MoveToUnit;Ally;Closest;S;If_B_then_S_else_S;B;OpponentHasUnitThatKillsUnitInOneAttack;S;For_S;S;C;Build;Barracks;Up;5;S;C;Train;Light;EnemyDir;3;S;C;Harvest;25;S;For_S;S;C;Attack;Closest";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;If_B_then_S;B;is_Type;Heavy;S;C;Attack;Closest;S;If_B_then_S;B;CanAttack;S;C;MoveAway;S;S_S;S;S_S;S;S_S;S;C;Idle;S;For_S;S;S_S;S;C;Train;Worker;Left;2;S;C;Build;Barracks;EnemyDir;1;S;For_S;S;S_S;S;C;Idle;S;S_S;S;C;Harvest;20;S;C;Train;Heavy;Right;10;S;C;Attack;Strongest";
			}else if(map.equals("2")) {
				if(lado==0) j = "S;For_S;S;S_S;S;C;Build;Barracks;Up;2;S;S_S;S;C;Train;Worker;Right;6;S;S_S;S;For_S;S;C;Train;Heavy;EnemyDir;10;S;If_B_then_S_else_S;B;is_Type;Barracks;S;S_S;S;For_S;S;S_S;S;For_S;S;C;Harvest;15;S;For_S;S;C;Train;Heavy;Right;20;S;If_B_then_S_else_S;B;CanAttack;S;C;MoveToUnit;Enemy;Weakest;S;Empty;S;S_S;S;For_S;S;If_B_then_S_else_S;B;HaveQtdUnitsAttacking;10;S;Empty;S;C;Idle;S;S_S;S;C;Harvest;7;S;C;Attack;LessHealthy";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;C;Build;Barracks;EnemyDir;2;S;S_S;S;C;Harvest;2;S;C;MoveToUnit;Enemy;LessHealthy;S;S_S;S;S_S;S;S_S;S;If_B_then_S;B;IsBuilder;S;S_S;S;For_S;S;S_S;S;S_S;S;C;Harvest;7;S;If_B_then_S_else_S;B;HasUnitInOpponentRange;S;C;Train;Ranged;Left;15;S;If_B_then_S;B;HaveQtdUnitsAttacking;10;S;C;MoveToUnit;Enemy;Closest;S;C;Attack;Closest;S;For_S;S;C;Train;Heavy;Right;6;S;S_S;S;For_S;S;C;Idle;S;C;Train;Heavy;Down;20;S;C;MoveToUnit;Enemy;Strongest;S;C;Train;Worker;Left;4";
			} else if (map.equals("3")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;S_S;S;C;Train;Light;Left;5;S;S_S;S;C;Train;Worker;Left;7;S;For_S;S;If_B_then_S_else_S;B;CanHarvest;S;For_S;S;C;Idle;S;Empty;S;For_S;S;C;Train;Heavy;EnemyDir;6;S;S_S;S;For_S;S;S_S;S;For_S;S;C;Build;Barracks;Right;1;S;C;Harvest;7;S;S_S;S;C;Attack;Closest;S;For_S;S;C;Train;Heavy;Left;25";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;S_S;S;C;Train;Light;Left;5;S;S_S;S;C;Train;Worker;Left;7;S;For_S;S;If_B_then_S_else_S;B;CanHarvest;S;For_S;S;C;Idle;S;Empty;S;For_S;S;C;Train;Heavy;EnemyDir;6;S;S_S;S;For_S;S;S_S;S;For_S;S;C;Build;Barracks;Right;1;S;C;Harvest;7;S;S_S;S;C;Attack;Closest;S;For_S;S;C;Train;Heavy;Left;25";
				
			}
			Node_LS n = (Node_LS)Control.load(j,f);
			return new Interpreter(utt,n );
		}
		if(s.equals("22")) {
			String j="";
			if(map.equals("1")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;C;Harvest;3;S;S_S;S;For_S;S;S_S;S;C;Idle;S;C;Train;Worker;Right;5;S;For_S;S;C;Train;Heavy;Up;5;S;S_S;S;C;Build;Barracks;EnemyDir;1;S;C;Attack;Weakest";
				if(lado ==1)j ="S;For_S;S;S_S;S;For_S;S;C;Idle;S;S_S;S;S_S;S;S_S;S;C;Build;Barracks;EnemyDir;50;S;If_B_then_S_else_S;B;CanHarvest;S;C;Harvest;100;S;For_S;S;C;Train;Worker;Left;2;S;C;Attack;Closest;S;S_S;S;C;MoveToUnit;Ally;Farthest;S;C;Train;Heavy;Right;15";
			}else if(map.equals("2")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;C;Idle;S;S_S;S;C;Train;Worker;Left;5;S;S_S;S;For_S;S;C;Train;Heavy;Left;20;S;S_S;S;S_S;S;For_S;S;C;Train;Ranged;Up;10;S;S_S;S;C;Build;Barracks;Left;2;S;C;MoveToUnit;Enemy;MostHealthy;S;If_B_then_S_else_S;B;CanAttack;S;For_S;S;C;Harvest;6;S;Empty;S;If_B_then_S_else_S;B;OpponentHasUnitInPlayerRange;S;Empty;S;C;Attack;Strongest";
				if(lado ==1)j ="S;S_S;S;For_S;S;S_S;S;C;Train;Heavy;EnemyDir;20;S;For_S;S;C;Idle;S;For_S;S;S_S;S;For_S;S;C;Harvest;2;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Train;Worker;EnemyDir;3;S;C;Build;Barracks;Down;2;S;C;Harvest;20;S;C;Attack;Strongest";
			} else if (map.equals("3")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;S_S;S;S_S;S;C;Harvest;6;S;S_S;S;For_S;S;S_S;S;C;Train;Heavy;EnemyDir;10;S;For_S;S;C;Idle;S;C;Train;Worker;Right;6;S;If_B_then_S_else_S;B;HasNumberOfUnits;Base;7;S;C;MoveToUnit;Enemy;Closest;S;Empty;S;C;Attack;Closest;S;S_S;S;C;MoveToUnit;Enemy;Farthest;S;For_S;S;C;Build;Barracks;Up;4";
				if(lado ==1)j ="S;For_S;S;S_S;S;C;Train;Worker;Down;4;S;S_S;S;C;Harvest;15;S;S_S;S;C;Attack;LessHealthy;S;S_S;S;For_S;S;S_S;S;C;Build;Barracks;Up;7;S;For_S;S;S_S;S;C;Idle;S;C;Harvest;1;S;C;Train;Heavy;Down;25";
				
			}
			Node_LS n = (Node_LS)Control.load(j,f);
			return new Interpreter(utt,n );
		}
		if(s.equals("23")) {
			String j="";
			if(map.equals("1")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;S_S;S;C;Harvest;2;S;C;Idle;S;For_S;S;S_S;S;S_S;S;C;Build;Barracks;EnemyDir;8;S;C;Train;Worker;Down;2;S;C;Train;Heavy;EnemyDir;25;S;C;Attack;Closest";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;S_S;S;C;Harvest;3;S;S_S;S;S_S;S;C;Build;Barracks;Left;1;S;C;Idle;S;For_S;S;C;Idle;S;S_S;S;S_S;S;C;MoveToUnit;Enemy;Closest;S;For_S;S;S_S;S;C;Train;Worker;Right;4;S;If_B_then_S_else_S;B;is_Type;Worker;S;If_B_then_S;B;HasUnitInOpponentRange;S;C;Attack;Weakest;S;Empty;S;S_S;S;C;Train;Heavy;Down;50;S;C;MoveToUnit;Ally;Strongest;S;S_S;S;If_B_then_S_else_S;B;HasLessNumberOfUnits;Barracks;20;S;Empty;S;C;MoveToUnit;Enemy;Closest;S;S_S;S;If_B_then_S;B;HasUnitInOpponentRange;S;For_S;S;C;Train;Ranged;Down;10;S;C;MoveToUnit;Enemy;Closest";
			}else if(map.equals("2")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;C;Train;Heavy;Left;50;S;S_S;S;S_S;S;S_S;S;For_S;S;S_S;S;C;Train;Worker;Left;4;S;C;Train;Heavy;Down;4;S;S_S;S;C;Idle;S;S_S;S;C;Build;Barracks;EnemyDir;3;S;C;Harvest;7;S;If_B_then_S;B;OpponentHasUnitThatKillsUnitInOneAttack;S;C;MoveToUnit;Enemy;Closest;S;C;Attack;LessHealthy;S;For_S;S;C;Train;Heavy;EnemyDir;10";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;C;Train;Heavy;Left;50;S;S_S;S;S_S;S;S_S;S;For_S;S;S_S;S;C;Train;Worker;Left;4;S;C;Train;Heavy;Down;4;S;S_S;S;C;Idle;S;S_S;S;C;Build;Barracks;EnemyDir;3;S;C;Harvest;7;S;If_B_then_S;B;OpponentHasUnitThatKillsUnitInOneAttack;S;C;MoveToUnit;Enemy;Closest;S;C;Attack;LessHealthy;S;For_S;S;C;Train;Heavy;EnemyDir;10";
			} else if (map.equals("3")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;C;Train;Worker;Right;8;S;C;Attack;LessHealthy;S;S_S;S;S_S;S;C;MoveToUnit;Enemy;MostHealthy;S;S_S;S;S_S;S;C;Train;Heavy;Left;6;S;S_S;S;For_S;S;C;Build;Barracks;Right;1;S;If_B_then_S;B;IsBuilder;S;For_S;S;C;Harvest;25;S;For_S;S;C;Idle;S;S_S;S;C;MoveToUnit;Ally;Weakest;S;C;Train;Light;Right;6";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;S_S;S;C;Attack;Weakest;S;For_S;S;C;Idle;S;For_S;S;S_S;S;S_S;S;S_S;S;C;Build;Barracks;Right;1;S;If_B_then_S_else_S;B;OpponentHasNumberOfUnits;Ranged;15;S;C;MoveToUnit;Enemy;Strongest;S;C;Train;Heavy;Up;15;S;C;Harvest;15;S;S_S;S;For_S;S;C;Harvest;5;S;If_B_then_S_else_S;B;HasUnitThatKillsInOneAttack;S;Empty;S;C;Attack;Farthest;S;C;Train;Worker;Left;6";
				
			}
			Node_LS n = (Node_LS)Control.load(j,f);
			return new Interpreter(utt,n );
		}
		if(s.equals("24")) {
			String j="";
			if(map.equals("1")) {
				if(lado==0) j = "S;S_S;S;For_S;S;If_B_then_S;B;HasNumberOfUnits;Heavy;1;S;If_B_then_S_else_S;B;HasUnitInOpponentRange;S;C;Idle;S;Empty;S;S_S;S;For_S;S;S_S;S;C;Harvest;7;S;S_S;S;If_B_then_S;B;HasUnitInOpponentRange;S;C;Train;Light;Left;3;S;S_S;S;C;Train;Worker;Right;3;S;S_S;S;C;Train;Heavy;Left;4;S;S_S;S;C;Attack;Closest;S;For_S;S;C;Build;Barracks;EnemyDir;1;S;S_S;S;For_S;S;C;MoveToUnit;Ally;Strongest;S;For_S;S;C;Train;Heavy;Down;8";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Build;Base;Up;10;S;For_S;S;C;Idle;S;S_S;S;C;Build;Barracks;Right;10;S;S_S;S;For_S;S;S_S;S;C;Harvest;3;S;C;Train;Heavy;Left;20;S;S_S;S;C;Train;Worker;Up;4;S;If_B_then_S;B;HasNumberOfWorkersHarvesting;2;S;If_B_then_S_else_S;B;CanAttack;S;If_B_then_S;B;CanHarvest;S;C;MoveAway;S;C;MoveToUnit;Enemy;MostHealthy;S;C;Attack;Strongest";
			}else if(map.equals("2")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;C;Harvest;100;S;C;Idle;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Train;Heavy;EnemyDir;15;S;C;Attack;Closest;S;If_B_then_S;B;HasUnitThatKillsInOneAttack;S;C;Train;Heavy;Down;100;S;S_S;S;C;Train;Worker;EnemyDir;4;S;S_S;S;For_S;S;C;Build;Barracks;Right;50;S;C;MoveToUnit;Enemy;Farthest";
				if(lado ==1)j ="S;For_S;S;S_S;S;C;Train;Worker;Up;4;S;For_S;S;S_S;S;S_S;S;C;Build;Barracks;Up;2;S;C;Harvest;7;S;S_S;S;S_S;S;C;Attack;Closest;S;S_S;S;For_S;S;For_S;S;C;Harvest;3;S;S_S;S;If_B_then_S_else_S;B;OpponentHasUnitThatKillsUnitInOneAttack;S;S_S;S;For_S;S;S_S;S;C;Train;Heavy;EnemyDir;8;S;C;Harvest;4;S;C;MoveToUnit;Ally;Farthest;S;Empty;S;C;MoveToUnit;Enemy;LessHealthy;S;For_S;S;C;Train;Worker;Up;5";
			} else if (map.equals("3")) {
				if(lado==0) j = "S;For_S;S;S_S;S;For_S;S;S_S;S;S_S;S;C;Idle;S;S_S;S;C;Train;Worker;Right;7;S;S_S;S;For_S;S;C;Train;Heavy;Down;10;S;S_S;S;If_B_then_S_else_S;B;OpponentHasUnitThatKillsUnitInOneAttack;S;C;Harvest;1;S;Empty;S;C;Build;Barracks;EnemyDir;1;S;S_S;S;C;Harvest;20;S;C;Attack;Closest;S;C;Train;Worker;Right;9";
				if(lado ==1)j ="S;S_S;S;S_S;S;For_S;S;C;Build;Barracks;Right;6;S;S_S;S;If_B_then_S;B;OpponentHasNumberOfUnits;Light;15;S;C;MoveToUnit;Ally;Weakest;S;For_S;S;S_S;S;S_S;S;For_S;S;C;Idle;S;For_S;S;S_S;S;C;Harvest;25;S;C;Attack;Closest;S;S_S;S;C;Train;Heavy;Left;25;S;C;Train;Worker;Right;5;S;If_B_then_S;B;OpponentHasNumberOfUnits;Barracks;20;S;C;MoveToUnit;Ally;Weakest";
				
			}
			Node_LS n = (Node_LS)Control.load(j,f);
			return new Interpreter(utt,n );
		}
		if(s.equals("25")) {
			String j="";
			if(map.equals("1")) {
				if(lado==0) j = "S;S_S;S;S_S;S;For_S;S;S_S;S;S_S;S;S_S;S;C;Harvest;2;S;C;Train;Ranged;Down;3;S;C;Train;Heavy;Left;15;S;S_S;S;C;Idle;S;C;Train;Worker;EnemyDir;3;S;For_S;S;S_S;S;C;Build;Barracks;Left;9;S;C;Harvest;50;S;For_S;S;S_S;S;If_B_then_S;B;HasNumberOfWorkersHarvesting;1;S;S_S;S;S_S;S;C;MoveToUnit;Ally;Weakest;S;C;MoveToUnit;Enemy;Closest;S;C;MoveToUnit;Ally;MostHealthy;S;C;Attack;Strongest";
				if(lado ==1)j ="S;S_S;S;For_S;S;C;Train;Worker;EnemyDir;2;S;For_S;S;S_S;S;S_S;S;If_B_then_S;B;HasNumberOfWorkersHarvesting;1;S;C;Idle;S;C;Train;Heavy;EnemyDir;20;S;S_S;S;For_S;S;If_B_then_S;B;CanAttack;S;C;Build;Barracks;Left;15;S;S_S;S;C;Harvest;15;S;C;Attack;Closest";
			}else if(map.equals("2")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;C;Train;Ranged;EnemyDir;1;S;C;Idle;S;S_S;S;For_S;S;S_S;S;S_S;S;C;Build;Barracks;EnemyDir;7;S;C;Train;Heavy;Right;50;S;S_S;S;C;Train;Worker;EnemyDir;3;S;For_S;S;If_B_then_S;B;is_Type;Barracks;S;For_S;S;C;Harvest;7;S;S_S;S;S_S;S;If_B_then_S;B;IsBuilder;S;C;MoveToUnit;Ally;Weakest;S;If_B_then_S_else_S;B;OpponentHasNumberOfUnits;Light;50;S;C;MoveToUnit;Enemy;LessHealthy;S;Empty;S;S_S;S;C;Attack;Closest;S;C;Train;Worker;Left;5";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;C;Train;Ranged;EnemyDir;1;S;C;Idle;S;S_S;S;For_S;S;S_S;S;S_S;S;C;Build;Barracks;EnemyDir;7;S;C;Train;Heavy;Right;50;S;S_S;S;C;Train;Worker;EnemyDir;3;S;For_S;S;If_B_then_S;B;is_Type;Barracks;S;For_S;S;C;Harvest;7;S;S_S;S;S_S;S;If_B_then_S;B;IsBuilder;S;C;MoveToUnit;Ally;Weakest;S;If_B_then_S_else_S;B;OpponentHasNumberOfUnits;Light;50;S;C;MoveToUnit;Enemy;LessHealthy;S;Empty;S;S_S;S;C;Attack;Closest;S;C;Train;Worker;Left;5";
			} else if (map.equals("3")) {
				if(lado==0) j = "S;For_S;S;S_S;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Train;Heavy;Up;10;S;C;Train;Worker;Right;7;S;For_S;S;C;Idle;S;For_S;S;S_S;S;If_B_then_S_else_S;B;HasUnitWithinDistanceFromOpponent;50;S;Empty;S;C;Harvest;3;S;C;Harvest;2;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Train;Light;Left;6;S;C;Build;Barracks;Down;1;S;C;Harvest;7;S;C;Attack;Closest";
				if(lado ==1)j ="S;For_S;S;S_S;S;S_S;S;C;Idle;S;S_S;S;S_S;S;S_S;S;For_S;S;C;Train;Heavy;Up;10;S;C;Train;Worker;Right;7;S;For_S;S;C;Idle;S;If_B_then_S;B;HasUnitWithinDistanceFromOpponent;4;S;C;MoveToUnit;Enemy;Closest;S;S_S;S;S_S;S;C;Build;Barracks;Down;1;S;C;Harvest;7;S;C;Attack;MostHealthy";
				
			}
			Node_LS n = (Node_LS)Control.load(j,f);
			return new Interpreter(utt,n );
		}
		
		
		
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
	
		String id_IA = args[1];
		
		
		UnitTypeTable utt = new UnitTypeTable(2);
		String path_map = getMap(args[0]);
		PhysicalGameState pgs = PhysicalGameState.load(path_map, utt);
		GameState gs2 = new GameState(pgs, utt);
		
		
	
			
		Interpreter j0 = (Interpreter) getAdv(gs2,""+id_IA,utt,0,map);
		Interpreter j1 = (Interpreter) getAdv(gs2,""+id_IA,utt,1,map);
			
		
			double r = Avalia(gs2,utt,id_IA,map,j0,j1);
			System.out.println("Camp\t"+id_IA+"\t"+r);
			
			
			
			
		
		

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
	
	private static double Avalia(GameState gs2, UnitTypeTable utt, String id_ia,String map, AI j0, AI j1) throws Exception {
		// TODO Auto-generated method stub
		double r=0;
		
		for(int i =16;i<18;i++) {
			
			
			
			double rL=0;
			for(int rep =0;rep<5;rep++){
				AI adv1 = getAdv(gs2,""+i,utt,1,map);
				rL+=partida(gs2,utt,0,max,j0,adv1,false);
			}
			System.out.println("VS\t"+i+"\t"+rL);
			for(int rep =0;rep<5;rep++) {
				AI adv0 = getAdv(gs2,""+i,utt,0,map);
				rL+=partida(gs2,utt,1,max,j1,adv0,false);
			}
			System.out.println("VS\t"+i+"\t"+rL);
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
