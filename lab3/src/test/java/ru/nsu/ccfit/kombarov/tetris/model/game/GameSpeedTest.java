package ru.nsu.ccfit.kombarov.tetris.model.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameSpeedTest {

    @Test
    void getDelayMsShouldReturnInitialDelayForFirstLevel() {
        GameSpeed gameSpeed = new GameSpeed();

        assertEquals(700, gameSpeed.getDelayMs(1));
    }

    @Test
    void getDelayMsShouldDecreaseDelayByFiftyMsForEachNextLevel() {
        GameSpeed gameSpeed = new GameSpeed();

        assertEquals(650, gameSpeed.getDelayMs(2));
        assertEquals(600, gameSpeed.getDelayMs(3));
        assertEquals(550, gameSpeed.getDelayMs(4));
    }

    @Test
    void getDelayMsShouldNotReturnLessThanMinimumDelay() {
        GameSpeed gameSpeed = new GameSpeed();

        assertEquals(100, gameSpeed.getDelayMs(13));
        assertEquals(100, gameSpeed.getDelayMs(14));
        assertEquals(100, gameSpeed.getDelayMs(100));
    }
}