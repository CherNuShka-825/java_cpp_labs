package ru.nsu.ccfit.kombarov.tetris.view.loop;

import javafx.animation.AnimationTimer;

public class RenderLoop {

    private final Runnable renderCallback;
    private AnimationTimer timer;

    public RenderLoop(Runnable renderCallback) {
        this.renderCallback = renderCallback;
    }

    public void start() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                renderCallback.run();
            }
        };

        timer.start();
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }
}