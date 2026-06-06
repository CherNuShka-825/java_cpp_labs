package ru.nsu.ccfit.kombarov.tetris.view.input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import ru.nsu.ccfit.kombarov.tetris.controller.game.GameController;

import java.util.HashSet;
import java.util.Set;

public class InputHandler {

    private final GameController controller;
    private final Runnable renderCallback;
    private final Set<KeyCode> pressedKeys = new HashSet<>();
    private Runnable escapeCallback;

    public InputHandler(GameController controller, Runnable renderCallback) {
        this.controller = controller;
        this.renderCallback = renderCallback;
    }

    public void attachTo(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            pressedKeys.add(code);

            if (pressedKeys.contains(KeyCode.S) && pressedKeys.contains(KeyCode.D)) {
                controller.moveDownRight();
            } else if (pressedKeys.contains(KeyCode.S) && pressedKeys.contains(KeyCode.A)){
                controller.moveDownLeft();
            } else {
                switch (code) {
                    case A -> controller.moveLeft();
                    case D -> controller.moveRight();
                    case S -> controller.moveDown();
                    case SPACE -> controller.hardDrop();
                    case W -> controller.rotateClockwise();
                    case ALT -> controller.rotateCounterClockwise();
                    case R -> controller.startNewGame();
                    case ESCAPE -> escapeRun();
                }
            }

            renderCallback.run();
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.remove(event.getCode());
        });
    }

    public void setEscapeCallback(Runnable escapeCallback) {
        this.escapeCallback = escapeCallback;
    }

    private void escapeRun() {
        if (escapeCallback != null) {
            escapeCallback.run();
        }
    }
}