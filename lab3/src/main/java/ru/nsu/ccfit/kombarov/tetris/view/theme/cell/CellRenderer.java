package ru.nsu.ccfit.kombarov.tetris.view.theme.cell;

import javafx.scene.canvas.GraphicsContext;

public interface CellRenderer {
    void renderCell(GraphicsContext gc,
                    int x, int y,
                    int size,
                    String type,
                    CellRenderMode mode,
                    double time);
}