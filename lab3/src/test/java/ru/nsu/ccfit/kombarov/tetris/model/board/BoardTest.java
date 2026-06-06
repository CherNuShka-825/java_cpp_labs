package ru.nsu.ccfit.kombarov.tetris.model.board;

import org.junit.jupiter.api.Test;
import ru.nsu.ccfit.kombarov.tetris.model.Coordinate;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BoardTest {

    private static Tetromino tetromino(String type, Coordinate... blocks) {
        Tetromino tetromino = mock(Tetromino.class);

        when(tetromino.getType()).thenReturn(type);
        when(tetromino.getBlocks()).thenReturn(List.of(blocks));

        return tetromino;
    }

    @Test
    void constructorShouldCreateEmptyBoardWithGivenSize() {
        Board board = new Board(10, 20);

        assertEquals(10, board.getWidth());
        assertEquals(20, board.getHeight());

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                assertNull(board.getCell(x, y));
            }
        }
    }

    @Test
    void isInsideShouldCorrectlyCheckBorders() {
        Board board = new Board(10, 20);

        assertTrue(board.isInside(0, 0));
        assertTrue(board.isInside(9, 19));

        assertFalse(board.isInside(-1, 0));
        assertFalse(board.isInside(0, -1));
        assertFalse(board.isInside(10, 0));
        assertFalse(board.isInside(0, 20));
    }

    @Test
    void canPlaceShouldReturnTrueWhenAllBlocksAreInsideAndCellsAreEmpty() {
        Board board = new Board(10, 20);

        Tetromino tetromino = tetromino(
                "T",
                new Coordinate(4, 0),
                new Coordinate(3, 1),
                new Coordinate(4, 1),
                new Coordinate(5, 1)
        );

        assertTrue(board.canPlace(tetromino));
    }

    @Test
    void canPlaceShouldReturnFalseWhenAnyBlockIsOutsideBoard() {
        Board board = new Board(10, 20);

        Tetromino tetromino = tetromino(
                "I",
                new Coordinate(8, 0),
                new Coordinate(9, 0),
                new Coordinate(10, 0),
                new Coordinate(11, 0)
        );

        assertFalse(board.canPlace(tetromino));
    }

    @Test
    void lockShouldWriteTetrominoTypeAndCanPlaceShouldSeeCollision() {
        Board board = new Board(10, 20);

        Tetromino tetromino = tetromino(
                "O",
                new Coordinate(4, 0),
                new Coordinate(5, 0),
                new Coordinate(4, 1),
                new Coordinate(5, 1)
        );

        board.lock(tetromino);

        assertEquals("O", board.getCell(4, 0));
        assertEquals("O", board.getCell(5, 0));
        assertEquals("O", board.getCell(4, 1));
        assertEquals("O", board.getCell(5, 1));

        Tetromino anotherTetromino = tetromino(
                "T",
                new Coordinate(5, 1),
                new Coordinate(6, 1),
                new Coordinate(7, 1),
                new Coordinate(6, 2)
        );

        assertFalse(board.canPlace(anotherTetromino));
    }

    @Test
    void clearShouldRemoveAllCells() {
        Board board = new Board(10, 20);

        board.lock(tetromino(
                "L",
                new Coordinate(0, 0),
                new Coordinate(0, 1),
                new Coordinate(0, 2),
                new Coordinate(1, 2)
        ));

        board.clear();

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                assertNull(board.getCell(x, y));
            }
        }
    }

    @Test
    void clearFullLinesShouldReturnZeroWhenThereAreNoFullLines() {
        Board board = new Board(4, 4);

        board.lock(tetromino(
                "I",
                new Coordinate(0, 3),
                new Coordinate(1, 3),
                new Coordinate(2, 3)
        ));

        int cleared = board.clearFullLines();

        assertEquals(0, cleared);
        assertEquals("I", board.getCell(0, 3));
        assertEquals("I", board.getCell(1, 3));
        assertEquals("I", board.getCell(2, 3));
        assertNull(board.getCell(3, 3));
    }

    @Test
    void clearFullLinesShouldRemoveConsecutiveFullLinesAndShiftUpperRowsDown() {
        Board board = new Board(4, 5);

        // Неполная строка сверху, она должна упасть вниз после удаления.
        board.lock(tetromino(
                "T",
                new Coordinate(1, 1),
                new Coordinate(2, 1)
        ));

        // Две подряд заполненные строки.
        board.lock(tetromino(
                "A",
                new Coordinate(0, 3),
                new Coordinate(1, 3),
                new Coordinate(2, 3),
                new Coordinate(3, 3)
        ));

        board.lock(tetromino(
                "B",
                new Coordinate(0, 4),
                new Coordinate(1, 4),
                new Coordinate(2, 4),
                new Coordinate(3, 4)
        ));

        int cleared = board.clearFullLines();

        assertEquals(2, cleared);

        assertNull(board.getCell(0, 3));
        assertEquals("T", board.getCell(1, 3));
        assertEquals("T", board.getCell(2, 3));
        assertNull(board.getCell(3, 3));

        for (int x = 0; x < board.getWidth(); x++) {
            assertNull(board.getCell(x, 0));
            assertNull(board.getCell(x, 1));
            assertNull(board.getCell(x, 2));
            assertNull(board.getCell(x, 4));
        }
    }

    @Test
    void clearFullLinesShouldRemoveTopLine() {
        Board board = new Board(4, 4);

        board.lock(tetromino(
                "X",
                new Coordinate(0, 0),
                new Coordinate(1, 0),
                new Coordinate(2, 0),
                new Coordinate(3, 0)
        ));

        int cleared = board.clearFullLines();

        assertEquals(1, cleared);

        for (int x = 0; x < board.getWidth(); x++) {
            assertNull(board.getCell(x, 0));
        }
    }
}