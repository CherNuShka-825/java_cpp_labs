package main;

import java.io.*;


public class Main {
    public static void main(String[] args) {
        try (
                Reader reader = new BufferedReader(new FileReader("src/in.txt"));
                Writer writer = new BufferedWriter(new FileWriter("src/out.txt"))
        ) {

            ConverterToCSV converter = new ConverterToCSV(reader, writer);
            converter.convert();

        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        }
    }
}
