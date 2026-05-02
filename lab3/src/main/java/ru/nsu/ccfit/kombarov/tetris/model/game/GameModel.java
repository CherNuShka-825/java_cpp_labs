package ru.nsu.ccfit.kombarov.tetris.model.game;

import ru.nsu.ccfit.kombarov.tetris.exceptions.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.exceptions.TetrominoExeption;
import ru.nsu.ccfit.kombarov.tetris.model.board.Board;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.TetrominoGenerator;

public class GameModel {

    private final Board board;
    private final TetrominoGenerator generator;

    private Tetromino currentTetromino;
    private Tetromino nextTetromino;

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
        state = GameState.PAUSED;
    }

    public void resume() {
        state = GameState.RUNNING;
    }

    public void newGame() throws FactoryException, TetrominoExeption {
        board.clear();
        scoreManager.reset();

        currentTetromino = spawnCentered();
        nextTetromino = spawnCentered();

        if (!board.canPlace(currentTetromino)) {
            state = GameState.GAME_OVER;
            return;
        }

        state = GameState.RUNNING;
    }

    public void moveLeft() {
        currentTetromino.moveLeft();

        if (!board.canPlace(currentTetromino)) {
            currentTetromino.moveRight();
        }
    }

    public void moveRight() {
        currentTetromino.moveRight();

        if (!board.canPlace(currentTetromino)) {
            currentTetromino.moveLeft();
        }
    }

    public void rotateClockwise() {
        currentTetromino.rotateClockwise();

        if (!tryWallKick()) {
            currentTetromino.rotateCounterClockwise();
        }
    }

    public void rotateCounterClockwise() {
        currentTetromino.rotateCounterClockwise();

        if (!tryWallKick()) {
            currentTetromino.rotateCounterClockwise();
        }
    }

    public void tick() throws FactoryException, TetrominoExeption {
        currentTetromino.moveDown();

        if (!board.canPlace(currentTetromino)) {
            currentTetromino.moveUp();

            board.lock(currentTetromino);

            int lines = board.clearFullLines();
            scoreManager.addClearedLines(lines);

            spawnNext();
        }
    }

    private void spawnNext() {
        if (!board.canPlace(nextTetromino)) {
            currentTetromino = null;
            state = GameState.GAME_OVER;
            return;
        }

        currentTetromino = nextTetromino;
        nextTetromino = spawnCentered();
    }

    private Tetromino spawnCentered() throws FactoryException, TetrominoExeption {
        Tetromino tetromino = generator.oneFromBag();

        int startX = (board.getWidth() - tetromino.getWidth()) / 2;

        tetromino.setPosition(startX, 0);

        return tetromino;
    }

    private boolean tryWallKick() {
        int[] offsets = {0, -1, 1, -2, 2};

        for (int dx : offsets) {
            currentTetromino.setPosition(
                    currentTetromino.getX() + dx,
                    currentTetromino.getY()
            );

            if (board.canPlace(currentTetromino)) {
                return true;
            }

            currentTetromino.setPosition(
                    currentTetromino.getX() - dx,
                    currentTetromino.getY()
            );
        }

        return false;
    }
}