package de.eternalwings.uni;

import java.io.*;
import java.util.*;

public class Slow {
    private static class IPRange { // Structure for holding the IP data
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

    private final Queue<String> lines = new LinkedList<>(); // Queue for lines that were read and need to be processed
    private final Object linesLock = new Object(); // Mutex for access to the lines
    private final Queue<IPRange> ranges = new LinkedList<>(); // Queue
    private final Object rangesLock = new Object();
    private String filename;
    private boolean done = false;
    private boolean done2 = false;

    public static void main(String[] args) throws IOException, InterruptedException {
        Slow s = new Slow();
        s.filename = FILENAME;
        Thread reader = new Thread(s::readLines); // Thread for reading lines
        reader.start();
        Thread parser = new Thread(s::parseLines); // Thread for parsing lines
        parser.start();
        Thread calculator = new Thread(s::calculate); // Thread for calculating something with parsed lines
        calculator.start();
        calculator.join(); // Wait for the last thread to finish
    }

    private void readLines() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            String line;
            // Go through all the lines of the file
            while((line = reader.readLine()) != null) {
                synchronized (linesLock) {
                    lines.offer(line);
                }
            }
        } catch(IOException ignored) {
            System.out.println("Cannot read file");
        }

        done = true;
    }

    private void parseLines() {
        while(!done || !lines.isEmpty()) {
            String line;
            synchronized (linesLock) {
                line = lines.poll();
                if(line == null) {
                    continue;
                }
            }

            String[] split = line.split(",");
            IPRange range = new IPRange(split[2], intToIP(Long.valueOf(split[1])), intToIP(Long.valueOf(split[0])));
            synchronized (rangesLock) {
                if(!ranges.offer(range)) {
                    System.out.println("Cannot offer!");
                }
            }
        }

        done2 = true;
    }

    private void calculate() {
        // Some data that we want to get out of all data
        Map<String, Integer> occurrences = new HashMap<>();

        while(!done2 || !ranges.isEmpty()) {
            IPRange range;
            synchronized (rangesLock) {
                range = ranges.poll();
                if(range == null) {
                    continue;
                }

                String firstPart = range.getFirst();
                occurrences.put(firstPart, occurrences.getOrDefault(firstPart, 0) + 1);
            }
        }

        // Do something with the data
        System.out.println(occurrences.toString());
    }

    private static String intToIP(long ip) {
        List<String> parts = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            parts.add("" + ((ip >> i * 8) % 256));
        }
        Collections.reverse(parts);
        return String.join(".", parts);
    }
}
