package main;

import java.io.*;
import java.util.*;


public class ReaderToSet {
    private Reader reader;
    private Set<WordRate> set;

    public ReaderToSet(Reader reader) {
        this.reader = reader;
        this.set = new HashSet<WordRate>();
    }

    private void processWord(StringBuilder str) {
        if (str.isEmpty()) {
            return;
        }

        WordRate word = new WordRate(str.toString());

        if (this.set.contains(word)) {
            for (WordRate w : this.set) {
                if (w.equals(word)) {
                    w.increment();
                }
            }
        } else {
            this.set.add(word);
        }

        str.setLength(0);
    }

    private void fillSet() throws IOException{
        int ch;
        StringBuilder str = new StringBuilder();
        while ((ch = reader.read()) != -1) {
            if (Character.isLetterOrDigit(ch)) {
                str.append((char) ch);
            } else {
                processWord(str);
            }
        }
        processWord(str);
    }

    public Set<WordRate> convert() throws IOException {
        fillSet();
        return set;
    }
}
