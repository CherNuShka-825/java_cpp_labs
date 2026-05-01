package ru.nsu.ccfit.kombarov.tetris.model.facrory;

import ru.nsu.ccfit.kombarov.tetris.exceptions.model.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class TetrominoFactory {

    private final Properties config = new Properties();

    public TetrominoFactory(String configFile) throws FactoryException {
        try (InputStream is = TetrominoFactory.class.getClassLoader().getResourceAsStream(configFile)) {
            if (is == null) {
                throw new FactoryException("Config file not found: " + configFile);
            }
            config.load(is);
        } catch (IOException e) {
            throw new FactoryException("Failed to load config", e);
        }
    }

    public Tetromino create(String type, int x, int y) throws FactoryException {
        String className = config.getProperty(type);

        if (className == null) {
            throw new FactoryException("Unknown tetromino type: " + type);
        }

        try {
            Class<?> clazz = Class.forName(className);
            Object obj = clazz.getDeclaredConstructor(int.class, int.class).newInstance(x, y);
            return (Tetromino) obj;

        } catch (ClassNotFoundException e) {
            throw new FactoryException("Class not found: " + className, e);

        } catch (ReflectiveOperationException e) {
            throw new FactoryException("Cannot create tetromino: " + className, e);

        } catch (ClassCastException e) {
            throw new FactoryException("Class is not Tetromino: " + className, e);
        }
    }

    public Set<String> getAvailableTypes() {
        return config.stringPropertyNames();
    }
}
