package tm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TMSimulator{

    //Development plan: This class will parse input file, 
    // Initialize the TM, run the simulation and print the 
    // tape as output


    //Initialize TM


    //Run sim


    //return tape as output
    public static void main(String[] args){
        //declare variables

        //initialize empty TM

        //parse file
        File file = new File(args[0]);
        try{
            Scanner scnr = new Scanner(file);

            //first line: number of states

            //second line: alphabet

            //lines 3 - (|Q|x|T| - 1): transitions
            // each state gets |T| transitions, starting with 0 
            // idr exactly how it works, but I think in each |T| grouping,
            // T1 will be reading a blank, T2 will be reading a 1, and so on
            // this can probably be accomplished with a while loop and some
            // if else statements that use modulo? 

            //last line (if it exists): starting string

            //so many thoughts

            scnr.close();

        }
        catch(FileNotFoundException e){

        }
    }
}