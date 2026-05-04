package ru.nsu.ccfit.kombarov.tetris.model.highScore;

public class HighScoreEntry {

    private final String playerName;
    private final int score;

    public HighScoreEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }
}