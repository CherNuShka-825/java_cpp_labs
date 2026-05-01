package ru.nsu.ccfit.kombarov.tetris.model.tetromino;

import ru.nsu.ccfit.kombarov.tetris.exceptions.model.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.exceptions.model.TetrominoExeption;
import ru.nsu.ccfit.kombarov.tetris.model.facrory.TetrominoFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TetrominoGenerator {

    private final TetrominoFactory factory;
    private final List<String> tetrominoBag = new ArrayList<>();
    private String lastTetrominoType;

    public TetrominoGenerator(TetrominoFactory factory) {
        this.factory = factory;
    }

    public Tetromino oneFromBag(int x, int y) throws TetrominoExeption, FactoryException {
        if (tetrominoBag.isEmpty()) {
            fillTetrominoBag();
        }

        String type = tetrominoBag.removeLast();

        return factory.create(type, x, y);
    }

    private void fillTetrominoBag() throws TetrominoExeption {
        tetrominoBag.addAll(factory.getAvailableTypes());

        if (tetrominoBag.isEmpty()) {
            throw new TetrominoExeption("Tetromino bag is empty");
        }

        shuffleBag();

        lastTetrominoType = tetrominoBag.getFirst();
    }

    private void shuffleBag() {
        if (lastTetrominoType == null || tetrominoBag.size() == 1) {
            Collections.shuffle(tetrominoBag);
            return;
        }

        do {
            Collections.shuffle(tetrominoBag);
        } while (tetrominoBag.getLast().equals(lastTetrominoType));
    }
}