package ru.nsu.ccfit.kombarov.tetris.view.screen.gameover;

import javafx.application.Platform;
import javafx.scene.Scene;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;

import java.util.function.BiConsumer;

public class GameOverScreen {

    private final GameOverLayout layout;
    private final Scene scene;

    private Runnable onMenu;
    private int score;

    public GameOverScreen(
            Theme theme,
            ViewConfig config,
            BiConsumer<String, Integer> onSave
    ) {
        this.layout = new GameOverLayout(theme, config);
        this.scene = new Scene(
                layout,
                config.getWindowWidth(),
                config.getWindowHeight()
        );

        layout.getSaveButton().setOnAction(event -> {
            onSave.accept(layout.getPlayerName(), score);

            if (onMenu != null) {
                onMenu.run();
            }
        });

        layout.getMenuButton().setOnAction(event -> {
            if (onMenu != null) {
                onMenu.run();
            }
        });
    }

    public void setOnMenu(Runnable onMenu) {
        this.onMenu = onMenu;
    }

    public void prepare(int score) {
        this.score = score;

        layout.setScore(score);
        layout.resetName();

        Platform.runLater(layout::resetName);
    }

    public Scene getScene() {
        return scene;
    }
}