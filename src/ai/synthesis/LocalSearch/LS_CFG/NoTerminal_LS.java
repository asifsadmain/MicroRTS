package ai.synthesis.LocalSearch.LS_CFG;

import ai.synthesis.Synthesis_Base.CFG.NoTerminal;

public interface NoTerminal_LS extends NoTerminal {
	Node_LS sorteiaFilho(int budget);
}
