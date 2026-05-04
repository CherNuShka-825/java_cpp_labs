package ru.nsu.ccfit.kombarov.tetris.model.game;

public class GameTimer {

    private long elapsedBeforeStartNanos;
    private long startNanos;
    private boolean running;

    public void reset() {
        elapsedBeforeStartNanos = 0;
        startNanos = 0;
        running = false;
    }

    public void start() {
        elapsedBeforeStartNanos = 0;
        startNanos = System.nanoTime();
        running = true;
    }

    public void pause() {
        if (!running) {
            return;
        }

        elapsedBeforeStartNanos += System.nanoTime() - startNanos;
        running = false;
    }

    public void resume() {
        if (running) {
            return;
        }

        startNanos = System.nanoTime();
        running = true;
    }

    public long getElapsedSeconds() {
        long elapsed = elapsedBeforeStartNanos;

        if (running) {
            elapsed += System.nanoTime() - startNanos;
        }

        return elapsed / 1_000_000_000L;
    }

    public String getFormattedTime() {
        long seconds = getElapsedSeconds();

        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;

        return "%02d:%02d".formatted(minutes, remainingSeconds);
    }
}