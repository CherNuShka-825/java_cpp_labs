package ru.nsu.ccfit.kombarov.tetris.model.tetromino;

import ru.nsu.ccfit.kombarov.tetris.exceptions.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.exceptions.TetrisException;
import ru.nsu.ccfit.kombarov.tetris.exceptions.TetrominoExeption;
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

    public Tetromino oneFromBag() {
        try {
            if (tetrominoBag.isEmpty()) {
                fillTetrominoBag();
            }
            String type = tetrominoBag.removeLast();
            return factory.create(type, 0, 0);

        } catch (FactoryException | TetrominoExeption e) {
            throw new TetrisException("Generator failed", e);
        }
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