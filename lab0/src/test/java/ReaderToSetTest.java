import convert.ReaderText;
import convert.WordRate;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTextTest {

    private Set<WordRate> read(String text) throws IOException {
        Reader reader = new StringReader(text);
        ReaderText readerText = new ReaderText(reader);
        return readerText.read();
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
        Set<WordRate> result = read("");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReadSingleWord() throws IOException {
        Set<WordRate> result = read("hello");

        assertEquals(1, result.size());

        WordRate word = findWord(result, "hello");
        assertNotNull(word);
        assertEquals(1, word.getRate());
        assertEquals(1.0, word.getPercent(), 1e-9);
    }

    @Test
    void shouldCountRepeatedWord() throws IOException {
        Set<WordRate> result = read("hello hello hello");

        assertEquals(1, result.size());

        WordRate word = findWord(result, "hello");
        assertNotNull(word);
        assertEquals(3, word.getRate());
        assertEquals(1.0, word.getPercent(), 1e-9);
    }

    @Test
    void shouldReadSeveralDifferentWords() throws IOException {
        Set<WordRate> result = read("cat dog mouse");

        assertEquals(3, result.size());

        WordRate cat = findWord(result, "cat");
        WordRate dog = findWord(result, "dog");
        WordRate mouse = findWord(result, "mouse");

        assertNotNull(cat);
        assertNotNull(dog);
        assertNotNull(mouse);

        assertEquals(1, cat.getRate());
        assertEquals(1, dog.getRate());
        assertEquals(1, mouse.getRate());

        assertEquals(1.0 / 3.0, cat.getPercent(), 1e-9);
        assertEquals(1.0 / 3.0, dog.getPercent(), 1e-9);
        assertEquals(1.0 / 3.0, mouse.getPercent(), 1e-9);
    }

    @Test
    void shouldTreatLettersAndDigitsAsPartOfWord() throws IOException {
        Set<WordRate> result = read("abc123 abc123 test1");

        assertEquals(2, result.size());

        WordRate abc123 = findWord(result, "abc123");
        WordRate test1 = findWord(result, "test1");

        assertNotNull(abc123);
        assertNotNull(test1);

        assertEquals(2, abc123.getRate());
        assertEquals(1, test1.getRate());

        assertEquals(2.0 / 3.0, abc123.getPercent(), 1e-9);
        assertEquals(1.0 / 3.0, test1.getPercent(), 1e-9);
    }
}