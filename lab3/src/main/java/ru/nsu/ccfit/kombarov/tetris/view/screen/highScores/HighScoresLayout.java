package ru.nsu.ccfit.kombarov.tetris.view.screen.highScores;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ru.nsu.ccfit.kombarov.tetris.model.highScore.HighScoreEntry;
import ru.nsu.ccfit.kombarov.tetris.model.highScore.HighScoreTable;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;
import ru.nsu.ccfit.kombarov.tetris.view.ui.UiFactory;

public class HighScoresLayout extends VBox {

    private final Button backButton;
    private final VBox scoresBox;

    private final UiFactory uiFactory;

    public HighScoresLayout(Theme theme, ViewConfig config) {
        this.uiFactory = new UiFactory(theme, config);

        this.backButton = uiFactory.createButton("Back");
        this.scoresBox = new VBox(config.getSidePanelSpacing());

        setAlignment(Pos.CENTER);
        setSpacing(config.getScreenSpacing());
        setStyle(theme.getUiStyle().mainBackgroundStyle());

        Label title = uiFactory.createTitleLabel("HIGH SCORES");

        scoresBox.setAlignment(Pos.CENTER);

        getChildren().addAll(title, scoresBox, backButton);
    }

    public void refresh(HighScoreTable table) {
        scoresBox.getChildren().clear();

        if (table.getEntries().isEmpty()) {
            Label empty = uiFactory.createLabel("No scores yet");
            scoresBox.getChildren().add(empty);
            return;
        }

        int place = 1;

        for (HighScoreEntry entry : table.getEntries()) {
            Label label = uiFactory.createLabel(
                    place + ". " + entry.getPlayerName() + " — " + entry.getScore()
            );

            scoresBox.getChildren().add(label);
            place++;
        }
    }

    public Button getBackButton() {
        return backButton;
    }
}