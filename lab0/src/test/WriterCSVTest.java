package test;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

import main.WriterCSV;
import main.WordRate;

class WriterCSVTest {

    private String write(Set<WordRate> set) throws IOException {
        StringWriter writer = new StringWriter();
        WriterCSV csv = new WriterCSV(writer);
        csv.fillOut(set);
        return writer.toString();
    }

    private WordRate make(String word, int count) {
        WordRate w = new WordRate(word);
        for (int i = 1; i < count; i++) {
            w.increment();
        }
        return w;
    }

    @Test
    void shouldWriteSingleWord() throws IOException {
        Set<WordRate> set = new HashSet<>();
        set.add(make("hello", 2));

        String result = write(set);

        assertEquals("hello;2;1.0000\n", result);
    }

    @Test
    void shouldCalculatePercentagesCorrectly() throws IOException {
        Set<WordRate> set = new HashSet<>();
        set.add(make("a", 1));
        set.add(make("b", 3));

        String result = write(set);

        assertTrue(result.contains("a;1;0.2500"));
        assertTrue(result.contains("b;3;0.7500"));
    }

    @Test
    void shouldWriteSeveralWords() throws IOException {
        Set<WordRate> set = new HashSet<>();
        set.add(make("cat", 2));
        set.add(make("dog", 1));

        String result = write(set);

        assertTrue(result.contains("cat;2;"));
        assertTrue(result.contains("dog;1;"));
    }

    @Test
    void shouldFormatToFourDecimals() throws IOException {
        Set<WordRate> set = new HashSet<>();
        set.add(make("a", 1));
        set.add(make("b", 2));

        String result = write(set);

        assertTrue(result.contains("0.3333"));
        assertTrue(result.contains("0.6667"));
    }

    @Test
    void shouldWriteEmptyWhenSetEmpty() throws IOException {
        Set<WordRate> set = new HashSet<>();

        String result = write(set);

        assertEquals("", result);
    }
}