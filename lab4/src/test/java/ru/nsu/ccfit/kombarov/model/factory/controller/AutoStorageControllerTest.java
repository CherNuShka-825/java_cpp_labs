package ru.nsu.ccfit.kombarov.model.factory.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ccfit.kombarov.model.factory.entity.Accessory;
import ru.nsu.ccfit.kombarov.model.factory.entity.Auto;
import ru.nsu.ccfit.kombarov.model.factory.entity.Body;
import ru.nsu.ccfit.kombarov.model.factory.entity.Motor;
import ru.nsu.ccfit.kombarov.model.factory.statistics.FactoryStatistics;
import ru.nsu.ccfit.kombarov.model.factory.storage.Storage;
import ru.nsu.ccfit.kombarov.model.threadpool.ThreadPool;

import java.time.Duration;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

class AutoStorageControllerTest {

    private Storage<Body> bodyStorage;
    private Storage<Motor> motorStorage;
    private Storage<Accessory> accessoryStorage;
    private Storage<Auto> autoStorage;
    private FactoryStatistics statistics;
    private ThreadPool threadPool;
    private AutoStorageController controller;

    @BeforeEach
    void setUp() {
        bodyStorage = new Storage<>(10);
        motorStorage = new Storage<>(10);
        accessoryStorage = new Storage<>(10);
        autoStorage = new Storage<>(3);
        statistics = new FactoryStatistics();
        threadPool = new ThreadPool(1);

        controller = new AutoStorageController(
                bodyStorage,
                motorStorage,
                accessoryStorage,
                autoStorage,
                threadPool,
                statistics
        );
    }

    @Test
    void runShouldCreatePendingTasksForFreeAutoStoragePlaces() throws Exception {
        autoStorage.put(createAuto());

        Thread thread = new Thread(controller);

        thread.start();

        waitUntil(() -> controller.getPendingAutos() == 2);

        assertEquals(2, controller.getPendingAutos());

        thread.interrupt();
        thread.join(1000);

        assertFalse(thread.isAlive());
    }

    @Test
    void runShouldNotCreatePendingTasksWhenAutoStorageIsFull() throws Exception {
        autoStorage.put(createAuto());
        autoStorage.put(createAuto());
        autoStorage.put(createAuto());

        Thread thread = new Thread(controller);

        thread.start();

        Thread.sleep(50);

        assertEquals(0, controller.getPendingAutos());

        thread.interrupt();
        thread.join(1000);

        assertFalse(thread.isAlive());
    }

    @Test
    void notifyAutoTakenShouldWakeControllerAndCreateMissingPendingTask() throws Exception {
        autoStorage.put(createAuto());

        Thread thread = new Thread(controller);

        thread.start();

        waitUntil(() -> controller.getPendingAutos() == 2);

        controller.autoBuilt();

        assertEquals(1, controller.getPendingAutos());

        Thread.sleep(50);

        controller.notifyAutoTaken();

        waitUntil(() -> controller.getPendingAutos() == 2);

        thread.interrupt();
        thread.join(1000);

        assertFalse(thread.isAlive());
    }

    @Test
    void autoBuiltShouldDecreasePendingAutos() {
        controller.autoBuilt();

        assertEquals(-1, controller.getPendingAutos());
    }

    private Auto createAuto() {
        return new Auto(new Body(), new Motor(), new Accessory());
    }

    private void waitUntil(BooleanSupplier condition) {
        assertTimeout(Duration.ofSeconds(1), () -> {
            while (!condition.getAsBoolean()) {
                Thread.sleep(1);
            }
        });
    }
}