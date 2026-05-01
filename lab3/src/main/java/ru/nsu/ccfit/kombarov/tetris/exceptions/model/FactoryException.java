package ru.nsu.ccfit.kombarov.tetris.exceptions.model;

public class FactoryException extends ModelException{

    public FactoryException(String messege) {
        super(messege);
    }

    public FactoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
