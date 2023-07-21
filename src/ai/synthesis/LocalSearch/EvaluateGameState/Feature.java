package ai.synthesis.LocalSearch.EvaluateGameState;

import java.util.List;

public interface Feature {
	int compareTo(Feature n);
    public int hashCode();
    Feature Clone();
    void mutacao();
    double semelhaca(Feature n);
    int[] convertList();
}
