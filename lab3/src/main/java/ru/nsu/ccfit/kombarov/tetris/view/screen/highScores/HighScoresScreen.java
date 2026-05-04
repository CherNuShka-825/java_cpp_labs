package ru.nsu.ccfit.kombarov.tetris.view.screen.highScores;

import javafx.scene.Scene;
import ru.nsu.ccfit.kombarov.tetris.model.highScore.HighScoreTable;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;

public class HighScoresScreen {

    private final HighScoresLayout layout;
    private final Scene scene;

    public HighScoresScreen(Theme theme, ViewConfig config) {
        this.layout = new HighScoresLayout(theme, config);
        this.scene = new Scene(layout);
    }

    public void refresh(HighScoreTable table) {
        layout.refresh(table);
    }

    public void setOnBack(Runnable onBack) {
        layout.getBackButton().setOnAction(event -> onBack.run());
    }

    public Scene getScene() {
        return scene;
    }
}