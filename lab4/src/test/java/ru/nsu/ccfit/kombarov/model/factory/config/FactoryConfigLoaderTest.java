package ru.nsu.ccfit.kombarov.model.factory.config;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FactoryConfigLoaderTest {

    private static final String RESOURCE_DIR =
            "ru/nsu/ccfit/kombarov/model/factory/config/";

    @Test
    void loadShouldReadValidConfigFromResources() throws IOException {
        FactoryConfigLoader loader = new FactoryConfigLoader();

        FactoryConfig config = loader.load(RESOURCE_DIR + "factory-test.properties");

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
    void loadShouldThrowIOExceptionWhenConfigFileDoesNotExist() {
        FactoryConfigLoader loader = new FactoryConfigLoader();

        IOException exception = assertThrows(
                IOException.class,
                () -> loader.load(RESOURCE_DIR + "missing-config.properties")
        );

        assertEquals(
                "Config file not found: " + RESOURCE_DIR + "missing-config.properties",
                exception.getMessage()
        );
    }

    @Test
    void loadShouldThrowIllegalArgumentExceptionWhenIntPropertyIsMissing() {
        FactoryConfigLoader loader = new FactoryConfigLoader();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> loader.load(RESOURCE_DIR + "factory-missing-int.properties")
        );

        assertEquals(
                "Missing property: BodyStorageSize",
                exception.getMessage()
        );
    }

    @Test
    void loadShouldThrowIllegalArgumentExceptionWhenBooleanPropertyIsMissing() {
        FactoryConfigLoader loader = new FactoryConfigLoader();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> loader.load(RESOURCE_DIR + "factory-missing-boolean.properties")
        );

        assertEquals(
                "Missing property: LogSale",
                exception.getMessage()
        );
    }
}