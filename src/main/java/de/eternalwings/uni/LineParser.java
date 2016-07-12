package de.eternalwings.uni;

import java.util.*;

public class LineParser {
    private final LineReader reader;
    private final Object rangesLock = new Object();
    private final Queue<JIPRange> ranges = new LinkedList<>();
    private boolean done = false;

    public LineParser(LineReader reader) {
        this.reader = reader;
    }

    public void run() {
        while(!reader.isDone() || this.reader.hasLines()) {
            String line = this.reader.getNextLine();
            if(line == null) {
                continue;
            }

            String[] split = line.split(",");
            JIPRange range = new JIPRange(split[2], intToIP(Long.valueOf(split[1])), intToIP(Long.valueOf(split[0])));
            synchronized (rangesLock) {
                if(!ranges.offer(range)) {
                    System.out.println("Cannot offer!");
                }
            }
        }

        done = true;
    }

    public boolean isDone() {
        return this.done;
    }

    public boolean hasRanges() {
        return !this.ranges.isEmpty();
    }

    public JIPRange getNextRange() {
        synchronized (this.rangesLock) {
            return this.ranges.poll();
        }
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
