package ru.nsu.ccfit.kombarov.tetris.model.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreManagerTest {

    @Test
    void resetShouldSetInitialValues() {
        ScoreManager scoreManager = new ScoreManager();

        scoreManager.addClearedLines(4);
        scoreManager.reset();

        assertEquals(0, scoreManager.getScore());
        assertEquals(1, scoreManager.getLevel());
        assertEquals(0, scoreManager.getClearedLines());
    }

    @Test
    void addClearedLinesShouldDoNothingWhenLinesIsZero() {
        ScoreManager scoreManager = new ScoreManager();

        scoreManager.reset();
        scoreManager.addClearedLines(0);

        assertEquals(0, scoreManager.getScore());
        assertEquals(1, scoreManager.getLevel());
        assertEquals(0, scoreManager.getClearedLines());
    }

    @Test
    void addClearedLinesShouldAddScoreForOneLine() {
        ScoreManager scoreManager = new ScoreManager();

        scoreManager.reset();
        scoreManager.addClearedLines(1);

        assertEquals(100, scoreManager.getScore());
        assertEquals(1, scoreManager.getLevel());
        assertEquals(1, scoreManager.getClearedLines());
    }

    @Test
    void addClearedLinesShouldAddScoreForTwoLines() {
        ScoreManager scoreManager = new ScoreManager();

        scoreManager.reset();
        scoreManager.addClearedLines(2);

        assertEquals(300, scoreManager.getScore());
        assertEquals(1, scoreManager.getLevel());
        assertEquals(2, scoreManager.getClearedLines());
    }

    @Test
    void addClearedLinesShouldAddScoreForThreeLines() {
        ScoreManager scoreManager = new ScoreManager();

        scoreManager.reset();
        scoreManager.addClearedLines(3);

        assertEquals(500, scoreManager.getScore());
        assertEquals(1, scoreManager.getLevel());
        assertEquals(3, scoreManager.getClearedLines());
    }

    @Test
    void addClearedLinesShouldAddScoreForFourLines() {
        ScoreManager scoreManager = new ScoreManager();

        scoreManager.reset();
        scoreManager.addClearedLines(4);

        assertEquals(800, scoreManager.getScore());
        assertEquals(1, scoreManager.getLevel());
        assertEquals(4, scoreManager.getClearedLines());
    }

    @Test
    void addClearedLinesShouldAddScoreForMoreThanFourLines() {
        ScoreManager scoreManager = new ScoreManager();

        scoreManager.reset();
        scoreManager.addClearedLines(5);

        assertEquals(900, scoreManager.getScore());
        assertEquals(1, scoreManager.getLevel());
        assertEquals(5, scoreManager.getClearedLines());
    }

    @Test
    void addClearedLinesShouldIncreaseLevelEveryTenClearedLines() {
        ScoreManager scoreManager = new ScoreManager();

        scoreManager.reset();

        scoreManager.addClearedLines(4);
        scoreManager.addClearedLines(4);
        scoreManager.addClearedLines(2);

        assertEquals(10, scoreManager.getClearedLines());
        assertEquals(2, scoreManager.getLevel());
    }

    @Test
    void addClearedLinesShouldUseCurrentLevelForScoreBeforeLevelUpdate() {
        ScoreManager scoreManager = new ScoreManager();

        scoreManager.reset();

        scoreManager.addClearedLines(10);

        assertEquals(1400, scoreManager.getScore());
        assertEquals(2, scoreManager.getLevel());
        assertEquals(10, scoreManager.getClearedLines());

        scoreManager.addClearedLines(1);

        assertEquals(1600, scoreManager.getScore());
        assertEquals(2, scoreManager.getLevel());
        assertEquals(11, scoreManager.getClearedLines());
    }
}