****************
* Project 3: Turing Machine Simulator
* CS 361
* April 24, 2026
* Calvin Arnold, Audrey Fitchett
****************

OVERVIEW:
This program implements a Turing Machine (TM) simulator in Java. It simulates a bi-infinite TM by parsing an encoding file with states, alphabet, transitions, and input string, then running the simulation to produce the final tape contents as output.

INCLUDED FILES:
* tm/TMSimulator.java - main class that parses input, initializes TM, runs simulation, and prints output
* tm/TM.java - core TM class managing states, transitions, tape, and simulation logic
* tm/TMState.java - TM state class (extends State for OO principles)
* fa/ - retained from Project 2 for reference
* test/ - test files for TM (file0.txt, file2.txt, file5.txt)
* README.md - this file

COMPILING AND RUNNING:
From the top-level project directory, compile the TM classes with:
$ javac tm/*.java

To run the TM simulator:
$ java tm.TMSimulator <input_file>

The input file contains TM encoding as per project specs. Output is the visited tape cells as a string.

PROGRAM DESIGN AND IMPORTANT CONCEPTS:
The TM simulator is built around three main classes: TMSimulator, TM, and TMState.

TMSimulator handles file parsing: reads numStates, numSigma, transitions (in order), and input string, then initializes and runs the TM.

TM manages the automaton: uses a 3D array for transitions [state][symbol][nextState/write/move] for efficiency. Tape is a TreeMap<Integer, Integer> for bi-infinite support, tracking only visited positions. Simulation starts at state 0, head at 0, runs until halting state (numStates-1).

TMState extends State for OO design, though transitions are stored centrally in TM for performance.

Key decisions:
- Bi-infinite tape: TreeMap allows sparse storage, sorted for output.
- Transitions: 3D array for O(1) lookups, crucial for large simulations.
- Parsing: Computed state/symbol indices from line order to match spec.
- Efficiency: No unnecessary data structures; focused on speed for 5-min timeout.

TESTING:
Tested on provided files (file0.txt, file2.txt, file5.txt). Verified outputs match expected strings and lengths.

DISCUSSION:
Calvin: For Project 3, we transitioned from NFA to TM simulation. Key decisions included using a TreeMap for the bi-infinite tape to handle infinity efficiently without fixed arrays, and a 3D int array for transitions to ensure fast lookups during simulation. We expanded the development plan by focusing on efficiency: parsing transitions in the exact order specified (state-major, symbol-minor), initializing tape with input centered at position 0, and tracking min/max visited positions for output. During development, we debated OO design—keeping TMState simple since transitions are global, but retained it for grading. We also ensured deterministic simulation with no epsilon transitions, unlike NFA. Challenges included parsing the transition order correctly and handling blank inputs (ε). We referenced NFA code for state concepts but rebuilt for TM's tape-based model. Future improvements could include more robust error handling for malformed files.


Audrey: Things started out pretty nicely with this project, since a lot of the methods worked similarly to those in the DFA project. Being able to reference that code saved a lot of time, which was super helpful when we reached the methods specific to NFAs. Those required a lot of thought and a lot of patience. One of the biggest hurdles for me was trying to figure out the addTransition method. In the DFA project, all the transitions were kept in a map in the NFA class, but the this project recommended each NFAState object store its own transitions. And for some reason, I just had the hardest time wrapping my head around why we would need to do it one way versus the other and how to store the transitions in general. It took looking up how hashmaps work again and actually drawing out an NFA transition table to finally put two and two together. Also, not that this is an actual issue, but naming variables and methods was a challenge. Whenever I look back through the code, it's like "Oh geez, I did name it like that."

EXTRA CREDIT:
No extra credit was attempted.

SOURCES:
Project 2 NFA code for reference
Java TreeMap documentation
Project 3 specifications PDF 