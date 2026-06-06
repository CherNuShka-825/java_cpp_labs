package ru.nsu.ccfit.kombarov.tetris.view.screen.game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import ru.nsu.ccfit.kombarov.tetris.model.Coordinate;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameModel;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.cellRender.CellRenderMode;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;

public class NextPieceCanvas extends Canvas {

    private final ViewConfig config;

    private final GameModel model;
    private Theme theme;

    public NextPieceCanvas(GameModel model, Theme theme, ViewConfig config) {
        super(config.getPreviewCanvasSize(), config.getPreviewCanvasSize());

        this.model = model;
        this.theme = theme;
        this.config = config;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
        render(0);
    }

    public void render(double time) {
        GraphicsContext gc = getGraphicsContext2D();

        theme.getBackground().update(time);

        gc.setFill(theme.getPalette().background());
        gc.fillRect(0, 0, getWidth(), getHeight());

        Tetromino next = model.getNextTetromino();
        if (next == null) {
            return;
        }

        int previewSize = config.getPreviewSizeInCells();

        int offsetX = Math.max(0, (previewSize - next.getWidth()) / 2);
        int offsetY = Math.max(0, (previewSize - next.getHeight()) / 2);

        for (Coordinate block : next.getBlocks()) {
            int previewX = block.getX() - next.getX() + offsetX;
            int previewY = block.getY() - next.getY() + offsetY;

            theme.getCellRenderer().renderCell(
                    gc,
                    previewX,
                    previewY,
                    config.getPreviewCellSize(),
                    next.getType(),
                    CellRenderMode.NORMAL,
                    time
            );
        }
    }
}