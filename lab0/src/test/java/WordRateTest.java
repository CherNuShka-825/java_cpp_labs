import convert.WordRate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WordRateTest {

    @Test
    void equals_SameWord_ShouldReturnTrue() {
        WordRate w1 = new WordRate("test");
        WordRate w2 = new WordRate("test");

        assertTrue(w1.equals(w2));
    }

    @Test
    void equals_DifferentWord_ShouldReturnFalse() {
        WordRate w1 = new WordRate("test");
        WordRate w2 = new WordRate("other");

        assertFalse(w1.equals(w2));
    }

    @Test
    void equals_NullOrDifferentType_ShouldReturnFalse() {
        WordRate w = new WordRate("test");

        assertFalse(w.equals(null));
        assertFalse(w.equals("test"));
    }

    @Test
    void equals_ThisAndThis_ShouldReturnTrue() {
        WordRate w = new WordRate("test");

        assertTrue(w.equals(w));
    }
}
