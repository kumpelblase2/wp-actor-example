package de.eternalwings.uni;

import java.io.*;
import java.util.*;

public class Slow {
    private static class IPRange {
        final String start;
        final String end;
        final String timezone;

        IPRange(String timezone, String end, String start) {
            this.timezone = timezone;
            this.end = end;
            this.start = start;
        }

        public String getFirst() {
            return start.split("\\.")[0];
        }
    }


    private static final String FILENAME = "./src/main/resources/i2t.csv";

    public static void main(String[] args) throws IOException {
        // Reader for file
        BufferedReader reader = new BufferedReader(new FileReader(new File(FILENAME)));
        String line;

        // Some data that we want to get out of all data
        Map<String, Integer> occurrences = new HashMap<>();

        // Go through all the lines of the file
        while((line = reader.readLine()) != null) {

            // "Parse" them into a nicer format
            String[] split = line.split(",");
            IPRange range = new IPRange(split[2], intToIP(Long.valueOf(split[1])), intToIP(Long.valueOf(split[0])));

            // Do some work with it
            String firstPart = range.getFirst();
            occurrences.put(firstPart, occurrences.getOrDefault(firstPart, 0) + 1);
        }

        // Do something with the data
        System.out.println(occurrences.toString());
    }









































    static String intToIP(long ip) {
        List<String> parts = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            parts.add("" + ((ip >> i * 8) % 256));
        }
        Collections.reverse(parts);
        return String.join(".", parts);
    }
}
