package ru.nsu.ccfit.kombarov.tetris.view.input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import ru.nsu.ccfit.kombarov.tetris.controller.GameController;

public class InputHandler {

    private final GameController controller;
    private final Runnable renderCallback;

    public InputHandler(GameController controller, Runnable renderCallback) {
        this.controller = controller;
        this.renderCallback = renderCallback;
    }

    public void attachTo(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            switch (code) {
                case LEFT -> controller.moveLeft();
                case RIGHT -> controller.moveRight();
                case DOWN -> controller.moveDown();
                case UP -> controller.rotateClockwise();
                case Z -> controller.rotateCounterClockwise();
                case SPACE -> controller.startNewGame();
                case P -> controller.pause();
                case R -> controller.resume();
            }

            renderCallback.run();
        });
    }
}