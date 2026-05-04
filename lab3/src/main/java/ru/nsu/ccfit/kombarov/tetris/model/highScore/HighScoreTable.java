package ru.nsu.ccfit.kombarov.tetris.model.highScore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighScoreTable {

    private static final int MAX_ENTRIES = 10;

    private static final String DEFAULT_PLAYER_NAME = "Player";
    private static final int MAX_PLAYER_NAME_LENGTH = 12;

    private static final char FILE_SEPARATOR = ';';

    private final List<HighScoreEntry> entries = new ArrayList<>();

    public void add(String playerName, int score) {
        String normalizedName = normalizePlayerName(playerName);

        HighScoreEntry oldEntry = findEntryByName(normalizedName);

        if (oldEntry != null) {
            updateExistingEntryIfBetter(oldEntry, normalizedName, score);
        } else {
            addNewEntry(normalizedName, score);
        }

        sortAndTrim();
    }

    public boolean isHighScore(int score) {
        if (entries.size() < MAX_ENTRIES) {
            return true;
        }

        return score > entries.getLast().getScore();
    }

    public List<HighScoreEntry> getEntries() {
        return List.copyOf(entries);
    }

    private void addNewEntry(String playerName, int score) {
        entries.add(new HighScoreEntry(playerName, score));
    }

    private void updateExistingEntryIfBetter(
            HighScoreEntry oldEntry,
            String playerName,
            int score
    ) {
        if (score <= oldEntry.getScore()) {
            return;
        }

        entries.remove(oldEntry);
        entries.add(new HighScoreEntry(playerName, score));
    }

    private HighScoreEntry findEntryByName(String playerName) {
        for (HighScoreEntry entry : entries) {
            if (entry.getPlayerName().equals(playerName)) {
                return entry;
            }
        }

        return null;
    }

    private void sortAndTrim() {
        entries.sort(
                Comparator.comparingInt(HighScoreEntry::getScore).reversed()
        );

        while (entries.size() > MAX_ENTRIES) {
            entries.removeLast();
        }
    }

    private String normalizePlayerName(String name) {
        String normalized = name.trim();

        normalized = normalized.replace('\n', ' ');
        normalized = normalized.replace('\r', ' ');
        normalized = normalized.replace(FILE_SEPARATOR, ' ');

        if (normalized.isBlank()) {
            return DEFAULT_PLAYER_NAME;
        }

        if (normalized.length() > MAX_PLAYER_NAME_LENGTH) {
            return normalized.substring(0, MAX_PLAYER_NAME_LENGTH);
        }

        return normalized;
    }
}