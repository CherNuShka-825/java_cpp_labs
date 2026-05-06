package ru.nsu.ccfit.kombarov.tetris.model.factory;

import org.junit.jupiter.api.Test;
import ru.nsu.ccfit.kombarov.tetris.exceptions.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.Tetromino;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TetrominoFactoryTest {

    private static final String CONFIG_PATH =
            "ru/nsu/ccfit/kombarov/tetris/model/factory/test-tetromino-factory.properties";

    private static final String MISSING_CONFIG_PATH = "missing-config.properties";

    private static final String UNKNOWN_CLASS_NAME =
            "ru.nsu.ccfit.kombarov.tetris.model.tetromino.shapes.UnknownTetromino";

    private TetrominoFactory createFactory() throws FactoryException {
        return new TetrominoFactory(CONFIG_PATH);
    }

    @Test
    void constructorShouldLoadConfigAndReturnAvailableTypes() throws FactoryException {
        TetrominoFactory factory = createFactory();

        Set<String> types = factory.getAvailableTypes();

        assertEquals(5, types.size());
        assertTrue(types.contains("I"));
        assertTrue(types.contains("anotherType"));
        assertTrue(types.contains("unknownClass"));
        assertTrue(types.contains("badConstructor"));
        assertTrue(types.contains("notTetromino"));
    }

    @Test
    void constructorShouldThrowExceptionWhenConfigFileNotFound() {
        FactoryException exception = assertThrows(
                FactoryException.class,
                () -> new TetrominoFactory(MISSING_CONFIG_PATH)
        );

        assertEquals(
                "Config file not found: " + MISSING_CONFIG_PATH,
                exception.getMessage()
        );
    }

    @Test
    void createShouldCreateTetrominoByType() throws FactoryException {
        TetrominoFactory factory = createFactory();

        Tetromino tetromino = factory.create("I", 3, 5);

        assertNotNull(tetromino);
        assertEquals("I", tetromino.getType());
    }

    @Test
    void createShouldThrowExceptionWhenTypeIsUnknown() throws FactoryException {
        TetrominoFactory factory = createFactory();

        FactoryException exception = assertThrows(
                FactoryException.class,
                () -> factory.create("Z", 0, 0)
        );

        assertEquals("Unknown tetromino type: Z", exception.getMessage());
    }

    @Test
    void createShouldThrowExceptionWhenClassNotFound() throws FactoryException {
        TetrominoFactory factory = createFactory();

        FactoryException exception = assertThrows(
                FactoryException.class,
                () -> factory.create("unknownClass", 0, 0)
        );

        assertEquals(
                "Class not found: " + UNKNOWN_CLASS_NAME,
                exception.getMessage()
        );
        assertNotNull(exception.getCause());
        assertEquals(ClassNotFoundException.class, exception.getCause().getClass());
    }

    @Test
    void createShouldThrowExceptionWhenConstructorIsInvalid() throws FactoryException {
        TetrominoFactory factory = createFactory();

        FactoryException exception = assertThrows(
                FactoryException.class,
                () -> factory.create("badConstructor", 0, 0)
        );

        assertEquals(
                "Cannot create tetromino: " + BadConstructor.class.getName(),
                exception.getMessage()
        );
        assertNotNull(exception.getCause());
        assertEquals(NoSuchMethodException.class, exception.getCause().getClass());
    }

    @Test
    void createShouldThrowExceptionWhenClassIsNotTetromino() throws FactoryException {
        TetrominoFactory factory = createFactory();

        FactoryException exception = assertThrows(
                FactoryException.class,
                () -> factory.create("notTetromino", 0, 0)
        );

        assertEquals(
                "Class is not Tetromino: " + NotTetromino.class.getName(),
                exception.getMessage()
        );
        assertNotNull(exception.getCause());
        assertEquals(ClassCastException.class, exception.getCause().getClass());
    }

    public static class BadConstructor {

        public BadConstructor() {
        }
    }

    public static class NotTetromino {

        public NotTetromino(int x, int y) {
        }
    }
}