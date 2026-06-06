package ru.nsu.ccfit.kombarov.tetris.model.factory;

import ru.nsu.ccfit.kombarov.tetris.exceptions.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.model.board.Board;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

public class TetrominoFactory {

    private static final Logger logger = Logger.getLogger(TetrominoFactory.class.getName());

    private final Properties config = new Properties();

    public TetrominoFactory(String configFile) throws FactoryException {
        try (InputStream is = TetrominoFactory.class.getClassLoader().getResourceAsStream(configFile)) {
            if (is == null) {
                logger.severe("Config file not found: " + configFile);
                throw new FactoryException("Config file not found: " + configFile);
            }
            config.load(is);
            logger.info("Config loaded successfully");
            logger.fine("Loaded command: " + config);
        } catch (IOException e) {
            logger.severe("Failed to load config" + e.getMessage());
            throw new FactoryException("Failed to load config", e);
        }
    }

    public Tetromino create(String type, int x, int y) throws FactoryException {
        logger.info("Request tetromino type: " + type);
        String className = config.getProperty(type);

        if (className == null) {
            logger.warning("Unknown tetromino type: " + type);
            throw new FactoryException("Unknown tetromino type: " + type);
        }

        try {
            Class<?> clazz = Class.forName(className);
            Object obj = clazz.getDeclaredConstructor(int.class, int.class).newInstance(x, y);
            logger.info("Tetromino instance created: " + type);
            return (Tetromino) obj;

        } catch (ClassNotFoundException e) {
            logger.severe("Class not found: " + className);
            throw new FactoryException("Class not found: " + className, e);

        } catch (ReflectiveOperationException e) {
            logger.severe("Cannot create tetromino: " + className);
            throw new FactoryException("Cannot create tetromino: " + className, e);

        } catch (ClassCastException e) {
            logger.severe("Class is not Tetromino: " + className);
            throw new FactoryException("Class is not Tetromino: " + className, e);
        }
    }

    public Set<String> getAvailableTypes() {
        return config.stringPropertyNames();
    }
}
