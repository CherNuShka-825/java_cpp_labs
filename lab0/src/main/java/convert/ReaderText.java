package convert;

import java.io.*;
import java.util.*;


public class ReaderText {
    private Reader reader;
    private Set<WordRate> set;
    private int totalWords;

    public ReaderText(Reader reader) {
        this.reader = reader;
        this.set = new HashSet<>();
    }

    private void processWord(StringBuilder str) {
        if (str.isEmpty()) {
            return;
        }

        totalWords++;

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

    public Set<WordRate> read() throws IOException {
        fillSet();

        for (WordRate word : set) {
            word.setPercent((double) word.getRate() / totalWords);
        }
        return set;
    }
}
