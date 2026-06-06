package ru.nsu.ccfit.kombarov.tetris.model.tetromino.shapes;

import ru.nsu.ccfit.kombarov.tetris.model.Coordinate;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;

public class OTetromino extends Tetromino {

    private static final Coordinate[][] SHAPES = {
            {
                    new Coordinate(1, 0),
                    new Coordinate(2, 0),
                    new Coordinate(1, 1),
                    new Coordinate(2, 1)
            }
    };

    public OTetromino(int x, int y) {
        super("O", x, y);
    }

    @Override
    protected Coordinate[][] getShapes() {
        return SHAPES;
    }

    @Override
    protected Tetromino createSameType(int x, int y) {
        return new OTetromino(x, y);
    }
}