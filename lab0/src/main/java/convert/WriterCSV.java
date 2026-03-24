package convert;

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

        for (WordRate word : list) {
            writer.write(word.getWord() + ";"
                    + word.getRate() + ";"
                    + String.format(Locale.US, "%.4f", word.getPercent()));
            writer.write("\n");
        }
    }
}
