package ai.synthesis.LocalSearch.IAs2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ai.synthesis.LocalSearch.LS_CFG.FactoryLS;
import ai.synthesis.Synthesis_Base.AIs.Interpreter;
import ai.synthesis.Synthesis_Base.CFG.Control;
import ai.synthesis.Synthesis_Base.CFG.Factory;
import ai.synthesis.LocalSearch.EvaluateGameState.Playout;
import ai.synthesis.LocalSearch.EvaluateGameState.SimplePlayout;
import ai.synthesis.LocalSearch.LS_CFG.Node_LS;
import ai.core.AI;
import rts.GameState;
import rts.units.UnitTypeTable;

public class HC implements Search2 {

    int time_limit;

    Factory f;
    Random r;

    public HC(int time) {
        // TODO Auto-generated constructor stub
        System.out.println("Busca HC");

        this.time_limit=time;
        f = new FactoryLS();
        r =new Random();
    }




    @Override
    public Node_LS run(GameState gs, int max, Node_LS best,Evaluation ava) throws Exception {
        // TODO Auto-generated method stub
        this.resert();
        double v = ava.evaluation(gs, max, best);
        long Tini = System.currentTimeMillis();
        long tempo = System.currentTimeMillis()-Tini;



        while( (tempo*1.0)/1000.0 <time_limit && !ava.stoppingCriterion(v)) {

            Node_LS melhor_vizinho = (Node_LS) best.Clone(f) ;
            double v_vizinho = -111111;
            for(int i= 0;i<1000;i++) {

                Node_LS aux = (Node_LS) (melhor_vizinho.Clone(f));
                List<Node_LS> list =new ArrayList<>();
                for(int ii=0;ii<1;ii++) {

                    aux.countNode(list);
                    int custo = r.nextInt(9)+1;
                    int no = r.nextInt(list.size());

                    list.get(no).mutation(0, custo, false);

                }
                double v2 = ava.evaluation(gs, max, aux);

                if(v_vizinho<v2) {
                    melhor_vizinho = (Node_LS) aux.Clone(f);
                    v_vizinho=v2;
                }

                tempo = System.currentTimeMillis()-Tini;
                if((tempo*1.0)/1000.0 >time_limit || ava.stoppingCriterion(v_vizinho))break;

            }

            //System.out.println(v_vizinho.m_b+"   t2\t"+melhor_vizinho.translate());
            tempo = System.currentTimeMillis()-Tini;


            if(v<v_vizinho) {
                best = (Node_LS) melhor_vizinho.Clone(f);
                v= v_vizinho;

            }



        }
        return best;
    }


    @Override
    public void resert() {
        // TODO Auto-generated method stub

    }

}
