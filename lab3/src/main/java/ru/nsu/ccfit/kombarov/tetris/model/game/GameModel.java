package ru.nsu.ccfit.kombarov.tetris.model.game;

import ru.nsu.ccfit.kombarov.tetris.exceptions.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.exceptions.TetrominoExeption;
import ru.nsu.ccfit.kombarov.tetris.model.board.Board;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.TetrominoGenerator;

import java.util.logging.Logger;

public class GameModel {

    private static final Logger logger = Logger.getLogger(GameModel.class.getName());

    private final Board board;
    private final TetrominoGenerator generator;

    private Tetromino currentTetromino;
    private Tetromino nextTetromino;

    private GameState state = GameState.NOT_STARTED;

    private final ScoreManager scoreManager = new ScoreManager();

    private final GameTimer gameTimer = new GameTimer();

    private Runnable onBlockLocked;
    private Runnable onLineCleared;

    public GameModel(Board board, TetrominoGenerator generator) {
        this.board = board;
        this.generator = generator;
        logger.info("GameModel Created");
    }

    public Board getBoard() {
        logger.fine("get Board");
        return board;
    }

    public Tetromino getCurrentTetromino() {
        logger.fine("get Current Tetromino");
        return currentTetromino;
    }

    public Tetromino getNextTetromino() {
        logger.fine("get Next Tetromino");
        return nextTetromino;
    }

    public Tetromino getGhostTetromino() {
        logger.fine("get Ghost Tetromino");
        if (currentTetromino == null) {
            return null;
        }

        Tetromino ghost = currentTetromino.copy();
        moveToDropPosition(ghost);
        return ghost;
    }

    public int getScore() {
        logger.fine("get Score");
        return scoreManager.getScore();
    }

    public int getLevel() {
        logger.fine("get Level");
        return scoreManager.getLevel();
    }

    public int getClearedLines() {
        logger.fine("get Cleared Lines");
        return scoreManager.getClearedLines();
    }

    public GameState getState() {
        logger.fine("get State");
        return state;
    }

    public String getFormattedTime() {
        logger.fine("get Formated Time");
        return gameTimer.getFormattedTime();
    }

    public void setOnBlockLocked(Runnable onBlockLocked) {
        logger.fine("set On Block Locked");
        this.onBlockLocked = onBlockLocked;
    }

    public void setOnLineCleared(Runnable onLineCleared) {
        logger.fine("set On Line Cleared");
        this.onLineCleared = onLineCleared;
    }

    public void pause() {
        logger.fine("pause");
        if (state != GameState.PAUSED) {
            state = GameState.PAUSED;
            gameTimer.pause();
        }
    }

    public void resume() {
        logger.fine("resume");
        if (state != GameState.RUNNING) {
            state = GameState.RUNNING;
            gameTimer.resume();
        }
    }

    public void newGame() throws FactoryException, TetrominoExeption {
        logger.info("start new game");
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
        logger.fine("move Left");
        if (state != GameState.RUNNING) {
            return;
        }

        currentTetromino.moveLeft();

        if (!board.canPlace(currentTetromino)) {
            currentTetromino.moveRight();
        }
    }

    public void moveRight() {
        logger.fine("move Right");
        if (state != GameState.RUNNING) {
            return;
        }

        currentTetromino.moveRight();

        if (!board.canPlace(currentTetromino)) {
            currentTetromino.moveLeft();
        }
    }

    public void moveDownLeft() {
        logger.fine("move Down Left");
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
        logger.fine("move Down Right");
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
        logger.fine("rotate Clockwise");
        if (state != GameState.RUNNING) {
            return;
        }

        currentTetromino.rotateClockwise();

        if (!tryWallKick()) {
            currentTetromino.rotateCounterClockwise();
        }
    }

    public void rotateCounterClockwise() {
        logger.fine("rotate Counter Clockwise");
        if (state != GameState.RUNNING) {
            return;
        }

        currentTetromino.rotateCounterClockwise();

        if (!tryWallKick()) {
            currentTetromino.rotateClockwise();
        }
    }

    public void tick() throws FactoryException, TetrominoExeption {
        logger.finest("tick");
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
        logger.fine("hard Drope");
        if (state != GameState.RUNNING) {
            return;
        }

        moveToDropPosition(currentTetromino);
        lockCurrentTetromino();
    }

    private void moveToDropPosition(Tetromino tetromino) {
        logger.finer("move To Drope Position");
        while (true) {
            tetromino.moveDown();

            if (!board.canPlace(tetromino)) {
                tetromino.moveUp();
                return;
            }
        }
    }

    private void lockCurrentTetromino() {
        logger.finer("lock Current Tetromino");
        board.lock(currentTetromino);
        notifyBlockLocked();

        int lines = board.clearFullLines();
        if (lines > 0) {
            notifyLineCleared();
        }

        scoreManager.addClearedLines(lines);

        spawnNext();
    }

    private void spawnNext() {
        logger.finer("spawn Next");
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
        logger.finer("spawn Centred");
        Tetromino tetromino = generator.oneFromBag();

        int startX = (board.getWidth() - tetromino.getWidth()) / 2;

        tetromino.setPosition(startX, 0);

        return tetromino;
    }

    private boolean tryWallKick() {
        logger.finer("try Wall Kick");
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
        logger.finer("notify Block Locked");
        if (onBlockLocked != null) {
            onBlockLocked.run();
        }
    }

    private void notifyLineCleared() {
        logger.finer("notify Line Cleared");
        if (onLineCleared != null) {
            onLineCleared.run();
        }
    }
}