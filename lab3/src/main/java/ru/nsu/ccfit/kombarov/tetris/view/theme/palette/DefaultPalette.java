package ru.nsu.ccfit.kombarov.tetris.view.theme.palette;

import javafx.scene.paint.Color;

public class DefaultPalette implements ColorPalette {

    @Override
    public Color background() {
        return Color.rgb(18, 18, 24);
    }

    @Override
    public Color grid() {
        return Color.rgb(45, 45, 55);
    }

    @Override
    public Color tetromino(String type) {
        return switch (type) {
            case "I" -> Color.CYAN;
            case "J" -> Color.BLUE;
            case "L" -> Color.ORANGE;
            case "O" -> Color.YELLOW;
            case "S" -> Color.LIMEGREEN;
            case "T" -> Color.PURPLE;
            case "Z" -> Color.RED;
            default -> Color.GRAY;
        };
    }

    @Override
    public Color ghost(String type) {
        Color base = tetromino(type);
        return Color.color(base.getRed(), base.getGreen(), base.getBlue(), 0.25);
    }
}