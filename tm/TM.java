package tm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class TM {
    //Development plan: Use NFA as inspiration, 
    //Include logic to handle state management,transitions,tape and simulation logic.

    //Declare variables
    private Set<TMState> states;
    private Set<Character> sigma;
    private TMState startState;
    private TMState acceptState; //pretty sure there will only be one accept state?
    private ArrayList<Character> tape; //not sure if this is the right choice for tape
    private int head; // for head location on tape
    private int size; // tape size, if needed 

    public TM(){
        states = new LinkedHashSet<>();
        sigma = new LinkedHashSet<>();
        startState = null;
        acceptState = null;
        tape = new ArrayList<>();
        head = 0; //need to check if head always starts at the beginning of tape - pretty sure it does
        size = 0;
    }

    public void addState(TMState state){
        //TODO;

        //check if state is already in there - possibly a waste of time? 

        //add state to states
    }

    public void addStart(TMState state){
        //TODO;

        //check if state is a part of states - could add it if it isn't - kill two birds with one stone?
        // set startstate to state
    }

    public void acceptState(TMState state){
        //TODO;
        //check if state is a part of states - could add it if it isn't - kill two birds with one stone?
        // set startstate to state
    }

    public void addSigma(char c){
        //TODO;
    }

    public void readWrite(TMState state){
        //TODO;

        // read char from tape
        // find transition in current state
        // replace char from tape with write char in current transition
        // move right or left 
        // go to next state
    }

    public void runTM(){
        //TODO;
        // maybe something like while currentState != acceptState
        // readWrite(currentState)? 
    }

    public int getSigmaSize(){
        return sigma.size();
    }

    /** Possible functions:
     *  TM constructor (of course)
     *  addState
     *  acceptState (Will the final state always be the accept state?)
     *  start state (will always be the first state)
     *  addsigma
     *  addTransition
     *  readWrite
     *  nextState
     *  runTM
     */


}
