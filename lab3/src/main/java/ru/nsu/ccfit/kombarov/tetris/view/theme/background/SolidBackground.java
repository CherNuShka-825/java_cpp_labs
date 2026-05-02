package ru.nsu.ccfit.kombarov.tetris.view.theme.background;

import javafx.scene.canvas.GraphicsContext;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.ColorPalette;

public class SolidBackground implements BackgroundRenderer {

    private final ColorPalette palette;

    public SolidBackground(ColorPalette palette) {
        this.palette = palette;
    }

    @Override
    public void update(double time) {
        // ничего не делает, т.к. нет эффектов
    }

    @Override
    public void render(GraphicsContext gc, double width, double height) {
        gc.setFill(palette.background());
        gc.fillRect(0, 0, width, height);
    }
}