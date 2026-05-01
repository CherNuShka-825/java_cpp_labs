package ru.nsu.ccfit.kombarov.tetris.model.tetromino;

import ru.nsu.ccfit.kombarov.tetris.model.Coordinate;

import java.util.ArrayList;
import java.util.List;

public abstract class Tetromino {

    protected int x;
    protected int y;
    protected int rotation;

    protected final TetrominoType type;

    protected Tetromino(TetrominoType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.rotation = 0;
    }

    public TetrominoType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRotation() {
        return rotation;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void moveDown() {
        y++;
    }

    public void moveUp() {
        y--;
    }

    public void rotateClockwise() {
        rotation = (rotation + 1) % getShapes().length;
    }

    public void rotateCounterClockwise() {
        rotation = (rotation - 1 + getShapes().length) % getShapes().length;
    }

    public List<Coordinate> getBlocks() {
        Coordinate[] shape = getShapes()[rotation];

        List<Coordinate> blocks = new ArrayList<>();

        for (Coordinate block : shape) {
            int blockX = x + block.getX();
            int blockY = y + block.getY();

            blocks.add(new Coordinate(blockX, blockY));
        }

        return blocks;
    }

    protected abstract Coordinate[][] getShapes();
}