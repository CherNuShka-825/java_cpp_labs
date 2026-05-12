package ru.nsu.ccfit.kombarov.model.factory.worker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ccfit.kombarov.model.factory.config.FactoryDelays;
import ru.nsu.ccfit.kombarov.model.factory.controller.AutoStorageController;
import ru.nsu.ccfit.kombarov.model.factory.entity.Accessory;
import ru.nsu.ccfit.kombarov.model.factory.entity.Auto;
import ru.nsu.ccfit.kombarov.model.factory.entity.Body;
import ru.nsu.ccfit.kombarov.model.factory.entity.Motor;
import ru.nsu.ccfit.kombarov.model.factory.statistics.FactoryStatistics;
import ru.nsu.ccfit.kombarov.model.factory.storage.Storage;
import ru.nsu.ccfit.kombarov.model.threadpool.ThreadPool;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class BuildAutoTaskTest {

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
    void runShouldBuildAutoFromParts() throws InterruptedException {
        Body body = new Body();
        Motor motor = new Motor();
        Accessory accessory = new Accessory();

        bodyStorage.put(body);
        motorStorage.put(motor);
        accessoryStorage.put(accessory);

        BuildAutoTask task = new BuildAutoTask(
                bodyStorage,
                motorStorage,
                accessoryStorage,
                autoStorage,
                statistics,
                controller
        );

        assertTimeout(Duration.ofSeconds(1), task::run);

        assertEquals(0, bodyStorage.getSize());
        assertEquals(0, motorStorage.getSize());
        assertEquals(0, accessoryStorage.getSize());

        assertEquals(1, autoStorage.getSize());
        assertEquals(1, statistics.getProducedAutos());

        Auto auto = autoStorage.take();

        assertSame(body, auto.getBody());
        assertSame(motor, auto.getMotor());
        assertSame(accessory, auto.getAccessory());

        assertEquals(-1, controller.getPendingAutos());
    }

    @Test
    void runShouldStopWhenInterruptedDuringSleep() throws InterruptedException {
        FactoryDelays.init(
                0,
                0,
                0,
                0,
                10_000
        );

        BuildAutoTask task = new BuildAutoTask(
                bodyStorage,
                motorStorage,
                accessoryStorage,
                autoStorage,
                statistics,
                controller
        );

        Thread thread = new Thread(task);

        thread.start();
        thread.interrupt();
        thread.join(1000);

        assertFalse(thread.isAlive());
        assertEquals(0, autoStorage.getSize());
        assertEquals(0, statistics.getProducedAutos());
    }
}