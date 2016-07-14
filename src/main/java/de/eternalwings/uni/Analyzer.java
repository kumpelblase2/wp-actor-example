package de.eternalwings.uni;

import java.util.HashMap;
import java.util.Map;

public class Analyzer {
    private final LineParser parser;
    private Map<String, Integer> occurrences = new HashMap<>();

    public Analyzer(LineParser parser) {
        this.parser = parser;
    }

    public void run() {
        while(!parser.isDone() || parser.hasRanges()) {
            JIPRange range = this.parser.getNextRange();
            if(range == null) {
                continue;
            }

            String firstPart = range.getFirst();
            occurrences.put(firstPart, occurrences.getOrDefault(firstPart, 0) + 1);
        }

        // Do something with the data
        System.out.println(occurrences.toString());
    }
}
