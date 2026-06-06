package ru.nsu.ccfit.kombarov.tetris.view.screen.pause;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;
import ru.nsu.ccfit.kombarov.tetris.view.ui.UiFactory;

public class PauseMenuLayout extends VBox {

    private final Button continueButton;
    private final Button mainMenuButton;

    public PauseMenuLayout(Theme theme, ViewConfig config) {
        UiFactory uiFactory = new UiFactory(theme, config);

        this.continueButton = uiFactory.createButton("Continue");
        this.mainMenuButton = uiFactory.createButton("Main Menu");

        setAlignment(Pos.CENTER);
        setSpacing(config.getScreenSpacing());
        setStyle(theme.getUiStyle().mainBackgroundStyle());

        Label title = uiFactory.createTitleLabel("PAUSE");

        getChildren().addAll(
                title,
                continueButton,
                mainMenuButton
        );
    }

    public Button getContinueButton() {
        return continueButton;
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }
}