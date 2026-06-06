package ru.nsu.ccfit.kombarov.tetris.view.screen.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameModel;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;

public class GameLayout extends BorderPane {

    private final BoardCanvas boardCanvas;
    private final NextPieceCanvas nextPieceCanvas;
    private final InfoPanel infoPanel;

    public GameLayout(GameModel model, Theme theme, ViewConfig config) {
        this.boardCanvas = new BoardCanvas(model, theme, config);
        this.nextPieceCanvas = new NextPieceCanvas(model, theme, config);
        this.infoPanel = new InfoPanel(model, theme, config);

        setPadding(new Insets(config.getWindowPadding()));
        setStyle(theme.getUiStyle().mainBackgroundStyle());

        VBox sidePanel = new VBox(config.getSidePanelSpacing());
        sidePanel.setAlignment(Pos.TOP_CENTER);
        sidePanel.setPadding(new Insets(config.getSidePanelPadding()));
        sidePanel.setPrefWidth(config.getSidePanelWidth());
        sidePanel.setMinWidth(config.getSidePanelWidth());
        sidePanel.setMaxWidth(config.getSidePanelWidth());
        sidePanel.setStyle(theme.getUiStyle().panelStyle());

        Label nextLabel = new Label("NEXT");
        nextLabel.setStyle(
                theme.getUiStyle().textStyle(config.getTitleFontSize())
                        + "-fx-font-weight: bold;"
        );

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