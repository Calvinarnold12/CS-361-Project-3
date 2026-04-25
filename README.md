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


Audrey: One thing I struggled with was keeping track of the implicit information in the file, especially when it came to testing and verifying that the output string matches
the expected output. While we went over those details in class, the order kept getting mixed up in my head. What helped was to explicitly write everything out in a table so that I could physically see what was going on. It was also difficult deciding on what to use to hold things like the tape and transitions. Since this was meant to be built for
speed, we couldn't just rely on the same structures that were used in the NFA and DFA projects. For me, it was particularly hard to let go of the idea that a state is supposed to be an object. I don't have a good reason for why it should be one, especially since it made transitions more difficult. Thank goodness Calvin had the sense to frame the states as numbers, as then the transitions could be stored in a 3d array, which allows for quick access. 

EXTRA CREDIT:
No extra credit was attempted.

SOURCES:
Project 2 NFA code for reference
Java TreeMap documentation
Project 3 specifications PDF 