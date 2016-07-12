package de.eternalwings.uni;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class LineReader {
    private final Object linesLock = new Object();

    private final String file;
    private boolean done = false;
    private Queue<String> lines = new LinkedList<>();

    public LineReader(String file) {
        this.file = file;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(this.file)));
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

    public boolean isDone() {
        return this.done;
    }

    public String getNextLine() {
        synchronized (linesLock) {
            return lines.poll();
        }
    }

    public boolean hasLines() {
        return !this.lines.isEmpty();
    }
}
