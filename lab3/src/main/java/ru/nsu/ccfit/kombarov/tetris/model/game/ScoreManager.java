package ru.nsu.ccfit.kombarov.tetris.model.game;

import java.util.logging.Logger;

public class ScoreManager {

    private static final Logger logger = Logger.getLogger(ScoreManager.class.getName());

    private int score;
    private int level;
    private int clearedLines;

    public void reset() {
        score = 0;
        level = 1;
        clearedLines = 0;
        logger.fine("reset");
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
        logger.fine("add Cleared Lines");
    }

    public int getScore() {
        logger.fine("get Score");
        return score;
    }

    public int getLevel() {
        logger.fine("get Level");
        return level;
    }

    public int getClearedLines() {
        logger.fine("get Cleared Lines");
        return clearedLines;
    }
}