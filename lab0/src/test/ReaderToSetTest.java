package test;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

import main.WordRate;
import main.ReaderToSet;

class ReaderToSetTest {

    private Set<WordRate> convert(String text) throws IOException {
        Reader reader = new StringReader(text);
        ReaderToSet converter = new ReaderToSet(reader);
        return converter.convert();
    }

    private WordRate findWord(Set<WordRate> set, String word) {
        for (WordRate w : set) {
            if (w.getWord().equals(word)) {
                return w;
            }
        }
        return null;
    }

    @Test
    void shouldReturnEmptySetForEmptyInput() throws IOException {
        Set<WordRate> result = convert("");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReadSingleWord() throws IOException {
        Set<WordRate> result = convert("hello");

        assertEquals(1, result.size());

        WordRate word = findWord(result, "hello");
        assertNotNull(word);
        assertEquals(1, word.getRate());
    }

    @Test
    void shouldCountRepeatedWord() throws IOException {
        Set<WordRate> result = convert("hello hello hello");

        assertEquals(1, result.size());

        WordRate word = findWord(result, "hello");
        assertNotNull(word);
        assertEquals(3, word.getRate());
    }

    @Test
    void shouldReadSeveralDifferentWords() throws IOException {
        Set<WordRate> result = convert("cat dog mouse");

        assertEquals(3, result.size());

        assertEquals(1, findWord(result, "cat").getRate());
        assertEquals(1, findWord(result, "dog").getRate());
        assertEquals(1, findWord(result, "mouse").getRate());
    }

    @Test
    void shouldHandleDifferentSeparators() throws IOException {
        Set<WordRate> result = convert("one,two.three\nfour\tfive");

        assertEquals(5, result.size());

        assertNotNull(findWord(result, "one"));
        assertNotNull(findWord(result, "two"));
        assertNotNull(findWord(result, "three"));
        assertNotNull(findWord(result, "four"));
        assertNotNull(findWord(result, "five"));
    }

    @Test
    void shouldProcessLastWordWithoutSeparator() throws IOException {
        Set<WordRate> result = convert("abc def");

        assertEquals(2, result.size());
        assertNotNull(findWord(result, "abc"));
        assertNotNull(findWord(result, "def"));
    }

    @Test
    void shouldTreatLettersAndDigitsAsPartOfWord() throws IOException {
        Set<WordRate> result = convert("abc123 abc123 test1");

        assertEquals(2, result.size());

        assertEquals(2, findWord(result, "abc123").getRate());
        assertEquals(1, findWord(result, "test1").getRate());
    }
}