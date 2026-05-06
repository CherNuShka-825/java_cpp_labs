package ru.nsu.ccfit.kombarov.tetris.model.highScore;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HighScoreTableTest {

    @Test
    void addShouldAddEntry() {
        HighScoreTable table = new HighScoreTable();

        table.add("Alice", 100);

        List<HighScoreEntry> entries = table.getEntries();

        assertEquals(1, entries.size());
        assertEntry(entries.get(0), "Alice", 100);
    }

    @Test
    void addShouldSortEntriesByScoreDescending() {
        HighScoreTable table = new HighScoreTable();

        table.add("Alice", 100);
        table.add("Bob", 300);
        table.add("Charlie", 200);

        List<HighScoreEntry> entries = table.getEntries();

        assertEquals(3, entries.size());
        assertEntry(entries.get(0), "Bob", 300);
        assertEntry(entries.get(1), "Charlie", 200);
        assertEntry(entries.get(2), "Alice", 100);
    }

    @Test
    void addShouldKeepOnlyBestScoreForSameNormalizedName() {
        HighScoreTable table = new HighScoreTable();

        table.add("Alice", 300);
        table.add("Alice", 100);

        List<HighScoreEntry> entries = table.getEntries();

        assertEquals(1, entries.size());
        assertEntry(entries.get(0), "Alice", 300);

        table.add("Alice", 500);

        entries = table.getEntries();

        assertEquals(1, entries.size());
        assertEntry(entries.get(0), "Alice", 500);
    }

    @Test
    void addShouldNormalizePlayerName() {
        HighScoreTable table = new HighScoreTable();

        table.add("  Alice\nBob\r;Test  ", 100);

        List<HighScoreEntry> entries = table.getEntries();

        assertEquals(1, entries.size());
        assertEntry(entries.get(0), "Alice Bob  T", 100);
    }

    @Test
    void addShouldUseDefaultNameWhenPlayerNameIsBlankAfterTrim() {
        HighScoreTable table = new HighScoreTable();

        table.add("   ", 100);

        List<HighScoreEntry> entries = table.getEntries();

        assertEquals(1, entries.size());
        assertEntry(entries.get(0), "Player", 100);
    }

    @Test
    void addShouldTrimLongPlayerNameToMaxLength() {
        HighScoreTable table = new HighScoreTable();

        table.add("VeryLongPlayerName", 100);

        List<HighScoreEntry> entries = table.getEntries();

        assertEquals(1, entries.size());
        assertEntry(entries.get(0), "VeryLongPlay", 100);
    }

    @Test
    void addShouldKeepOnlyTenBestEntries() {
        HighScoreTable table = new HighScoreTable();

        table.add("P1", 10);
        table.add("P2", 20);
        table.add("P3", 30);
        table.add("P4", 40);
        table.add("P5", 50);
        table.add("P6", 60);
        table.add("P7", 70);
        table.add("P8", 80);
        table.add("P9", 90);
        table.add("P10", 100);
        table.add("P11", 110);

        List<HighScoreEntry> entries = table.getEntries();

        assertEquals(10, entries.size());

        assertEntry(entries.get(0), "P11", 110);
        assertEntry(entries.get(9), "P2", 20);

        assertFalse(containsEntry(entries, "P1", 10));
    }

    @Test
    void isHighScoreShouldReturnTrueWhenTableHasLessThanTenEntries() {
        HighScoreTable table = new HighScoreTable();

        table.add("Alice", 100);

        assertTrue(table.isHighScore(0));
    }

    @Test
    void isHighScoreShouldCompareWithWorstEntryWhenTableIsFull() {
        HighScoreTable table = createFullTable();

        assertFalse(table.isHighScore(10));
        assertTrue(table.isHighScore(11));
        assertTrue(table.isHighScore(20));
    }

    @Test
    void getEntriesShouldReturnImmutableCopy() {
        HighScoreTable table = new HighScoreTable();

        table.add("Alice", 100);

        List<HighScoreEntry> entries = table.getEntries();

        assertThrows(
                UnsupportedOperationException.class,
                () -> entries.add(new HighScoreEntry("Bob", 200))
        );

        assertEquals(1, table.getEntries().size());
        assertEntry(table.getEntries().get(0), "Alice", 100);
    }

    private static HighScoreTable createFullTable() {
        HighScoreTable table = new HighScoreTable();

        table.add("P1", 10);
        table.add("P2", 20);
        table.add("P3", 30);
        table.add("P4", 40);
        table.add("P5", 50);
        table.add("P6", 60);
        table.add("P7", 70);
        table.add("P8", 80);
        table.add("P9", 90);
        table.add("P10", 100);

        return table;
    }

    private static void assertEntry(
            HighScoreEntry entry,
            String expectedName,
            int expectedScore
    ) {
        assertEquals(expectedName, entry.getPlayerName());
        assertEquals(expectedScore, entry.getScore());
    }

    private static boolean containsEntry(
            List<HighScoreEntry> entries,
            String expectedName,
            int expectedScore
    ) {
        return entries.stream()
                .anyMatch(entry ->
                        entry.getPlayerName().equals(expectedName)
                                && entry.getScore() == expectedScore
                );
    }
}