package de.eternalwings.uni;

import java.io.*;
import java.util.*;

public class Slow {

    private static final String FILENAME = "./src/main/resources/i2t.csv";

    public static void main(String[] args) throws IOException, InterruptedException {
        LineReader reader = new LineReader(FILENAME);
        LineParser parser = new LineParser(reader);
        Analyzer analyzer = new Analyzer(parser);

        Thread readerThread = new Thread(reader::run); // Thread for reading lines
        readerThread.start();
        Thread parserThread = new Thread(parser::run); // Thread for parsing lines
        parserThread.start();
        Thread analyzerThread = new Thread(analyzer::run); // Thread for calculating something with parsed lines
        analyzerThread.start();
        analyzerThread.join(); // Wait for the last thread to finish
    }
}
