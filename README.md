****************
* Project 2: Nondeterministic Finite Automata
* CS 361
* March 30, 2026
* Calvin Arnold, Audrey Fitchett
****************

OVERVIEW:
This program implements a nondeterministic finite automaton (NFA) in Java. It supports building an NFA by adding states, alphabet symbols, and transitions, then analyzing that NFA by computing epsilon-closures, testing whether it is also a DFA, checking whether it accepts an input string, and determining the maximum number of active NFA copies created while processing a string.

INCLUDED FILES:
* fa/nfa/NFA.java - source file containing the main NFA implementation
* fa/nfa/NFAState.java - source file containing the concrete NFA state implementation and per-state transition storage
* fa/nfa/NFAInterface.java - provided interface that defines required NFA behavior
* fa/FAInterface.java - provided interface describing common finite automaton operations
* fa/State.java - provided abstract class used as the base state type
* test/nfa/NFATest.java - provided JUnit test suite for validating the implementation
* README.md - this file

COMPILING AND RUNNING:
From the top-level project directory, compile the implementation classes with:
$ javac fa/nfa/NFA.java fa/nfa/NFAState.java

To compile the provided JUnit test class on onyx, use:
$ javac -cp .:/usr/share/java/junit.jar ./test/nfa/NFATest.java

To run the provided JUnit tests on onyx, use:
$ java -cp .:/usr/share/java/junit.jar:/usr/share/java/hamcrest/core.jar org.junit.runner.JUnitCore test.nfa.NFATest

The program does not require interactive console input. The JUnit output reports whether the NFA implementation passes the required tests.

PROGRAM DESIGN AND IMPORTANT CONCEPTS:
The program is organized around two main classes: NFA and NFAState. The NFA class is responsible for managing the overall automaton, including the set of states, the alphabet, the start state, the set of final states, and lookup of states by name. The NFAState class is responsible for storing outgoing transitions from a single state.

NFAState stores transitions in a map from Character to Set<NFAState>. This representation matches the definition of an NFA because one symbol can lead to multiple destination states. The addSubDelta method merges destinations for repeated transitions on the same symbol instead of overwriting earlier transitions.

For this project, we chose to associate transition data with each NFAState instead of storing the entire transition table centrally inside NFA. We made that choice because transition ownership felt more natural at the state level: each state is responsible for knowing where it can go on a given symbol. That made helper operations such as toStates and epsilon traversal more direct, since eClosure, accepts, and isDFA can ask a state for its outgoing transitions without constantly unpacking a separate global delta structure. A centralized transition map in NFA would also have worked, and it can make the whole automaton easier to inspect in one place, but for this implementation the state-owned approach kept the behavior closer to the data it uses.

The eClosure method in NFA computes epsilon-closure using depth-first search with an explicit stack in a loop, as required by the project specification. Starting from one state, it follows only epsilon transitions labeled with the reserved character 'e' and collects every reachable state, including the original state.

The accepts method simulates the NFA by tracking the current set of active states, which represent the current NFA copies. It begins with the epsilon-closure of the start state. For each input symbol, it gathers all destination states reachable from the current active states, then expands those states again through epsilon-closure. After the full string has been processed, the string is accepted if at least one active state is final.

The maxCopies method uses the same general simulation as accepts, but instead of only checking acceptance, it records the largest number of active states seen at any point in the computation. This corresponds to the maximum number of NFA copies alive during processing.

The isDFA method checks whether the implemented NFA also satisfies DFA rules. It returns false if any state has an epsilon transition or if any alphabet symbol leads to anything other than exactly one destination state.

This design keeps most automaton-wide behavior inside NFA and most per-state transition behavior inside NFAState. That separation makes the code easier to understand and update. One improvement would be to add even more automated tests for additional edge cases beyond the provided suite.

TESTING:
The primary testing strategy was to use the provided JUnit test suite in test/nfa/NFATest.java. Those tests cover several categories of behavior: correct state and transition construction, start and final state handling, DFA detection, epsilon-closure computation, acceptance of input strings, and maximum-copy counting.

The provided tests exercise three different NFAs and include both accepted and rejected strings. They also verify invalid operations such as duplicate states, missing states, and transitions using symbols that are not part of the alphabet.

The program handles several kinds of bad input defensively. For example, it rejects transitions when the source or destination state does not exist, rejects non-epsilon transition symbols that are not in the alphabet, rejects duplicate state names, and returns false if acceptance is attempted without a valid start state. Based on the provided test suite, no known functional bugs remain, although hidden tests may still reveal edge cases not covered by the supplied examples.

DISCUSSION:
One issue during development was making sure the implementation matched both the interfaces and the test expectations. In particular, the tests expect getState to return an NFAState object that can be used directly in methods such as eClosure and toStates, so the implementation uses a covariant return type.

Another important issue was handling epsilon transitions correctly. The reserved character 'e' is used to mark epsilon transitions and should not be treated like a regular alphabet symbol. That distinction affects both addTransition and the traversal logic in eClosure, accepts, and maxCopies.

It was also necessary to ensure that transitions on the same symbol were merged rather than overwritten. If transitions were overwritten, the automaton would lose valid nondeterministic branches and fail acceptance or maxCopies tests.

The most challenging parts of the project were the graph-based algorithms and making sure the simulation matched NFA behavior exactly. The design became much clearer once epsilon-closure and active-state tracking were treated as separate steps: first compute transitions on the current symbol, then expand through epsilon transitions.

Calvin: The hardest part for me was making sure the transition structure really behaved like an NFA instead of a DFA. With DFAs it is easy to think in terms of one current state, but here I had to think in terms of sets of active states and remember that epsilon transitions can expand those sets even when no input symbol is consumed. What finally clicked was treating the current configuration as a set of copies and updating that set in phases.

EXTRA CREDIT:
No extra credit was attempted.

SOURCES:
https://www.w3schools.com/java/java_hashmap.asp  
https://www.w3schools.com/java/java_hashset.asp 
Calvin: I needed to review these articles AGAIN. I wonder if there will ever be a time when I do not need to brush up on java hashsets and maps before using them. 