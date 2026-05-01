package ru.nsu.ccfit.kombarov.tetris.controller;

import ru.nsu.ccfit.kombarov.tetris.model.game.GameModel;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameState;

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
        if (model.getState() == GameState.RUNNING) {
            model.pause();
            loop.stop();
        }
    }

    public void resume() {
        if (model.getState() == GameState.PAUSED) {
            model.resume();
            loop.start();
        }
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

    public void rotateClockwise() {
        model.rotateClockwise();
    }

    public void rotateCounterClockwise() {
        model.rotateCounterClockwise();
    }
}
