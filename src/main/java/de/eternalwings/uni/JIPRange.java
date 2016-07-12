package de.eternalwings.uni;

public class JIPRange {
    final String start;
    final String end;
    final String timezone;

    JIPRange(String timezone, String end, String start) {
        this.timezone = timezone;
        this.end = end;
        this.start = start;
    }

    public String getFirst() {
        return start.split("\\.")[0];
    }
}
