package ru.nsu.ccfit.kombarov.tetris.exceptions;

public class TetrominoExeption extends TetrisException {

    public TetrominoExeption(String message) {
        super(message);
    }

    public TetrominoExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
