package ru.nsu.ccfit.kombarov.tetris.view.theme.cellRender;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.ColorPalette;

public class PulsingCellRenderer extends AbstractCellRenderer {

    public PulsingCellRenderer(ColorPalette palette) {
        super(palette);
    }

    @Override
    protected Color getBaseColor(String type, double time) {
        Color base = palette.tetromino(type);

        double pulse = Math.sin(time * 2);
        double brightness = 0.8 + 0.2 * pulse;

        return Color.color(
                clamp(base.getRed() * brightness),
                clamp(base.getGreen() * brightness),
                clamp(base.getBlue() * brightness),
                base.getOpacity()
        );
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

    private double clamp(double v) {
        return Math.max(0, Math.min(1, v));
    }
}