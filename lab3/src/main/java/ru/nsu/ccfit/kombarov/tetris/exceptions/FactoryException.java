package ru.nsu.ccfit.kombarov.tetris.exceptions;

public class FactoryException extends TetrisException {

    public FactoryException(String messege) {
        super(messege);
    }

    public FactoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
