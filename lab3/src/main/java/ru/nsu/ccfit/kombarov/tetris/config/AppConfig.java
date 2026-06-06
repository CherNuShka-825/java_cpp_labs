package ru.nsu.ccfit.kombarov.tetris.config;

public final class AppConfig {

    private AppConfig() {}

    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;

    public static final int APP_BASE_SIZE = 70;

    public static final String TETROMINO_CONFIG =
            "ru/nsu/ccfit/kombarov/tetris/model/factory/tetromino.properties";

    public static final String THEME_CONFIG =
            "ru/nsu/ccfit/kombarov/tetris/view/theme/factory/theme.properties";

    public static final String DEFAULT_THEME = "default";

    public static final String WINDOW_TITLE = "Tetris";

    public static final String HIGH_SCORES_FILE = "data/highscores.txt";

    public static final String GAME_MUSIC_FILE =
            "ru/nsu/ccfit/kombarov/tetris/audio/game.wav";

    public static final String MENU_MUSIC_FILE =
            "ru/nsu/ccfit/kombarov/tetris/audio/menu.wav";

    public static final String BUTTON_CLICK_SOUND =
            "ru/nsu/ccfit/kombarov/tetris/audio/button.mp3";

    public static final String BLOCK_LOCK_SOUND =
            "ru/nsu/ccfit/kombarov/tetris/audio/block_lock.mp3";

    public static final String LINE_CLEARED_SOUND =
            "ru/nsu/ccfit/kombarov/tetris/audio/clear_line.mp3";
}