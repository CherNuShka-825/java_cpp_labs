package ru.nsu.ccfit.kombarov.tetris.view.screen.menu;

import javafx.scene.Scene;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;

public class MainMenuScreen {

    private final MainMenuLayout layout;
    private final Scene scene;

    private Runnable onNewGame;
    private Runnable onHighScores;
    private Runnable onAbout;
    private Runnable onExit;

    public MainMenuScreen(Theme theme, ViewConfig config) {
        this.layout = new MainMenuLayout(theme, config);
        this.scene = new Scene(
                layout,
                config.getWindowWidth(),
                config.getWindowHeight()
        );

        layout.getNewGameButton().setOnAction(event -> {
            if (onNewGame != null) {
                onNewGame.run();
            }
        });

        layout.getHighScoresButton().setOnAction(event -> {
            if (onHighScores != null) {
                onHighScores.run();
            }
        });

        layout.getAboutButton().setOnAction(event -> {
            if (onAbout != null) {
                onAbout.run();
            }
        });

        layout.getExitButton().setOnAction(event -> {
            if (onExit != null) {
                onExit.run();
            }
        });
    }

    public void setOnNewGame(Runnable onNewGame) {
        this.onNewGame = onNewGame;
    }

    public void setOnHighScores(Runnable onHighScores) {
        this.onHighScores = onHighScores;
    }

    public void setOnAbout(Runnable onAbout) {
        this.onAbout = onAbout;
    }

    public void setOnExit(Runnable onExit) {
        this.onExit = onExit;
    }

    public Scene getScene() {
        return scene;
    }
}