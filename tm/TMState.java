package tm;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class TMState extends State{
    //Development Plan: Use NFASTATE as inspiration, while
    //including necessary turing machine elements

    private Map<TMState, char[]> transition;

    /**
     * Creates a state with the provided label.
     * @param name state label
     */
    public TMState(String name) {
        super(name);
        transition = new LinkedHashMap<>();
    }

    /**
     * Adds transitions for a symbol, merging with any existing targets.
     * @param state destination state
     * @param c character array for read, write, l/r
     */
    // this could maybe work, but it's not very efficient
    // I'm thinking this could work better if the key 
    // is the read character, and the value holds write, 
    // l/r, and the next state
    public void addTransition(TMState state, char[] c) {
        char[] existing = transition.get(state);
        if (existing == null) {
            // existing = c;
            transition.put(state, c);
        }
        // existing.addAll(set);
    }

    /**
     * Returns the state transition map.
     * @return map of symbol to destination states
     */
    public Map<TMState, char[]> getTransition() {
        return transition;
    }

    /**
     * Returns destinations on a symbol.
     * @param symbol transition symbol
     * @return destination state set (copy), or empty set when no transition exists
     */
    // public Set<NFAState> toStates(char symbol) {
    //     Set<NFAState> out = transition.get(symbol);
    //     if (out == null) {
    //         return Set.of();
    //     }
    //     return new LinkedHashSet<>(out);
    // }

    //This should be pretty 1 to 1, since it just seems to hold a state's transitions
}
