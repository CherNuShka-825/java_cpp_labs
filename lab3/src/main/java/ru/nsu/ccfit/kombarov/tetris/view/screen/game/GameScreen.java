package ru.nsu.ccfit.kombarov.tetris.view.screen.game;

import javafx.scene.Scene;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameModel;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;

public class GameScreen {

    private final GameLayout layout;
    private final Scene scene;

    public GameScreen(GameModel model, Theme theme, ViewConfig config) {
        this.layout = new GameLayout(model, theme, config);
        this.scene = new Scene(layout);
    }

    public void render(double time) {
        layout.render(time);
    }

    public void requestFocus() {
        layout.requestFocus();
    }

    public Scene getScene() {
        return scene;
    }
}