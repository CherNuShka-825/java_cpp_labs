package ru.nsu.ccfit.kombarov.tetris.model.tetromino;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.nsu.ccfit.kombarov.tetris.exceptions.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.exceptions.TetrisException;
import ru.nsu.ccfit.kombarov.tetris.exceptions.TetrominoExeption;
import ru.nsu.ccfit.kombarov.tetris.model.factory.TetrominoFactory;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TetrominoGeneratorTest {

    @Test
    void oneFromBagShouldCreateTetrominoFromFactory() throws FactoryException {
        TetrominoFactory factory = mock(TetrominoFactory.class);
        Tetromino tetromino = mock(Tetromino.class);

        when(factory.getAvailableTypes()).thenReturn(Set.of("I"));
        when(factory.create("I", 0, 0)).thenReturn(tetromino);

        TetrominoGenerator generator = new TetrominoGenerator(factory);

        Tetromino result = generator.oneFromBag();

        assertSame(tetromino, result);

        verify(factory, times(1)).getAvailableTypes();
        verify(factory, times(1)).create("I", 0, 0);
    }

    @Test
    void oneFromBagShouldUseAllTypesFromBagBeforeRefill() throws FactoryException {
        TetrominoFactory factory = mock(TetrominoFactory.class);
        Tetromino tetromino = mock(Tetromino.class);

        when(factory.getAvailableTypes()).thenReturn(Set.of("I", "O", "T"));
        when(factory.create(anyString(), eq(0), eq(0))).thenReturn(tetromino);

        TetrominoGenerator generator = new TetrominoGenerator(factory);

        generator.oneFromBag();
        generator.oneFromBag();
        generator.oneFromBag();

        ArgumentCaptor<String> typeCaptor = ArgumentCaptor.forClass(String.class);

        verify(factory, times(1)).getAvailableTypes();
        verify(factory, times(3)).create(typeCaptor.capture(), eq(0), eq(0));

        List<String> createdTypes = typeCaptor.getAllValues();

        assertEquals(3, createdTypes.size());
        assertTrue(createdTypes.contains("I"));
        assertTrue(createdTypes.contains("O"));
        assertTrue(createdTypes.contains("T"));
    }

    @Test
    void oneFromBagShouldRefillBagWhenItBecomesEmpty() throws FactoryException {
        TetrominoFactory factory = mock(TetrominoFactory.class);
        Tetromino tetromino = mock(Tetromino.class);

        when(factory.getAvailableTypes()).thenReturn(Set.of("I", "O"));
        when(factory.create(anyString(), eq(0), eq(0))).thenReturn(tetromino);

        TetrominoGenerator generator = new TetrominoGenerator(factory);

        generator.oneFromBag();
        generator.oneFromBag();
        generator.oneFromBag();

        verify(factory, times(2)).getAvailableTypes();
        verify(factory, times(3)).create(anyString(), eq(0), eq(0));
    }

    @Test
    void oneFromBagShouldThrowTetrisExceptionWhenFactoryThrowsException() throws FactoryException {
        TetrominoFactory factory = mock(TetrominoFactory.class);

        FactoryException factoryException = new FactoryException("Cannot create tetromino");

        when(factory.getAvailableTypes()).thenReturn(Set.of("I"));
        when(factory.create("I", 0, 0)).thenThrow(factoryException);

        TetrominoGenerator generator = new TetrominoGenerator(factory);

        TetrisException exception = assertThrows(
                TetrisException.class,
                generator::oneFromBag
        );

        assertEquals("Generator failed", exception.getMessage());
        assertSame(factoryException, exception.getCause());
    }

    @Test
    void oneFromBagShouldThrowTetrisExceptionWhenAvailableTypesAreEmpty() {
        TetrominoFactory factory = mock(TetrominoFactory.class);

        when(factory.getAvailableTypes()).thenReturn(Set.of());

        TetrominoGenerator generator = new TetrominoGenerator(factory);

        TetrisException exception = assertThrows(
                TetrisException.class,
                generator::oneFromBag
        );

        assertEquals("Generator failed", exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals(TetrominoExeption.class, exception.getCause().getClass());
        assertEquals("Tetromino bag is empty", exception.getCause().getMessage());

        verify(factory, times(1)).getAvailableTypes();
        verify(factory, never()).create(anyString(), anyInt(), anyInt());
    }
}