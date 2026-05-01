package ru.nsu.ccfit.kombarov.tetris.exceptions.model;

public class TetrominoExeption extends ModelException {

    public TetrominoExeption(String message) {
        super(message);
    }

    public TetrominoExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
