package ru.nsu.ccfit.kombarov.tetris.model.game;

import org.junit.jupiter.api.Test;
import ru.nsu.ccfit.kombarov.tetris.exceptions.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.exceptions.TetrominoExeption;
import ru.nsu.ccfit.kombarov.tetris.model.Coordinate;
import ru.nsu.ccfit.kombarov.tetris.model.board.Board;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.TetrominoGenerator;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameModelTest {

    @Test
    void newGameShouldClearBoardResetScoreSpawnTetrominoesAndStartRunning()
            throws FactoryException, TetrominoExeption {
        Board board = new Board(10, 20);
        TetrominoGenerator generator = mock(TetrominoGenerator.class);

        Tetromino current = new OneBlockTetromino("A", 0, 0);
        Tetromino next = new OneBlockTetromino("B", 0, 0);

        when(generator.oneFromBag()).thenReturn(current, next);

        GameModel model = new GameModel(board, generator);

        model.newGame();

        assertSame(current, model.getCurrentTetromino());
        assertSame(next, model.getNextTetromino());
        assertEquals(GameState.RUNNING, model.getState());

        assertEquals(0, model.getScore());
        assertEquals(1, model.getLevel());
        assertEquals(0, model.getClearedLines());

        assertEquals(4, current.getX());
        assertEquals(0, current.getY());

        assertEquals(4, next.getX());
        assertEquals(0, next.getY());

        verify(generator, times(2)).oneFromBag();
    }

    @Test
    void newGameShouldSetGameOverWhenCurrentTetrominoCannotBePlaced()
            throws FactoryException, TetrominoExeption {
        Board board = mock(Board.class);
        TetrominoGenerator generator = mock(TetrominoGenerator.class);

        Tetromino current = new OneBlockTetromino("A", 0, 0);
        Tetromino next = new OneBlockTetromino("B", 0, 0);

        when(board.getWidth()).thenReturn(10);
        when(board.canPlace(current)).thenReturn(false);
        when(generator.oneFromBag()).thenReturn(current, next);

        GameModel model = new GameModel(board, generator);

        model.newGame();

        assertEquals(GameState.GAME_OVER, model.getState());
        assertSame(current, model.getCurrentTetromino());
        assertSame(next, model.getNextTetromino());

        verify(board).clear();
        verify(board).canPlace(current);
    }

    @Test
    void pauseAndResumeShouldChangeState() throws FactoryException, TetrominoExeption {
        Board board = new Board(10, 20);
        TetrominoGenerator generator = mock(TetrominoGenerator.class);

        when(generator.oneFromBag()).thenReturn(
                new OneBlockTetromino("A", 0, 0),
                new OneBlockTetromino("B", 0, 0)
        );

        GameModel model = new GameModel(board, generator);

        model.newGame();

        model.pause();
        assertEquals(GameState.PAUSED, model.getState());

        model.resume();
        assertEquals(GameState.RUNNING, model.getState());
    }

    @Test
    void moveLeftAndMoveRightShouldMoveOnlyWhenPlacementIsValid()
            throws FactoryException, TetrominoExeption {
        Board board = new Board(3, 3);
        TetrominoGenerator generator = mock(TetrominoGenerator.class);

        Tetromino current = new OneBlockTetromino("A", 0, 0);
        Tetromino next = new OneBlockTetromino("B", 0, 0);

        when(generator.oneFromBag()).thenReturn(current, next);

        GameModel model = new GameModel(board, generator);

        model.newGame();

        assertEquals(1, current.getX());

        model.moveLeft();
        assertEquals(0, current.getX());

        model.moveLeft();
        assertEquals(0, current.getX());

        model.moveRight();
        assertEquals(1, current.getX());

        model.moveRight();
        assertEquals(2, current.getX());

        model.moveRight();
        assertEquals(2, current.getX());
    }

    @Test
    void moveDownLeftAndMoveDownRightShouldRollbackWhenPlacementIsInvalid()
            throws FactoryException, TetrominoExeption {
        Board board = new Board(1, 1);
        TetrominoGenerator generator = mock(TetrominoGenerator.class);

        Tetromino current = new OneBlockTetromino("A", 0, 0);
        Tetromino next = new OneBlockTetromino("B", 0, 0);

        when(generator.oneFromBag()).thenReturn(current, next);

        GameModel model = new GameModel(board, generator);

        model.newGame();

        assertEquals(0, current.getX());
        assertEquals(0, current.getY());

        model.moveDownLeft();

        assertEquals(0, current.getX());
        assertEquals(0, current.getY());

        model.moveDownRight();

        assertEquals(0, current.getX());
        assertEquals(0, current.getY());
    }

    @Test
    void rotateClockwiseShouldRollbackWhenRotatedTetrominoCannotBePlaced()
            throws FactoryException, TetrominoExeption {
        Board board = new Board(1, 1);
        TetrominoGenerator generator = mock(TetrominoGenerator.class);

        Tetromino current = new TwoRotationTetromino("A", 0, 0);
        Tetromino next = new OneBlockTetromino("B", 0, 0);

        when(generator.oneFromBag()).thenReturn(current, next);

        GameModel model = new GameModel(board, generator);

        model.newGame();

        assertEquals(0, current.getRotation());

        model.rotateClockwise();

        assertEquals(0, current.getRotation());
    }

    @Test
    void rotateCounterClockwiseShouldRollbackWhenRotatedTetrominoCannotBePlaced()
            throws FactoryException, TetrominoExeption {
        Board board = new Board(1, 1);
        TetrominoGenerator generator = mock(TetrominoGenerator.class);

        Tetromino current = new FourRotationTetromino("A", 0, 0);
        Tetromino next = new OneBlockTetromino("B", 0, 0);

        when(generator.oneFromBag()).thenReturn(current, next);

        GameModel model = new GameModel(board, generator);

        model.newGame();

        assertEquals(0, current.getRotation());

        model.rotateCounterClockwise();

        assertEquals(0, current.getRotation());
    }

    @Test
    void tickShouldMoveCurrentTetrominoDownWhenCellBelowIsFree()
            throws FactoryException, TetrominoExeption {
        Board board = new Board(3, 3);
        TetrominoGenerator generator = mock(TetrominoGenerator.class);

        Tetromino current = new OneBlockTetromino("A", 0, 0);
        Tetromino next = new OneBlockTetromino("B", 0, 0);

        when(generator.oneFromBag()).thenReturn(current, next);

        GameModel model = new GameModel(board, generator);

        model.newGame();

        assertEquals(0, current.getY());

        model.tick();

        assertEquals(1, current.getY());
        assertSame(current, model.getCurrentTetromino());
        assertEquals(GameState.RUNNING, model.getState());
    }

    @Test
    void tickShouldLockTetrominoClearLinesUpdateScoreAndSpawnNext()
            throws FactoryException, TetrominoExeption {
        Board board = new Board(1, 1);
        TetrominoGenerator generator = mock(TetrominoGenerator.class);

        Tetromino current = new OneBlockTetromino("A", 0, 0);
        Tetromino next = new OneBlockTetromino("B", 0, 0);
        Tetromino afterNext = new OneBlockTetromino("C", 0, 0);

        when(generator.oneFromBag()).thenReturn(current, next, afterNext);

        AtomicInteger blockLockedCalls = new AtomicInteger();
        AtomicInteger lineClearedCalls = new AtomicInteger();

        GameModel model = new GameModel(board, generator);
        model.setOnBlockLocked(blockLockedCalls::incrementAndGet);
        model.setOnLineCleared(lineClearedCalls::incrementAndGet);

        model.newGame();
        model.tick();

        assertEquals(1, blockLockedCalls.get());
        assertEquals(1, lineClearedCalls.get());

        assertEquals(100, model.getScore());
        assertEquals(1, model.getClearedLines());

        assertSame(next, model.getCurrentTetromino());
        assertSame(afterNext, model.getNextTetromino());
        assertEquals(GameState.RUNNING, model.getState());

        assertNull(board.getCell(0, 0));

        verify(generator, times(3)).oneFromBag();
    }

    @Test
    void hardDropShouldMoveTetrominoToBottomAndSetGameOverWhenNextCannotBePlaced()
            throws FactoryException, TetrominoExeption {
        Board board = new Board(2, 1);
        TetrominoGenerator generator = mock(TetrominoGenerator.class);

        Tetromino current = new OneBlockTetromino("A", 0, 0);
        Tetromino next = new OneBlockTetromino("B", 0, 0);

        when(generator.oneFromBag()).thenReturn(current, next);

        GameModel model = new GameModel(board, generator);

        model.newGame();
        model.hardDrop();

        assertEquals(GameState.GAME_OVER, model.getState());
        assertNull(model.getCurrentTetromino());

        assertEquals("A", board.getCell(0, 0));
    }

    @Test
    void getGhostTetrominoShouldReturnCopyAtDropPositionWithoutMovingCurrent()
            throws FactoryException, TetrominoExeption {
        Board board = new Board(1, 4);
        TetrominoGenerator generator = mock(TetrominoGenerator.class);

        Tetromino current = new OneBlockTetromino("A", 0, 0);
        Tetromino next = new OneBlockTetromino("B", 0, 0);

        when(generator.oneFromBag()).thenReturn(current, next);

        GameModel model = new GameModel(board, generator);

        assertNull(model.getGhostTetromino());

        model.newGame();

        Tetromino ghost = model.getGhostTetromino();

        assertNotNull(ghost);
        assertNotSame(current, ghost);

        assertEquals(0, current.getX());
        assertEquals(0, current.getY());

        assertEquals(0, ghost.getX());
        assertEquals(3, ghost.getY());
    }

    @Test
    void actionsShouldDoNothingWhenGameIsNotRunning() throws FactoryException, TetrominoExeption {
        Board board = new Board(3, 3);
        TetrominoGenerator generator = mock(TetrominoGenerator.class);

        Tetromino current = new OneBlockTetromino("A", 0, 0);
        Tetromino next = new OneBlockTetromino("B", 0, 0);

        when(generator.oneFromBag()).thenReturn(current, next);

        GameModel model = new GameModel(board, generator);

        model.newGame();
        model.pause();

        int x = current.getX();
        int y = current.getY();

        model.moveLeft();
        model.moveRight();
        model.moveDownLeft();
        model.moveDownRight();
        model.rotateClockwise();
        model.rotateCounterClockwise();
        model.tick();
        model.hardDrop();

        assertEquals(x, current.getX());
        assertEquals(y, current.getY());
        assertEquals(GameState.PAUSED, model.getState());
    }

    private static class OneBlockTetromino extends Tetromino {

        private static final Coordinate[][] SHAPES = {
                {
                        new Coordinate(0, 0)
                }
        };

        private OneBlockTetromino(String type, int x, int y) {
            super(type, x, y);
        }

        @Override
        protected Coordinate[][] getShapes() {
            return SHAPES;
        }

        @Override
        protected Tetromino createSameType(int x, int y) {
            return new OneBlockTetromino(type, x, y);
        }
    }

    private static class TwoRotationTetromino extends Tetromino {

        private static final Coordinate[][] SHAPES = {
                {
                        new Coordinate(0, 0)
                },
                {
                        new Coordinate(0, 1)
                }
        };

        private TwoRotationTetromino(String type, int x, int y) {
            super(type, x, y);
        }

        @Override
        protected Coordinate[][] getShapes() {
            return SHAPES;
        }

        @Override
        protected Tetromino createSameType(int x, int y) {
            return new TwoRotationTetromino(type, x, y);
        }
    }

    private static class FourRotationTetromino extends Tetromino {

        private static final Coordinate[][] SHAPES = {
                {
                        new Coordinate(0, 0)
                },
                {
                        new Coordinate(0, 0)
                },
                {
                        new Coordinate(0, 0)
                },
                {
                        new Coordinate(0, 1)
                }
        };

        private FourRotationTetromino(String type, int x, int y) {
            super(type, x, y);
        }

        @Override
        protected Coordinate[][] getShapes() {
            return SHAPES;
        }

        @Override
        protected Tetromino createSameType(int x, int y) {
            return new FourRotationTetromino(type, x, y);
        }
    }
}