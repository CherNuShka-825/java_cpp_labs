package ru.nsu.ccfit.kombarov.model.factory.log;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.ccfit.kombarov.model.factory.entity.Accessory;
import ru.nsu.ccfit.kombarov.model.factory.entity.Auto;
import ru.nsu.ccfit.kombarov.model.factory.entity.Body;
import ru.nsu.ccfit.kombarov.model.factory.entity.Motor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class SaleLoggerTest {

    @TempDir
    Path tempDir;

    @Test
    void logSaleShouldWriteSaleInfoWhenEnabled() throws IOException {
        Path logPath = tempDir.resolve("sales.log");

        Body body = new Body();
        Motor motor = new Motor();
        Accessory accessory = new Accessory();
        Auto auto = new Auto(body, motor, accessory);

        SaleLogger logger = new SaleLogger(logPath, true);

        logger.logSale(7, auto);
        logger.close();

        String content = Files.readString(logPath);

        assertTrue(content.contains("Dealer 7"));
        assertTrue(content.contains("Auto " + auto.getId()));
        assertTrue(content.contains("Body: " + body.getId()));
        assertTrue(content.contains("Motor: " + motor.getId()));
        assertTrue(content.contains("Accessory: " + accessory.getId()));
    }

    @Test
    void logSaleShouldDoNothingWhenDisabled() throws IOException {
        Path logPath = tempDir.resolve("sales.log");

        Body body = new Body();
        Motor motor = new Motor();
        Accessory accessory = new Accessory();
        Auto auto = new Auto(body, motor, accessory);

        SaleLogger logger = new SaleLogger(logPath, false);

        logger.logSale(1, auto);
        logger.close();

        assertFalse(Files.exists(logPath));
    }

    @Test
    void constructorShouldThrowIOExceptionForInvalidPath() {
        Path invalidPath = tempDir;

        assertThrows(IOException.class, () -> new SaleLogger(invalidPath, true));
    }

    @Test
    void closeShouldBeSafeWhenDisabled() throws IOException {
        Path logPath = tempDir.resolve("sales.log");

        SaleLogger logger = new SaleLogger(logPath, false);

        assertDoesNotThrow(logger::close);
    }
}