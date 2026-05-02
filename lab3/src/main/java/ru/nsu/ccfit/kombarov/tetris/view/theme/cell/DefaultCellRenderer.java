package ru.nsu.ccfit.kombarov.tetris.view.theme.cell;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.ColorPalette;

public class DefaultCellRenderer extends BaseCellRenderer {

    public DefaultCellRenderer(ColorPalette palette) {
        super(palette);
    }

    @Override
    protected Color getBaseColor(String type, double time) {
        return palette.tetromino(type);
    }

    @Override
    protected void drawCell(GraphicsContext gc,
                            int px,
                            int py,
                            int size,
                            Color color,
                            double time) {

        gc.setFill(color);
        gc.fillRect(px, py, size, size);
    }
}