package ru.nsu.ccfit.kombarov.tetris.controller.game;

import ru.nsu.ccfit.kombarov.tetris.model.game.GameModel;

public class GameController {

    private final GameModel model;
    private final GameLoop loop;

    public GameController(GameModel model, GameLoop loop) {
        this.model = model;
        this.loop = loop;
    }

    public void startNewGame() {
        model.newGame();
        loop.start();
    }

    public void pause() {
        model.pause();
        loop.stop();
    }

    public void resume() {
        model.resume();
        loop.start();
    }

    public void moveLeft() {
        model.moveLeft();
    }

    public void moveRight() {
        model.moveRight();
    }

    public void moveDown() {
        model.tick();
    }

    public void moveDownLeft() {
        model.moveDownLeft();
    }

    public void moveDownRight() {
        model.moveDownRight();
    }

    public void rotateClockwise() {
        model.rotateClockwise();
    }

    public void rotateCounterClockwise() {
        model.rotateCounterClockwise();
    }

    public void hardDrop() {
        model.hardDrop();
    }
}
