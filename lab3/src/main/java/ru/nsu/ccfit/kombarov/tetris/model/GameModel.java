package ru.nsu.ccfit.kombarov.tetris.model;

import ru.nsu.ccfit.kombarov.tetris.exceptions.model.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.exceptions.model.TetrominoExeption;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.TetrominoGenerator;

public class GameModel {

    private final Board board;
    private final TetrominoGenerator generator;

    private Tetromino currentTetromino, nextTetromino;
    private GameState state = GameState.NOT_STARTED;

    private int score, level, clearedLines;

    public GameState getState() {
        return state;
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
        return score;
    }

    public int getLevel() {
        return level;
    }

    public int getClearedLines() {
        return clearedLines;
    }

    public GameModel(Board board, TetrominoGenerator generator) {
        this.board = board;
        this.generator = generator;
    }

    public void newGame() throws FactoryException, TetrominoExeption {
        board.clear();

        score = 0;
        level = 1;
        clearedLines = 0;

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
        if (state != GameState.RUNNING) {
            return;
        }

        currentTetromino.moveDown();

        if (!board.canPlace(currentTetromino)) {
            currentTetromino.moveUp();

            board.lock(currentTetromino);
            int lines = board.clearFullLines();
            updateScore(lines);

            spawnNext();
        }
    }

    private void spawnNext() throws FactoryException, TetrominoExeption {
        currentTetromino = nextTetromino;
        nextTetromino = generator.oneFromBag(board.getWidth() / 2, 0);

        if (!board.canPlace(currentTetromino)) {
            state = GameState.GAME_OVER;
        }
    }

    private void updateScore(int lines) {
        if (lines == 0) {
            return;
        }

        clearedLines += lines;

        score += switch (lines) {
            case 1 -> 100 * level;
            case 2 -> 300 * level;
            case 3 -> 500 * level;
            case 4 -> 800 * level;
            default -> (800 + (lines - 4) * 100) * level;
        };

        level = clearedLines / 10 + 1;
    }
}
