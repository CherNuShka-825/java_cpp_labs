package ru.nsu.ccfit.kombarov.tetris.view.layout;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameModel;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.canvas.BoardCanvas;
import ru.nsu.ccfit.kombarov.tetris.view.canvas.NextPieceCanvas;
import ru.nsu.ccfit.kombarov.tetris.view.theme.Theme;

public class GameLayout extends BorderPane {

    private final BoardCanvas boardCanvas;
    private final NextPieceCanvas nextPieceCanvas;
    private final InfoPanel infoPanel;

    public GameLayout(GameModel model, Theme theme, ViewConfig config) {
        this.boardCanvas = new BoardCanvas(model, theme, config);
        this.nextPieceCanvas = new NextPieceCanvas(model, theme, config);
        this.infoPanel = new InfoPanel(model, config);

        setPadding(new Insets(config.getWindowPadding()));
        setStyle("-fx-background-color: #111118;");

        VBox sidePanel = new VBox(config.getSidePanelSpacing());
        sidePanel.setAlignment(Pos.TOP_CENTER);
        sidePanel.setPadding(new Insets(config.getSidePanelPadding()));
        sidePanel.setPrefWidth(config.getSidePanelWidth());
        sidePanel.setMinWidth(config.getSidePanelWidth());
        sidePanel.setMaxWidth(config.getSidePanelWidth());

        sidePanel.setStyle("""
            -fx-background-color: #1b1b24;
            -fx-background-radius: 12;
            """);

        Label nextLabel = new Label("NEXT");
        nextLabel.setStyle("""
            -fx-text-fill: white;
            -fx-font-size: %dpx;
            -fx-font-weight: bold;
            """.formatted(config.getTitleFontSize()));

        sidePanel.getChildren().addAll(nextLabel, nextPieceCanvas, infoPanel);

        setCenter(boardCanvas);
        setRight(sidePanel);

        BorderPane.setMargin(sidePanel, new Insets(0, 0, 0, config.getGap()));
    }

    public void render(double time) {
        boardCanvas.render(time);
        nextPieceCanvas.render(time);
        infoPanel.update();
    }
}