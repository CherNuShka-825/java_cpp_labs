package ru.nsu.ccfit.kombarov.tetris.view.theme.cellRender;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.ColorPalette;

public abstract class AbstractCellRenderer implements CellRenderer {

    protected final ColorPalette palette;

    protected AbstractCellRenderer(ColorPalette palette) {
        this.palette = palette;
    }

    @Override
    public final void renderCell(GraphicsContext gc,
                                 int x,
                                 int y,
                                 int size,
                                 String type,
                                 CellRenderMode mode,
                                 double time) {

        int gap = Math.max(1, size / 20);

        int px = x * size + gap;
        int py = y * size + gap;
        int drawSize = size - 2 * gap;

        Color color = getBaseColor(type, time);

        double alpha = 1.0;

        if (mode == CellRenderMode.GHOST) {
            color = color.darker().desaturate();
            alpha = 0.5;
        }

        gc.setGlobalAlpha(alpha);
        drawCell(gc, px, py, drawSize, color, time);
        gc.setGlobalAlpha(1.0);
    }

    protected abstract Color getBaseColor(String type, double time);

    protected abstract void drawCell(GraphicsContext gc,
                                     int px,
                                     int py,
                                     int size,
                                     Color color,
                                     double time);
}