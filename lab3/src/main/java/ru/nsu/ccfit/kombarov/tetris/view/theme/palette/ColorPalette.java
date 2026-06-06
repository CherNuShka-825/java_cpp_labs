package ru.nsu.ccfit.kombarov.tetris.view.theme.palette;

import javafx.scene.paint.Color;

public interface ColorPalette {
    Color background();
    Color grid();
    Color tetromino(String type);
    Color ghost(String type);
}