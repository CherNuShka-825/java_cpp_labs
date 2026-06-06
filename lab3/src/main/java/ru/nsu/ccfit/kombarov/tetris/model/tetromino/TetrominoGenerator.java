package ru.nsu.ccfit.kombarov.tetris.model.tetromino;

import ru.nsu.ccfit.kombarov.tetris.exceptions.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.exceptions.TetrisException;
import ru.nsu.ccfit.kombarov.tetris.exceptions.TetrominoExeption;
import ru.nsu.ccfit.kombarov.tetris.model.factory.TetrominoFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class TetrominoGenerator {

    private static final Logger logger = Logger.getLogger(TetrominoGenerator.class.getName());

    private final TetrominoFactory factory;
    private final List<String> tetrominoBag = new ArrayList<>();
    private String lastTetrominoType;

    public TetrominoGenerator(TetrominoFactory factory) {
        logger.info("created Tetromino Generator");
        this.factory = factory;
    }

    public Tetromino oneFromBag() {
        try {
            if (tetrominoBag.isEmpty()) {
                fillTetrominoBag();
            }
            String type = tetrominoBag.removeLast();

            logger.info("try to create tetromino");
            return factory.create(type, 0, 0);

        } catch (FactoryException | TetrominoExeption e) {
            logger.severe("Generator failed");
            throw new TetrisException("Generator failed", e);
        }
    }

    private void fillTetrominoBag() throws TetrominoExeption {
        tetrominoBag.addAll(factory.getAvailableTypes());

        if (tetrominoBag.isEmpty()) {
            logger.severe("Tetromino bag is empty");
            throw new TetrominoExeption("Tetromino bag is empty");
        }

        shuffleBag();

        lastTetrominoType = tetrominoBag.getFirst();
        logger.fine("fill Tetromino Bag");
    }

    private void shuffleBag() {
        if (lastTetrominoType == null || tetrominoBag.size() == 1) {
            Collections.shuffle(tetrominoBag);
            return;
        }

        do {
            Collections.shuffle(tetrominoBag);
        } while (tetrominoBag.getLast().equals(lastTetrominoType));

        logger.finest("shuffle Bag");
    }
}