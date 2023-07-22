package ai.synthesis.ComplexDSL.IAs2;

import ai.synthesis.ComplexDSL.LS_CFG.Node_LS;
import rts.GameState;

import java.util.List;

public interface Avaliador {
  double Avalia(GameState gs,int max,Node_LS n) throws Exception;
  void update(GameState gs,int max,Node_LS n) throws Exception;
  Node_LS getIndividuo();
  Node_LS getBest();
  boolean criterioParada(double d);
  int getBudget();
  List<Node_LS> getIndividuos();

  void addTojs(Node_LS j1);
}
