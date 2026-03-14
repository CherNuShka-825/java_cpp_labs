package main;

import java.io.*;
import java.util.*;


public class ConverterToCSV {
    private Reader reader;
    private Writer writer;

    public ConverterToCSV(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    private void processWord(Set<WordRate> set, StringBuilder str) {
        if (str.isEmpty()) {
            return;
        }

        WordRate word = new WordRate(str.toString());

        if (set.contains(word)) {
            for (WordRate w : set) {
                if (w.equals(word)) {
                    w.ratePluseOdin();
                }
            }
        } else {
            set.add(word);
        }

        str.setLength(0);
    }

    private void fillSet(Set<WordRate> set) throws IOException{
        int ch;
        StringBuilder str = new StringBuilder();
        while ((ch = reader.read()) != -1) {
            if (Character.isLetterOrDigit(ch)) {
                str.append((char) ch);
            } else {
                processWord(set, str);
            }
        }
        processWord(set, str);
    }

    private void fillOut(List<WordRate> list) throws IOException {
        int sum = 0;
        for (WordRate word : list) {
            sum += word.getRate();
        }

        for (WordRate word : list) {
            double proc = (double) word.getRate() / sum;
            writer.write(word.getWord() + ";" + word.getRate() + ";" + String.format("%.4f", proc));
            writer.write("\n");
        }
    }

    public void convert() throws IOException {
        Set<WordRate> set = new HashSet<>();
        fillSet(set);
        List<WordRate> list = new ArrayList<WordRate>(set);
        list.sort(Comparator.comparing(WordRate::getRate));
        fillOut(list);
    }
}
