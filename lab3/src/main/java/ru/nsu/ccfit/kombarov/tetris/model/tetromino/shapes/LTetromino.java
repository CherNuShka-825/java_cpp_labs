package ru.nsu.ccfit.kombarov.tetris.model.tetromino.shapes;

import ru.nsu.ccfit.kombarov.tetris.model.Coordinate;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;

public class LTetromino extends Tetromino {

    private static final Coordinate[][] SHAPES = {
            {
                    new Coordinate(2, 0),
                    new Coordinate(0, 1),
                    new Coordinate(1, 1),
                    new Coordinate(2, 1)
            },
            {
                    new Coordinate(1, 0),
                    new Coordinate(1, 1),
                    new Coordinate(1, 2),
                    new Coordinate(2, 2)
            },
            {
                    new Coordinate(0, 1),
                    new Coordinate(1, 1),
                    new Coordinate(2, 1),
                    new Coordinate(0, 2)
            },
            {
                    new Coordinate(0, 0),
                    new Coordinate(1, 0),
                    new Coordinate(1, 1),
                    new Coordinate(1, 2)
            }
    };

    public LTetromino(int x, int y) {
        super("L", x, y);
    }

    @Override
    protected Coordinate[][] getShapes() {
        return SHAPES;
    }

    @Override
    protected Tetromino createSameType(int x, int y) {
        return new LTetromino(x, y);
    }
}