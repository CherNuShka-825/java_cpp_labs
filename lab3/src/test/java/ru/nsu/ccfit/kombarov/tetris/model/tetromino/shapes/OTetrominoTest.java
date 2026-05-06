package ru.nsu.ccfit.kombarov.tetris.model.tetromino.shapes;

import org.junit.jupiter.api.Test;
import ru.nsu.ccfit.kombarov.tetris.model.Coordinate;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class OTetrominoTest {

    @Test
    void constructorShouldCreateOTetrominoWithInitialState() {
        OTetromino tetromino = new OTetromino(10, 20);

        assertEquals("O", tetromino.getType());
        assertEquals(10, tetromino.getX());
        assertEquals(20, tetromino.getY());
        assertEquals(0, tetromino.getRotation());
    }

    @Test
    void getBlocksShouldReturnSquareShapeWithOffset() {
        OTetromino tetromino = new OTetromino(10, 20);

        assertBlocks(
                tetromino.getBlocks(),
                "(11,20)",
                "(12,20)",
                "(11,21)",
                "(12,21)"
        );
    }

    @Test
    void rotateClockwiseShouldKeepSameRotationAndSameBlocks() {
        OTetromino tetromino = new OTetromino(10, 20);

        tetromino.rotateClockwise();

        assertEquals(0, tetromino.getRotation());
        assertBlocks(
                tetromino.getBlocks(),
                "(11,20)",
                "(12,20)",
                "(11,21)",
                "(12,21)"
        );
    }

    @Test
    void rotateCounterClockwiseShouldKeepSameRotationAndSameBlocks() {
        OTetromino tetromino = new OTetromino(10, 20);

        tetromino.rotateCounterClockwise();

        assertEquals(0, tetromino.getRotation());
        assertBlocks(
                tetromino.getBlocks(),
                "(11,20)",
                "(12,20)",
                "(11,21)",
                "(12,21)"
        );
    }

    @Test
    void moveMethodsShouldChangePositionAndShiftBlocks() {
        OTetromino tetromino = new OTetromino(10, 20);

        tetromino.moveRight();

        assertEquals(11, tetromino.getX());
        assertEquals(20, tetromino.getY());
        assertBlocks(
                tetromino.getBlocks(),
                "(12,20)",
                "(13,20)",
                "(12,21)",
                "(13,21)"
        );

        tetromino.moveLeft();

        assertEquals(10, tetromino.getX());
        assertEquals(20, tetromino.getY());
        assertBlocks(
                tetromino.getBlocks(),
                "(11,20)",
                "(12,20)",
                "(11,21)",
                "(12,21)"
        );

        tetromino.moveDown();

        assertEquals(10, tetromino.getX());
        assertEquals(21, tetromino.getY());
        assertBlocks(
                tetromino.getBlocks(),
                "(11,21)",
                "(12,21)",
                "(11,22)",
                "(12,22)"
        );

        tetromino.moveUp();

        assertEquals(10, tetromino.getX());
        assertEquals(20, tetromino.getY());
        assertBlocks(
                tetromino.getBlocks(),
                "(11,20)",
                "(12,20)",
                "(11,21)",
                "(12,21)"
        );
    }

    @Test
    void setPositionShouldMoveTetrominoToGivenPosition() {
        OTetromino tetromino = new OTetromino(0, 0);

        tetromino.setPosition(5, 7);

        assertEquals(5, tetromino.getX());
        assertEquals(7, tetromino.getY());
        assertBlocks(
                tetromino.getBlocks(),
                "(6,7)",
                "(7,7)",
                "(6,8)",
                "(7,8)"
        );
    }

    @Test
    void getWidthAndGetHeightShouldReturnSquareBounds() {
        OTetromino tetromino = new OTetromino(0, 0);

        assertEquals(3, tetromino.getWidth());
        assertEquals(2, tetromino.getHeight());

        tetromino.rotateClockwise();

        assertEquals(3, tetromino.getWidth());
        assertEquals(2, tetromino.getHeight());

        tetromino.rotateCounterClockwise();

        assertEquals(3, tetromino.getWidth());
        assertEquals(2, tetromino.getHeight());
    }

    @Test
    void copyShouldCreateIndependentTetrominoWithSameState() {
        OTetromino tetromino = new OTetromino(10, 20);
        tetromino.moveDown();

        Tetromino copy = tetromino.copy();

        assertNotSame(tetromino, copy);
        assertInstanceOf(OTetromino.class, copy);

        assertEquals(tetromino.getType(), copy.getType());
        assertEquals(tetromino.getX(), copy.getX());
        assertEquals(tetromino.getY(), copy.getY());
        assertEquals(tetromino.getRotation(), copy.getRotation());
        assertBlocks(
                copy.getBlocks(),
                "(11,21)",
                "(12,21)",
                "(11,22)",
                "(12,22)"
        );

        copy.moveRight();

        assertEquals(10, tetromino.getX());
        assertEquals(11, copy.getX());
    }

    private static void assertBlocks(List<Coordinate> actualBlocks, String... expectedBlocks) {
        Set<String> actual = actualBlocks.stream()
                .map(block -> "(" + block.getX() + "," + block.getY() + ")")
                .collect(Collectors.toSet());

        assertEquals(Set.of(expectedBlocks), actual);
    }
}