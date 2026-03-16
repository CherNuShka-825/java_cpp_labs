package main;

import java.io.*;
import java.util.*;

public class WriterCSV {

    private Writer writer;

    public WriterCSV(Writer writer) {
        this.writer = writer;
    }

    public void fillOut(Set<WordRate> set) throws IOException {
        List<WordRate> list = new ArrayList<>(set);
        list.sort(Comparator.comparing(WordRate::getRate));

        int sum = 0;
        for (WordRate word : list) {
            sum += word.getRate();
        }

        for (WordRate word : list) {
            double proc = (double) word.getRate() / sum;
            writer.write(word.getWord() + ";" + word.getRate() + ";" + String.format(Locale.US, "%.4f", proc));
            writer.write("\n");
        }
    }

}
