package fa.nfa;

import fa.State;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Nondeterministic finite automaton implementation.
 */
public class NFA implements NFAInterface {

    private Set<NFAState> states;
    private Set<Character> sigma;
    private Map<String, NFAState> statesByName;
    private NFAState startState;
    private Set<NFAState> finalStates;

    public NFA() {
        states = new LinkedHashSet<>();
        sigma = new LinkedHashSet<>();
        statesByName = new LinkedHashMap<>();
        startState = null;
        finalStates = new LinkedHashSet<>();
    }

    @Override
    public boolean addState(String name) {
        if (name == null || statesByName.containsKey(name)) {
            return false;
        }
        NFAState ns = new NFAState(name);
        states.add(ns);
        statesByName.put(name, ns);
        return true;
    }

    @Override
    public boolean setFinal(String name) {
        State s = getState(name);
        if (s == null) {
            return false;
        }
        return finalStates.add((NFAState) s);
    }

    @Override
    public boolean setStart(String name) {
        State s = getState(name);
        if (s == null) {
            return false;
        }
        startState = (NFAState) s;
        return true;
    }

    @Override
    public void addSigma(char symbol) {
        if (symbol == 'e') {
            return;
        }
        sigma.add(symbol);
    }

    @Override
    public boolean accepts(String s) {
        if (s == null || startState == null) {
            return false;
        }

        Set<NFAState> current = eClosure(startState);
        for (char symbol : s.toCharArray()) {
            Set<NFAState> next = new LinkedHashSet<>();
            for (NFAState state : current) {
                next.addAll(getToState(state, symbol));
            }

            Set<NFAState> withClosure = new LinkedHashSet<>();
            for (NFAState state : next) {
                withClosure.addAll(eClosure(state));
            }
            current = withClosure;
        }

        for (NFAState state : current) {
            if (finalStates.contains(state)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Character> getSigma() {
        return new LinkedHashSet<>(sigma);
    }

    @Override
    public NFAState getState(String name) {
        if (name == null) {
            return null;
        }
        return statesByName.get(name);
    }

    @Override
    public boolean isFinal(String name) {
        State s = getState(name);
        return s != null && finalStates.contains((NFAState) s);
    }

    @Override
    public boolean isStart(String name) {
        if (name == null || startState == null) {
            return false;
        }
        return name.equals(startState.getName());
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        if (from == null) {
            return Set.of();
        }
        return from.toStates(onSymb);
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        Set<NFAState> closure = new LinkedHashSet<>();
        if (s == null) {
            return closure;
        }

        // DFS with explicit stack as required by the project specs.
        Deque<NFAState> stack = new ArrayDeque<>();
        stack.push(s);
        closure.add(s);

        while (!stack.isEmpty()) {
            NFAState current = stack.pop();
            for (NFAState next : current.toStates('e')) {
                if (!closure.contains(next)) {
                    closure.add(next);
                    stack.push(next);
                }
            }
        }

        return closure;
    }

    @Override
    public int maxCopies(String s) {
        if (startState == null) {
            return 0;
        }
        if (s == null) {
            return eClosure(startState).size();
        }

        Set<NFAState> current = eClosure(startState);
        int max = current.size();

        for (char symbol : s.toCharArray()) {
            Set<NFAState> next = new LinkedHashSet<>();
            for (NFAState state : current) {
                next.addAll(getToState(state, symbol));
            }

            Set<NFAState> withClosure = new LinkedHashSet<>();
            for (NFAState state : next) {
                withClosure.addAll(eClosure(state));
            }
            current = withClosure;

            if (current.size() > max) {
                max = current.size();
            }
        }

        return max;
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        if (fromState == null || toStates == null) {
            return false;
        }

        // Epsilon transitions are marked by reserved character 'e'.
        if (onSymb != 'e' && !sigma.contains(onSymb)) {
            return false;
        }

        State from = getState(fromState);
        if (from == null) {
            return false;
        }

        Set<NFAState> to = new LinkedHashSet<>();
        for (String label : toStates) {
            State state = getState(label);
            if (state == null) {
                return false;
            }
            to.add((NFAState) state);
        }

        ((NFAState) from).addSubDelta(onSymb, to);
        return true;
    }

    @Override
    public boolean isDFA() {
        for (NFAState state : states) {
            if (!state.toStates('e').isEmpty()) {
                return false;
            }
            for (char symbol : sigma) {
                if (state.toStates(symbol).size() != 1) {
                    return false;
                }
            }
        }
        return true;
    }
}
