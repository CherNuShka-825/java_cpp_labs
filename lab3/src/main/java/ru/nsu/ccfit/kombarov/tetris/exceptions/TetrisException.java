package ru.nsu.ccfit.kombarov.tetris.exceptions;

public class TetrisException extends RuntimeException {

    public TetrisException(String message) {
        super(message);
    }

    public TetrisException(String message, Throwable cause) {
        super(message, cause);
    }
}
