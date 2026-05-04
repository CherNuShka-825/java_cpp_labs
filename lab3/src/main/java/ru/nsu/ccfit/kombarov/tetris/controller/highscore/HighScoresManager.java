package ru.nsu.ccfit.kombarov.tetris.controller.highscore;

import ru.nsu.ccfit.kombarov.tetris.model.highScore.HighScoreRepository;
import ru.nsu.ccfit.kombarov.tetris.model.highScore.HighScoreTable;
import ru.nsu.ccfit.kombarov.tetris.view.screen.highScores.HighScoresScreen;

import java.nio.file.Path;

public class HighScoresManager {

    private final HighScoreRepository repository;
    private final HighScoreTable table;
    private final HighScoresScreen screen;

    public HighScoresManager(Path path, HighScoresScreen screen) {
        this.repository = new HighScoreRepository(path);
        this.table = repository.load();
        this.screen = screen;

        screen.refresh(table);
    }

    public boolean isHighScore(int score) {
        return table.isHighScore(score);
    }

    public void addScore(String playerName, int score) {
        table.add(playerName, score);
        repository.save(table);
        screen.refresh(table);
    }
}