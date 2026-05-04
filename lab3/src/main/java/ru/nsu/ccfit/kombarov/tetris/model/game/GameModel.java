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

    private final GameTimer gameTimer = new GameTimer();

    private Runnable onBlockLocked;

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

    public Tetromino getGhostTetromino() {
        if (currentTetromino == null) {
            return null;
        }

        Tetromino ghost = currentTetromino.copy();
        moveToDropPosition(ghost);
        return ghost;
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

    public String getFormattedTime() {
        return gameTimer.getFormattedTime();
    }

    public void setOnBlockLocked(Runnable onBlockLocked) {
        this.onBlockLocked = onBlockLocked;
    }

    public void pause() {
        if (state != GameState.PAUSED) {
            state = GameState.PAUSED;
            gameTimer.pause();
        }
    }

    public void resume() {
        if (state != GameState.RUNNING) {
            state = GameState.RUNNING;
            gameTimer.resume();
        }
    }

    public void newGame() throws FactoryException, TetrominoExeption {
        board.clear();
        scoreManager.reset();
        gameTimer.start();

        currentTetromino = spawnCentered();
        nextTetromino = spawnCentered();

        if (!board.canPlace(currentTetromino)) {
            state = GameState.GAME_OVER;
            return;
        }

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

    public void moveDownLeft() {
        if (state != GameState.RUNNING) {
            return;
        }

        currentTetromino.moveLeft();
        currentTetromino.moveDown();

        if (!board.canPlace(currentTetromino)) {
            currentTetromino.moveUp();
            currentTetromino.moveRight();
        }
    }

    public void moveDownRight() {
        if (state != GameState.RUNNING) {
            return;
        }

        currentTetromino.moveRight();
        currentTetromino.moveDown();

        if (!board.canPlace(currentTetromino)) {
            currentTetromino.moveUp();
            currentTetromino.moveLeft();
        }
    }

    public void rotateClockwise() {
        if (state != GameState.RUNNING) {
            return;
        }

        currentTetromino.rotateClockwise();

        if (!tryWallKick()) {
            currentTetromino.rotateCounterClockwise();
        }
    }

    public void rotateCounterClockwise() {
        if (state != GameState.RUNNING) {
            return;
        }

        currentTetromino.rotateCounterClockwise();

        if (!tryWallKick()) {
            currentTetromino.rotateCounterClockwise();
        }
    }

    public void tick() throws FactoryException, TetrominoExeption {
        if (state != GameState.RUNNING) {
            return;
        }

        currentTetromino.moveDown();

        if (!board.canPlace(currentTetromino)) {
            currentTetromino.moveUp();
            lockCurrentTetromino();
        }
    }

    public void hardDrop() {
        if (state != GameState.RUNNING) {
            return;
        }

        moveToDropPosition(currentTetromino);
        lockCurrentTetromino();
    }

    private void moveToDropPosition(Tetromino tetromino) {
        while (true) {
            tetromino.moveDown();

            if (!board.canPlace(tetromino)) {
                tetromino.moveUp();
                return;
            }
        }
    }

    private void lockCurrentTetromino() {
        board.lock(currentTetromino);
        notifyBlockLocked();

        int lines = board.clearFullLines();
        scoreManager.addClearedLines(lines);

        spawnNext();
    }

    private void spawnNext() {
        if (!board.canPlace(nextTetromino)) {
            currentTetromino = null;
            state = GameState.GAME_OVER;
            gameTimer.pause();
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

    private void notifyBlockLocked() {
        if (onBlockLocked != null) {
            onBlockLocked.run();
        }
    }
}