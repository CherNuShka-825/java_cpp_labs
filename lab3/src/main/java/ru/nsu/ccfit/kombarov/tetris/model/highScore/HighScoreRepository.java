package ru.nsu.ccfit.kombarov.tetris.model.highScore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HighScoreRepository {

    private static final String SEPARATOR = ";";
    private static final int EXPECTED_PARTS = 2;

    private final Path filePath;

    public HighScoreRepository(Path filePath) {
        this.filePath = filePath;
    }

    public HighScoreTable load() {
        HighScoreTable table = new HighScoreTable();

        if (!Files.exists(filePath)) {
            return table;
        }

        try {
            for (String line : Files.readAllLines(filePath)) {
                String[] parts = line.split(SEPARATOR, EXPECTED_PARTS);

                if (parts.length != EXPECTED_PARTS) {
                    continue;
                }

                String name = parts[0];
                int score = Integer.parseInt(parts[1]);

                table.add(name, score);
            }

            return table;

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Failed to load high scores", e);
        }
    }

    public void save(HighScoreTable table) {
        try {
            createParentDirectoryIfNeeded();

            StringBuilder builder = new StringBuilder();

            for (HighScoreEntry entry : table.getEntries()) {
                builder.append(entry.getPlayerName())
                        .append(SEPARATOR)
                        .append(entry.getScore())
                        .append(System.lineSeparator());
            }

            Files.writeString(filePath, builder.toString());

        } catch (IOException e) {
            throw new RuntimeException("Failed to save high scores", e);
        }
    }

    private void createParentDirectoryIfNeeded() throws IOException {
        Path parent = filePath.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }
    }
}