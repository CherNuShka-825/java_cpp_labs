package ru.nsu.ccfit.kombarov.model.factory.dealer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.ccfit.kombarov.model.factory.config.FactoryDelays;
import ru.nsu.ccfit.kombarov.model.factory.controller.AutoStorageController;
import ru.nsu.ccfit.kombarov.model.factory.entity.Accessory;
import ru.nsu.ccfit.kombarov.model.factory.entity.Auto;
import ru.nsu.ccfit.kombarov.model.factory.entity.Body;
import ru.nsu.ccfit.kombarov.model.factory.entity.Motor;
import ru.nsu.ccfit.kombarov.model.factory.log.SaleLogger;
import ru.nsu.ccfit.kombarov.model.factory.statistics.FactoryStatistics;
import ru.nsu.ccfit.kombarov.model.factory.storage.Storage;
import ru.nsu.ccfit.kombarov.model.threadpool.ThreadPool;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {

    @TempDir
    Path tempDir;

    private Storage<Body> bodyStorage;
    private Storage<Motor> motorStorage;
    private Storage<Accessory> accessoryStorage;
    private Storage<Auto> autoStorage;
    private FactoryStatistics statistics;
    private AutoStorageController controller;

    @BeforeEach
    void setUp() {
        FactoryDelays.init(
                0,
                0,
                0,
                0,
                0
        );

        bodyStorage = new Storage<>(10);
        motorStorage = new Storage<>(10);
        accessoryStorage = new Storage<>(10);
        autoStorage = new Storage<>(10);

        statistics = new FactoryStatistics();

        controller = new AutoStorageController(
                bodyStorage,
                motorStorage,
                accessoryStorage,
                autoStorage,
                new ThreadPool(1),
                statistics
        );
    }

    @Test
    void runShouldTakeAutoFromStorageAndIncrementSoldAutos() throws Exception {
        Auto auto = new Auto(new Body(), new Motor(), new Accessory());
        autoStorage.put(auto);

        SaleLogger logger = new SaleLogger(tempDir.resolve("sales.log"), false);

        Dealer dealer = new Dealer(
                1,
                autoStorage,
                statistics,
                logger,
                controller
        );

        Thread thread = new Thread(dealer);

        thread.start();

        assertTimeout(Duration.ofSeconds(1), () -> {
            while (statistics.getSoldAutos() == 0) {
                Thread.sleep(1);
            }
        });

        thread.interrupt();
        thread.join(1000);

        logger.close();

        assertFalse(thread.isAlive());
        assertEquals(0, autoStorage.getSize());
        assertEquals(1, statistics.getSoldAutos());
    }

    @Test
    void runShouldStopWhenInterruptedDuringSleep() throws IOException, InterruptedException {
        FactoryDelays.init(
                0,
                0,
                0,
                10_000,
                0
        );

        SaleLogger logger = new SaleLogger(tempDir.resolve("sales.log"), false);

        Dealer dealer = new Dealer(
                1,
                autoStorage,
                statistics,
                logger,
                controller
        );

        Thread thread = new Thread(dealer);

        thread.start();
        Thread.sleep(50);
        thread.interrupt();
        thread.join(1000);

        logger.close();

        assertFalse(thread.isAlive());
        assertEquals(0, statistics.getSoldAutos());
        assertEquals(0, autoStorage.getSize());
    }
}