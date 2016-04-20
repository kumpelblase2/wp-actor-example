package de.eternalwings.uni;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Slow {
    private static class IPRange {
        private final long start;
        private final long end;
        private final String timezone;

        IPRange(String timezone, long end, long start) {
            this.timezone = timezone;
            this.end = end;
            this.start = start;
        }

        public long getStart() {
            return start;
        }

        public long getEnd() {
            return end;
        }

        public String getTimezone() {
            return timezone;
        }
    }


    private static final String FILENAME = "./src/main/resources/i2t.csv";

    public static void main(String[] args) throws IOException {
        // Reader for file
        BufferedReader reader = new BufferedReader(new FileReader(new File(FILENAME)));
        String line;

        // Some data that we want to get out of all data
        long count = 0;

        // Go through all the lines of the file
        while((line = reader.readLine()) != null) {

            // "Parse" them into a nicer format
            String[] split = line.split(",");
            IPRange range = new IPRange(split[2], Long.valueOf(split[1]), Long.valueOf(split[0]));

            // Do some work with it
            count += Math.abs(range.start - range.end);
        }

        // Do something with the data
        System.out.println(count);
    }
}
