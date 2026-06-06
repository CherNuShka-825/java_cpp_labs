package ru.nsu.ccfit.kombarov.tetris.view.screen.game;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameModel;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;
import ru.nsu.ccfit.kombarov.tetris.view.ui.UiFactory;

public class InfoPanel extends VBox {

    private final GameModel model;

    private final Label scoreLabel;
    private final Label levelLabel;
    private final Label linesLabel;
    private final Label stateLabel;
    private final Label timeLabel;

    public InfoPanel(GameModel model, Theme theme, ViewConfig config) {
        this.model = model;

        UiFactory uiFactory = new UiFactory(theme, config);

        this.scoreLabel = uiFactory.createLabel();
        this.levelLabel = uiFactory.createLabel();
        this.linesLabel = uiFactory.createLabel();
        this.stateLabel = uiFactory.createLabel();
        this.timeLabel = uiFactory.createLabel();

        setSpacing(config.getSidePanelSpacing());
        setPadding(new Insets(config.getSidePanelPadding()));
        setStyle(theme.getUiStyle().panelStyle());

        getChildren().addAll(scoreLabel,
                levelLabel,
                linesLabel,
                stateLabel,
                timeLabel
        );

        update();
    }

    public void update() {
        scoreLabel.setText("Score: " + model.getScore());
        levelLabel.setText("Level: " + model.getLevel());
        linesLabel.setText("Lines: " + model.getClearedLines());
        stateLabel.setText("State: " + model.getState());
        timeLabel.setText("Time: " + model.getFormattedTime());
    }
}