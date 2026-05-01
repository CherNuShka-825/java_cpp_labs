package ru.nsu.ccfit.kombarov.tetris.model.game;

import ru.nsu.ccfit.kombarov.tetris.exceptions.model.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.exceptions.model.TetrominoExeption;
import ru.nsu.ccfit.kombarov.tetris.model.board.Board;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.TetrominoGenerator;

public class GameModel {

    private final Board board;
    private final TetrominoGenerator generator;

    private Tetromino currentTetromino, nextTetromino;
    private GameState state = GameState.NOT_STARTED;

    private final ScoreManager scoreManager = new ScoreManager();

    public GameModel(Board board, TetrominoGenerator generator) {
        this.board = board;
        this.generator = generator;
    }

    public Board getBoard() {
        return board;
    }

    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    public Tetromino getNextTetromino() {
        return nextTetromino;
    }

    public int getScore() {
        return scoreManager.getScore();
    }

    public int getLevel() {
        return scoreManager.getLevel();
    }

    public int getClearedLines() {
        return scoreManager.getClearedLines();
    }

    public GameState getState() {
        return state;
    }

    public void pause() {
        if (state == GameState.RUNNING) {
            state = GameState.PAUSED;
        }
    }

    public void resume() {
        if (state == GameState.PAUSED) {
            state = GameState.RUNNING;
        }
    }

    public void newGame() {
        board.clear();

        scoreManager.reset();

        currentTetromino = generator.oneFromBag(board.getWidth() / 2, 0);
        nextTetromino = generator.oneFromBag(board.getWidth() / 2, 0);

        state = GameState.RUNNING;
    }

    public void moveLeft() {
        if (state != GameState.RUNNING) {
            return;
        }

        currentTetromino.moveLeft();

        if (!board.canPlace(currentTetromino)) {
            currentTetromino.moveRight();
        }
    }

    public void moveRight() {
        if (state != GameState.RUNNING) {
            return;
        }

        currentTetromino.moveRight();

        if (!board.canPlace(currentTetromino)) {
            currentTetromino.moveLeft();
        }
    }

    public void rotateClockwise() {
        if (state != GameState.RUNNING) {
            return;
        }

        currentTetromino.rotateClockwise();

        if (!board.canPlace(currentTetromino)) {
            currentTetromino.rotateCounterClockwise();
        }
    }

    public void rotateCounterClockwise() {
        if (state != GameState.RUNNING) {
            return;
        }

        currentTetromino.rotateCounterClockwise();

        if (!board.canPlace(currentTetromino)) {
            currentTetromino.rotateClockwise();
        }
    }

    public void tick() throws FactoryException, TetrominoExeption {
        int lines = board.clearFullLines();
        scoreManager.addClearedLines(lines);
    }

    private void spawnNext() throws FactoryException, TetrominoExeption {
        currentTetromino = nextTetromino;

        nextTetromino = generator.oneFromBag(board.getWidth() / 2, 0);

        if (!board.canPlace(currentTetromino)) {
            state = GameState.GAME_OVER;
        }
    }
}
