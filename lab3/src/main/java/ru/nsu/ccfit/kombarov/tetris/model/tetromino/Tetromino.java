package ru.nsu.ccfit.kombarov.tetris.model.tetromino;

import ru.nsu.ccfit.kombarov.tetris.model.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class Tetromino {

    private static final Logger logger = Logger.getLogger(Tetromino.class.getName());

    protected int x;
    protected int y;
    protected int rotation;

    protected final String type;

    protected Tetromino(String type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.rotation = 0;
        logger.info("created tetromino");
    }

    public String getType() {
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

    public Tetromino copy() {
        Tetromino copy = createSameType(x, y);
        copy.rotation = this.rotation;
        return copy;
    }

    public int getWidth() {
        Coordinate[] shape = getShapes()[rotation];

        int maxX = 0;

        for (Coordinate c : shape) {
            maxX = Math.max(maxX, c.getX());
        }

        return maxX + 1;
    }

    public int getHeight() {
        Coordinate[] shape = getShapes()[rotation];

        int maxY = 0;

        for (Coordinate c : shape) {
            maxY = Math.max(maxY, c.getY());
        }

        return maxY + 1;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected abstract Coordinate[][] getShapes();

    protected abstract Tetromino createSameType(int x, int y);
}