package ru.nsu.ccfit.kombarov.tetris.controller.gameover;

import ru.nsu.ccfit.kombarov.tetris.controller.highscore.HighScoresManager;
import ru.nsu.ccfit.kombarov.tetris.controller.screen.SceneManager;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameModel;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameState;
import ru.nsu.ccfit.kombarov.tetris.view.screen.gameover.GameOverScreen;

public class GameOverHandler {

    private final GameModel model;
    private final HighScoresManager highScoresManager;
    private final GameOverScreen gameOverScreen;
    private final SceneManager sceneManager;
    private final Runnable onGameSessionEnd;

    private boolean handled;

    public GameOverHandler(
            GameModel model,
            HighScoresManager highScoresManager,
            GameOverScreen gameOverScreen,
            SceneManager sceneManager,
            Runnable onGameSessionEnd
    ) {
        this.model = model;
        this.highScoresManager = highScoresManager;
        this.gameOverScreen = gameOverScreen;
        this.sceneManager = sceneManager;
        this.onGameSessionEnd = onGameSessionEnd;
    }

    public void check() {
        if (model.getState() != GameState.GAME_OVER) {
            handled = false;
            return;
        }

        if (handled) {
            return;
        }

        handled = true;

        finishGameSession();
    }

    public void exitToMenuWithHighScoreCheck() {
        finishGameSession();
    }

    private void finishGameSession() {
        onGameSessionEnd.run();

        int score = model.getScore();

        if (highScoresManager.isHighScore(score)) {
            gameOverScreen.prepare(score);
            sceneManager.showGameOver();
            return;
        }

        sceneManager.showMenu();
    }
}