package ru.nsu.ccfit.kombarov.tetris.view.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import ru.nsu.ccfit.kombarov.tetris.model.Coordinate;
import ru.nsu.ccfit.kombarov.tetris.model.board.Board;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameModel;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.Theme;
import ru.nsu.ccfit.kombarov.tetris.view.theme.cell.CellRenderMode;

public class BoardCanvas extends Canvas {

    private static int cellSize;

    private final GameModel model;
    private Theme theme;

    public BoardCanvas(GameModel model, Theme theme, ViewConfig config) {
        super(
                model.getBoard().getWidth() * config.getCellSize(),
                model.getBoard().getHeight() * config.getCellSize()
        );

        this.model = model;
        this.theme = theme;
        this.cellSize = config.getCellSize();
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
        render(0);
    }

    public void render(double time) {
        GraphicsContext gc = getGraphicsContext2D();

        theme.getBackground().update(time);
        theme.getBackground().render(gc, getWidth(), getHeight());

        drawLockedBlocks(gc, time);
        drawGhostTetromino(gc, time);
        drawCurrentTetromino(gc, time);
    }

    private void drawLockedBlocks(GraphicsContext gc, double time) {
        Board board = model.getBoard();

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                String type = board.getCell(x, y);

                if (type != null) {
                    theme.getCellRenderer().renderCell(gc, x, y, cellSize, type, CellRenderMode.NORMAL, time);
                }
            }
        }
    }

    private void drawCurrentTetromino(GraphicsContext gc, double time) {
        Tetromino tetromino = model.getCurrentTetromino();

        if (tetromino == null) {
            return;
        }

        drawTetromino(gc, time, tetromino, CellRenderMode.NORMAL);
    }

    private void drawGhostTetromino(GraphicsContext gc, double time) {
        Tetromino original = model.getCurrentTetromino();
        if (original == null) {
            return;
        }

        Tetromino ghost = original.copy();

        while (true) {
            ghost.moveDown();
            if (!model.getBoard().canPlace(ghost)) {
                ghost.moveUp();
                break;
            }
        }

        drawTetromino(gc, time, ghost, CellRenderMode.GHOST);
    }

    private void drawTetromino(GraphicsContext gc, double time, Tetromino tetromino, CellRenderMode mode) {
        for (Coordinate block : tetromino.getBlocks()) {
            theme.getCellRenderer().renderCell(
                    gc,
                    block.getX(),
                    block.getY(),
                    cellSize,
                    tetromino.getType(),
                    mode,
                    time
            );
        }
    }
}