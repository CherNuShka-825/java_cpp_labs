package main;

import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        try (
                Reader reader = new BufferedReader(new FileReader("src/in.txt"));
                Writer writer = new BufferedWriter(new FileWriter("src/out.txt"))
        ) {

            ReaderToSet converter = new ReaderToSet(reader);
            Set<WordRate> set = converter.convert();
            WriterCSV writeOut = new WriterCSV(writer);
            writeOut.fillOut(set);

        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        }
    }
}
