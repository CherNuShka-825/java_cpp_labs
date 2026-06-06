package ru.nsu.ccfit.kombarov.tetris.view.screen.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;
import ru.nsu.ccfit.kombarov.tetris.view.ui.UiFactory;

public class MainMenuLayout extends VBox {

    private final Button newGameButton;
    private final Button highScoresButton;
    private final Button aboutButton;
    private final Button exitButton;

    public MainMenuLayout(Theme theme, ViewConfig config) {
        UiFactory uiFactory = new UiFactory(theme, config);

        this.newGameButton = uiFactory.createButton("New Game");
        this.highScoresButton = uiFactory.createButton("High Scores");
        this.aboutButton = uiFactory.createButton("About");
        this.exitButton = uiFactory.createButton("Exit");

        setAlignment(Pos.CENTER);
        setSpacing(config.getScreenSpacing());
        setStyle(theme.getUiStyle().mainBackgroundStyle());

        Label title = uiFactory.createLargeTitleLabel("TETRIS");

        getChildren().addAll(
                title,
                newGameButton,
                highScoresButton,
                aboutButton,
                exitButton
        );
    }

    public Button getNewGameButton() {
        return newGameButton;
    }

    public Button getHighScoresButton() {
        return highScoresButton;
    }

    public Button getAboutButton() {
        return aboutButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}