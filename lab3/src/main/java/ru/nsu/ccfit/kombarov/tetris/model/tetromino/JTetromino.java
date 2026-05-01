package ru.nsu.ccfit.kombarov.tetris.model.tetromino;

import ru.nsu.ccfit.kombarov.tetris.model.Coordinate;

public class JTetromino extends Tetromino {

    private static final Coordinate[][] SHAPES = {
            {
                    new Coordinate(0, 0),
                    new Coordinate(1, 0),
                    new Coordinate(1, 1),
                    new Coordinate(1, 2)
            },
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
            }
    };

    public JTetromino(int x, int y) {
        super(TetrominoType.J, x, y);
    }

    @Override
    protected Coordinate[][] getShapes() {
        return SHAPES;
    }
}