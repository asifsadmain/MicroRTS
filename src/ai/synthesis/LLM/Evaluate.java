package ai.synthesis.LLM;

import ai.asymmetric.GAB.SandBox.GAB;
import ai.asymmetric.SAB.SAB;
import ai.competition.COAC.CoacAI;
import ai.competition.mayariBot.mayari;
import ai.competition.rojobot.Rojo;
import ai.core.AI;
import ai.mcts.naivemcts.NaiveMCTS;
import ai.synthesis.LocalSearch.EvaluateGameState.SimplePlayout;
import ai.synthesis.LocalSearch.LS_CFG.FactoryLS;
import ai.synthesis.LocalSearch.LS_CFG.Node_LS;
import ai.synthesis.Synthesis_Base.AIs.Interpreter;
import ai.synthesis.Synthesis_Base.CFG.Control;
import ai.synthesis.Synthesis_Base.CFG.Factory;
import rts.GameState;
import rts.PhysicalGameState;
import rts.units.UnitTypeTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Evaluate {
    public static void main(String[] args) throws Exception {
        HashMap<String, Double> result = new HashMap<String, Double>();

        UnitTypeTable utt = new UnitTypeTable(2);

        String path_map = "maps/8x8/basesWorkers8x8A.xml";
//        String path_map = "maps/24x24/basesWorkers24x24A.xml";
//        String path_map = "maps/32x32/basesWorkers32x32A.xml";
//        String path_map = "maps/16x16/basesWorkers16x16A.xml";
//        String path_map = "maps/BWDistantResources32x32.xml";
//        String path_map = "maps/BroodWar/(4)BloodBath.scmB.xml";
//        String path_map = "maps/8x8/FourBasesWorkers8x8.xml";
//        String path_map = "maps/16x16/TwoBasesBarracks16x16.xml";
//        String path_map = "maps/NoWhereToRun9x8.xml";
//        String path_map = "maps/DoubleGame24x24.xml";

        PhysicalGameState pgs = PhysicalGameState.load(path_map, utt);
        GameState gs2 = new GameState(pgs, utt);

        SimplePlayout playout = new SimplePlayout();
        FactoryLS f = new FactoryLS();

        String csvFile = "src/ai/synthesis/LLM/bestStrategies.csv"; // Replace with the actual path to your CSV file

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                result.put(line, 0.0);
//                System.out.println(line);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        for (Map.Entry<String, Double> strategy1 : result.entrySet()) {
            Node_LS pre_ai = (Node_LS) Control.load(strategy1.getKey(), f);
            AI ai1 = new Interpreter(utt, pre_ai);
            double r = 0.0;
            for (Map.Entry<String, Double> strategy2 : result.entrySet()) {
                Node_LS pre_ai2 = (Node_LS) Control.load(strategy2.getKey(), f);
                //translate it to a AI
                AI ai2 = new Interpreter(utt, pre_ai2);

                if (!strategy1.getKey().equals(strategy2.getKey()))
                    r += playout.run(gs2, utt,0, 6000, ai1, ai2, false).m_a;
            }
//            AI ai2 = new CoacAI(utt);
//            r += playout.run(gs2, utt,0, 6000, ai1, ai2, false).m_a;
//            AI ai2 = new mayari(utt);
//            r += playout.run(gs2, utt,0, 6000, ai1, ai2, false).m_a;
//            ai2 = new GAB(utt);
//            r += playout.run(gs2, utt,0, 6000, ai1, ai2, false).m_a;
//            ai2 = new SAB(utt);
//            r += playout.run(gs2, utt,0, 6000, ai1, ai2, false).m_a;
//            ai2 = new NaiveMCTS(utt);
//            r += playout.run(gs2, utt,0, 6000, ai1, ai2, false).m_a;
//            ai2 = new Rojo(utt);
//            r += playout.run(gs2, utt,0, 6000, ai1, ai2, false).m_a;
            result.put(strategy1.getKey(), r);
            System.out.println(strategy1.getKey() + " : " + r);
        }

        System.out.println();
        System.out.println();
        for (Map.Entry<String, Double> strategy : result.entrySet()) {
            System.out.println(strategy.getKey() + "," + strategy.getValue());
        }
    }
}
