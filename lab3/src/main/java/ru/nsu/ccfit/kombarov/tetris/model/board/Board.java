package ru.nsu.ccfit.kombarov.tetris.model.board;

import ru.nsu.ccfit.kombarov.tetris.model.Coordinate;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;

public class Board {

    private final int width;
    private final int height;
    private final String[][] cells;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new String[height][width];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean canPlace(Tetromino tetromino) {
        for (Coordinate block : tetromino.getBlocks()) {
            int x = block.getX();
            int y = block.getY();

            if (!isInside(x, y)) {
                return false;
            }

            if (cells[y][x] != null) {
                return false;
            }
        }
        return true;
    }

    public void lock(Tetromino tetromino) {
        for (Coordinate block : tetromino.getBlocks()) {
            int x = block.getX();
            int y = block.getY();

            cells[y][x] = tetromino.getType();
        }
    }

    public String getCell(int x, int y) {
        return cells[y][x];
    }


    public boolean isInside(int x, int y) {
        return (x >= 0 && x < width) && (y >= 0 && y < height);
    }

    public void clear() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = null;
            }
        }
    }

    public int clearFullLines() {
        int cleared = 0;

        for (int y = height - 1; y >= 0; y--) {
            if (isLineFull(y)) {
                removeLine(y);
                cleared++;
                y++;
            }
        }

        return cleared;
    }

    private boolean isLineFull(int y) {
        for (int x = 0; x < width; x++) {
            if (cells[y][x] == null) {
                return false;
            }
        }

        return true;
    }

    private void removeLine(int line) {
        for (int y = line; y > 0; y--) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = cells[y-1][x];
            }
        }

        for (int x = 0; x < width; x++) {
            cells[0][x] = null;
        }
    }
}
