package fa.nfa;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * A concrete NFA state that stores outgoing transitions by symbol.
 */
public class NFAState extends fa.State {
    private Map<Character, Set<NFAState>> subDelta;

    /**
     * Creates a state with the provided label.
     * @param name state label
     */
    public NFAState(String name) {
        super(name);
        subDelta = new LinkedHashMap<>();
    }

    /**
     * Adds transitions for a symbol, merging with any existing targets.
     * @param c transition symbol
     * @param set destination states to add
     */
    public void addSubDelta(char c, Set<NFAState> set) {
        Set<NFAState> existing = subDelta.get(c);
        if (existing == null) {
            existing = new LinkedHashSet<>();
            subDelta.put(c, existing);
        }
        existing.addAll(set);
    }

    /**
     * Returns the state transition map.
     * @return map of symbol to destination states
     */
    public Map<Character, Set<NFAState>> getSubDelta() {
        return subDelta;
    }

    /**
     * Returns destinations on a symbol.
     * @param symbol transition symbol
     * @return destination state set (copy), or empty set when no transition exists
     */
    public Set<NFAState> toStates(char symbol) {
        Set<NFAState> out = subDelta.get(symbol);
        if (out == null) {
            return Set.of();
        }
        return new LinkedHashSet<>(out);
    }
}
