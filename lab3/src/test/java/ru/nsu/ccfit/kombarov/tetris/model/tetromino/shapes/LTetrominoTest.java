package ru.nsu.ccfit.kombarov.tetris.model.tetromino.shapes;

import org.junit.jupiter.api.Test;
import ru.nsu.ccfit.kombarov.tetris.model.Coordinate;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class LTetrominoTest {

    @Test
    void constructorShouldCreateLTetrominoWithInitialState() {
        LTetromino tetromino = new LTetromino(10, 20);

        assertEquals("L", tetromino.getType());
        assertEquals(10, tetromino.getX());
        assertEquals(20, tetromino.getY());
        assertEquals(0, tetromino.getRotation());
    }

    @Test
    void getBlocksShouldReturnInitialShapeWithOffset() {
        LTetromino tetromino = new LTetromino(10, 20);

        assertBlocks(
                tetromino.getBlocks(),
                "(12,20)",
                "(10,21)",
                "(11,21)",
                "(12,21)"
        );
    }

    @Test
    void rotateClockwiseShouldSwitchThroughAllFourLShapes() {
        LTetromino tetromino = new LTetromino(10, 20);

        assertEquals(0, tetromino.getRotation());
        assertBlocks(
                tetromino.getBlocks(),
                "(12,20)",
                "(10,21)",
                "(11,21)",
                "(12,21)"
        );

        tetromino.rotateClockwise();

        assertEquals(1, tetromino.getRotation());
        assertBlocks(
                tetromino.getBlocks(),
                "(11,20)",
                "(11,21)",
                "(11,22)",
                "(12,22)"
        );

        tetromino.rotateClockwise();

        assertEquals(2, tetromino.getRotation());
        assertBlocks(
                tetromino.getBlocks(),
                "(10,21)",
                "(11,21)",
                "(12,21)",
                "(10,22)"
        );

        tetromino.rotateClockwise();

        assertEquals(3, tetromino.getRotation());
        assertBlocks(
                tetromino.getBlocks(),
                "(10,20)",
                "(11,20)",
                "(11,21)",
                "(11,22)"
        );

        tetromino.rotateClockwise();

        assertEquals(0, tetromino.getRotation());
        assertBlocks(
                tetromino.getBlocks(),
                "(12,20)",
                "(10,21)",
                "(11,21)",
                "(12,21)"
        );
    }

    @Test
    void rotateCounterClockwiseShouldSwitchThroughAllFourLShapes() {
        LTetromino tetromino = new LTetromino(10, 20);

        tetromino.rotateCounterClockwise();

        assertEquals(3, tetromino.getRotation());
        assertBlocks(
                tetromino.getBlocks(),
                "(10,20)",
                "(11,20)",
                "(11,21)",
                "(11,22)"
        );

        tetromino.rotateCounterClockwise();

        assertEquals(2, tetromino.getRotation());
        assertBlocks(
                tetromino.getBlocks(),
                "(10,21)",
                "(11,21)",
                "(12,21)",
                "(10,22)"
        );

        tetromino.rotateCounterClockwise();

        assertEquals(1, tetromino.getRotation());
        assertBlocks(
                tetromino.getBlocks(),
                "(11,20)",
                "(11,21)",
                "(11,22)",
                "(12,22)"
        );

        tetromino.rotateCounterClockwise();

        assertEquals(0, tetromino.getRotation());
        assertBlocks(
                tetromino.getBlocks(),
                "(12,20)",
                "(10,21)",
                "(11,21)",
                "(12,21)"
        );
    }

    @Test
    void moveMethodsShouldChangePositionAndShiftBlocks() {
        LTetromino tetromino = new LTetromino(10, 20);

        tetromino.moveRight();

        assertEquals(11, tetromino.getX());
        assertEquals(20, tetromino.getY());
        assertBlocks(
                tetromino.getBlocks(),
                "(13,20)",
                "(11,21)",
                "(12,21)",
                "(13,21)"
        );

        tetromino.moveLeft();

        assertEquals(10, tetromino.getX());
        assertEquals(20, tetromino.getY());
        assertBlocks(
                tetromino.getBlocks(),
                "(12,20)",
                "(10,21)",
                "(11,21)",
                "(12,21)"
        );

        tetromino.moveDown();

        assertEquals(10, tetromino.getX());
        assertEquals(21, tetromino.getY());
        assertBlocks(
                tetromino.getBlocks(),
                "(12,21)",
                "(10,22)",
                "(11,22)",
                "(12,22)"
        );

        tetromino.moveUp();

        assertEquals(10, tetromino.getX());
        assertEquals(20, tetromino.getY());
        assertBlocks(
                tetromino.getBlocks(),
                "(12,20)",
                "(10,21)",
                "(11,21)",
                "(12,21)"
        );
    }

    @Test
    void setPositionShouldMoveTetrominoToGivenPosition() {
        LTetromino tetromino = new LTetromino(0, 0);

        tetromino.setPosition(5, 7);

        assertEquals(5, tetromino.getX());
        assertEquals(7, tetromino.getY());
        assertBlocks(
                tetromino.getBlocks(),
                "(7,7)",
                "(5,8)",
                "(6,8)",
                "(7,8)"
        );
    }

    @Test
    void getWidthAndGetHeightShouldDependOnCurrentRotation() {
        LTetromino tetromino = new LTetromino(0, 0);

        assertEquals(3, tetromino.getWidth());
        assertEquals(2, tetromino.getHeight());

        tetromino.rotateClockwise();

        assertEquals(3, tetromino.getWidth());
        assertEquals(3, tetromino.getHeight());

        tetromino.rotateClockwise();

        assertEquals(3, tetromino.getWidth());
        assertEquals(3, tetromino.getHeight());

        tetromino.rotateClockwise();

        assertEquals(2, tetromino.getWidth());
        assertEquals(3, tetromino.getHeight());
    }

    @Test
    void copyShouldCreateIndependentTetrominoWithSameState() {
        LTetromino tetromino = new LTetromino(10, 20);
        tetromino.rotateClockwise();
        tetromino.moveDown();

        Tetromino copy = tetromino.copy();

        assertNotSame(tetromino, copy);
        assertInstanceOf(LTetromino.class, copy);

        assertEquals(tetromino.getType(), copy.getType());
        assertEquals(tetromino.getX(), copy.getX());
        assertEquals(tetromino.getY(), copy.getY());
        assertEquals(tetromino.getRotation(), copy.getRotation());
        assertBlocks(
                copy.getBlocks(),
                "(11,21)",
                "(11,22)",
                "(11,23)",
                "(12,23)"
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