package ru.nsu.ccfit.kombarov.tetris.view.theme.backgroundRender;

import javafx.scene.canvas.GraphicsContext;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.ColorPalette;

public class SolidBackgroundRenderer extends AbstractBackgroundRenderer {

    public SolidBackgroundRenderer(ColorPalette palette) {
        super(palette);
    }

    @Override
    public void render(GraphicsContext gc, double width, double height) {
        fillBackground(gc, width, height);
    }
}