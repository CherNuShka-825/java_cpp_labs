package ru.nsu.ccfit.kombarov.tetris.model.tetromino.shapes;

import ru.nsu.ccfit.kombarov.tetris.model.Coordinate;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;

public class ITetromino extends Tetromino {

    private static final Coordinate[][] SHAPES = {
            {
                    new Coordinate(0, 1),
                    new Coordinate(1, 1),
                    new Coordinate(2, 1),
                    new Coordinate(3, 1)
            },
            {
                    new Coordinate(2, 0),
                    new Coordinate(2, 1),
                    new Coordinate(2, 2),
                    new Coordinate(2, 3)
            },
            {
                    new Coordinate(0, 2),
                    new Coordinate(1, 2),
                    new Coordinate(2, 2),
                    new Coordinate(3, 2)
            },
            {
                    new Coordinate(1, 0),
                    new Coordinate(1, 1),
                    new Coordinate(1, 2),
                    new Coordinate(1, 3)
            }
    };

    public ITetromino(int x, int y) {
        super(TetrominoType.I, x, y);
    }

    @Override
    protected Coordinate[][] getShapes() {
        return SHAPES;
    }
}