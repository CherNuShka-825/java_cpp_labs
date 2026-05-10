package ru.nsu.ccfit.kombarov.model.factory.dealer;

import ru.nsu.ccfit.kombarov.model.factory.controller.AutoStorageController;
import ru.nsu.ccfit.kombarov.model.factory.entity.Auto;
import ru.nsu.ccfit.kombarov.model.factory.log.SaleLogger;
import ru.nsu.ccfit.kombarov.model.factory.statistics.FactoryStatistics;
import ru.nsu.ccfit.kombarov.model.factory.storage.Storage;
import ru.nsu.ccfit.kombarov.model.factory.supplier.PartSupplier.DelayProvider;

public final class Dealer implements Runnable {

    private final int id;
    private final Storage<Auto> autoStorage;
    private final DelayProvider delayProvider;
    private final FactoryStatistics statistics;
    private final SaleLogger saleLogger;
    private final AutoStorageController autoStorageController;

    public Dealer(
            int id,
            Storage<Auto> autoStorage,
            DelayProvider delayProvider,
            FactoryStatistics statistics,
            SaleLogger saleLogger,
            AutoStorageController autoStorageController
    ) {
        this.id = id;
        this.autoStorage = autoStorage;
        this.delayProvider = delayProvider;
        this.statistics = statistics;
        this.saleLogger = saleLogger;
        this.autoStorageController = autoStorageController;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(delayProvider.getDelayMs());

                Auto auto = autoStorage.take();

                autoStorageController.notifyAutoTaken();

                saleLogger.logSale(id, auto);
                statistics.incrementSoldAutos();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}