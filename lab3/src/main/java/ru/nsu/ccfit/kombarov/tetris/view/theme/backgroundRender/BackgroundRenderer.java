package ru.nsu.ccfit.kombarov.tetris.view.theme.backgroundRender;

import javafx.scene.canvas.GraphicsContext;

public interface BackgroundRenderer {
    void update(double time);
    void render(GraphicsContext gc, double width, double height);
}