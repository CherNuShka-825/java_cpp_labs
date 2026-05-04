package ru.nsu.ccfit.kombarov.tetris.view.theme.backgroundRender;

import javafx.scene.canvas.GraphicsContext;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.ColorPalette;

public abstract class AbstractBackgroundRenderer implements BackgroundRenderer {

    protected final ColorPalette palette;

    protected AbstractBackgroundRenderer(ColorPalette palette) {
        this.palette = palette;
    }

    @Override
    public void update(double time) {
        // По умолчанию фон не анимируется.
    }

    protected void fillBackground(GraphicsContext gc, double width, double height) {
        gc.setFill(palette.background());
        gc.fillRect(0, 0, width, height);
    }
}