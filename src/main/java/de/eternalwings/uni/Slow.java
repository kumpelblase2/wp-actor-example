package de.eternalwings.uni;

import scala.math.BigInt;

import java.io.*;
import java.util.Stack;

public class Slow {
    private static final String FILENAME = "./src/main/resources/i2t.csv";

    public static void main(String[] args) throws IOException {
        // Reader for file
        BufferedReader reader = new BufferedReader(new FileReader(new File(FILENAME)));

        // Stack for data
        Stack<IPRange> ranges = new Stack<>();
        String line;

        // Go through all the lines of the file
        while((line = reader.readLine()) != null) {

            // "Parse" them into a nicer format
            String[] split = line.split(",");
            IPRange range = IPRange.apply(BigInt.apply(split[0]), BigInt.apply(split[1]), split[2]);

            // Push them on the stack
            ranges.push(range);
        }

        // Do something with the data
        System.out.println(ranges.size());
    }
}
