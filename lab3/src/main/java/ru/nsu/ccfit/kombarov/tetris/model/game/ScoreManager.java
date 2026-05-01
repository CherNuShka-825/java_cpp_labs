package ru.nsu.ccfit.kombarov.tetris.model.game;

public class ScoreManager {
    private int score;
    private int level;
    private int clearedLines;

    public void reset() {
        score = 0;
        level = 1;
        clearedLines = 0;
    }

    public void addClearedLines(int lines) {
        if (lines == 0) {
            return;
        }

        clearedLines += lines;

        score += switch (lines) {
            case 1 -> 100 * level;
            case 2 -> 300 * level;
            case 3 -> 500 * level;
            case 4 -> 800 * level;
            default -> (800 + (lines - 4) * 100) * level;
        };

        level = clearedLines / 10 + 1;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public int getClearedLines() {
        return clearedLines;
    }
}