package ru.nsu.ccfit.kombarov.tetris.view.screen.pause;

import javafx.scene.Scene;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;

public class PauseMenuScreen {

    private final PauseMenuLayout layout;
    private final Scene scene;

    private Runnable onContinue;
    private Runnable onMainMenu;

    public PauseMenuScreen(Theme theme, ViewConfig config) {
        this.layout = new PauseMenuLayout(theme, config);
        this.scene = new Scene(
                layout,
                config.getWindowWidth(),
                config.getWindowHeight()
        );

        layout.getContinueButton().setOnAction(event -> {
            if (onContinue != null) {
                onContinue.run();
            }
        });

        layout.getMainMenuButton().setOnAction(event -> {
            if (onMainMenu != null) {
                onMainMenu.run();
            }
        });
    }

    public void setOnContinue(Runnable onContinue) {
        this.onContinue = onContinue;
    }

    public void setOnMainMenu(Runnable onMainMenu) {
        this.onMainMenu = onMainMenu;
    }

    public Scene getScene() {
        return scene;
    }
}