package ru.nsu.ccfit.kombarov.tetris.model.highScore;

import java.util.logging.Logger;

public class HighScoreEntry {

    private static final Logger logger = Logger.getLogger(HighScoreEntry.class.getName());

    private final String playerName;
    private final int score;

    public HighScoreEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
        logger.info("created High Score Entry");
    }

    public String getPlayerName() {
        logger.fine("get Player Name");
        return playerName;
    }

    public int getScore() {
        logger.fine("get Score");
        return score;
    }
}