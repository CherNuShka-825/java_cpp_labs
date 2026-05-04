package ru.nsu.ccfit.kombarov.tetris.view.screen.about;

import javafx.scene.Scene;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;

public class AboutScreen {

    private final AboutLayout layout;
    private final Scene scene;

    public AboutScreen(Theme theme, ViewConfig config) {
        this.layout = new AboutLayout(theme, config);
        this.scene = new Scene(layout);
    }

    public void setOnBack(Runnable onBack) {
        layout.getBackButton().setOnAction(event -> onBack.run());
    }

    public Scene getScene() {
        return scene;
    }
}