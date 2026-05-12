package ru.nsu.ccfit.kombarov.model.factory;

import ru.nsu.ccfit.kombarov.model.factory.config.FactoryConfig;
import ru.nsu.ccfit.kombarov.model.factory.config.FactoryDelays;
import ru.nsu.ccfit.kombarov.model.factory.controller.AutoStorageController;
import ru.nsu.ccfit.kombarov.model.factory.dealer.Dealer;
import ru.nsu.ccfit.kombarov.model.factory.entity.*;
import ru.nsu.ccfit.kombarov.model.factory.log.SaleLogger;
import ru.nsu.ccfit.kombarov.model.factory.statistics.FactoryStatistics;
import ru.nsu.ccfit.kombarov.model.factory.storage.Storage;
import ru.nsu.ccfit.kombarov.model.factory.supplier.PartSupplier;
import ru.nsu.ccfit.kombarov.model.threadpool.ThreadPool;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class Factory {

    private final FactoryConfig config;

    private final Storage<Body> bodyStorage;
    private final Storage<Motor> motorStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Auto> autoStorage;

    private final FactoryStatistics statistics;

    private final ThreadPool threadPool;
    private final AutoStorageController autoStorageController;
    private final SaleLogger saleLogger;

    private final List<Thread> threads = new ArrayList<>();

    public Factory(FactoryConfig config) throws IOException {
        this.config = config;

        FactoryDelays.init(
                config.getBodySupplierDelayMs(),
                config.getMotorSupplierDelayMs(),
                config.getAccessorySupplierDelayMs(),
                config.getDealerDelayMs(),
                config.getWorkerDelayMs()
        );

        bodyStorage = new Storage<>(config.getBodyStorageSize());
        motorStorage = new Storage<>(config.getMotorStorageSize());
        accessoryStorage = new Storage<>(config.getAccessoryStorageSize());
        autoStorage = new Storage<>(config.getAutoStorageSize());

        statistics = new FactoryStatistics();

        threadPool = new ThreadPool(config.getWorkers());

        saleLogger = new SaleLogger(
                Path.of("sales.log"),
                config.isLogSale()
        );

        autoStorageController = new AutoStorageController(
                bodyStorage,
                motorStorage,
                accessoryStorage,
                autoStorage,
                threadPool,
                statistics
        );

        createSuppliers();
        createDealers();
    }

    public void start() {
        threadPool.start();

        Thread controllerThread = new Thread(
                autoStorageController,
                "AutoStorageController"
        );

        threads.add(controllerThread);

        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }

        threadPool.shutdown();

        saleLogger.close();
    }

    public FactoryStatistics getStatistics() {
        return statistics;
    }

    public Storage<Body> getBodyStorage() {
        return bodyStorage;
    }

    public Storage<Motor> getMotorStorage() {
        return motorStorage;
    }

    public Storage<Accessory> getAccessoryStorage() {
        return accessoryStorage;
    }

    public Storage<Auto> getAutoStorage() {
        return autoStorage;
    }

    public ThreadPool getThreadPool() {
        return threadPool;
    }

    private void createSuppliers() {
        for (int i = 0; i < config.getBodySuppliers(); i++) {
            PartSupplier<Body> supplier = new PartSupplier<>(
                    bodyStorage,
                    Body::new,
                    FactoryDelays::getBodySupplierDelayMs,
                    statistics::incrementProducedBodies
            );

            threads.add(new Thread(
                    supplier,
                    "BodySupplier-" + (i + 1)
            ));
        }

        for (int i = 0; i < config.getMotorSuppliers(); i++) {
            PartSupplier<Motor> supplier = new PartSupplier<>(
                    motorStorage,
                    Motor::new,
                    FactoryDelays::getMotorSupplierDelayMs,
                    statistics::incrementProducedMotors
            );

            threads.add(new Thread(
                    supplier,
                    "MotorSupplier-" + (i + 1)
            ));
        }

        for (int i = 0; i < config.getAccessorySuppliers(); i++) {
            PartSupplier<Accessory> supplier = new PartSupplier<>(
                    accessoryStorage,
                    Accessory::new,
                    FactoryDelays::getAccessorySupplierDelayMs,
                    statistics::incrementProducedAccessories
            );

            threads.add(new Thread(
                    supplier,
                    "AccessorySupplier-" + (i + 1)
            ));
        }
    }

    private void createDealers() {
        for (int i = 0; i < config.getDealers(); i++) {
            Dealer dealer = new Dealer(
                    i + 1,
                    autoStorage,
                    statistics,
                    saleLogger,
                    autoStorageController
            );

            threads.add(new Thread(
                    dealer,
                    "Dealer-" + (i + 1)
            ));
        }
    }
}