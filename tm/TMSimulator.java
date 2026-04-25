import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TMSimulator {

    // Development plan: Parse input file, initialize TM, run simulation, print tape output

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java TMSimulator <input_file>");
            return;
        }

        String filename = args[0];
        try {
            Scanner scnr = new Scanner(new File(filename));

            // First line: number of states
            int numStates = Integer.parseInt(scnr.nextLine().trim());

            // Second line: number of symbols in Σ (m)
            int numSigma = Integer.parseInt(scnr.nextLine().trim());

            // Create TM
            TM tm = new TM(numStates, numSigma);

            // Next |Γ| * (numStates - 1) lines: transitions
            // |Γ| = numSigma + 1 (0 for blank, 1 to numSigma)
            int numTransitions = (numSigma + 1) * (numStates - 1);
            for (int i = 0; i < numTransitions; i++) {
                String line = scnr.nextLine().trim();
                String[] parts = line.split(",");
                int nextState = Integer.parseInt(parts[0]);
                int writeSymbol = Integer.parseInt(parts[1]);
                int move = parts[2].equals("L") ? 0 : 1; // 0=L, 1=R

                // Compute state and symbol from index
                int state = i / (numSigma + 1);
                int symbol = i % (numSigma + 1);

                tm.addTransition(state, symbol, nextState, writeSymbol, move);
            }

            // Last line: input string (or blank for ε)
            String input = "";
            if (scnr.hasNextLine()) {
                input = scnr.nextLine().trim();
            }

            // Initialize tape with input
            tm.initializeTape(input);

            // Run the TM
            tm.runTM();

            // Print the tape output
            System.out.println(tm.getTapeOutput());

            scnr.close();

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        } catch (Exception e) {
            System.err.println("Error parsing file: " + e.getMessage());
        }
    }
}