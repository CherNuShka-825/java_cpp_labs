import convert.WordRate;
import convert.WriterCSV;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WriterCSVTest {

    private String write(Set<WordRate> set) throws IOException {
        StringWriter writer = new StringWriter();
        WriterCSV csv = new WriterCSV(writer);
        csv.fillOut(set);
        return writer.toString();
    }

    private WordRate make(String word, int count, double percent) {
        WordRate w = new WordRate(word);
        for (int i = 1; i < count; i++) {
            w.increment();
        }
        w.setPercent(percent);
        return w;
    }

    @Test
    void shouldWriteSingleWord() throws IOException {
        Set<WordRate> set = new HashSet<>();
        set.add(make("hello", 2, 1.0));

        String result = write(set);

        assertEquals("hello;2;1.0000\n", result);
    }

    @Test
    void shouldWriteSeveralWords() throws IOException {
        Set<WordRate> set = new HashSet<>();
        set.add(make("cat", 2, 0.6667));
        set.add(make("dog", 1, 0.3333));

        String result = write(set);

        assertTrue(result.contains("cat;2;0.6667\n"));
        assertTrue(result.contains("dog;1;0.3333\n"));
    }

    @Test
    void shouldWriteEmptyWhenSetEmpty() throws IOException {
        Set<WordRate> set = new HashSet<>();

        String result = write(set);

        assertEquals("", result);
    }

    @Test
    void shouldSortByRate() throws IOException {
        Set<WordRate> set = new HashSet<>();
        set.add(make("big", 3, 0.6));
        set.add(make("small", 1, 0.2));
        set.add(make("mid", 2, 0.4));

        String result = write(set);

        String expected =
                "small;1;0.2000\n" +
                "mid;2;0.4000\n" +
                "big;3;0.6000\n";

        assertEquals(expected, result);
    }
}