package ru.nsu.ccfit.kombarov.tetris.view.layout;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameModel;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;

public class InfoPanel extends VBox {

    private final GameModel model;

    private final Label scoreLabel = new Label();
    private final Label levelLabel = new Label();
    private final Label linesLabel = new Label();
    private final Label stateLabel = new Label();

    public InfoPanel(GameModel model, ViewConfig config) {
        this.model = model;

        setSpacing(config.getSidePanelSpacing());
        setPadding(new Insets(config.getSidePanelPadding()));

        String labelStyle = """
            -fx-text-fill: white;
            -fx-font-size: %dpx;
            """.formatted(config.getTextFontSize());

        scoreLabel.setStyle(labelStyle);
        levelLabel.setStyle(labelStyle);
        linesLabel.setStyle(labelStyle);
        stateLabel.setStyle(labelStyle);

        setStyle("""
            -fx-background-color: #242432;
            -fx-background-radius: 8;
            """);

        getChildren().addAll(scoreLabel, levelLabel, linesLabel, stateLabel);

        update();
    }

    public void update() {
        scoreLabel.setText("Score: " + model.getScore());
        levelLabel.setText("Level: " + model.getLevel());
        linesLabel.setText("Lines: " + model.getClearedLines());
        stateLabel.setText("State: " + model.getState());
    }
}