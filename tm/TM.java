import java.util.TreeMap;

public class TM {
    // Development plan: Implement Turing Machine simulator based on specs.
    // Use efficient data structures for bi-infinite tape and transitions.

    // Declare variables
    private int numStates;
    private int[][][] transitions; // [state][symbol][0=nextState, 1=writeSymbol, 2=move(0=L,1=R)]
    private TreeMap<Integer, Integer> tape; // position -> symbol, sorted for output
    private int currentState;
    private int headPosition;
    private int minVisited;
    private int maxVisited;

    public TM(int numStates, int numSigma) {
        this.numStates = numStates;
        this.transitions = new int[numStates][numSigma + 1][3]; // symbols 0 to m
        this.tape = new TreeMap<>();
        this.currentState = 0; // start state
        this.headPosition = 0;
        this.minVisited = Integer.MAX_VALUE;
        this.maxVisited = Integer.MIN_VALUE;
    }

    // Initialize tape with input string, starting at position 0
    public void initializeTape(String input) {
        for (int i = 0; i < input.length(); i++) {
            int symbol = input.charAt(i) - '0'; // assuming input is digits 1-m
            tape.put(i, symbol);
            updateVisited(i);
        }
        // If input is empty, tape starts with blanks (0)
    }

    // Add transition: for state, on symbol, nextState, writeSymbol, move
    public void addTransition(int state, int symbol, int nextState, int writeSymbol, int move) {
        transitions[state][symbol][0] = nextState;
        transitions[state][symbol][1] = writeSymbol;
        transitions[state][symbol][2] = move; // 0=L, 1=R
    }

    // Read current symbol at head (default to 0 if not set)
    private int readSymbol() {
        return tape.getOrDefault(headPosition, 0);
    }

    // Write symbol at head
    private void writeSymbol(int symbol) {
        tape.put(headPosition, symbol);
        updateVisited(headPosition);
    }

    // Move head: 0=L, 1=R
    private void moveHead(int direction) {
        if (direction == 0) { // L
            headPosition--;
        } else { // R
            headPosition++;
        }
        updateVisited(headPosition);
    }

    // Update min/max visited positions
    private void updateVisited(int pos) {
        if (pos < minVisited) minVisited = pos;
        if (pos > maxVisited) maxVisited = pos;
    }

    // Perform one step: read, write, move, change state
    public void step() {
        int symbol = readSymbol();
        int nextState = transitions[currentState][symbol][0];
        int writeSymbol = transitions[currentState][symbol][1];
        int move = transitions[currentState][symbol][2];

        writeSymbol(writeSymbol);
        moveHead(move);
        currentState = nextState;
    }

    // Run the TM until halting state
    public void runTM() {
        while (currentState != numStates - 1) { // halting state is n-1
            step();
        }
    }

    // Get the tape output as string of visited cells
    public String getTapeOutput() {
        StringBuilder sb = new StringBuilder();
        for (int pos = minVisited; pos <= maxVisited; pos++) {
            sb.append(tape.getOrDefault(pos, 0));
        }
        return sb.toString();
    }

    // Getters for testing/debugging
    public int getCurrentState() {
        return currentState;
    }

    public int getHeadPosition() {
        return headPosition;
    }
}
