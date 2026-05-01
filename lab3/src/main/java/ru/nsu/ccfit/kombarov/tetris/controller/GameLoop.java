package ru.nsu.ccfit.kombarov.tetris.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameModel;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameSpeed;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameState;

public class GameLoop {

    private final GameModel model;
    private final GameSpeed speed;
    private final Runnable renderCallback;

    private Timeline timeline;
    private int currentDelayMs;

    public GameLoop(GameModel model, GameSpeed speed, Runnable renderCallback) {
        this.model = model;
        this.speed = speed;
        this.renderCallback = renderCallback;
    }

    public void start() {
        currentDelayMs = speed.getDelayMs(model.getLevel());
        createTimeline();
        timeline.play();
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    private void createTimeline() {
        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(
                new KeyFrame(Duration.millis(currentDelayMs), event -> update())
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void update() {
        model.tick();
        renderCallback.run();

        if (model.getState() == GameState.GAME_OVER) {
            stop();
            return;
        }

        updateSpeedIfNeeded();
    }

    private void updateSpeedIfNeeded() {
        int newDelayMs = speed.getDelayMs(model.getLevel());

        if (newDelayMs != currentDelayMs) {
            currentDelayMs = newDelayMs;
            createTimeline();
            timeline.play();
        }
    }
}
