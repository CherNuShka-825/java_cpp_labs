package ru.nsu.ccfit.kombarov.tetris.view.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import ru.nsu.ccfit.kombarov.tetris.model.Coordinate;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameModel;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.Theme;
import ru.nsu.ccfit.kombarov.tetris.view.theme.cell.CellRenderMode;

public class NextPieceCanvas extends Canvas {

    private final ViewConfig config;
    private static final int PREVIEW_SIZE = 6;

    private final GameModel model;
    private Theme theme;

    public NextPieceCanvas(GameModel model, Theme theme, ViewConfig config) {
        super(
                PREVIEW_SIZE * config.getPreviewCellSize(),
                PREVIEW_SIZE * config.getPreviewCellSize()
        );

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

        theme.getBackground().render(gc, getWidth(), getHeight());

        Tetromino next = model.getNextTetromino();
        if (next == null) {
            return;
        }

        int offsetX = (PREVIEW_SIZE - next.getWidth()) / 2;
        int offsetY = (PREVIEW_SIZE - next.getHeight()) / 2;

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