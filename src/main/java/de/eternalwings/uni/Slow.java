package de.eternalwings.uni;

import de.eteranalwings.uni.IPRange;
import scala.math.BigInt;

import java.io.*;
import java.util.Stack;

public class Slow {
    private static final String FILENAME = "./src/main/resources/i2t.csv";

    public static void main(String[] args) throws IOException {
        Stack<IPRange> ranges = new Stack<>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(FILENAME)));
        String line;
        while((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            IPRange range = IPRange.apply(BigInt.apply(split[0]), BigInt.apply(split[1]), split[2]);
            ranges.push(range);
        }

        System.out.println(ranges.size());
    }
}
