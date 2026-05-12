package ru.nsu.ccfit.kombarov.model.factory.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactoryConfigTest {

    @Test
    void constructorShouldSaveAllValues() {
        FactoryConfig config = new FactoryConfig(
                10,
                11,
                12,
                13,
                1,
                2,
                3,
                4,
                5,
                100,
                200,
                300,
                400,
                500,
                true
        );

        assertEquals(10, config.getBodyStorageSize());
        assertEquals(11, config.getMotorStorageSize());
        assertEquals(12, config.getAccessoryStorageSize());
        assertEquals(13, config.getAutoStorageSize());

        assertEquals(1, config.getBodySuppliers());
        assertEquals(2, config.getMotorSuppliers());
        assertEquals(3, config.getAccessorySuppliers());

        assertEquals(4, config.getWorkers());
        assertEquals(5, config.getDealers());

        assertEquals(100, config.getBodySupplierDelayMs());
        assertEquals(200, config.getMotorSupplierDelayMs());
        assertEquals(300, config.getAccessorySupplierDelayMs());
        assertEquals(400, config.getDealerDelayMs());
        assertEquals(500, config.getWorkerDelayMs());

        assertTrue(config.isLogSale());
    }

    @Test
    void constructorShouldSaveFalseLogSale() {
        FactoryConfig config = new FactoryConfig(
                10,
                11,
                12,
                13,
                1,
                2,
                3,
                4,
                5,
                100,
                200,
                300,
                400,
                500,
                false
        );

        assertFalse(config.isLogSale());
    }
}