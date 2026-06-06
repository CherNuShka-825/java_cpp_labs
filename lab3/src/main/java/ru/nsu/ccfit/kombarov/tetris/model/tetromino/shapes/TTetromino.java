package ru.nsu.ccfit.kombarov.tetris.model.tetromino.shapes;

import ru.nsu.ccfit.kombarov.tetris.model.Coordinate;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;

public class TTetromino extends Tetromino {

    private static final Coordinate[][] SHAPES = {
            {
                    new Coordinate(1, 0),
                    new Coordinate(0, 1),
                    new Coordinate(1, 1),
                    new Coordinate(2, 1)
            },
            {
                    new Coordinate(1, 0),
                    new Coordinate(1, 1),
                    new Coordinate(2, 1),
                    new Coordinate(1, 2)
            },
            {
                    new Coordinate(0, 1),
                    new Coordinate(1, 1),
                    new Coordinate(2, 1),
                    new Coordinate(1, 2)
            },
            {
                    new Coordinate(1, 0),
                    new Coordinate(0, 1),
                    new Coordinate(1, 1),
                    new Coordinate(1, 2)
            }
    };

    public TTetromino(int x, int y) {
        super("T", x, y);
    }

    @Override
    protected Coordinate[][] getShapes() {
        return SHAPES;
    }

    @Override
    protected Tetromino createSameType(int x, int y) {
        return new TTetromino(x, y);
    }
}