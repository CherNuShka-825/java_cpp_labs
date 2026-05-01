package ru.nsu.ccfit.kombarov.tetris;

import ru.nsu.ccfit.kombarov.tetris.exceptions.model.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.model.facrory.TetrominoFactory;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;

public class Main {
    public static void main(String[] args) {
        try {
            TetrominoFactory factory = new TetrominoFactory("ru/nsu/ccfit/kombarov/tetris/tetromino.properties");

            Tetromino i = factory.create("I", 3, 0);

            System.out.println(i.getClass().getSimpleName());
            System.out.println(i.getType());
            System.out.println(i.getX());
            System.out.println(i.getY());
            System.out.println(i.getBlocks());

        } catch (FactoryException e) {
            e.printStackTrace();
        }
    }
}