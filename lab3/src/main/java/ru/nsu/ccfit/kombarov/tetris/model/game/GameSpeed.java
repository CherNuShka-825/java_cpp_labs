package ru.nsu.ccfit.kombarov.tetris.model.game;

import java.util.logging.Logger;

public class GameSpeed {

    private static final Logger logger = Logger.getLogger(GameSpeed.class.getName());

    private static final int INITIAL_DELAY_MS = 700;
    private static final int MIN_DELAY_MS = 100;
    private static final int DELAY_STEP_MS = 50;

    public int getDelayMs(int level) {
        logger.fine("get Delay Ms");
        int delay = INITIAL_DELAY_MS - (level - 1) * DELAY_STEP_MS;
        return Math.max(MIN_DELAY_MS, delay);
    }
}
