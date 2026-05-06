package ru.nsu.ccfit.kombarov.tetris.model.game;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTimerTest {

    private static final long SECOND = 1_000_000_000L;

    @Test
    void resetShouldClearElapsedTimeAndStopTimer() throws Exception {
        GameTimer timer = new GameTimer();

        setTimerState(timer, 5 * SECOND, System.nanoTime() - 3 * SECOND, true);

        timer.reset();

        assertEquals(0, timer.getElapsedSeconds());
        assertEquals("00:00", timer.getFormattedTime());
    }

    @Test
    void startShouldResetElapsedTimeAndStartTimer() throws Exception {
        GameTimer timer = new GameTimer();

        setTimerState(timer, 10 * SECOND, 0, false);

        timer.start();

        assertEquals(0, timer.getElapsedSeconds());
        assertEquals("00:00", timer.getFormattedTime());
    }

    @Test
    void pauseShouldDoNothingWhenTimerIsNotRunning() throws Exception {
        GameTimer timer = new GameTimer();

        setTimerState(timer, 4 * SECOND, 0, false);

        timer.pause();

        assertEquals(4, timer.getElapsedSeconds());
        assertEquals("00:04", timer.getFormattedTime());
    }

    @Test
    void pauseShouldAddCurrentRunTimeAndStopTimer() throws Exception {
        GameTimer timer = new GameTimer();

        setTimerState(timer, 3 * SECOND, System.nanoTime() - 2 * SECOND, true);

        timer.pause();

        assertEquals(5, timer.getElapsedSeconds());
        assertEquals("00:05", timer.getFormattedTime());

        timer.pause();

        assertEquals(5, timer.getElapsedSeconds());
    }

    @Test
    void resumeShouldDoNothingWhenTimerIsAlreadyRunning() throws Exception {
        GameTimer timer = new GameTimer();

        setTimerState(timer, 3 * SECOND, System.nanoTime() - 2 * SECOND, true);

        timer.resume();

        assertEquals(5, timer.getElapsedSeconds());
    }

    @Test
    void resumeShouldContinueFromElapsedTimeWhenTimerIsPaused() throws Exception {
        GameTimer timer = new GameTimer();

        setTimerState(timer, 7 * SECOND, 0, false);

        timer.resume();

        long elapsed = timer.getElapsedSeconds();

        assertTrue(elapsed >= 7);
        assertTrue(elapsed < 8);
    }

    @Test
    void getElapsedSecondsShouldReturnOnlySavedElapsedWhenTimerIsPaused() throws Exception {
        GameTimer timer = new GameTimer();

        setTimerState(timer, 42 * SECOND, System.nanoTime() - 10 * SECOND, false);

        assertEquals(42, timer.getElapsedSeconds());
    }

    @Test
    void getElapsedSecondsShouldIncludeCurrentRunWhenTimerIsRunning() throws Exception {
        GameTimer timer = new GameTimer();

        setTimerState(timer, 10 * SECOND, System.nanoTime() - 5 * SECOND, true);

        assertEquals(15, timer.getElapsedSeconds());
    }

    @Test
    void getFormattedTimeShouldFormatMinutesAndSeconds() throws Exception {
        GameTimer timer = new GameTimer();

        setTimerState(timer, 125 * SECOND, 0, false);

        assertEquals("02:05", timer.getFormattedTime());
    }

    private static void setTimerState(
            GameTimer timer,
            long elapsedBeforeStartNanos,
            long startNanos,
            boolean running
    ) throws Exception {
        setLongField(timer, "elapsedBeforeStartNanos", elapsedBeforeStartNanos);
        setLongField(timer, "startNanos", startNanos);
        setBooleanField(timer, "running", running);
    }

    private static void setLongField(
            GameTimer timer,
            String fieldName,
            long value
    ) throws Exception {
        Field field = GameTimer.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.setLong(timer, value);
    }

    private static void setBooleanField(
            GameTimer timer,
            String fieldName,
            boolean value
    ) throws Exception {
        Field field = GameTimer.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.setBoolean(timer, value);
    }
}