package ru.nsu.ccfit.kombarov.tetris.controller.screen;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.ccfit.kombarov.tetris.config.AppConfig;
import ru.nsu.ccfit.kombarov.tetris.view.screen.about.AboutScreen;
import ru.nsu.ccfit.kombarov.tetris.view.screen.game.GameScreen;
import ru.nsu.ccfit.kombarov.tetris.view.screen.gameover.GameOverScreen;
import ru.nsu.ccfit.kombarov.tetris.view.screen.highScores.HighScoresScreen;
import ru.nsu.ccfit.kombarov.tetris.view.screen.menu.MainMenuScreen;
import ru.nsu.ccfit.kombarov.tetris.view.screen.pause.PauseMenuScreen;

public class SceneManager {

    private final Stage stage;

    private final MainMenuScreen menuScreen;
    private final GameScreen gameScreen;
    private final HighScoresScreen highScoresScreen;
    private final AboutScreen aboutScreen;
    private final GameOverScreen gameOverScreen;
    private final PauseMenuScreen pauseMenuScreen;

    public SceneManager(
            Stage stage,
            MainMenuScreen menuScreen,
            GameScreen gameScreen,
            HighScoresScreen highScoresScreen,
            AboutScreen aboutScreen,
            GameOverScreen gameOverScreen,
            PauseMenuScreen pauseMenuScreen
    ) {
        this.stage = stage;
        this.menuScreen = menuScreen;
        this.gameScreen = gameScreen;
        this.highScoresScreen = highScoresScreen;
        this.aboutScreen = aboutScreen;
        this.gameOverScreen = gameOverScreen;
        this.pauseMenuScreen = pauseMenuScreen;

        stage.setTitle(AppConfig.WINDOW_TITLE);
    }

    public Scene getGameScene() {
        return gameScreen.getScene();
    }

    public void showMenu() {
        show(menuScreen.getScene());
    }

    public void showGame() {
        show(gameScreen.getScene());
        gameScreen.requestFocus();
    }

    public void showHighScores() {
        show(highScoresScreen.getScene());
    }

    public void showAbout() {
        show(aboutScreen.getScene());
    }

    public void showGameOver() {
        show(gameOverScreen.getScene());
    }

    public void showPauseMenu() {
        show(pauseMenuScreen.getScene());
    }

    private void show(Scene scene) {
        stage.setScene(scene);
        stage.show();
        Platform.runLater(stage::centerOnScreen);
    }
}