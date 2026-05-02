package ru.nsu.ccfit.kombarov.tetris.config;

public final class AppConfig {

    private AppConfig() {}

    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;

    public static final int CELL_SIZE = 30*2;
    public static final int PREVIEW_CELL_SIZE = 24*2;

    public static final String TETROMINO_CONFIG =
            "ru/nsu/ccfit/kombarov/tetris/model/factory/tetromino.properties";

    public static final String THEME_CONFIG =
            "ru/nsu/ccfit/kombarov/tetris/view/theme/factory/theme.properties";

    public static final String DEFAULT_THEME = "pulsing";

    public static final String WINDOW_TITLE = "Tetris";
}