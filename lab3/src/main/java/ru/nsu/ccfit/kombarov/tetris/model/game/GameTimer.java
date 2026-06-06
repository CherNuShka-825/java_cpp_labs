package ru.nsu.ccfit.kombarov.tetris.model.game;

import java.util.logging.Logger;

public class GameTimer {

    private static final Logger logger = Logger.getLogger(GameTimer.class.getName());

    private long elapsedBeforeStartNanos;
    private long startNanos;
    private boolean running;

    public void reset() {
        elapsedBeforeStartNanos = 0;
        startNanos = 0;
        running = false;
        logger.fine("reset");
    }

    public void start() {
        elapsedBeforeStartNanos = 0;
        startNanos = System.nanoTime();
        running = true;
        logger.fine("start");
    }

    public void pause() {
        if (!running) {
            return;
        }

        elapsedBeforeStartNanos += System.nanoTime() - startNanos;
        running = false;
        logger.fine("pause");
    }

    public void resume() {
        if (running) {
            return;
        }

        startNanos = System.nanoTime();
        running = true;
        logger.fine("resume");
    }

    public long getElapsedSeconds() {
        long elapsed = elapsedBeforeStartNanos;

        if (running) {
            elapsed += System.nanoTime() - startNanos;
        }

        logger.fine("get Elapsed Second");

        return elapsed / 1_000_000_000L;
    }

    public String getFormattedTime() {
        long seconds = getElapsedSeconds();

        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;

        logger.finer("get Formated Time");

        return "%02d:%02d".formatted(minutes, remainingSeconds);
    }
}