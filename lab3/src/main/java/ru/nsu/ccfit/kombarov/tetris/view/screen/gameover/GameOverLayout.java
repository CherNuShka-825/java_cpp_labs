package ru.nsu.ccfit.kombarov.tetris.view.screen.gameover;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;
import ru.nsu.ccfit.kombarov.tetris.view.ui.UiFactory;

public class GameOverLayout extends VBox {

    private final Label scoreLabel;
    private final TextField nameField;
    private final Button saveButton;
    private final Button menuButton;

    public GameOverLayout(Theme theme, ViewConfig config) {
        UiFactory uiFactory = new UiFactory(theme, config);

        Label title = uiFactory.createTitleLabel("GAME OVER");

        this.scoreLabel = uiFactory.createLabel();
        this.nameField = uiFactory.createTextField("Player");
        this.saveButton = uiFactory.createButton("Save Score");
        this.menuButton = uiFactory.createButton("Menu");

        setAlignment(Pos.CENTER);
        setSpacing(config.getScreenSpacing());
        setStyle(theme.getUiStyle().mainBackgroundStyle());

        getChildren().addAll(
                title,
                scoreLabel,
                nameField,
                saveButton,
                menuButton
        );
    }

    public void setScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public String getPlayerName() {
        return nameField.getText();
    }

    public void resetName() {
        nameField.setText("Player");
        nameField.selectAll();
        nameField.requestFocus();
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getMenuButton() {
        return menuButton;
    }
}