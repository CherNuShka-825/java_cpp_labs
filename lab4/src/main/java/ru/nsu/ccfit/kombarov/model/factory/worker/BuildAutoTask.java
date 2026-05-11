package ru.nsu.ccfit.kombarov.model.factory.worker;

import ru.nsu.ccfit.kombarov.model.factory.controller.AutoStorageController;
import ru.nsu.ccfit.kombarov.model.factory.entity.Accessory;
import ru.nsu.ccfit.kombarov.model.factory.entity.Auto;
import ru.nsu.ccfit.kombarov.model.factory.entity.Body;
import ru.nsu.ccfit.kombarov.model.factory.entity.Motor;
import ru.nsu.ccfit.kombarov.model.factory.statistics.FactoryStatistics;
import ru.nsu.ccfit.kombarov.model.factory.storage.Storage;
import ru.nsu.ccfit.kombarov.model.factory.supplier.PartSupplier.DelayProvider;

public final class BuildAutoTask implements Runnable {

    private final Storage<Body> bodyStorage;
    private final Storage<Motor> motorStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Auto> autoStorage;

    private final FactoryStatistics statistics;

    private final AutoStorageController controller;

    private final DelayProvider delayProvider;

    public BuildAutoTask(
            Storage<Body> bodyStorage,
            Storage<Motor> motorStorage,
            Storage<Accessory> accessoryStorage,
            Storage<Auto> autoStorage,
            FactoryStatistics statistics,
            AutoStorageController controller,
            DelayProvider delayProvider
    ) {
        this.bodyStorage = bodyStorage;
        this.motorStorage = motorStorage;
        this.accessoryStorage = accessoryStorage;
        this.autoStorage = autoStorage;

        this.statistics = statistics;
        this.controller = controller;

        this.delayProvider = delayProvider;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(delayProvider.getDelayMs());

            Body body = bodyStorage.take();
            Motor motor = motorStorage.take();
            Accessory accessory = accessoryStorage.take();

            Auto auto = new Auto(body, motor, accessory);

            autoStorage.put(auto);

            statistics.incrementProducedAutos();

            controller.autoBuilt();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}