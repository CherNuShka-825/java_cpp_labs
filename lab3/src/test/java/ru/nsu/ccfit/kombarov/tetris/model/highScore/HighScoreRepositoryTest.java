package ru.nsu.ccfit.kombarov.tetris.model.highScore;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HighScoreRepositoryTest {

    @TempDir
    Path tempDir;

    @Test
    void loadShouldReturnEmptyTableWhenFileDoesNotExist() {
        Path filePath = tempDir.resolve("missing").resolve("scores.txt");
        HighScoreRepository repository = new HighScoreRepository(filePath);

        HighScoreTable table = repository.load();

        assertTrue(table.getEntries().isEmpty());
    }

    @Test
    void loadShouldReadValidEntriesAndSkipMalformedLines() throws IOException {
        Path filePath = tempDir.resolve("scores.txt");

        Files.writeString(
                filePath,
                "Alice;100" + System.lineSeparator()
                        + "brokenLine" + System.lineSeparator()
                        + "Bob;200" + System.lineSeparator()
        );

        HighScoreRepository repository = new HighScoreRepository(filePath);

        HighScoreTable table = repository.load();

        List<HighScoreEntry> entries = table.getEntries();

        assertEquals(2, entries.size());
        assertContainsEntry(entries, "Alice", 100);
        assertContainsEntry(entries, "Bob", 200);
    }

    @Test
    void loadShouldThrowRuntimeExceptionWhenScoreIsInvalid() throws IOException {
        Path filePath = tempDir.resolve("scores.txt");

        Files.writeString(filePath, "Alice;notNumber");

        HighScoreRepository repository = new HighScoreRepository(filePath);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                repository::load
        );

        assertEquals("Failed to load high scores", exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals(NumberFormatException.class, exception.getCause().getClass());
    }

    @Test
    void loadShouldThrowRuntimeExceptionWhenPathIsDirectory() throws IOException {
        Path directoryPath = tempDir.resolve("scores-directory");
        Files.createDirectory(directoryPath);

        HighScoreRepository repository = new HighScoreRepository(directoryPath);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                repository::load
        );

        assertEquals("Failed to load high scores", exception.getMessage());
        assertNotNull(exception.getCause());
        assertInstanceOf(IOException.class, exception.getCause());
    }

    @Test
    void saveShouldCreateParentDirectoriesAndWriteEntries() throws IOException {
        Path filePath = tempDir
                .resolve("nested")
                .resolve("directory")
                .resolve("scores.txt");

        HighScoreTable table = new HighScoreTable();
        table.add("Alice", 100);
        table.add("Bob", 200);

        HighScoreRepository repository = new HighScoreRepository(filePath);

        repository.save(table);

        assertTrue(Files.exists(filePath));

        String content = Files.readString(filePath);

        assertTrue(content.contains("Alice;100"));
        assertTrue(content.contains("Bob;200"));
    }

    @Test
    void saveAndLoadShouldPreserveEntries() {
        Path filePath = tempDir.resolve("scores.txt");

        HighScoreTable originalTable = new HighScoreTable();
        originalTable.add("Alice", 100);
        originalTable.add("Bob", 200);

        HighScoreRepository repository = new HighScoreRepository(filePath);

        repository.save(originalTable);
        HighScoreTable loadedTable = repository.load();

        List<HighScoreEntry> entries = loadedTable.getEntries();

        assertEquals(2, entries.size());
        assertContainsEntry(entries, "Alice", 100);
        assertContainsEntry(entries, "Bob", 200);
    }

    @Test
    void saveShouldThrowRuntimeExceptionWhenFilePathIsDirectory() throws IOException {
        Path directoryPath = tempDir.resolve("scores-directory");
        Files.createDirectory(directoryPath);

        HighScoreTable table = new HighScoreTable();
        table.add("Alice", 100);

        HighScoreRepository repository = new HighScoreRepository(directoryPath);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> repository.save(table)
        );

        assertEquals("Failed to save high scores", exception.getMessage());
        assertNotNull(exception.getCause());
        assertInstanceOf(IOException.class, exception.getCause());
    }

    private static void assertContainsEntry(
            List<HighScoreEntry> entries,
            String expectedName,
            int expectedScore
    ) {
        boolean contains = entries.stream()
                .anyMatch(entry ->
                        entry.getPlayerName().equals(expectedName)
                                && entry.getScore() == expectedScore
                );

        assertTrue(contains);
    }
}